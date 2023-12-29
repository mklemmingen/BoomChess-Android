package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.*;

public class CreditsStage {

    public static Stage initializeUI() {
        Stage creditsStage = new Stage();

        // Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();

        // ---------------------- Credits Image ----------------------------------------------------------

        Image credits = new Image(BoomChess.credits);

        credits.setScaling(Scaling.fit); // Set scaling to fit

        // Wrap in a container for better control
        Container<Image> container = new Container<>(credits);
        container.setClip(true); // Enable clipping if necessary
        container.prefSize(tileSize*12, tileSize*7); // Set preferred size

        Stack creditsStack = new Stack();
        creditsStack.add(container); // Add container to the stack

        // Add stack to the root table and adjust layout
        root.add(creditsStack).center(); // Expand, fill, and center in the table cell
        root.row().left(); // Move to the next row for other UI elements

        //---------------------- Buttons -----------------------------------------------------------

        // extended credits button
        TextButton extendedCreditsButton = new TextButton("Sound Credits", skin);
        root.add(extendedCreditsButton).center().padBottom(tileSize/8);
        extendedCreditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                createExtendedCredits();
            }
        });

        root.row().left();

        // back button to return to the main menu
        TextButton backButton = new TextButton("Back", skin);
        root.add(backButton).center();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                BoomChess.createMainMenuStage();
            }
        });

        root.row().left();

        //------------------------------------------------------------------------------------------

        // Set root to expand and fill the stage
        root.setFillParent(true);

        creditsStage.addActor(root);

        return creditsStage;
    }

    public static void createExtendedCredits(){

        Stage creditsStage = new Stage();

        // Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();

        // ---------------------- Credits Image ----------------------------------------------------------

        Image credits = new Image(BoomChess.extendedCredits);

        credits.setScaling(Scaling.fit); // Set scaling to fit

        // Wrap in a container for better control
        Container<Image> container = new Container<>(credits);
        container.setClip(true); // Enable clipping if necessary
        container.prefSize(tileSize*12, tileSize*7); // Set preferred size

        Stack creditsStack = new Stack();
        creditsStack.add(container); // Add container to the stack

        // Add stack to the root table and adjust layout
        root.add(creditsStack).center(); // Expand, fill, and center in the table cell
        root.row().left(); // Move to the next row for other UI elements

        //---------------------- Buttons -----------------------------------------------------------

        // back button to return to the main menu
        TextButton backButton = new TextButton("Back", skin);
        root.add(backButton).center();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                createCreditsStage();
            }
        });

        root.row().left();

        //------------------------------------------------------------------------------------------

        // Set root to expand and fill the stage
        root.setFillParent(true);

        creditsStage.addActor(root);

        switchToStage(creditsStage);
    }
}
