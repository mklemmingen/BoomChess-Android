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

public class Commando extends Soldier
        implements takeSelfieInterface, calculateDamageInterface, defendAndBleedInterface, makeASoundInterface, takeIntervalSelfie {
    /*
     * Commando.java is the object for the chess piece Commando in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtCommando lowers the current health amount any returns the new health amount.
     * The method defaultCommando resets the health to the initial amount.
     */

    public static int standardHealth = 40;

    public Commando(String teamColor) {
        /*
         * Constructor for the Commando object, takes positional arguments and team color
         */
        super(teamColor, 50);
    }

    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */

        if (BoomChess.isMedievalMode) {
            if (teamColor.equals("red")) {
                return BoomChess.redQueen;
            } else {
                return BoomChess.greenQueen;
            }
        }
        else {
            if(BoomChess.isColourChanged){
                if (teamColor.equals("red")) {
                    return BoomChess.redCommando;
                } else {
                    return BoomChess.blueCommando;
                }
            } else{
                if (teamColor.equals("red")) {
                    return BoomChess.redCommando;
                } else {
                    return BoomChess.greenCommando;
                }
            }
        }
    }
    
    public int calculateDamage(Soldier soldierDefend) {

        // deals 5-30 damage
        // deals +10 to attacking tanks
        int minValue = 5;
        int maxValue = 30;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if(soldierDefend instanceof Tank){
            randomDamage += 10;
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

        // a Commando Soldier, advanced to the classic Queen piece, can move endless vertically,
        // horizontally and diagonal

        // we need to check if the tile is occupied by anything before putting it in the array
        // the 2D-Array will hold the x value in the first-layer array and the y value in the second-layer array

        // 8 different for loops this time.
        // A for loop for Y 0 + 8. A for loop for Y -8. A for loop for X + 8. A for loop for X - 8.
        // A for loop for X + 8 and Y +8. A for loop for X + 8 and Y - 8.
        // A for loop for X - 8 and Y -8. A for loop for X - 8 and Y - 8

        // we will first do diagonally down left to up right, then diagonally up left to down right
        // then we do the horizontal and vertical tiles
        // if in any of these single operations, there is a tile that is not empty, we stop the full loop,
        // since we cannot move further in that direction

        // We do not skip the case where xOffset and yOffset are both 0 (the current position), since
        // it would take more computations doing that each loop that it takes to just check if the
        // tile is occupied and a valid move

        // Loop to move upwards
        for (int yOffset = 1; yOffset <= 7; yOffset++) {
            int newY = positionY + yOffset;

            if (Board.isValidMove(positionX, newY)) {
                if ((gameBoard[positionX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(positionX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move downwards
        for (int yOffset = 1; yOffset <= 7; yOffset++) {
            int newY = positionY - yOffset;

            if (Board.isValidMove(positionX, newY)) {
                if ((gameBoard[positionX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(positionX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move to the right
        for (int xOffset = 1; xOffset <= 7; xOffset++) {
            int newX = positionX + xOffset;

            if (Board.isValidMove(newX, positionY)) {
                if ((gameBoard[newX][positionY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, positionY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move to the left
        for (int xOffset = 1; xOffset <= 7; xOffset++) {
            int newX = positionX - xOffset;

            if (Board.isValidMove(newX, positionY)) {
                if ((gameBoard[newX][positionY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, positionY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

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
        // The Commando is very sneaky and rogue. His resistance is randomized.
        // on a randomized 1-3 scale, he takes damage / factor(1-3) damage

        int minValue = 1;
        int maxValue = 3;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int factor = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        return damage / factor;
    }

    public void makeASound(){
        if(BoomChess.isMedievalMode){
            BoomChess.queenSound.play(BoomChess.soundVolume);
        } else {
            BoomChess.bigArmsSound.play(BoomChess.soundVolume);
        }
    }

    public Texture showInterval() {
        return BoomChess.fiveThirty;
    }
}

