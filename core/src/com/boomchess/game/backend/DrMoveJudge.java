package com.boomchess.game.backend;

import com.boomchess.game.backend.subsoldier.Artillery;
import com.boomchess.game.backend.subsoldier.Empty;
import com.boomchess.game.backend.subsoldier.General;
import com.boomchess.game.backend.subsoldier.Hill;

public class DrMoveJudge {

    // Assuming that the board and soldiers have necessary methods to get their state

    public static int evaluateMove(int oldx, int oldy, int moveX, int moveY, int enemyGeneralX, int enemyGeneralY,
                                   int friendlyGeneralX, int friendlyGeneralY) {
        int score = 0;

        // Get the board
        Soldier[][] board = Board.getGameBoard();

        // Consider clustering bonus
        int clusteringScore = calculateClusteringScore(board, moveX, moveY);
        score += clusteringScore;

        // Check how much the gap between soldier and enemy general the move would close
        int proximityScore = calculateProximityScore(oldx, oldy, moveX, moveY, enemyGeneralX, enemyGeneralY);
        score += proximityScore;

        // Estimate potential damage
        int damageScore = calculateDamageScore(board, moveX, moveY, oldx, oldy);
        score += damageScore;

        // Evaluate defensive strength
        int defensiveScore = calculateDefensiveScore(board, moveX, moveY, oldx, oldy);
        score += defensiveScore; // adds 0 if move exposes soldier to big risk, adds 5 if move is safe

        // if the move is to a hill, add 2 to the score
        if (calculateNearHill(board, moveX, moveY)){
            score += 2;
        }

        // if the move is more than 5 away from the friendly general in X or Y, halve the score
        if (Math.abs(moveX - friendlyGeneralX) > 4 || Math.abs(moveY - friendlyGeneralY) > 4){
            score = score / 2;
        }

        return score;
    }

    private static boolean calculateNearHill(Soldier[][] board, int X, int Y){
        // this method checks if the move is next to a hill
        // if it is, it returns true
        // if not, it returns false

        boolean hillFound = false;

        // check if the move is to a hill
        // loop through surrounding by distance of 1
        int startX = Math.max(0, X - 1);
        int endX = Math.min(8, X + 1);

        int startY = Math.max(0, Y - 1);
        int endY = Math.min(7, Y + 1);

        for (int i = startX; i <= endX; i++) { // X Coordinate Loop
            for (int j = startY; j <= endY; j++) { // Y coordinate loop
                if (i == X && j == Y) continue; // Skip on checking the original piece
                if (board[i][j] instanceof Hill) {
                    hillFound = true;
                    break;
                }
            }
        }
        return hillFound;
    }
    private static int calculateProximityScore(int oldx, int oldy, int X, int Y,
                                               int enemyGeneralX, int enemyGeneralY) {
        // Check how much the gap between soldier and enemy general the move would close,
        // only allow score if move is in the direction of the enemy general

        // determine the direction of the move by comparing the old and new coordinates by mapping the orientation into
        // positive || negative quadrants depending on relativity

        // perspective of the old coordinates. move has to be in same direction as enemy general

        int xDirectionOldToMove = calculateOrientationValue(oldx, X);
        int yDirectionOldToMove = calculateOrientationValue(oldy, Y);
        int xDirectionOldToEnemy = calculateOrientationValue(oldx, enemyGeneralX);
        int yDirectionOldToEnemy = calculateOrientationValue(oldy, enemyGeneralY);

        // if the move is in the direction of the enemy general, we calculate the score
        if (xDirectionOldToMove == xDirectionOldToEnemy && yDirectionOldToMove == yDirectionOldToEnemy){
            int funcScore = 0; // Default score

            // the numbers of tiles in x and y that the piece X,Y is away from enemyGeneralX, enemyGeneralY is subtracted
            // from base
            int xDist = Math.abs(X - enemyGeneralX); // value => 0 to 8
            int yDist = Math.abs(Y - enemyGeneralY); // value => 0 to 7
            funcScore += xDist;
            funcScore += yDist;

            return funcScore;
        } else {
            return 0;
        }
    }

