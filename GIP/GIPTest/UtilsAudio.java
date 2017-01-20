package GIP.GIPTest;

public class UtilsAudio extends Settings {

	public void init() {
		// Music settings
		SETTING_VOLUME_db = -6 + (20 * Math.log(SETTING_VOLUME / 10));
		musicPlay(audioTrack.AUDIO_TRACK_MENU.value, audioLine.AUDIOLINE_MAIN);
		
		try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
		
		if(hasAudio == false) {
			System.err.println("Client has no audio or audio issues!");
		} else {
			System.out.println("Audio is present");
		}	
	}

	/**
	 * TODO Make desc This function can get you any font within the font Dir
	 * 
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i
	 *            The font choice index
	 */
	public void musicPlay(String musicFile, audioLine line) {
		// New audio file player
		if (hasAudio) {
			if (DEBUG)
				System.out.println("Audio File : " + musicFile);
			audioPlayer = new Audio(musicFile);
			switch (line) {
			case AUDIOLINE_MAIN:
				audioLine1 = new Thread(audioPlayer, "Audio Thread : 1 - MAIN");
				audioLine1.start();
				break;
			case AUDIOLINE_1:
				audioLine1 = new Thread(audioPlayer, "Audio Thread : " + line.value);
				audioLine1.start();
				break;
			case AUDIOLINE_2:
				audioLine2 = new Thread(audioPlayer, "Audio Thread : " + line.value);
				audioLine2.start();
				break;
			case AUDIOLINE_3:
				audioLine3 = new Thread(audioPlayer, "Audio Thread : " + line.value);
				audioLine3.start();
				break;
			case AUDIOLINE_4:
				audioLine4 = new Thread(audioPlayer, "Audio Thread : " + line.value);
				audioLine4.start();
				break;
			default:
				System.err.println("Audio Line not supported. Playing Audio on Line 4");
				audioLine4 = new Thread(audioPlayer, "Audio Thread : " + line.value);
				audioLine4.start();
				break;
			}
		}

	}

	/**
	 * TODO Make desc This function can get you any font within the font Dir
	 * 
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i
	 *            The font choice index
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public void musicStop(audioLine line) {
		if (hasAudio) {
			switch (line) {
			case AUDIOLINE_MAIN:
				audioLine1.stop();
				break;
			case AUDIOLINE_1:
				audioLine1.stop();
				break;
			case AUDIOLINE_2:
				audioLine2.stop();
				break;
			case AUDIOLINE_3:
				audioLine3.stop();
				break;
			case AUDIOLINE_4:
				audioLine4.stop();
				break;
			default:
				System.err.println("Error stopping audio!");
				break;
			}

			if (DEBUG) {
				System.err.println("Audio Line : " + line.value + " force stopped.");
			}
		}

	}

	/**
	 * TODO Make desc This function can get you any font within the font Dir
	 * 
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i
	 *            The font choice index
	 */
	public void updateMusic(String track, audioLine line) {
		if (hasAudio) {
			if (!audioLine1.isAlive()) {
				uAudio.musicPlay(track, line);
			}
			// SETTING_VOLUME_db = 100 - 20 * Math.log((SETTING_VOLUME + 1) *
			// 100 - 99);
			SETTINGS[0] = -6 + (20 * Math.log(SETTING_VOLUME / 10));
		}
	}
}
