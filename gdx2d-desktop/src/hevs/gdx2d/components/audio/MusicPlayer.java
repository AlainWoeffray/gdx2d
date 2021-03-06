package hevs.gdx2d.components.audio;

import hevs.gdx2d.lib.utils.Logger;
import hevs.gdx2d.lib.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * A class to stream music without loading it within gdx2d.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.2
 */
public class MusicPlayer implements Disposable {
	protected Music s;
	protected float volume = 1.0f;

	/**
	 * Constructor
	 *
	 * @param file The file to be played, using internal path representation
	 */
	public MusicPlayer(String file) {
		try {
			s = Gdx.audio.newMusic(Gdx.files.internal(file));
			Utils.assertGdxLoaded("MusicPlayers can only be created in the onInit "
					+ "method of a class extending PortableApplication "
					+ "(or must be called from within this method)");

		} catch (GdxRuntimeException e) {
			Logger.error("Unable to load the music" + file);
			Gdx.app.exit();
		}
	}

	/**
	 * Changes volume of the song played
	 *
	 * @param v Should be between 0 and 1
	 */
	public void setVolume(float v) {
		if (v > 1.0f || v < 0.0f) {
			new UnsupportedOperationException(
					"Volume must be set in a range between 0 and 1");
		} else {
			volume = v;
		}
	}

	/**
	 * Starts playing the song
	 */
	public void play() {
		s.play();
	}

	/**
	 * Stops playing the song
	 */
	public void stop() {
		s.stop();
	}

	/**
	 * @return True if the music is playing
	 */
	public boolean isPlaying() {
		return s.isPlaying();
	}

	/**
	 * @return True if the music is currently looping (aka playing repeatedly)
	 */
	public boolean isLooping() {
		return s.isLooping();
	}

	/**
	 * Plays the song in loop, repeating forever
	 */
	public void loop() {
		s.play();
		s.setLooping(true);
	}

	/**
	 * Release resources when done working with them
	 */
	@Override
	public void dispose() {
		s.dispose();
	}

	/**
	 * Called when the object is destroyed
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
}
