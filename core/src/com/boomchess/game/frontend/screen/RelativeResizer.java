package com.boomchess.game.frontend.screen;

import com.badlogic.gdx.Gdx;
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
        BoomChess.tileSize = (float) width / 20;
    }

    // this function should be run each frame
    public static boolean ensure(){
        /*
        this function should be run each frame. It checks if the screen has been resized and if it has, it changes
        certain game specific variables to make sure that the game still looks good
         */
        if (width != Gdx.graphics.getWidth() || height != Gdx.graphics.getHeight()){
            // if the screen has been resized, then the width and height of the screen is changed
            width = Gdx.graphics.getWidth();
            height = Gdx.graphics.getHeight();

            // the tile size is changed to make sure that the game still looks good
            BoomChess.tileSize = (float) width / 20;
            return true;
        } else {
            return false; // since no resizing has happened
        }
    }
}
