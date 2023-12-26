package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;

import static com.boomchess.game.BoomChess.*;

public class MenuStage extends Stage{

    public static Stage initializeUI() {
        BoomChess.showMove = false;

        // setOverlay to false
        BoomChess.renderOverlay = false;

        Stage menuStage = new Stage();
        Gdx.input.setInputProcessor(menuStage);

        // start menu music
        // BoomChess.loadingSound.stop();
        background_music.stop();
        menu_music.setLooping(true);
        menu_music.play();
        menu_music.setVolume(volume);

        // Begin of Main Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);
        menuStage.addActor(root);

        final Image title = boomLogo;
        title.setSize(tileSize*4, tileSize*4);
        root.add(title).top().padBottom(tileSize/4);
        root.row();

        TextButton helpButton = new TextButton("Help!", skin);
        root.add(helpButton).padBottom(tileSize/4);
        // if help button is pressed, create a new stage for the help information
        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // switch showHelp, if on, turn off, if off, turn on
                createHelpStage();
            }
        });
        root.row();

        TextButton ChallengesButton = new TextButton("Challenges", skin);
        root.add(ChallengesButton).padBottom(tileSize/4);
        ChallengesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.createChallengeStage();
            }
        });

        root.row();

        TextButton play2Button = new TextButton("Play 2 Player Game", skin);
        root.add(play2Button).padBottom(tileSize/4);

        play2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.setLooping(true);
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.RED_TURN;

                isBotMatch = false;

                // create the big game Board as an object of the Board class
                Board.initialise();
                if(isMedievalMode){
                    isBeepMode = true;
                }
                else{
                    isBeepMode = false;
                }

                showArm = false;

                inGame = true;
                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });
        root.row();

        TextButton playBotButton = new TextButton("Play Against Computer: " + botDifficulty, skin);
        root.add(playBotButton).padBottom(tileSize/40);
        playBotButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // create the first gameBoard
                Board.initialise();

                // stop menu music and start background_music
                menu_music.stop();
                background_music.setLooping(true);
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.RED_TURN;

                if(isMedievalMode){
                    isBeepMode = true;
                }
                else{
                    isBeepMode = false;
                }

                BoomChess.isBotMatch = true;

                // create the big game Board as an object of the Board class
                Board.initialise();

                showArm = true;

                inGame = true;
                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });
        root.row();

        // button to change bot difficulty
        // text that displays a text saying "Bot Difficulty"
        final TextButton botDifficultyText = new TextButton("Change Bot", skin);
        root.add(botDifficultyText).padBottom(tileSize/4);
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
                BoomChess.createMainMenuStage();
            }
        });
        root.row();

        // Change Mode button to switch medieval and modern
        String currentMode;
        if(isMedievalMode){
            currentMode = "Medieval";
        }
        else{
            currentMode = "Modern";
        }
        TextButton modeButton = new TextButton("Switch Mode: " + currentMode, skin);
        root.add(modeButton).padBottom(tileSize/4);
        modeButton.addListener(new ChangeListener() {
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
                BoomChess.createMainMenuStage();
            }
        });
        root.row();

        TextButton optionsButton = new TextButton("Options", skin);
        root.add(optionsButton).padBottom(tileSize/4);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.createOptionsStage();
            }
        });
        root.row();

        TextButton creditsButton = new TextButton("Credits", skin);
        root.add(creditsButton).padBottom(tileSize/4);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.createCreditsStage();
            }
        });
        root.row();

        TextButton exitButton = new TextButton("Exit", skin);
        root.add(exitButton).padBottom(tileSize/40).padRight(tileSize/4);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // for exiting the game
                Gdx.app.exit();
                // for ending all background activity on Windows systems specifically
                System.exit(0);
            }
        });
        root.row();

       return menuStage;
    }

}
