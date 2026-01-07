import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class sounds {
    static File walking_sound_file = new File("src/res/walking.wav");
    static File pickup_sound_file = new File("src/res/pickup.wav");
    static File win_sound_file = new File("src/res/win.wav");
    static File lose_sound_file = new File("src/res/lose.wav");
    static File rock_sound_file = new File("src/res/rock.wav");


    public static void walkingSound() {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(walking_sound_file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(sound);
            clip.start();


        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void pickup() {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(pickup_sound_file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(sound);
            clip.start();


        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void win() {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(win_sound_file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(sound);
            clip.start();


        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
        }

    }
    public static void lose() {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(lose_sound_file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(sound);
            clip.start();


        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static void rockBreak() {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(rock_sound_file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(sound);
            clip.start();


        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
