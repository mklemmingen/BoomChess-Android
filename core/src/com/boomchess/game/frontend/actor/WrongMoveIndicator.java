package com.boomchess.game.frontend.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.boomchess.game.BoomChess;
import com.boomchess.game.frontend.stage.MenuStage;

import static com.boomchess.game.BoomChess.*;
import static com.boomchess.game.BoomChess.soundVolume;

public class WrongMoveIndicator extends Actor {
    /*
    class to hold an actor that gets displayed on screen for 2 seconds in the wrongMoveStage if the player has tried
    moving a piece when it is not yet his turn
     */
    // px coords
    private float X;
    private float Y;
    // elapsed time since addition to stage
    private float elapsed = 0;
    // this is the maximum duration that the bubble will be on the screen
    private static final float MAX_DURATION = 2f;
    // this is the stack of the bubble
    private final Stack stack;

    public WrongMoveIndicator(){
        /*
        This function creates a Stack object that holds the speech bubble that says "Attack in Progress!"
         */
        this.stack = new Stack();
        stack.setSize(tileSize*2, tileSize);
        stack.addActor(new Image(wrongMoveLogo));
        this.X = (float) Gdx.graphics.getWidth()/2-tileSize;
        this.Y = (float) Gdx.graphics.getHeight() /2+tileSize;
    }

    @Override
    public void act(float delta) {
        /*
         * this method is called every frame to update the WrongMoveIndicator
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
        This method is called every frame to draw the indicator
         */
        super.draw(batch, parentAlpha);
        stack.setPosition(X, Y);
        stack.draw(batch, parentAlpha);
    }

    public void makeSound(){
        // plays a sound
        BoomChess.brick.play(soundVolume);
    }
}

