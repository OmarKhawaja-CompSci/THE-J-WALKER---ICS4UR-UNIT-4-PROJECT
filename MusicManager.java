import java.io.File;
import javax.sound.sampled.*;

public class MusicManager {
    
    private Clip currentClip;
    private String currentTrack = "";

    public void play(String filePath) {
        // Don't restart if it's the same song already playing
        if (currentTrack.equals(filePath) && currentClip != null && currentClip.isRunning()) {
            return;
        }

        stop(); // Stop old music

        try {
            File musicFile = new File(filePath);
            if (musicFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioInput);
                currentClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop forever
                currentClip.start();
                currentTrack = filePath;
            } else {
                System.out.println("Error: Could not find " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
        }
    }
}