    private static int calculateDamageScore(Soldier[][] board, int x, int y, int oldx, int oldy) {
        // Implementation to calculate the score based on potential damage
        // in the Medium Bot, we will consider a base damage value of 2 per piece

        // if the current X Y Piece is artillery, we check at a distance of 2
        // if not, we check at a distance of 1

        int funcScore = 0; // Default score

        int distance = board[oldx][oldy] instanceof Artillery ? 2 : 1;

        int startX = Math.max(0, x - distance);
        int endX = Math.min(7, x + distance);

        int startY = Math.max(0, y - distance);
        int endY = Math.min(7, y + distance);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (i == x && j == y) continue; // Skip on checking the original piece
                if (!(board[i][j] instanceof Empty)){
                    if (!(board[i][j] instanceof Hill)){
                        String hurtColor = board[i][j].getTeamColor();
                        if (!hurtColor.equals("red")) {
                            funcScore += 2;
                        }
                    }
                }
            }
        }

        return funcScore;
    }

    private static int calculateDefensiveScore(Soldier[][] board, int X, int Y, int oldX, int oldY) {
       // a bonus is given if the piece has between 1 and 3 enemy pieces surrounding it.

        int funcScore = 5; // Default BonusScore

        // loop through surrounding by distance of 1
        int startX = Math.max(0, X - 1);
        int endX = Math.min(7, X + 1);

        int startY = Math.max(0, Y - 1);
        int endY = Math.min(7, Y + 1);

        int numberOfEnemies = 0;

        for (int i = startX; i <= endX; i++) { // X Coordinate Loop
            for (int j = startY; j <= endY; j++) { // Y coordinate loop
                if (i == X && j == Y) continue; // Skip on checking the original piece
                if (!(board[i][j] instanceof Empty)){
                    if (!(board[i][j] instanceof Hill)){
                        String hurtColor = board[i][j].getTeamColor();
                        if (!hurtColor.equals("red")) {
                            numberOfEnemies += 1;
                        }
                    }
                }
            }
        }

        if (numberOfEnemies > 0 && board[oldX][oldY] instanceof General){
            return -10;
        }

        if (numberOfEnemies <= 2 && numberOfEnemies >= 1){
            return funcScore;
        } else {
            return 0;
        }
    }

    private static int calculateClusteringScore(Soldier[][] board, int X, int Y) {
        // Implementation to calculate the score based on clustering of pieces

        // for each piece that is at a distance of 2, add 1 to the score
        // for each piece that is at a distance of 1, add 2 to the score
        // for each piece that is at any distance and a general, add 3

        int funcScore = 0; // Default score

        // variable for holding the current distance that is checked in the loop to the current Piece
        int curDistCheck;

        // loop through surrounding by distance of 1
        int startX = Math.max(0, X - 2);
        int endX = Math.min(7, X + 2);

        int startY = Math.max(0, Y - 2);
        int endY = Math.min(7, Y + 2);

        for (int i = startX; i <= endX; i++) { // X Coordinate Loop

            // nested if for deciding the distance to check
            if (i == X) {
                curDistCheck = 1;
                // if we are on the same row as the piece, we only check the tiles above and below
            } else if (i == X - 1 || i == X + 1) {
                curDistCheck = 1;
                // if we are on the row above or below, we only check the tiles above and below (curDistCheck = 1
            } else {
                curDistCheck = 2;
                // otherwise we check the tiles to the distance of 2
            }

            for (int j = startY; j <= endY; j++) { // Y coordinate loop
                if (i == X && j == Y) continue; // Skip on checking the original piece

                if (!(board[i][j] instanceof Empty)) {
                    if (!(board[i][j] instanceof Hill)) {

                        if (curDistCheck == 1) { // if one away
                            funcScore += 2; //  cluster generally

                        } else {
                            if (board[i][j] instanceof General) {
                                funcScore += 2; // if general 2 away, add 3

                            } else {
                                funcScore += 1; // if not general, add 1, so cluster generally with spaces
                            }

                        }
                    }
                }
            }
        }

        return funcScore;
    }

    public static int calculateOrientationValue(int C, int newC){
        // takes two Coordinates of one axis and displays their relative orientation by -1, native 0 or 1

        int DirectionCToNewC = C - newC;
        if (DirectionCToNewC > 0){
            DirectionCToNewC = 1;
        } else if (DirectionCToNewC < 0){
            DirectionCToNewC = -1;
        }

        return DirectionCToNewC;
    }
}

