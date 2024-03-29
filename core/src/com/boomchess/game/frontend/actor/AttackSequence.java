package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.botMovingStage;
import static com.boomchess.game.BoomChess.currentState;
import static com.boomchess.game.BoomChess.damageNumberStage;
import static com.boomchess.game.BoomChess.deathExplosionStage;
import static com.boomchess.game.BoomChess.dottedLineStage;
import static com.boomchess.game.BoomChess.quickAction;
import static com.boomchess.game.BoomChess.reRenderGame;
import static com.boomchess.game.BoomChess.smallExplosionSound;
import static com.boomchess.game.BoomChess.soundVolume;
import static com.boomchess.game.BoomChess.speechBubbleStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomchess.game.BoomChess;
import com.boomchess.game.frontend.stage.MenuStage;

import java.util.ArrayList;

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

        // clear all stages where actors are added afterwards
        deathExplosionStage.clear();
        dottedLineStage.clear();
        botMovingStage.clear();
        speechBubbleStage.clear();

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

    private void actActor() {
        // if statement of instance of either HitMarkerActor or DeathExplosionActor or DottedLineActor

        // this ensures that if the player has gone back to the menu, the sequence will not continue
        if (BoomChess.currentStage instanceof MenuStage) {
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

        if (currentIndex >= lengthOfAttackList) {
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
            if(quickAction){
                timePerBreak = 0.25f;
            } else {
                timePerBreak = 0.5f;
            }
        } else if (actorBuddy instanceof DamageNumber) {
            // if it is a DamageNumber, add it to the GameStage
            damageNumberStage.addActor(actorBuddy);
            if (quickAction) {
                timePerBreak = 0.35f;
            } else {
                timePerBreak = 0.75f;
            }
        } else if (actorBuddy instanceof DeathExplosionActor) {
            // if it is a DeathExplosionActor, add it to the deathExplosionStage
            BoomChess.bigExplosionSound.play(BoomChess.soundVolume);
            deathExplosionStage.addActor(actorBuddy);
            // calls upon a function that adds a red-cross above the dead piece till gameStage is reloaded
            BoomChess.addCrossOfDeath(((DeathExplosionActor) actorBuddy).X, ((DeathExplosionActor) actorBuddy).Y);
            if (quickAction) {
                timePerBreak = 1f;
            } else {
                timePerBreak = 2f;
            }

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
            if(quickAction){
                timePerBreak = 0.25f;
            } else {
                timePerBreak = 0.5f;
            }
        } else if (actorBuddy instanceof Bubble) {
            // if it is a Bubble, add it to the speechBubbleStage
            ((Bubble) actorBuddy).makeSound(); // plays radio chatter
            speechBubbleStage.addActor(actorBuddy);
            if(quickAction){
                timePerBreak = 0.5f;
            } else {
                timePerBreak = 1f;
            }
        } else {
            timePerBreak = 0;
            Gdx.app.log("AttackSequence", "Error: Actor not recognized");
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

    public boolean isListEmpty(){
        return attackList.isEmpty();
    }
}
