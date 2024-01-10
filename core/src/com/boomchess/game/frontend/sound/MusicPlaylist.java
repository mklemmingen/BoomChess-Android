package com.boomchess.game.frontend.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.boomchess.game.BoomChess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicPlaylist {
    /*
     * MusicPlaylist.java is the object for the music playlist in the game Boom Chess.
     * It holds the playlist of music and the methods to play, pause, resume, and dispose of the music.
     */
    private final List<Song> songs;
    private int currentIndex = 0; // Initialize to -1 to indicate no song has been played yet

    public MusicPlaylist() {
        /*
        * Constructor for MusicPlaylist.java
        * does not take any arguments.
        */
        songs = new ArrayList<>();
    }

    public void addSong(String fileName, String songName, String artistName) {
        /*
         * addSong adds a song, given a fileName/Direction to a Music Object, to the playlist.
         * Adds a listener to go to the next song when the current song is finished.
         */
        Music song = Gdx.audio.newMusic(Gdx.files.internal(fileName));
        song.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                nextSong();
            }
        });
        songs.add(new Song(songName, artistName, song));
    }

    public void play() {

        songs.get(currentIndex).getSong().stop();

        int randomIndex;
        if(songs.size() == 1){
            // if there is only one song in the playlist, then it is played,
            // prevents endless loop
            randomIndex = 0;
        }
        else {
            do {
                randomIndex = new Random().nextInt(songs.size());
            } while (randomIndex == currentIndex);
        }

        currentIndex = randomIndex;
        Music playSong = songs.get(currentIndex).getSong();

        AddToStage();

        playSong.play();
        playSong.setVolume(BoomChess.volume);
    }

    private void AddToStage() {
        /*
         * adds the song name to the music label
         */

        BoomChess.musicLabel.setText(songs.get(currentIndex).getSongName() + "\n" +
                songs.get(currentIndex).getArtistName());
    }


    public void pause() {
        if (!songs.isEmpty()) {
            songs.get(currentIndex).getSong().pause();
        }
    }

    public void resume() {
        if (!songs.isEmpty()) {
            songs.get(currentIndex).getSong().play();
        }
    }

    public void nextSong() {
        /*
         * nextSong goes to the next song in the playlist.
         * Responds to the isLooping variable
         */

        songs.get(currentIndex).getSong().stop();
        play();
    }

    public void stop() {
        if (!songs.isEmpty()) {
            songs.get(currentIndex).getSong().stop();
        }
    }

    public void setVolume(float volume){
        // can take but must not take a int value, if there is one, it is taken as the volume for setVolume
        if (!songs.isEmpty()) {
            songs.get(currentIndex).getSong().setVolume(volume);
        }
    }

    public void setLooping(boolean isLooping){
        /*
        * setLooping never lets the playlist end. It loops the playlist.
         */

    }

    public boolean isPlaying() {
    /*
    * isPlaying returns a boolean value of whether a song is playing.
    */
        if (!songs.isEmpty()) {
            return songs.get(currentIndex).getSong().isPlaying();
        }
        return false;
    }

    public void dispose() {
        for (Song song : songs) {
            song.dispose();
        }
    }
}
