package com.boomchess.game.frontend.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;

import java.util.LinkedList;

public class soldierAnimation extends Actor implements Cloneable{
    /*
    A soldier animation is a libGDX animation that loads a specific spriteSheet from the asset folder
    depending on the "teamColour" and "pieceType" of the soldier that is given in the constructor.
    The object extends Actor, which means that it can be added to a stage and rendered.
     */

    private boolean animationPlaying;
    private final Image standardNoAnimation;
    // time measurement for animation and no animation
    private float elapsedTimeAnim = 0f;
    private float timeWithoutAnim = 0f;

    // time between animations is random between 3 and 6 seconds
    private float randomNoAnimationTime = (float) (Math.random() * 6 + 3);
    private final int frameCount = 8;
    private final float frameDuration = 0.143f; // equals 7 fps

    private int currentIndex;
    private final LinkedList<Animation<TextureRegion>> animationLinkedList = new LinkedList<>();

    public soldierAnimation(Soldier soldier) {
        /*
        Constructor for soldierAnimation.java
        Takes a soldier as argument.
        The constructor loads the correct spriteSheet from the asset folder and creates an animation object from it.
         */

        String teamColour = soldier.getTeamColor();
        if(BoomChess.isColourChanged){
            if (teamColour.equals("green")) {
                teamColour = "blue";
            }
        }

        String pieceType = soldier.getPieceType();

        Texture spriteSheet;

        // Load the spriteSheet from the asset folder
        spriteSheet = new Texture(Gdx.files.internal("spritesheets/" + teamColour +
                "_" + pieceType + ".png"));

        int frameCols = 4;
        int frameWidth = spriteSheet.getWidth() / frameCols;
        int frameRows = 2;
        int frameHeight = spriteSheet.getHeight() / frameRows;

        // Split the spriteSheet into frames
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        // Put the frames into a 1D array
        TextureRegion[] frames = new TextureRegion[frameCount];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                // size all frames to tileSize*tileSize
                frames[index++] = tmp[i][j];
            }
        }

        // Create the animation object and put it into linkedList
        Animation<TextureRegion> animation = new Animation<>(frameDuration, frames);
        animationLinkedList.add(animation);

        // checks if there are any more spritesheets that are named the same but with an additioal
        // number at the end:
        // if yes, it adds it to the animation linked list:
        int i = 1;
        while (Gdx.files.internal("spritesheets/" + teamColour + "_" + pieceType + i + ".png")
                .exists()) {

            spriteSheet = new Texture(Gdx.files.internal("spritesheets/" + teamColour +
                    "_" + pieceType + i + ".png"));

            // Split the spriteSheet into frames
            tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

            // Put the frames into a 1D array
            frames = new TextureRegion[frameCount];
            index = 0;
            for (int j = 0; j < frameRows; j++) {
                for (int k = 0; k < frameCols; k++) {
                    // size all frames to tileSize*tileSize
                    frames[index++] = tmp[j][k];
                }
            }

            // Create the animation object
            Animation<TextureRegion> animation2 =
                    new Animation<>(frameDuration, frames);
            animationLinkedList.add(animation2);
            i++;
        }

        // Creates a standard image that is shown when the animation is not playing
        standardNoAnimation = new Image(frames[0]);
        standardNoAnimation.setSize(BoomChess.tileSize, BoomChess.tileSize);

        // 50 50 chance for animationPlaying set to true at the beginning of the creation
        animationPlaying = Math.random() < 0.5;

        // randomize currentIndex inbetween 0 and animationLinkedList.size()
        currentIndex = (int) (Math.random() * animationLinkedList.size());
    }

    public TextureRegion getFrame() {
        /*
        getFrame returns the current frame of the animation.
        The current frame is calculated from the elapsedTime.
         */
        // return the current index linkedList animation keyframe
        Animation<TextureRegion> animation = animationLinkedList.get(currentIndex);
        return animation.getKeyFrame(elapsedTimeAnim, true);
    }

    public void dispose() {
        /*
        dispose disposes of the animation object.
         */
        // for all animations in the linkedList
        for (Animation<TextureRegion> animation : animationLinkedList) {
            // dispose of the texture of the first frame
            animation.getKeyFrames()[0].getTexture().dispose();
        }
    }

    // draw and act methods of Actor

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        // Maintain aspect ratio and scale
        float scaleWidth = BoomChess.tileSize;
        float scaleHeight = BoomChess.tileSize;

        // Get the current frame
        TextureRegion currentFrame = getFrame();

        // Scale the frame maintaining its aspect ratio
        float aspectRatio = (float) currentFrame.getRegionWidth() / currentFrame.getRegionHeight();
        if (aspectRatio > 1) {
            // If the frame is wider than it's tall, adjust height
            scaleHeight = scaleWidth / aspectRatio;
        } else {
            // If the frame is taller than it's wide, adjust width
            scaleWidth = scaleHeight * aspectRatio;
        }

        if(animationPlaying) {
            batch.draw(currentFrame, getX(), getY(), scaleWidth, scaleHeight);
        } else {
            standardNoAnimation.setPosition(getX(), getY());
            standardNoAnimation.setSize(scaleWidth, scaleHeight);
            standardNoAnimation.draw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        /*
        act is called by the stage to update the animation.
        It updates the elapsedTime of the animation.
         */
        super.act(delta);
        if(animationPlaying) {
            elapsedTimeAnim += Gdx.graphics.getDeltaTime();
            if(elapsedTimeAnim >= frameDuration * frameCount){
                animationPlaying = false;
                elapsedTimeAnim = 0f;

                // randomize current index for next animation
                currentIndex = (int) (Math.random() * animationLinkedList.size());
            }
        } else {
            timeWithoutAnim += Gdx.graphics.getDeltaTime();
            if(timeWithoutAnim >= randomNoAnimationTime){
                // new randomNoAnimationTime
                // random number between 2 and 14
                randomNoAnimationTime = (float) (Math.random() * 12 + 2);

                animationPlaying = true;
                timeWithoutAnim = 0f;
            }
        }
    }


    @Override
    public soldierAnimation clone() {
        try {
            soldierAnimation cloned = (soldierAnimation) super.clone();

            // randomize either elapsedTimeAnim with animationplaying = true or timeWithoutAnim with false
            // randomize the time variables to the full extent till their maximal value

            // random number between 1 and 6
            // if 1, start with an animation

            int random = (int) (Math.random() * 6 + 1);
            if(random == 1 ){
                cloned.animationPlaying = true;

                // randomize currentindex inbetween 0 and animationLinkedList.size()
                cloned.currentIndex = (int) (Math.random() * animationLinkedList.size());
            } else {

                cloned.animationPlaying = false;
                //randomizing the timeWithoutAnim between 2 and 14
                cloned.randomNoAnimationTime = (float) (Math.random() * 12 + 2);
            }

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }
}
