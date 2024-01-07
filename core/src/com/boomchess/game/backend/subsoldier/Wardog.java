package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.interfaces.calculateDamageInterface;
import com.boomchess.game.backend.interfaces.defendAndBleedInterface;
import com.boomchess.game.frontend.interfaces.makeASoundInterface;
import com.boomchess.game.frontend.interfaces.takeIntervalSelfie;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

import java.util.ArrayList;

public class Wardog extends Soldier
        implements takeSelfieInterface, calculateDamageInterface, defendAndBleedInterface, makeASoundInterface, takeIntervalSelfie {
    /*
     * Wardog.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
    */

    public static int standardHealth = 40;

    public Wardog(String teamColor) {
        /*
         * Constructor for the Wardog object, takes positional arguments and team color
         */
        super(teamColor, 40);
    }

    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */

        if (BoomChess.isMedievalMode){
            if (teamColor.equals("red")) {
                return BoomChess.redFea;
            } else {
                return BoomChess.greenFea;
            }
        } else {
            if(BoomChess.isColourChanged){
                if (teamColor.equals("red")) {
                    return BoomChess.redWardog;
                } else {
                    return BoomChess.blueWardog;
                }
            } else{
                if (teamColor.equals("red")) {
                    return BoomChess.redWardog;
                } else {
                    return BoomChess.greenWardog;
                }
            }
        }
    }

    public int calculateDamage(Soldier soldierDefend) {

        // damage: 5-20
        // advantages: +5 to infantry
        // disadvantages: -5 to tank

        int minValue = 5;
        int maxValue = 20;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if(soldierDefend instanceof Tank){
            randomDamage -= 5;
        } else if (soldierDefend instanceof Infantry){
            randomDamage += 5;
        }

        if(!BoomChess.isMedievalMode) {
            BoomChess.dogSound.play(BoomChess.soundVolume);
        } else {
            BoomChess.magicSound.play(BoomChess.soundVolume);
        }
        return randomDamage;
    }

    public int getStandardHealth(){
        return standardHealth;
    }

    @Override
    public ArrayList<Coordinates> mathMove(int positionX, int positionY) {
        Soldier[][] gameBoard = Board.getGameBoard();
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // a war dog can move diagonally in all directions

        // Loop to move diagonally up and to the right
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX + offset;
            int newY = positionY + offset;

            if (Board.isValidMove(newX, newY)) {
                if ((gameBoard[newX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move diagonally up and to the left
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX - offset;
            int newY = positionY + offset;

            if (Board.isValidMove(newX, newY)) {
                if ((gameBoard[newX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move diagonally down and to the right
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX + offset;
            int newY = positionY - offset;

            if (Board.isValidMove(newX, newY)) {
                if ((gameBoard[newX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move diagonally down and to the left
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX - offset;
            int newY = positionY - offset;

            if (Board.isValidMove(newX, newY)) {
                if ((gameBoard[newX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        return possibleMoves;
    }

    public int defendAndBleed(int damage, Soldier soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack instanceof Infantry){
            return damage - 2;
        }

        return damage;
    }

    public void makeASound(){
        if(BoomChess.isMedievalMode){
            BoomChess.dogSound.play(BoomChess.soundVolume);
        } else {
            BoomChess.magicSound.play(BoomChess.soundVolume);
        }
    }

    public Texture showInterval() {
        return BoomChess.fiveTwenty;
    }
}
