package com.boomchess.game.frontend.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.frontend.interfaces.makeASoundInterface;
import com.boomchess.game.frontend.stage.MenuStage;

import static com.boomchess.game.BoomChess.reRenderGame;
import static com.boomchess.game.BoomChess.tileSize;


/*
* this class is the object for the dotted line that appears when a piece is selected. It acts as an Actor Object
* on a Scene2DUI Stage
*/
public class DottedLineActor extends Actor {
    // these values are the beginning and the end of the dotted Line


    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    // this is the time that the dotted line has been on the screen
    private float elapsed;
    // this is the maximum duration that the dotted line will be on the screen
    private static float MAX_DURATION = 1.25f;
    // this is the shapeRenderer that will be used to draw the dotted line
    private final ShapeRenderer shapeRenderer;

    private final boolean isDamage;

    // current State of the game
    private final BoomChess.GameState currentState;
    private final boolean coloursreversed;

    public DottedLineActor(int startX, int startY, int endX, int endY, ShapeRenderer shapeRenderer,
                           boolean isDamage, BoomChess.GameState currentState,
                           boolean coloursreversed) {
        /*
        * used to create a DottedLineActor Object
         */
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.shapeRenderer = shapeRenderer;
        this.isDamage = isDamage;
        this.currentState = currentState;
        this.coloursreversed = coloursreversed;
    }

    @Override
    public void act(float delta) {
        /*
        * this method is called every frame to update the DottedLineActor Object and overrides the standard act method
        * so that the dotted line will be removed after a certain amount of time
         */

        // this ensures that if the player has gone back to the menu, the sequence will not continue
        if (BoomChess.currentStage instanceof MenuStage){
            remove(); // This will remove the actor from the stage
            return;
        }

        super.act(delta);
        elapsed += delta;
        if (elapsed > MAX_DURATION) {
            if(!isDamage){
                reRenderGame();
            }
            remove(); // This will remove the actor from the stage
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end(); // To stop the batch temporarily because we'll be using the ShapeRenderer

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // set the color of the shapeRenderer to the color of the attacking piece (if Move, set white)
        if(isDamage) {
            // turn the current game stage enum into a string
            if (currentState.equals(BoomChess.GameState.GREEN_TURN)) {
                if (coloursreversed) {
                    shapeRenderer.setColor(Color.BLUE);
                } else {
                    shapeRenderer.setColor(Color.GREEN);

                }
            } else {
                shapeRenderer.setColor(Color.RED);
            }
        }
        else{
            shapeRenderer.setColor(Color.WHITE);
        }

        // logic for drawing the dotted line starts here ---------------------------------------

        // calculate Pixel Coordinates of attacker and defender
        Coordinates attacker = BoomChess.calculatePXbyTile(startX, startY);
        Coordinates defender = BoomChess.calculatePXbyTile(endX, endY);

        // if we are not doing an attack line, but a move line, change the time

        if (!isDamage) {
            // set the maximum duration fitting the length of the way that the soldier moves
            // per 50 pixel, add 0.5f to max duration
            // Begin: calculate Vector:

            Vector2 pointA = new Vector2(attacker.getX(), attacker.getY());
            Vector2 pointB = new Vector2(defender.getX(), defender.getY());
            Vector2 vectorAB = pointB.sub(pointA);

            float lengthVec = vectorAB.len();
            int timefactor = (int) lengthVec / 50;
            MAX_DURATION = timefactor * 0.75f;
        }

        // out of the Coordinate objects, get the PX
        float x1 = attacker.getX();
        float y1 = attacker.getY();
        float x2 = defender.getX();
        float y2 = defender.getY();


        // set dotLength and spaceLength
        float dotLength = tileSize/8;
        float spaceLength = tileSize/16;

        // to calculate the distance between the coordinates, create a simple Vector2 object
        float distance = Vector2.dst(x1, y1, x2, y2);
        // combine dotLength and spaceLength for easy drawing
        float dotSpaceLength = dotLength + spaceLength;
        // calculating the needed numbers of combined patterned Dots
        int numberOfDots = (int) (distance / dotSpaceLength);

        // drawing the dots using a for loop along the vector between the attacker and defender
        for (int i = 0; i < numberOfDots; i++) {
            float x = x1 + (x2 - x1) * (i * dotSpaceLength) / distance;
            float y = y1 + (y2 - y1) * (i * dotSpaceLength) / distance;
            float endX = x + (x2 - x1) * dotLength / distance;
            float endY = y + (y2 - y1) * dotLength / distance;

            shapeRenderer.rectLine(x, y, endX, endY, tileSize/16);  // 5 is the thickness of the line
        }

        // logic for drawing the dottedLine stops here ------------------------------------------

        shapeRenderer.end();

        // blue circle in the middle of a tile for debugging
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE); // Color of your choice
        Coordinates center = BoomChess.calculatePXbyTile(startX, startY);
        shapeRenderer.circle(center.getX(), center.getY(), tileSize/16);  // Draws a small circle
        shapeRenderer.end();

        batch.begin(); // Restart the batch for subsequent actors or UI elements
    }

    public void makeSound(){
        // calls the soldier object at the start position of the dotted line and plays its makeASound Method
        Soldier[][] board = Board.getGameBoard();

        Soldier mySoldata = board[startX][startY];


        if (mySoldata instanceof makeASoundInterface) {
            ((makeASoundInterface) mySoldata).makeASound();
        }
    }
}