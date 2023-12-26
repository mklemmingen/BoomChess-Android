package com.boomchess.game.frontend.picture;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.frontend.actor.Bubble;
import com.boomchess.game.frontend.actor.DeathExplosionActor;

import static com.boomchess.game.BoomChess.*;

public class SpeechBubbles {
    /*
    This class holds functions to create and load Stack objects that hold the speech bubbles
     */

    // this function creates a Stack object that holds the speech bubble that says "Attack in Progress!"
    public static void createSpeechAttackIncoming(){
        /*
        This function creates a Stack object that holds the speech bubble that says "Attack in Progress!"
         */
        Stack alarm = new Stack();

        Image alarmImage = new Image(BoomChess.alarmTexture);

        editImageStack(alarmImage, alarm);

        Coordinates generalCoordinates;

        // find the general coordinates to add a speech bubble to him
        if(currentState == GameState.GREEN_TURN){
            // if it is green's turn, find the general coordinates of green
            if(!isBotMatch) {
                generalCoordinates = BoomChess.findGeneral(true);
            }
            else{
                return; // we don't need a bot to tell us that an attack is incoming
            }
        }
        else{
            // if it is red's turn, find the general coordinates of red
            generalCoordinates = BoomChess.findGeneral(false);
        }

        // create bubble
        if(generalCoordinates != null) { // cant find general if the general has died.
                                         // Do not create a bubble if that is the case
            createBubble(alarm, generalCoordinates.getX() + (tileSize / 16),
                    generalCoordinates.getY() + tileSize / 4);
        }
    }

    // this function creates a Stack Object that creates a random speech bubble of defeating the enemy
    // and adds it to the correct position on the stage
    public static void createSpeechDefeatEnemy(int x, int y) {
        /*
        This function creates a Stack Object that creates a random speech bubble of defeating the enemy
         */
        Stack defeatEnemy = new Stack();
        Image defeatEnemyImage = new Image(BoomChess.killSpeeches.getRandomTexture());
        editImageStack(defeatEnemyImage, defeatEnemy);

        //adds a death kill speech bubble to the attacker
        Coordinates deathCoordinates = calculatePXbyTile(x, y);

        // create bubble
        createBubble(defeatEnemy, deathCoordinates.getX(), deathCoordinates.getY());
    }

    private static void editImageStack(Image image, Stack stack){
        /*
        This function scales the image and stack of each bubble according to the current tile size
         */
        image.setSize(tileSize*1, tileSize*0.5f);
        stack.setSize(tileSize*1, tileSize*0.5f);
        stack.add(image);
    }

    private static void createBubble(Stack stack, float pxX, float pxY){
        /*
        This function creates a Bubble Object that is added to the current Action Sequence Object in BoomChess
         */

        // create a Bubble Object with the parameters
        Bubble bubble = new Bubble(stack, pxX, pxY);
        bubble.setZIndex(1);
        // add the Bubble Object to the current Action Sequence Object in BoomChess
        actionSequence.addSequence(bubble);
        System.out.println("Added a Speech Bubble at Screen Coords: " + pxX + ", " + pxY + "\n");
    }




}
