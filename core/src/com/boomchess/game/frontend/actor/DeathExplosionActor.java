package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.tileSize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Coordinates;

/*
 * this class is the object for the dotted line that appears when a piece is selected. It acts as an Actor Object
 * on a Scene2DUI Stage
 */
public class DeathExplosionActor extends Actor {

    public final int X;
    public final int Y;
    // this is the time that the animation has been on the screen
    private float elapsed = 0;
    // this is the maximum duration that the animation will be on the screen
    private static final float MAX_DURATION = 4f;
    private static final int FRAME_COLS = 8; // Number of columns in animations/explosions.png sprite sheet
    private static final int FRAME_ROWS = 6; // Number of rows
    private final Animation<TextureRegion> explosionAnimation;
    private static final float SCALE_FACTOR = 0.5f;  // scaling factor to make explosion smaller

    public DeathExplosionActor(int X, int Y) {
        this.X = X;
        this.Y = Y;

        // loading the sprite sheet as a Texture
        Texture explosionSheet = new Texture(Gdx.files.internal("animations/explosions.png"));
        // splitting the Sprite Sheet into individual frames
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);

        // create the Animation objets by collecting the Frames from the Sprite Sheet
        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }
        explosionAnimation = new Animation<TextureRegion>(0.06f, explosionFrames);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsed += delta;
        if (elapsed > MAX_DURATION) {
            remove(); // This will remove the actor from the stage
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // logic for adding death animation  ---------------------------------------

        // calculate Pixel of corpse
        Coordinates corpse = BoomChess.calculatePXbyTileNonGDX(X, Y);

        // out of the Coordinate objects, get the PX
        float x1 = corpse.getX();
        float y1 = corpse.getY();

        /*
        // to factor in the scaling of the explosion
        x1 -= tileSize/3;
        y1 -= tileSize/3;
         */

        TextureRegion currentFrame = explosionAnimation.getKeyFrame(elapsed, false);

        // Calculate the scaled width and height
        float scaledWidth = currentFrame.getRegionWidth() * SCALE_FACTOR;
        float scaledHeight = currentFrame.getRegionHeight() * SCALE_FACTOR;

        batch.draw(currentFrame, x1, y1, scaledWidth, scaledHeight);
        // Draw the current frame at the specified position

    }
}
