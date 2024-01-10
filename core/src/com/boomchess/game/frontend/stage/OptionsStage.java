package com.boomchess.game.frontend.stage;

import static com.boomchess.game.BoomChess.botDifficulty;
import static com.boomchess.game.BoomChess.createMapStage;
import static com.boomchess.game.BoomChess.createOptionsStage;
import static com.boomchess.game.BoomChess.isBeepMode;
import static com.boomchess.game.BoomChess.isColourChanged;
import static com.boomchess.game.BoomChess.isMedievalMode;
import static com.boomchess.game.BoomChess.numberObstacle;
import static com.boomchess.game.BoomChess.showArm;
import static com.boomchess.game.BoomChess.skin;
import static com.boomchess.game.BoomChess.soundVolumeLabel;
import static com.boomchess.game.BoomChess.tileSize;
import static com.boomchess.game.BoomChess.volumeLabel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;

public class OptionsStage extends Stage{

    public static Stage initalizeUI() {
        Stage optionsStage = new Stage();

        // Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);

        root.row().padBottom(tileSize/4);
        // button to change bot difficulty
        // text that displays a text saying "Bot Difficulty"
        String currentDif = botDifficulty;
        final TextButton botDifficultyText = new TextButton("Bot Difficulty: " + currentDif, skin);
        root.add(botDifficultyText);
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
        root.row().padBottom(tileSize/4);

        // button for changing the botMovingSpeed
        // 0 is very fast
        // 1 is fast
        // 2 is normal
        String currentSpeed;
        switch (BoomChess.botMovingSpeed) {
            case 0:
                currentSpeed = "Very Fast";
                break;
            case 1:
                currentSpeed = "Fast";
                break;
            default:
                currentSpeed = "Normal";
                break;
        }
        TextButton speedButton = new TextButton("Bot-Move-Speed: " + currentSpeed, skin);
        root.add(speedButton);
        speedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (BoomChess.botMovingSpeed) {
                    case 0:
                        BoomChess.botMovingSpeed = 1;
                        break;
                    case 1:
                        BoomChess.botMovingSpeed = 2;
                        break;
                    case 2:
                        BoomChess.botMovingSpeed = 0;
                        break;
                }
                BoomChess.createOptionsStage();
            }
        });
        root.row().padBottom(tileSize/4);

        // Change Mode button to switch medieval and modern
        String currentGameMode;
        if(isMedievalMode){
            currentGameMode = "Medieval";
        }
        else{
            currentGameMode = "Modern";
        }
        TextButton modeGameButton = new TextButton("Switch Mode: " + currentGameMode, skin);
        root.add(modeGameButton);
        modeGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if(isMedievalMode){
                    isMedievalMode = false;
                    isBeepMode = false;
                    createMapStage();
                }
                else{
                    isMedievalMode = true;
                    isBeepMode = true;
                    createMapStage();
                }
                BoomChess.createOptionsStage();
            }
        });
        root.row().padBottom(tileSize/4);

        // attack circle show
        // button for turning the attack circles on and off
        TextButton attackCircleButton = new TextButton("Attack Circles: " +
                BoomChess.showAttackCircle, skin);
        attackCircleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.showAttackCircle = !BoomChess.showAttackCircle;
                createOptionsStage();
            }
        });
        root.add(attackCircleButton);
        root.row().padBottom(tileSize/4);

        // text that displays a text saying "Number of Obstacles"
        final TextButton numberObstaclesText = new TextButton(
                "Change Number of Obstacles: " + numberObstacle, skin);
        numberObstaclesText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (numberObstacle) {
                    case 0:
                        numberObstacle = 1;
                        break;
                    case 1:
                        numberObstacle = 2;
                        break;
                    case 2:
                        numberObstacle = 3;
                        break;
                    case 3:
                        numberObstacle = 4;
                        break;
                    case 4:
                        numberObstacle = 5;
                        break;
                    case 5:
                        numberObstacle = 6;
                        break;
                    case 6:
                        numberObstacle = 7;
                        break;
                    case 7:
                        numberObstacle = 8;
                        break;
                    case 8:
                        numberObstacle = 9;
                        break;
                    case 9:
                        numberObstacle = 10;
                        break;
                    case 10:
                        numberObstacle = 0;
                        break;
                }
                BoomChess.createOptionsStage();
            }
        });
        root.add(numberObstaclesText);

         root.row().padBottom(tileSize/4);

        // Change Mode button to switch blue and green
        String currentMode;
        if(isColourChanged){
            currentMode = "Blue";
        }
        else{
            currentMode = "Green";
        }
        TextButton modeButton = new TextButton("Switch 1.Player Colour: " + currentMode, skin);
        root.add(modeButton);
        modeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                isColourChanged = !isColourChanged;
                BoomChess.createOptionsStage();
            }
        });
        root.row().padBottom(tileSize/4);

        // button to change the beep mode of the speech bubbles isBeepMode true or false
        String currentBeepMode;
        if(isBeepMode){
            currentBeepMode = "Arcade";
        }
        else{
            currentBeepMode = "Battlefield";
        }
        TextButton beepModeButton = new TextButton("Speech Bubbles: " + currentBeepMode, skin);
        root.add(beepModeButton);
        beepModeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                isBeepMode = !isBeepMode;
                BoomChess.createOptionsStage();
            }
        });
        root.row().padBottom(tileSize/4);

        // button for turning the arm on and off
        TextButton armButton = new TextButton("BotArm: " + showArm, skin);
        root.add(armButton);
        armButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showArm = !showArm;
                createOptionsStage();
            }
        });
        root.row().padBottom(tileSize/4);

        root.add(volumeLabel);
        root.row().padBottom(tileSize/4);

        root.add(soundVolumeLabel);
        root.row().padBottom(tileSize/4);

        // back button to return to the main menu
        TextButton backButton = new TextButton("Back", skin);
        root.add(backButton);
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
