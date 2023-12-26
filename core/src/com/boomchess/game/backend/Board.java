package com.boomchess.game.backend;

import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.subsoldier.*;

import java.util.ArrayList;

public class Board {
    /*
     * Board.java is the object for the chess board in the game Boom Chess.
     * It holds the initialization of the board and the current state of the board.
     * The method initialize creates the initial board with the correct pieces.
     * The method updateBoard updates the board after a move has been made.
     */

    // Define the board as a 2D array
    private static Soldier[][] board;

    // current validMoveTiles
    public static ArrayList<Coordinates> validMoveTiles;

    public static void initialise() {

        // declaring the board

        board = new Soldier[9][8];

        // initialize the board with the correct pieces

        // the tank is the tower,
        // the helicopter the knight,
        // the wardog the bishop,
        // the general the king,
        // the commando
        // the queen and the infantry the pawns.
        // as to the standard layout of the board:
        // the red team is on the right of the screen
        // and the green team on the left

        //   green               red
        //    0  1  2  3  4  5  6  7  8
        //  0 t  i                 i  t
        //  1 h  i                 i  h
        //  2 a  w                 w  a
        //  3 g  i                 i  g
        //  4 c  i                 i  c
        //  5 w  w                 w  a
        //  6 h  i                 i  h
        //  7 t  i                 i  t


        // start of the creation of the board
        // green team
        // tanks
        board[0][0] = new Tank("green");
        board[0][7] = new Tank("green");
        // helicopters
        board[0][1] = new Helicopter("green");
        board[0][6] = new Helicopter("green");
        // artillery
        board[0][2] = new Artillery("green");
        board[0][5] = new Artillery("green");
        // wardog
        board[1][2] = new Wardog("green");
        board[1][5] = new Wardog("green");
        // general
        board[0][3] = new General("green");
        // commando
        board[0][4] = new Commando("green");
        // infantry
        int i = 1;
        for (int j = 0; j < 8; j++) {
            if (j == 2 || j == 5) {
                continue;
            }
            board[i][j] = new Infantry("green");
        }


        // red team
        // tanks
        board[8][0] = new Tank("red");
        board[8][7] = new Tank("red");
        // helicopters
        board[8][1] = new Helicopter("red");
        board[8][6] = new Helicopter("red");
        // artillery
        board[8][2] = new Artillery("red");
        board[8][5] = new Artillery("red");
        // wardog
        board[7][2] = new Wardog("red");
        board[7][5] = new Wardog("red");
        // general
        board[8][3] = new General("red");
        // commando
        board[8][4] = new Commando("red");
        // infantry
        int x = 7;
        for (int j = 0; j < 8; j++) {
            if (j == 2 || j == 5) {
                continue;
            }
            board[x][j] = new Infantry("red");
        }


        // Initialize the no-mans-land (empty space)
        for (int row = 2; row < 7; row++) {
            for (int col = 0; col <= 7; col++) {
                board[row][col] = new Empty("empty");
            }
        }

        // override with number of hills as defined in numberObstacles
        if (BoomChess.numberObstacle > 0)
            // value must be between 2 and 6 on x and 0 and 7 on y
            for (int iObstacle = 0; iObstacle < BoomChess.numberObstacle; iObstacle++) {
                int xObstacle = (int) (Math.random() * 5) + 2;
                int yObstacle = (int) (Math.random() * 8);
                board[xObstacle][yObstacle] = new Hill("empty");
            }
   }

   public static void initialiseChallengeOne() {

       // declaring the board

       board = new Soldier[9][8];

       // initialize the board with the correct pieces for the 1.Challenge

       // the tank is the tower,
       // the helicopter the knight,
       // the wardog the bishop,
       // the general the king,
       // the commando the queen and
       // the infantry the pawns.
       // artillery is unique and can hit further
       // as to the standard layout of the board:
       // the red team is on the right of the screen, its the BOT
       // and the green team on the left, its the PLAYER
       // ob is an obstacle called hill

       //   green               red
       //    0  1  2  3  4  5  6  7  8
       //  0 ob
       //  1 h        ob          i  h
       //  2    i     ob          w
       //  3 g  a     ob          w  g
       //  4    i                 w
       //  5                      w
       //  6 h ob                 i  h
       //  7   ob

         // start of the creation of the board

         // green team
            // infantry
            board[1][2] = new Infantry("green");
            board[1][4] = new Infantry("green");
            // artillery
            board[1][3] = new Artillery("green");
            // helicopter
            board[0][1] = new Helicopter("green");
            board[0][6] = new Helicopter("green");
            // general
            board[0][3] = new General("green");

        // red team
            // infantry
            board[7][1] = new Infantry("red");
            board[7][6] = new Infantry("red");
            // wardogs
            board[7][2] = new Wardog("red");
            board[7][3] = new Wardog("red");
            board[7][4] = new Wardog("red");
            board[7][5] = new Wardog("red");
            // helicopter
            board[8][1] = new Helicopter("red");
            board[8][6] = new Helicopter("red");
            // general
            board[8][3] = new General("red");

        // empty tiles around the soldiers
        board[0][0] = new Hill("empty");
        board[0][2] = new Empty("empty");
        board[0][4] = new Empty("empty");
        board[0][5] = new Empty("empty");
        board[0][7] = new Empty("empty");
        board[1][0] = new Empty("empty");
        board[1][1] = new Empty("empty");
        board[1][5] = new Empty("empty");
        board[1][6] = new Hill("empty");
        board[1][7] = new Hill("empty");
        board[7][0] = new Empty("empty");
        board[7][7] = new Empty("empty");
        board[8][0] = new Empty("empty");
        board[8][2] = new Empty("empty");
        board[8][4] = new Empty("empty");
        board[8][5] = new Empty("empty");
        board[8][7] = new Empty("empty");

        // from board[2][0] to board[6][7] is empty
       for (int col = 2; col <= 6; col++) {
           for (int row = 0; row < 8; row++) {
                board[col][row] = new Empty("empty");
           }
       }

        // override with hills were set
        board[2][1] = new Hill("empty");
        board[2][2] = new Hill("empty");
        board[2][3] = new Hill("empty");
 }

    public static Soldier[][] getGameBoard(){
        return board;
    }

    public static void update(int positionX, int positionY,
                                 int newPositionX, int newPositionY) {

        // we update a board by switching the two objects of the Soldier 2D-Array

        System.out.println("\n Updating board " + positionX + "_" + positionY + " to " +
                newPositionX + "_" + newPositionY + "\n");

        Soldier temp = board[positionX][positionY];
        board[positionX][positionY] = board[newPositionX][newPositionY];
        board[newPositionX][newPositionY] = temp;

        // successfully switched around the objects in the 2D-Array
    }

    // Helper method to check if the coordinates are within bounds
    public static boolean isValidMove(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 8;
    }

    // methods for setting and getting the validMoveTiles, as well as emptying them
    public static void setValidMoveTiles(ArrayList<Coordinates> newValidMoveTiles) {
        validMoveTiles = newValidMoveTiles;
    }

    public static void emptyValidMoveTiles() {
        validMoveTiles = new ArrayList<>();
    }

    public static ArrayList<Coordinates> getValidMoveTiles() {
        return validMoveTiles;
    }
}

