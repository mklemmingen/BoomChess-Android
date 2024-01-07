package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.interfaces.calculateDamageInterface;
import com.boomchess.game.backend.interfaces.defendAndBleedInterface;
import com.boomchess.game.frontend.interfaces.makeASoundInterface;
import com.boomchess.game.frontend.interfaces.takeIntervalSelfie;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

public class Artillery extends Soldier
        implements takeSelfieInterface, calculateDamageInterface, defendAndBleedInterface, makeASoundInterface, takeIntervalSelfie {
    /*
     * Artillery.java is a fully new piece in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     * As its speciality, it deals damage to 3 tiles surrounding him
     */

    private static int standardHealth = 40;

    public Artillery(String teamColor) {
        /*
         * Constructor for the Artillery object, takes positional arguments and team color
         */
        super(teamColor, 40);
    }

    public int calculateDamage(Soldier soldierDefend) {

        // deals 1-5 damage
        // advantages: deals +5 to infantry

        int minValue = 1;
        int maxValue = 5;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if (soldierDefend instanceof Infantry) {
            randomDamage += 5;
        }

        return randomDamage;

    }

    public int getStandardHealth(){
        return standardHealth;
    }

    public int defendAndBleed(int damage, Soldier soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack instanceof Wardog){
            return damage - 5;
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
                return BoomChess.redCatapult;
            } else {
                return BoomChess.greenCatapult;
            }
        } else {
            if(BoomChess.isColourChanged){
                if (teamColor.equals("red")) {
                return BoomChess.redArtillery;
                } else {
                return BoomChess.blueArtillery;
                }
            } else{
                if (teamColor.equals("red")) {
                    return BoomChess.redArtillery;
                } else {
                    return BoomChess.greenArtillery;
                }
            }
        }
    }

    public void makeASound(){
        if(BoomChess.isMedievalMode){
            BoomChess.catapultSound.play(BoomChess.soundVolume);
        } else {
            BoomChess.smallExplosionSound.play(BoomChess.soundVolume);
        }
    }

    public Texture showInterval() {
        return BoomChess.oneFive;
    }
}
