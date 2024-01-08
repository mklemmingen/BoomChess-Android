package com.boomchess.game.frontend.stage;

import static com.boomchess.game.BoomChess.GameState;
import static com.boomchess.game.BoomChess.background_music;
import static com.boomchess.game.BoomChess.botDifficulty;
import static com.boomchess.game.BoomChess.currentState;
import static com.boomchess.game.BoomChess.inGame;
import static com.boomchess.game.BoomChess.isBotMatch;
import static com.boomchess.game.BoomChess.menu_music;
import static com.boomchess.game.BoomChess.showArm;
import static com.boomchess.game.BoomChess.skin;
import static com.boomchess.game.BoomChess.switchToStage;
import static com.boomchess.game.BoomChess.tileSize;
import static com.boomchess.game.BoomChess.volume;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;

public class ChallengeStage {

    public static Stage initializeUI() {
        Stage challengeStage = new Stage();

        // Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);

        // button to start the first challenge
        TextButton challenge1Button = new TextButton("Challenge 1: Learn to Think", skin);
        root.add(challenge1Button).padBottom(tileSize/4);
        challenge1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeOne();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the second challenge
        TextButton challenge2Button = new TextButton("Challenge 2: High Mountains", skin);
        root.add(challenge2Button).padBottom(tileSize/4);
        challenge2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();

                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeTwo();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the third challenge
        TextButton challenge3Button = new TextButton("Challenge 3: Artillery Hell", skin);
        root.add(challenge3Button).padBottom(tileSize/4);
        challenge3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeThree();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the fourth challenge
        TextButton challenge4Button = new TextButton("Challenge 4: Football Booms", skin);
        root.add(challenge4Button).padBottom(tileSize/4);
        challenge4Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeFour();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the fifth challenge
        TextButton challenge5Button = new TextButton("Challenge 5: Danger Doghouse", skin);
        root.add(challenge5Button).padBottom(tileSize/4);
        challenge5Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeFive();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the six challenge
        TextButton challenge6Button = new TextButton("Challenge 6: A long way Home", skin);
        root.add(challenge6Button).padBottom(tileSize/4);
        challenge6Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeSix();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the seventh challenge
        TextButton challenge7Button = new TextButton("Challenge 7: A game of Luck", skin);
        root.add(challenge7Button).padBottom(tileSize/4);
        challenge7Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeSeven();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the eigth challenge
        TextButton challenge8Button = new TextButton("Challenge 8: Carry a long Stick", skin);
        root.add(challenge8Button).padBottom(tileSize/4);
        challenge8Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeEight();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the ninth challenge
        TextButton challenge9Button = new TextButton("Challenge 9: Chaotic Slander", skin);
        root.add(challenge9Button).padBottom(tileSize/4);
        challenge9Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeNine();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
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

        challengeStage.addActor(root);
        return challengeStage;
    }
}
