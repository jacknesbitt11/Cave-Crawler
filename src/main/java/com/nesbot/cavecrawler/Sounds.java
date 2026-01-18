package com.nesbot.cavecrawler;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {
    private static Clip walking;
    private static Clip pickup;
    private static Clip win;
    private static Clip lose;
    private static Clip rock;

    public static void loadSounds(){
        walking = load("walking.wav");
        pickup = load("pickup.wav");
        win = load("win.wav");
        lose = load("lose.wav");
        rock = load("rock.wav");
    }

    private static Clip load(String file){
        try {
            InputStream input = Sounds.class.getClassLoader().getResourceAsStream(file);
            AudioInputStream sound = AudioSystem.getAudioInputStream(input);
            Clip clip = AudioSystem.getClip();
            clip.open(sound);

            return clip;

        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void walkingSound(){
        walking.setFramePosition(0);
        walking.start();
    }

    public static void pickup() {
        pickup.setFramePosition(0);
        pickup.start();
    }

    public static void win() {
        win.setFramePosition(0);
        win.start();
    }
    public static void lose() {
        lose.setFramePosition(0);
        lose.start();
    }
    public static void rockBreak() {
        rock.setFramePosition(0);
        rock.start();
    }
}