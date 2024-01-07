package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.tileSize;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Coordinates;

public class SpecialDamageIndicator extends Actor {
    public final int X;
    public final int Y;
    private final boolean isBoni;
    private final int value;

    public SpecialDamageIndicator(int value, int tileX, int tileY, boolean isBoni) {
        /*
         * used to create a DamageNumber Object
          */
        Coordinates coords = BoomChess.calculatePXbyTile(tileX, tileY);
        this.X = coords.getX();
        this.Y = coords.getY();

        setSize(tileSize, tileSize/2);
        setPosition(X, Y);

        this.value = value;
        this.isBoni = isBoni;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        // Use the actor's position and size for drawing
        float symbolX = getX(); // Use the actor's X position
        float symbolY = getY(); // Use the actor's Y position
        float width = getWidth(); // Use the actor's width
        float height = getHeight(); // Use the actor's height

        // Apply parent's alpha to this actor's alpha value
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        switch(value) {
            case 5:
                if(isBoni){
                    batch.draw(BoomChess.plusFive, symbolX, symbolY, width, height);
                } else {
                    batch.draw(BoomChess.minusFive, symbolX, symbolY, width, height);
                }
                break;
            case 10:
                if(isBoni){
                    batch.draw(BoomChess.plusTen, symbolX, symbolY, width, height);
                } else {
                    batch.draw(BoomChess.minusTen, symbolX, symbolY, width, height);
                }
                break;
        }

        // Resetting the batch color to white
        batch.setColor(Color.WHITE);

    }


}
