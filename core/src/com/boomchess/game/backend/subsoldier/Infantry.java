package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.interfaces.calculateDamageInterface;
import com.boomchess.game.backend.interfaces.defendAndBleedInterface;
import com.boomchess.game.frontend.interfaces.makeASoundInterface;
import com.boomchess.game.frontend.interfaces.takeIntervalSelfie;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

public class Infantry extends Soldier
        implements takeSelfieInterface, calculateDamageInterface, defendAndBleedInterface, makeASoundInterface, takeIntervalSelfie {
    /*
     * Infantry.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     */

    public static int standardHealth = 40;

    public Infantry(String teamColor) {
        /*
         * Constructor for the Infantry object, takes positional arguments and team color
         */
        super(teamColor, 40);
    }

    public int calculateDamage(Soldier soldierDefend) {

        // deals 01-20 damage
        // advantages +5 to attacking helicopters

        int minValue = 5;
        int maxValue = 20;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if(soldierDefend instanceof Helicopter){
            randomDamage += 5;
        }

        return randomDamage;

    }

    public int getStandardHealth(){
        return standardHealth;
    }

    public int defendAndBleed(int damage, Soldier soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack instanceof Helicopter){
            return damage - 2;
        }

        return damage;
    }

    @Override
    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */
        if (BoomChess.isMedievalMode) {
            if (teamColor.equals("red")) {
                return BoomChess.redArcher;
            } else {
                return BoomChess.greenArcher;
            }
        }
        else {
            if(BoomChess.isColourChanged){
                if (teamColor.equals("red")) {
                    return BoomChess.redInfantry;
                } else {
                    return BoomChess.blueInfantry;
                }
            } else{
                if (teamColor.equals("red")) {
                    return BoomChess.redInfantry;
                } else {
                    return BoomChess.greenInfantry;
                }
            }
        }
    }

    public void makeASound(){
        if(BoomChess.isMedievalMode){
            BoomChess.archerSound.play(BoomChess.soundVolume);
        } else {
            BoomChess.smallArmsSound.play(BoomChess.soundVolume);
        }
    }

    public Texture showInterval() {
        return BoomChess.fiveTwenty;
    }
}
