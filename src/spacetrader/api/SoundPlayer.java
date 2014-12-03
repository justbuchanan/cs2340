package spacetrader.api;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by Philip on 12/02/2014.
 */
public class SoundPlayer {

    private static String clickPath = "../resources/sound/click.wav";

    public static void playClick() {
        playSound(clickPath);
    }

    private static void playSound(String path) {
        Media sound = new Media(SoundPlayer.class.getResource(path).toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.play();
    }
}
