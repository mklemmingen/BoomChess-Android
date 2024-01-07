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

public class HitMarkerActor extends Actor {

    private final int X;
    private final int Y;
    // this is the time that the animation has been on the screen
    private float elapsed = 0;
    // this is the maximum duration that the animation will be on the screen
    private static final float MAX_DURATION = 0.75f;
    private static final int FRAME_COLS = 4; // Number of columns in animations/explosions.png sprite sheet
    private static final int FRAME_ROWS = 4; // Number of rows
    private final Animation<TextureRegion> explosionAnimation;
    private static final float SCALE_FACTOR = 0.7f;  // sclaing factor to make explosion smaller

    public HitMarkerActor(int X, int Y) {
        this.X = X;
        this.Y = Y;

        // loading the sprite sheet as a Texture
        Texture explosionSheet = new Texture(Gdx.files.internal("animations/hitmarker1.png"));
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
        explosionAnimation = new Animation<TextureRegion>(0.08f, explosionFrames);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsed += delta;
        if (elapsed > MAX_DURATION) {
            remove(); // This will remove the actor from the stage
            BoomChess.deathExplosionStage.clear();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // logic for adding death animation  ---------------------------------------

        // calculate Pixel of defender
        Coordinates defender = BoomChess.calculatePXbyTileNonGDX(X, Y);

        // out of the Coordinate objects, get the PX
        float x1 = defender.getX();
        float y1 = defender.getY();
        // to factor in the scaling of the explosion
        x1 += tileSize/3;
        y1 += tileSize/3;

        TextureRegion currentFrame = explosionAnimation.getKeyFrame(elapsed, false);

        // Calculate the scaled width and height
        float scaledWidth = currentFrame.getRegionWidth() * SCALE_FACTOR;
        float scaledHeight = currentFrame.getRegionHeight() * SCALE_FACTOR;

        batch.draw(currentFrame, x1, y1, scaledWidth, scaledHeight); // Draw the current frame at the specified position

    }
}
