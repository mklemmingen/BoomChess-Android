package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

public class Hill extends Soldier implements takeSelfieInterface {
    /*
    * Hill.java is the object for the chess piece Hill in the game Boom Chess.
    * It acts as a non-moving and non-passable object on the board.
     */

    private final Texture texture;

    public Hill(String teamColor) {
        /*
         * Constructor for the Helicopter object, takes positional arguments and team color
         */
        super(teamColor, -1);
        texture = BoomChess.obstacleTextures.getRandomTexture();
    }

    public Texture takeSelfie() {
        /*
         * this method returns a Texture defined out of the randomTexture object obstacleTextures at Constructor
         */
        return texture;
    }
}
