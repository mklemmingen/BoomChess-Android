package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.tileSize;

public class MapStage {
    /*
    * MapStage delivers the map to the user by creating one and calling upon certain established parameters of BoomChess
     */

    // parameter holds last used random image
    private static Image currentMap;

    public static Stage initializeUI(boolean isResized) {
        /*
         * initializeUI is called when the mapStage should be created
         */

        Stage mapStage = new Stage(new ScreenViewport());

        // select map out of the list of maps
        // the boolean isResized ensures that the mapImage is not changed when the screen is resized
        Image map;
        if(!isResized) {
            if (BoomChess.isMedievalMode) {
                currentMap = new Image(BoomChess.medievalMaps.getRandomTexture());
            } else { // if not medieval mode
                currentMap = new Image(BoomChess.modernMaps.getRandomTexture());
            }
        }
        map = currentMap;
        map.setSize(tileSize*9, tileSize*8);
        // Center the map on the screen
        map.setPosition((float) Gdx.graphics.getWidth() /2 - map.getWidth()/2,
                (float) Gdx.graphics.getHeight() /2 - map.getHeight()/2);

        // Add a gray hue to the map
        map.setColor(0.8f, 0.8f, 0.8f, 1f);  // apply a grey tint to the map so the figures are easier visible

        mapStage.addActor(map);

        return mapStage;
    }
}
