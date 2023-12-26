package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.loadingSound;

public class LoadingScreenStage {

    public static Stage initalizeUI() {
        Stage loadingScreen = new Stage();

        // Begin of Options LoadingScreen Layout - Root Table arranges content automatically and adaptively as ui-structure
        Stack root = new Stack();
        root.setFillParent(true);
        Image picture = new Image(BoomChess.loadingScreenTextures.getRandomTexture());
        picture.setVisible(true);
        // set size by calling upon gdx getwidth and getheight
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        picture.setSize(width, height);
        root.setSize(width, height);
        root.add(picture);

        loadingScreen.addActor(root);
        return loadingScreen;
    }
}
