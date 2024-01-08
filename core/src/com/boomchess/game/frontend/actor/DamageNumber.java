package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.calculatePXbyTile;
import static com.boomchess.game.BoomChess.tileSize;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Coordinates;

public class DamageNumber extends Actor {
    /*
     * DamageNumber.java is the object for the damage numbers in the game Boom Chess.
     * It holds one to two textures of each tileSize/2*tileSize/2 next to each other
     * to display a integer value of damage dealt
     * over time, in the act method, the alpha value is decreased and the y position increased by
     * a couple of pixels
     */

    // px coords
    private final float X;
    private final float Y;
    // elapsed time since addition to stage
    private float elapsed = 0;

    private float stepElapsed = 0;

    // this is the maximum duration that the bubble will be on the screen
    private static final float MAX_DURATION = 4f;
    // this is the stack of the bubble
    private final Table table;

    public DamageNumber(int X, int Y, int damage){
        /*
         * This function creates a damage number object that holds the damage number that is displayed
         */
        Coordinates coords = calculatePXbyTile(X, Y);
        this.X = coords.getX();
        this.Y = coords.getY();

        table = createNumberTable(damage);
    }

    private Table createNumberTable(int damage) {
        Table table = new Table();

        table.setPosition(X-tileSize/2, Y+tileSize/4);
        table.setSize(tileSize, tileSize/2);

        // table.setOrigin(tileSize/2, tileSize/4);

        table.getColor().a = 1f;

        if(damage < 10){
            // damage is only one digit
            Image digit = new Image(getTexture(damage));
            digit.setSize(tileSize/2, tileSize/2);
            table.add(digit);
            table.row();

        } else if (damage <= 99) {
            // put the int into two digits
            int firstDigit = damage / 10;
            int secondDigit = damage % 10;

            // damage is two digits
            Image digitFirst = new Image(getTexture(firstDigit));
            digitFirst.setSize(tileSize/2, tileSize/2);
            table.add(digitFirst);
            Image digitSecond = new Image(getTexture(secondDigit));
            digitSecond.setSize(tileSize/2, tileSize/2);
            table.add(digitSecond);
            table.row();
        } else {
            // damage is three digits
            int firstDigit = damage / 100;
            int secondDigit = (damage % 100) / 10;
            int thirdDigit = damage % 10;

            Image digitFirst = new Image(getTexture(firstDigit));
            digitFirst.setSize(tileSize/2, tileSize/2);
            table.add(digitFirst);
            Image digitSecond = new Image(getTexture(secondDigit));
            digitSecond.setSize(tileSize/2, tileSize/2);
            table.add(digitSecond);
            Image digitThird = new Image(getTexture(thirdDigit));
            digitThird.setSize(tileSize/2, tileSize/2);
            table.add(digitThird);
            table.row();
        }

        return table;
    }

    private Texture getTexture(int damage) {
        switch(damage){
            case 1:
                return BoomChess.one;
            case 2:
                return BoomChess.two;
            case 3:
                return BoomChess.three;
            case 4:
                return BoomChess.four;
            case 5:
                return BoomChess.five;
            case 6:
                return BoomChess.six;
            case 7:
                return BoomChess.seven;
            case 8:
                return BoomChess.eight;
            case 9:
                return BoomChess.nine;
            default:
                return BoomChess.zero;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsed += delta;
        stepElapsed += delta;
        float maxStepSize = 0.5f;
        if (stepElapsed > maxStepSize) {
            // change y position and alpha value
            table.moveBy(0, 4);
            table.getColor().a -= 0.1f;
        }

        if(elapsed > MAX_DURATION){
            remove(); // This will remove the actor from the stage
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        /*
         * This method is called every frame to draw the damage number
         */
        super.draw(batch, parentAlpha);
        table.draw(batch, parentAlpha);
    }

}
