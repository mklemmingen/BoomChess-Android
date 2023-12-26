package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.*;

public class OptionsStage extends Stage{

    public static Stage initalizeUI() {
        Stage optionsStage = new Stage();

        // Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);

        // button to change bot difficulty
        // text that displays a text saying "Bot Difficulty"
        String currentDif = botDifficulty;
        final TextButton botDifficultyText = new TextButton("Bot Difficulty: " + currentDif, skin);
        root.add(botDifficultyText).padBottom(tileSize/2);
        botDifficultyText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (botDifficulty) {
                    case "easy":
                        botDifficulty = "medium";
                        break;
                    case "medium":
                        botDifficulty = "hard";
                        break;
                    case "hard":
                        botDifficulty = "easy";
                        break;
                }
                BoomChess.createOptionsStage();
            }
        });
        root.row();

        // text that displays a text saying "Number of Obstacles"
        final TextButton numberObstaclesText = new TextButton("Number of Obstacles 0-10", skin);
        root.add(numberObstaclesText).padBottom(tileSize/8);
        root.row();

        // slider for setting the number of obstacles in the initial no mans land
        final Slider obstacleSlider;
        obstacleSlider = new Slider(0, 10, 1f, false, skin);
        obstacleSlider.setValue(numberObstacle);
        root.add(obstacleSlider).padBottom(tileSize/2);
        obstacleSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.numberObstacle = obstacleSlider.getValue();
            }
        });
        root.row();

        // Change Mode button to switch blue and green
        String currentMode;
        if(isColourChanged){
            currentMode = "Blue";
        }
        else{
            currentMode = "Green";
        }
        TextButton modeButton = new TextButton("Switch 1.Player Colour: " + currentMode, skin);
        root.add(modeButton).padBottom(tileSize/2);
        modeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                isColourChanged = !isColourChanged;
                BoomChess.createOptionsStage();
            }
        });
        root.row();

        // button to change the beep mode of the speech bubbles isBeepMode true or false
        String currentBeepMode;
        if(isBeepMode){
            currentBeepMode = "Arcade";
        }
        else{
            currentBeepMode = "Battlefield";
        }
        TextButton beepModeButton = new TextButton("Speech Bubbles: " + currentBeepMode, skin);
        root.add(beepModeButton).padBottom(tileSize/2);
        beepModeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                isBeepMode = !isBeepMode;
                BoomChess.createOptionsStage();
            }
        });
        root.row();

        // button for turning the arm on and off
        root.row().padBottom(tileSize/8);
        TextButton armButton = new TextButton("BotArm: " + showArm, skin);
        root.add(armButton).padBottom(tileSize/2);
        armButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showArm = !showArm;
                createOptionsStage();
            }
        });

        root.row();


        // back button to return to the main menu
        TextButton backButton = new TextButton("Back", skin);
        root.add(backButton).padBottom(tileSize/4);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                BoomChess.createMainMenuStage();
            }
        });

        optionsStage.addActor(root);
        return optionsStage;
    }
}
