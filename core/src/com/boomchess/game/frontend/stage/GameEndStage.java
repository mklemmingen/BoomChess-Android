package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.*;

public class GameEndStage extends Stage{

    public static Stage initializeUI(String winnerTeamColour) {

        BoomChess.currentState = BoomChess.GameState.NOT_IN_GAME;

        Stage gameEndStage = new Stage();

        // Begin of GameEndLayout - Root Table arranges content automatically and adaptively as ui-structure
        final Table endRoot = new Table();
        endRoot.setFillParent(true);

        if(isColourChanged){
            if(winnerTeamColour.equals("green")){
                winnerTeamColour = "Blue";
            }
        }

        String coolShoutout;
        if(isMedievalMode){
            coolShoutout = "Huzzah! A King has found his final Rest!\n";
        } else {
            coolShoutout = "Hell yeah! A General has been neutralised!\n";
        }

        /*
        * Label to be created with WinnerTeamColour in mind, White Background and black font
         */
        TextButton winnerLabel = new TextButton("The " + winnerTeamColour + " Team won\n" + coolShoutout, skin);
        winnerLabel.setColor(Color.BLACK);
        endRoot.add(winnerLabel).padBottom(tileSize/4);
        endRoot.row();

    gameEndStage.addActor(endRoot);
    System.out.println("GameEndStage initialized\n");
    return gameEndStage;
    }
}
