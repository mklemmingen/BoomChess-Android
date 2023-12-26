package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.skin;
import static com.boomchess.game.BoomChess.tileSize;

public class CreditsStage {

    public static Stage initializeUI() {
        Stage creditsStage = new Stage();

        // Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);
        creditsStage.addActor(root);

        // TODO add long list of names of people who worked on the game, what they did, sources and tools used

        // https://javadoc.io/doc/com.badlogicgames.gdx/gdx/latest/com/badlogic/gdx/scenes/scene2d/ui/TextArea.html
        // so called TextArea Widget used for displaying text in a scrollable box

        // back button to return to the main menu
        TextButton backButton = new TextButton("Back", skin);
        root.add(backButton).padBottom(tileSize/4);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                BoomChess.createMainMenuStage();
            }
        });
        root.row();
        return creditsStage;
    }
}
