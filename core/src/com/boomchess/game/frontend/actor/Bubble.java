package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.radioChatter;
import static com.boomchess.game.BoomChess.soundVolume;
import static com.boomchess.game.BoomChess.speechSounds;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.boomchess.game.BoomChess;
import com.boomchess.game.frontend.stage.MenuStage;

public class Bubble extends Actor {
    /*
    This class holds functions to create objects that hold the speech bubbles, it self destructs after 2 seconds
     */

    // px coords
    private final float X;
    private final float Y;
    // elapsed time since addition to stage
    private float elapsed = 0;
    // this is the maximum duration that the bubble will be on the screen
    private static final float MAX_DURATION = 2f;
    // this is the stack of the bubble
    private final Stack stack;

    public Bubble(Stack stack, float X, float Y){
        /*
        This function creates a Stack object that holds the speech bubble that says "Attack in Progress!"
         */
        this.stack = stack;
        this.X = X;
        this.Y = Y;
    }

    @Override
    public void act(float delta) {
        /*
         * this method is called every frame to update the Bubbles Object and overrides the standard act method
         * so that the Bubble will be removed after a certain amount of time
         */

        // this ensures that if the player has gone back to the menu, the sequence will not continue
        if (BoomChess.currentStage instanceof MenuStage){
            remove(); // This will remove the actor from the stage
            return;
        }

        super.act(delta);
        elapsed += delta;
        if (elapsed > MAX_DURATION) {
            remove(); // This will remove the actor from the stage
        }
    }

    // Override the draw method to add the stack at the correct position
    @Override
    public void draw(Batch batch, float parentAlpha) {
        /*
        This method is called every frame to draw the bubble
         */
        super.draw(batch, parentAlpha);
        stack.setPosition(X, Y);
        stack.draw(batch, parentAlpha);
    }

    public void makeSound(){
        // plays some cool sounds

        if(BoomChess.isBeepMode) {
            speechSounds.play(soundVolume);
        } else{
            // play radio chatter
            radioChatter.play(soundVolume);
        }
    }

}
