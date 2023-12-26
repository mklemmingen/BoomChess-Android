package com.boomchess.game.frontend.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.frontend.picture.SpeechBubbles;
import com.boomchess.game.frontend.stage.MenuStage;

import java.util.ArrayList;

import static com.boomchess.game.BoomChess.*;

public class AttackSequence {

    private boolean damageSequenceRunning;

    private ArrayList<Actor> attackList;

    private float timePerBreak = 1f;

    private float elapsed;

    private int lengthOfAttackList;

    private int currentIndex;


    // --------------------------------------------------------------------------------------------------

    public AttackSequence(){
        damageSequenceRunning = false;
        attackList = new ArrayList<Actor>();
    }

    public void startSequences(){
        /*
        This method starts off by setting isRunning to true
         */

        if(attackList.isEmpty()){
            // if the checkSurroundings has not found any pieces to attack, switchTurn then return
            continueGame();
            return;
        }

        currentIndex = 0;
        damageSequenceRunning = true;
        lengthOfAttackList = attackList.size();

        actActor();
    }

    public void playNext(float delta){
        /*
        * plays the next sequence stored in the linked list if the first object has reached elapsed > timePerAttack
        * if the linkedlist is empty, sets sequence end
         */

        if (currentIndex >= lengthOfAttackList){

            // if the current index is greater than the length of the attack list,
            // then we are at the end of the sequence

            attackList = new ArrayList<Actor>(); // reset the attackList
            damageSequenceRunning = false;
            // this causes the object to not be called till start turns true

            continueGame();

            System.out.println("Action Sequence has ENDED! All actions have been completed! Cut! \n");

            return;
        }

        elapsed += delta;

        if(elapsed >= timePerBreak) {

            actActor();

            elapsed = 0;
        }
    }

    public void addSequence(Actor actor){
        // add a object
        attackList.add(actor);
    }

    private void actActor(){
        // if statement of instance of either HitMarkerActor or DeathExplosionActor or DottedLineActor

        // this ensures that if the player has gone back to the menu, the sequence will not continue
        if (BoomChess.currentStage instanceof MenuStage){
            // clear all the actors in the attackList
            attackList = new ArrayList<Actor>();
            // clear all the stages
            deathExplosionStage.clear();
            dottedLineStage.clear();
            // clear the botMovingStage
            botMovingStage.clear();
            // clear the speechBubbleStage
            speechBubbleStage.clear();
            // set damageSequenceRunning to false
            damageSequenceRunning = false;
            return;
        }

        if (currentIndex >= lengthOfAttackList){
            // if the current index is greater than the length of the attack list,
            // then we are at the end of the sequence

            attackList = new ArrayList<Actor>(); // reset the attackList
            damageSequenceRunning = false;
            return;
        }

        Actor actorBuddy = attackList.get(currentIndex);

        if (actorBuddy instanceof HitMarkerActor) {
            // if it is a HitMarkerActor, add it to the GameStage
            smallExplosionSound.play(soundVolume);
            deathExplosionStage.addActor(actorBuddy);
            timePerBreak = 1f;
        } else if (actorBuddy instanceof DeathExplosionActor) {
            // if it is a DeathExplosionActor, add it to the deathExplosionStage
            BoomChess.bigExplosionSound.play(BoomChess.soundVolume);
            deathExplosionStage.addActor(actorBuddy);
            // calls upon a function that adds a red-cross above the dead piece till gameStage is reloaded
            BoomChess.addCrossOfDeath(((DeathExplosionActor) actorBuddy).X, ((DeathExplosionActor) actorBuddy).Y);
            timePerBreak = 3f;

            // since we add a speech bubble in damage directly after creating the deathExplosion, we can instantly add
            // the speech bubble to the stage
            currentIndex += 1;
            actorBuddy = attackList.get(currentIndex);
            if (actorBuddy instanceof Bubble) {
                // if it is a Bubble, add it to the speechBubbleStage
                ((Bubble) actorBuddy).makeSound(); // plays radio chatter
                speechBubbleStage.addActor(actorBuddy);
            }
        } else if (actorBuddy instanceof DottedLineActor) {
            // if it is a DottedLineActor, add it to the GameStage
            ((DottedLineActor) actorBuddy).makeSound();
            dottedLineStage.addActor(actorBuddy);
            timePerBreak = 0.6f;
        } else if (actorBuddy instanceof Bubble) {
            // if it is a Bubble, add it to the speechBubbleStage
            ((Bubble) actorBuddy).makeSound(); // plays radio chatter
            speechBubbleStage.addActor(actorBuddy);
            timePerBreak = 0.25f;
        } else {
            // simulate throwing an error for the log
            System.out.println("attackSequence.playNext() has an unknown actor type in the attackList! \n");
        }

        currentIndex += 1;

        // adds an actor to the gameStage that displays "Attack in Progress"!
        // this is to make it clear to the player that the game is still completing a turn, and no moves may be made
        // while the sequence is running
    }

    // --------------------------------------------------------------------------------------------------

    public boolean getDamageSequenceRunning(){
        return damageSequenceRunning;
    }

    private void continueGame(){
        // in the case that the actionSequence were run through, this switches the turn to the next player
        BoomChess.switchTurn(currentState);
        // render the gameBoard as a on-screen update
        reRenderGame();
    }
}
