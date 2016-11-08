package GIP.GIPTest;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio implements Runnable {
	// size of the byte buffer used to read/write the audio stream
	private static final int BUFFER_SIZE = 4096;
	private String file = "";

	public Audio(String track) {
		this.file = track;
	}

	/**
	 * Play a given audio file.
	 * @param audioFilePath Path of the audio file.
	 */
	private void player() {
		InputStream in = Audio.class.getClass().getResourceAsStream(Settings.audioDir + file);
		try {			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(in));

			AudioFormat format = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

			SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

			audioLine.open(format);
						
			FloatControl volume = (FloatControl) audioLine.getControl(FloatControl.Type.MASTER_GAIN);
			
			if(Settings.DEBUG) {System.out.println("Audio started.");}

			audioLine.start();
			
			byte[] bytesBuffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
				volume.setValue((float) Math.min(volume.getMaximum(), Math.max(volume.getMinimum(), Settings.SETTING_VOLUME_db)));
				audioLine.write(bytesBuffer, 0, bytesRead);
			} 

			audioLine.drain();
			audioLine.close();
			audioStream.close();

			if(Settings.DEBUG) {System.out.println("Audio completed.");}

		} catch (UnsupportedAudioFileException ex) {
			System.err.println("The specified audio file is not supported.");
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			System.err.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		}catch (FileNotFoundException ex){
			System.err.println("Audio file : " + file + " not found.");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.err.println("Error playing the audio file.");
			ex.printStackTrace();
		}
	}

	public void run() {
		player();
	}
}
