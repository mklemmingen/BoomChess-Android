package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.tileSize;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.boomchess.game.BoomChess;

public class HealthNumber {
    /*
     * HealthNumber.java is the object for the health numbers in the game Boom Chess.
     * They are displayed as part of
     */


    public static Container<Table> giveContainer(int standardHealth, int currentHealth) {
        /*
         * This function creates container that holds the health number that is displayed
         */

        Container<Table> container = new Container<Table>();
        container.setSize(tileSize, tileSize/2);

        Table table = new Table();
        // put the int into two digits
        int firstDigit = currentHealth / 10;
        int secondDigit = currentHealth % 10;

        // colour selector based on ratio of standardHealth to currentHealth
        float ratio = (float) currentHealth / standardHealth;

        Image digitFirst;
        Image digitSecond;
        switch(ratio < 0.25 ? 0 : ratio < 0.5 ? 1 : ratio < 0.75 ? 2 : 3){
            case 0:
                // RED
                digitFirst = new Image(getTexture(firstDigit, "red"));
                digitSecond = new Image(getTexture(secondDigit, "red"));
                break;
            case 1:
                // ORANGE
                digitFirst = new Image(getTexture(firstDigit, "orange"));
                digitSecond = new Image(getTexture(secondDigit, "orange"));
                break;
            case 2:
                // YELLOW
                digitFirst = new Image(getTexture(firstDigit, "yellow"));
                digitSecond = new Image(getTexture(secondDigit, "yellow"));
                break;
            default:
                // GREEN
                digitFirst = new Image(getTexture(firstDigit, "green"));
                digitSecond = new Image(getTexture(secondDigit, "green"));
                break;
        }

        // health is two digits next to each other
        digitFirst.setSize(tileSize / 2, tileSize / 2);
        table.add(digitFirst);
        digitSecond.setSize(tileSize / 2, tileSize / 2);
        table.add(digitSecond);
        table.row();


        container.setActor(table);

        return container;
    }

    private static Texture getTexture(int health, String color) {
        /*
         * This function returns the texture of the number that is displayed
         */
        switch (health) {
            case 1:
                switch(color){
                    case "red":
                        return BoomChess.redOne;
                    case "orange":
                        return BoomChess.orangeOne;
                    case "yellow":
                        return BoomChess.yellowOne;
                    default:
                        return BoomChess.greenOne;
                }
            case 2:
                switch(color){
                    case "red":
                        return BoomChess.redTwo;
                    case "orange":
                        return BoomChess.orangeTwo;
                    case "yellow":
                        return BoomChess.yellowTwo;
                    default:
                        return BoomChess.greenTwo;
                }
            case 3:
                switch(color){
                    case "red":
                        return BoomChess.redThree;
                    case "orange":
                        return BoomChess.orangeThree;
                    case "yellow":
                        return BoomChess.yellowThree;
                    default:
                        return BoomChess.greenThree;
                }
            case 4:
                switch(color){
                    case "red":
                        return BoomChess.redFour;
                    case "orange":
                        return BoomChess.orangeFour;
                    case "yellow":
                        return BoomChess.yellowFour;
                    default:
                        return BoomChess.greenFour;
                }
            case 5:
                switch(color){
                    case "red":
                        return BoomChess.redFive;
                    case "orange":
                        return BoomChess.orangeFive;
                    case "yellow":
                        return BoomChess.yellowFive;
                    default:
                        return BoomChess.greenFive;
                }
            case 6:
                switch(color){
                    case "red":
                        return BoomChess.redSix;
                    case "orange":
                        return BoomChess.orangeSix;
                    case "yellow":
                        return BoomChess.yellowSix;
                    default:
                        return BoomChess.greenSix;
                }
            case 7:
                switch(color){
                    case "red":
                        return BoomChess.redSeven;
                    case "orange":
                        return BoomChess.orangeSeven;
                    case "yellow":
                        return BoomChess.yellowSeven;
                    default:
                        return BoomChess.greenSeven;
                }
            case 8:
                switch(color){
                    case "red":
                        return BoomChess.redEight;
                    case "orange":
                        return BoomChess.orangeEight;
                    case "yellow":
                        return BoomChess.yellowEight;
                    default:
                        return BoomChess.greenEight;
                }
            case 9:
                switch(color){
                    case "red":
                        return BoomChess.redNine;
                    case "orange":
                        return BoomChess.orangeNine;
                    case "yellow":
                        return BoomChess.yellowNine;
                    default:
                        return BoomChess.greenNine;
                }
            default:
                switch(color){
                    case "red":
                        return BoomChess.redZero;
                    case "orange":
                        return BoomChess.orangeZero;
                    case "yellow":
                        return BoomChess.yellowZero;
                    default:
                        return BoomChess.greenZero;
                }
        }
    }
}