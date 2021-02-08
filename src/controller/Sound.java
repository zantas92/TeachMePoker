package controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound class containing all the sounds that the program uses.
 * 
 * @author Lykke Levin
 * @version 1.1
 *
 */
public class Sound {

	private static Media m = new Media(Sound.class.getResource("/sounds/cool_struttin'.mp3").toString());
	public static MediaPlayer mp = new MediaPlayer(m);
	public static AudioClip checkSound = new AudioClip(Sound.class.getResource("/sounds/checkMeSound.m4a").toString());
	public static AudioClip shuffleSound = new AudioClip(Sound.class.getResource("/sounds/cardShuffle.wav").toString());
	public static AudioClip singleCard = new AudioClip(Sound.class.getResource("/sounds/cardSlide8.wav").toString());
	public static AudioClip cardFold = new AudioClip(Sound.class.getResource("/sounds/cardShove3.wav").toString());
	public static AudioClip chipSingle = new AudioClip(Sound.class.getResource("/sounds/chipsStacksSingle.wav").toString());
	public static AudioClip chipMulti = new AudioClip(Sound.class.getResource("/sounds/ChipMe.m4a").toString());
	public static AudioClip coinSound = new AudioClip(Sound.class.getResource("/sounds/ChingChingChip.m4a").toString());
	public static AudioClip wrongSound = new AudioClip(Sound.class.getResource("/sounds/buttonSoundWrong.mp3").toString());
	private static double volume = 1;
	

	/**
	 * Plays the AudioClip.
	 * @param whatSound Name of sound that is being sent from the different classes that uses the audio objects.
	 */
	public static void playSound(String whatSound) {
		if (!mp.isMute()) {
			if (whatSound.equals("check")) {
				checkSound.play();
			} else if (whatSound.equals("fold")) {
				cardFold.play();
			} else if (whatSound.equals("shuffle")) {
				shuffleSound.play();
			} else if (whatSound.equals("singleCard")) {
				singleCard.play();
			} else if (whatSound.equals("chipSingle")) {
				chipSingle.play();
			} else if (whatSound.equals("chipMulti")) {
				chipMulti.play();
			} else if (whatSound.equals("coinSound")) {
				coinSound.play();
			} else if (whatSound.equals("wrong")) {
				wrongSound.play();
			}
		}
	}

	/**
	 * Starts playing the background music.
	 */
	public static void playBackgroundMusic() {
		mp.play();
	}


	/**
	 * Method created to be used in a future volume slider. Could be separated into two sliders for Music and SFX.
	 * @param newVolume the next volume setting.
	 */
	public static void setVolume(double newVolume){
		volume = newVolume;
		mp.setMute(false);
		mp.setVolume(volume / 100);
		checkSound.setVolume(volume);
		shuffleSound.setVolume(volume);
		singleCard.setVolume(volume);
		cardFold.setVolume(volume);
		chipSingle.setVolume(volume);
		chipMulti.setVolume(volume);
		coinSound.setVolume(volume);
		wrongSound.setVolume(volume);
	}

	/**
	 * Toggles the sound on and off.
	 */
	public static void toggleMute(){
		mp.setMute(!mp.isMute());
	}

	/**
	 * Stops the music.
	 */
	public static void stopMusic(){
		mp.stop();
	}

}
