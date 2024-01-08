package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.botMovingStage;
import static com.boomchess.game.BoomChess.empty;
import static com.boomchess.game.BoomChess.reRenderGame;
import static com.boomchess.game.BoomChess.showArm;
import static com.boomchess.game.BoomChess.tileSize;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

public class moveBotTile {
    /*
     * This class is used to simulate the dragging of a piece by the bot. It slowly moves an image
     * of the soldier along the white line
     */

    // properties for beginning and end coordinates
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    private static Coordinates startPx;
    private static Coordinates endPx;

    // properties for elapsed time, boolean isMoving and current position
    private static float elapsedTime;
    private static float moveDuration;
    public static boolean isMoving;
    public boolean movingFinished;

    private static Image botArm;
    private static Stack soldierStack;

    public moveBotTile() {
        elapsedTime = 0;
        isMoving = false;
        movingFinished = false;
    }

    // method for starting the move
    public void startMove(int startX, int startY, int endX, int endY) {
        /*
         * Method to simulate a move (start of the Move)
         */

        // coordinates
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        // manipulate variables for GameStage that select an empty texture for a certain coordinate
        BoomChess.setEmptyCoordinate(startX, startY);

        // rerender GameStage with new Empty Variables
        reRenderGame();

        // set the maximum duration fitting the length of the way that the soldier moves
        // per 50 pixel, add 0.5f to max duration
        // Begin: calculate Vector:
        startPx = BoomChess.calculatePXbyTile(startX, startY);
        endPx = BoomChess.calculatePXbyTile(endX, endY);

        Vector2 pointA = new Vector2(startPx.getX(), startPx.getY());
        Vector2 pointB = new Vector2(endPx.getX(), endPx.getY());
        Vector2 vectorAB = pointB.sub(pointA);

        float lengthVec = vectorAB.len();
        int timefactor = (int) lengthVec / 50;

        // set the move duration based on setting in BoomChess
        switch(BoomChess.botMovingSpeed){
            case 0:
                moveDuration = timefactor * 0.1f;
                break;
            case 1:
                moveDuration = timefactor * 0.25f;
                break;
            case 2:
                moveDuration = timefactor * 0.5f;
                break;
            default:
                moveDuration = timefactor * 0.75f;
                break;
        }


        Soldier[][] gameBoard = Board.getGameBoard();

        Soldier soldier = gameBoard[startX][startY];
        // load the corresponding image through the Soldier Take Selfie Method
        Image soldierImage;
        if (soldier instanceof takeSelfieInterface) {
            soldierImage = new Image(((takeSelfieInterface) soldier).takeSelfie());
        } else {
            soldierImage = new Image(empty);
        }

        botArm = new Image(BoomChess.botArm);

        soldierStack = new Stack();

        soldierStack.setSize(BoomChess.tileSize, BoomChess.tileSize);
        soldierStack.setVisible(true);

        soldierImage.setSize(BoomChess.tileSize, BoomChess.tileSize);
        soldierImage.setVisible(true);

        // add SoldierImage to the widget and fill it
        soldierStack.add(soldierImage);

        soldierStack.setVisible(false);

        if(showArm) {
            botArm.setScale(0.75f);
            botArm.setSize(tileSize*13, tileSize*4);
            botArm.setVisible(false);
            botMovingStage.addActor(botArm);
        }

        BoomChess.botMovingStage.addActor(soldierStack);

        // setting boolean isMoving to true, since we started moving
        isMoving = true;
        // setting elapsedTime to zero, since time got set to zero
        elapsedTime = 0;
    }

    // method for updating the move
    public void update(float delta) {
        /*
         * updates the BotMove object every frame
         */

        if (isMoving) {
            elapsedTime += delta; // adding time to elapsed time variable

            if (elapsedTime < moveDuration) { // if the elapsed time has not reached the maximum duration

                // Interpolate position // could be option changed by user

                float progress = elapsedTime / moveDuration;

                int currentX = startPx.getX() + (int) ((endPx.getX() - startPx.getX()) * progress);
                int currentY = startPx.getY() + (int) ((endPx.getY() - startPx.getY()) * progress);


                // call the renderAt method to render the image at the current position
                renderAt(currentX, currentY);
            } else {
                // Move completed
                movingFinished = true;
                isMoving = false;
                BoomChess.resetEmptyCoordinate();
            }
        }
    }

    private static void renderAt(int currentX, int currentY) {
        /*
         * This method is used to render the image at the current position
         */

        currentX -= (int) (tileSize/2);
        currentY -= (int) (tileSize/2);

        soldierStack.setVisible(true);
        soldierStack.setPosition(currentX, currentY);
        if(showArm) {
            botArm.setVisible(true);
            botArm.setPosition(currentX + (0.75f * tileSize), currentY - tileSize);
        }
    }

    public boolean getIsMoving() {
        return isMoving;
    }

}
