package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;

import static com.boomchess.game.BoomChess.*;

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

        /*

        // button to start the tenth challenge
        TextButton challenge10Button = new TextButton("Challenge 10", skin);
        root.add(challenge10Button).padBottom(tileSize/4);
        challenge10Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeTen();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the eleventh challenge
        TextButton challenge11Button = new TextButton("Challenge 11", skin);
        root.add(challenge11Button).padBottom(tileSize/4);
        challenge11Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeEleven();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the twelfth challenge
        TextButton challenge12Button = new TextButton("Challenge 12", skin);
        root.add(challenge12Button).padBottom(tileSize/4);
        challenge12Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeTwelve();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the thirteenth challenge
        TextButton challenge13Button = new TextButton("Challenge 13", skin);
        root.add(challenge13Button).padBottom(tileSize/4);
        challenge13Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeThirteen();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the fourteen challenge
        TextButton challenge14Button = new TextButton("Challenge 14", skin);
        root.add(challenge14Button).padBottom(tileSize/4);
        challenge14Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeFourteen();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

        // button to start the fifteen challenge
        TextButton challenge15Button = new TextButton("Challenge 15", skin);
        root.add(challenge15Button).padBottom(tileSize/4);
        challenge15Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.GREEN_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeFifteen();

                showArm = true;

                inGame = true;

                botDifficulty = "medium";

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });

        root.row();

         */


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
