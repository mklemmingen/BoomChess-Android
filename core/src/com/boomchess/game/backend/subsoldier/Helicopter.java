package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.interfaces.calculateDamageInterface;
import com.boomchess.game.backend.interfaces.defendAndBleedInterface;
import com.boomchess.game.frontend.interfaces.makeASoundInterface;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

import java.util.ArrayList;

public class Helicopter extends Soldier
        implements takeSelfieInterface, calculateDamageInterface, defendAndBleedInterface, makeASoundInterface {
    /*
     * Helicopter.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     */

    public static int standardHealth = 50;

    public Helicopter(String teamColor) {
        /*
        * Constructor for the Helicopter object, takes positional arguments and team color
         */
        super(teamColor, 50);
    }

    public Texture takeSelfie() {
       /*
       * this method returns a Texture depending on the team color
        */

        if (BoomChess.isMedievalMode) {
            if (teamColor.equals("red")) {
                return BoomChess.redMagician;
            } else {
                return BoomChess.greenMagician;
            }
        }
        else {
            if(BoomChess.isColourChanged){
                if (teamColor.equals("red")) {
                    return BoomChess.redHelicopter;
                } else {
                    return BoomChess.blueHelicopter;
                }
            } else{
                if (teamColor.equals("red")) {
                    return BoomChess.redHelicopter;
                } else {
                    return BoomChess.greenHelicopter;
                }
            }
        }
    }

    public int calculateDamage(Soldier soldierDefend) {

        // deals 5-20 damage
        // advantages: deals +5 to tanks

        int minValue = 5;
        int maxValue = 20;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if(soldierDefend instanceof Tank){
            randomDamage += 5;
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

        // A Helicopter can move to any tile that is 3 tiles forward and 1 to the left
        // or right in any direction except diagonal
        // we need to check if the tile is occupied by anything before putting it in the array

        // we have a loop for each direction that the helicopter can move in. We start by creating four variables

        int upwardsY = positionY + 3;
        // Loop to move vertical up and to the right or left
        for (int offset = -1; offset < 2; offset += 2) {
            int newX = positionX + offset;

            if (Board.isValidMove(newX, upwardsY)) {
                if ((gameBoard[newX][upwardsY] instanceof Empty))  {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, upwardsY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        int downwardsY = positionY - 3;
        // Loop to move downwards vertical up and to the left or right
        for (int offset = -1; offset < 2; offset += 2) {
            int newX = positionX + offset;

            if (Board.isValidMove(newX, downwardsY)) {
                if ((gameBoard[newX][downwardsY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, downwardsY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        int rightsideX = positionX + 3;
        // Loop to move rightside horizontally and up or down
        for (int offset = -1; offset < 2; offset += 2) {
            int newY = positionY - offset;

            if (Board.isValidMove(rightsideX, newY)) {
                if ((gameBoard[rightsideX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(rightsideX, newY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        int leftsideX = positionX - 3;
        // Loop to move leftside horizontally and up or down
        for (int offset = -1; offset < 2; offset += 2) {
            int newY = positionY - offset;

            if (Board.isValidMove(leftsideX, newY)) {
                if ((gameBoard[leftsideX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(leftsideX, newY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        return possibleMoves;
    }

    public int defendAndBleed(int damage, Soldier soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack instanceof Tank){
            return damage - 5;
        }

        System.out.println("Helicopter has been attacked for" + damage + "damage by " + soldierAttack + ".\n");
        return damage;
    }

    public void makeASound(){
        if(BoomChess.isMedievalMode){
            BoomChess.magicSound.play(BoomChess.soundVolume);
        } else {
            BoomChess.helicopterSound.play(BoomChess.soundVolume);
        }
    }
    
}
