package com.boomchess.game.frontend.screen;

import static com.boomchess.game.BoomChess.skin;
import static com.boomchess.game.BoomChess.tileSize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.boomchess.game.BoomChess;

public class RelativeResizer {
    /*
    this class has functions that run each frame to check if the screen has been resized. If it has, then the
    class automatically changes certain game specific variables to make sure that the game still looks good

    This class specifically deals with the relative positioning of the game objects and is only used for games with
    tiles. f.ex in BoomChess here, it sets the tile size
     */

    // this is the width and height of the screen
    private static int width;
    private static int height;

    // this functions should be run when the game starts up
    public static void init(){
        /*
        this function should be run when the game starts up. It sets the width and height of the screen
         */
        RelativeResizer.width = Gdx.graphics.getWidth();
        RelativeResizer.height = Gdx.graphics.getHeight();
        tileSize = (float) width / 20;

        changeSkinBitmaps();
    }

    // this function should be run each frame
    public static void ensure(){
        /*
        this function should be run each frame. It checks if the screen has been resized and if it has, it changes
        certain game specific variables to make sure that the game still looks good
         */
        if (width != Gdx.graphics.getWidth() || height != Gdx.graphics.getHeight()){
            // if the screen has been resized, then the width and height of the screen is changed
            width = Gdx.graphics.getWidth();
            height = Gdx.graphics.getHeight();

            // the tile size is changed to make sure that the game still looks good
            tileSize = (float) width / 20;

            changeSkinBitmaps();

        }
    }

    private static void changeSkinBitmaps() {
        /*
        this function changes the bitmaps of the skin to make sure that the game still looks good
         */

        // Retrieving the font used in the skin
        BitmapFont font = skin.getFont("commodore-64");

        // Scaling the font depending on the relativresizer calculated tile size

        if (tileSize > 140) {
            font.getData().setScale(3.5f);
            BoomChess.musicLabelScale = 1.3f;
        } else if (tileSize > 100) {
            font.getData().setScale(2.8f);
            BoomChess.musicLabelScale = 1.05f;
        } else if (tileSize > 50){
            font.getData().setScale(2);
            BoomChess.musicLabelScale = 0.9f;
        } else {
            font.getData().setScale(1.5f);
            BoomChess.musicLabelScale = 0.6f;
        }

        // Optionally, update the skin with the scaled font if needed
        skin.add("commodore-64", font, BitmapFont.class);
    }
}
