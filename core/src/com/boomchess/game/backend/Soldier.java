package com.boomchess.game.backend;

import com.boomchess.game.backend.subsoldier.Empty;

import java.util.ArrayList;

public abstract class Soldier {
    /*
    * Soldiers.java contains the creation of the 2D-Array in the method initialiseBoard() and acts as an object
    * for what goes into each position of the board.
    * The information it holds is the soldier type, the teamColor, piece ID. It does not
    * hold the health value, since that is stored in the individual soldier objects callable by pieceID and teamColor.
     */

    protected String teamColor;
    protected int health;
    private int standardHealth;

    public Soldier(String teamColor, int health) {
        this.teamColor = teamColor;
        this.health = health;
    }

    public String getTeamColor() {
        return this.teamColor;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<Coordinates> mathMove(int positionX, int positionY) {
        Soldier[][] gameBoard = Board.getGameBoard();
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // an Infantry soldier, A General Soldier and an Artillery all move only one tile in all directions.
        // these Soldier do not override mathMove in their superclass, all others are special little Pieces

        // here we do math to get the tile coordinates of the tile we want to check
        //   a  b  c
        //   d  xy  e
        //   f  g  h

        // for loop that does -1 0 and +1 onto the x coordinate
        // for loop that does -1 0 and +1 onto the y coordinate
        // in total, we will have 8 iterations

        //   ax = x - 1 ; ay = y + 1
        //   bx = x ; by = y + 1
        //   cx = x + 1 ; cy = y + 1
        //   dx = x - 1 ; dy = y
        //   ex = x + 1 ; ey = y
        //   fx = x - 1 ; fy = y - 1
        //   gx = x ; gy = y - 1
        //   hx = x + 1 ; hy = y - 1
        //   we need to check if the tile is occupied by anything before putting it in the array

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {

                // We do not skip the case where xOffset and yOffset are both 0 (the current position), since
                // it would take more computations doing that each loop that it takes to just check if the
                // tile is occupied and a valid move

                int newX = positionX + xOffset;
                int newY = positionY + yOffset;

                if (Board.isValidMove(newX, newY) && (gameBoard[newX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                }
            }
        }
        return possibleMoves;
    }

    public int getStandardHealth(){
        return standardHealth;
    }
}
