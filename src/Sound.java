import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;;
public class Sound {
	
	private InputStream in;
	private Clip clip;
	private int counter;
	
	public Sound(String input, int counter) {
		try {
			this.counter = counter;
			this.in = new FileInputStream(input);
			this.clip = AudioSystem.getClip();
			InputStream buffered = new BufferedInputStream(in);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(buffered);
			clip.open(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void play() {
			if(counter == -1) clip.loop(clip.LOOP_CONTINUOUSLY);
			else clip.loop(counter);
	}
	
	
	public synchronized void stop() {
		clip.stop();
	}
	
}
