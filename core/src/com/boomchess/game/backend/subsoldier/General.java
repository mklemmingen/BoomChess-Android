package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.interfaces.calculateDamageInterface;
import com.boomchess.game.backend.interfaces.defendAndBleedInterface;
import com.boomchess.game.frontend.interfaces.makeASoundInterface;
import com.boomchess.game.frontend.interfaces.takeIntervalSelfie;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

public class General extends Soldier
        implements takeSelfieInterface, calculateDamageInterface, defendAndBleedInterface, makeASoundInterface, takeIntervalSelfie {
    /*
     * General.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     */

    public static int standardHealth = 50;


    public General(String teamColor) {
        /*
         * Constructor for the General object, takes positional arguments and team color
         */
        super(teamColor, 50, "general");
    }

    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */

        if (BoomChess.isMedievalMode) {
            if (teamColor.equals("red")) {
                return BoomChess.redKing;
            } else {
                return BoomChess.greenKing;
            }
        }
        else {
            if(BoomChess.isColourChanged){
                if (teamColor.equals("red")) {
                    return BoomChess.redGeneral;
                } else {
                    return BoomChess.blueGeneral;
                }
            } else{
                if (teamColor.equals("red")) {
                    return BoomChess.redGeneral;
                } else {
                    return BoomChess.greenGeneral;
                }
            }
        }
    }

    public int calculateDamage(Soldier soldierDefend) {
        // only deals 5-10 damage

        int minValue = 5;
        int maxValue = 10;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply

        return (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));
    }

    public int getStandardHealth(){
        return standardHealth;
    }

    @Override
    public String getPieceType() {
        return "general";
    }

    public int defendAndBleed(int damage, Soldier soldierAttack) {
        // The General is very resistant and only takes half damage to anything. He likes to chill in a Bunker

        return damage/2;
    }

    public void makeASound(){
        if(BoomChess.isMedievalMode){
            BoomChess.kingSound.play(BoomChess.soundVolume);
        } else {
            BoomChess.smallArmsSound.play(BoomChess.soundVolume);
        }
    }

    public Texture showInterval() {
        return BoomChess.fiveTen;
    }
}
