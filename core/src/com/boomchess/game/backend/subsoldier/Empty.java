package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

public class Empty extends Soldier
        implements takeSelfieInterface {
    /*
    * Empty.java is the object for the an Empty tile in the game Boom Chess.
    * It acts as a non-moving object on the board.
     */

    public Empty(String teamColor) {
        /*
         * Constructor for the Helicopter object, takes positional arguments and team color
         */
        super(teamColor, -1);
    }
    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */

        return BoomChess.empty;

    }

}
