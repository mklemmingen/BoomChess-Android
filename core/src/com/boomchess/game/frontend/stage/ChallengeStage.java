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
        TextButton challenge1Button = new TextButton("Challenge 1", skin);
        root.add(challenge1Button).padBottom(tileSize/4);
        challenge1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                // stop menu music and start background_music
                menu_music.stop();
                background_music.setLooping(true);
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.RED_TURN;

                isBotMatch = true;

                // create the first gameBoard
                Board.initialiseChallengeOne();

                showArm = true;

                inGame = true;
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
