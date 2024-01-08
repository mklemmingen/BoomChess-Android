package com.boomchess.game.frontend.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSound {
    private final List<Sound> sounds;
    private final Random random;

    public RandomSound() {
        sounds = new ArrayList<>();
        random = new Random();
    }

    public void addSound(String fileName) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(fileName));
        sounds.add(sound);
    }

    public void play(float volume) {
        if (!sounds.isEmpty()) {
            int index = random.nextInt(sounds.size());
            sounds.get(index).play(volume);
        }
    }

    public void dispose() {
        for (Sound sound : sounds) {
            sound.dispose();
        }
    }

    public void setVolume(float soundVolume) {
    }
}
