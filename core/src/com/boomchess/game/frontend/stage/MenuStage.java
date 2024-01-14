package com.boomchess.game.frontend.stage;

import static com.boomchess.game.BoomChess.GameState;
import static com.boomchess.game.BoomChess.background_music;
import static com.boomchess.game.BoomChess.boomLogo;
import static com.boomchess.game.BoomChess.botDifficulty;
import static com.boomchess.game.BoomChess.currentState;
import static com.boomchess.game.BoomChess.inGame;
import static com.boomchess.game.BoomChess.inTutorial;
import static com.boomchess.game.BoomChess.isBeepMode;
import static com.boomchess.game.BoomChess.isBotMatch;
import static com.boomchess.game.BoomChess.isMedievalMode;
import static com.boomchess.game.BoomChess.menu_music;
import static com.boomchess.game.BoomChess.rollPaperSound;
import static com.boomchess.game.BoomChess.showArm;
import static com.boomchess.game.BoomChess.skin;
import static com.boomchess.game.BoomChess.switchToStage;
import static com.boomchess.game.BoomChess.tileSize;
import static com.boomchess.game.BoomChess.volume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;

public class MenuStage extends Stage{

    public static Stage initializeUI() {        BoomChess.showMove = false;

        // setOverlay to false
        BoomChess.renderOverlay = false;

        Stage menuStage = new Stage();
        Gdx.input.setInputProcessor(menuStage);

        // Begin of Main Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);
        menuStage.addActor(root);

        final Image title = boomLogo;
        title.setSize(tileSize*4, tileSize*4);
        root.add(title).top().padBottom(tileSize/4);
        root.row();

        TextButton TutorialButton = new TextButton("Tutorial", skin);
        root.add(TutorialButton).padBottom(tileSize/4);
        TutorialButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /*
                 * method for starting the tutorial
                 */

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(0.05f);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseTutorialBoard();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                inTutorial = true;

                switchToStage(GameStage.createGameStage(isBotMatch));

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

                rollPaperSound();
            }
        });
        root.row();

        TextButton playBotButton = new TextButton("Play Against Computer", skin);
        root.add(playBotButton).padBottom(tileSize/4);
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

                rollPaperSound();
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
        root.add(exitButton).padBottom(tileSize/4).padRight(tileSize/4);
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

       Gdx.app.log("MenuStage", "MenuStage initialized");
       return menuStage;
    }

}
