package com.boomchess.game.frontend.picture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomImage {

    private final List<Texture> textures;
    private final Random random;

    public RandomImage() {
        textures = new ArrayList<>();
        random = new Random();
    }

    public void addTexture(String fileName) {
        try {
            Texture texture = new Texture(Gdx.files.internal(fileName));
            textures.add(texture);
        } catch (Exception e) {
            Gdx.app.error("RandomImage", "Error loading texture: " + fileName, e);
        }
    }

    public Texture getRandomTexture() {
        if (textures.isEmpty()) {
            throw new IllegalStateException("No textures available. " +
                    "Add textures using addTexture() before calling getRandomTexture().");
        }
        int index = random.nextInt(textures.size());
        return textures.get(index);
    }
}
