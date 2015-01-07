package com.haw.projecthorse.audiomanager;

import java.util.HashSet;
import java.util.Observable;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.settings.Settings;

/**
 * Implementierung der AudioManager Klasse als Singleton.
 * 
 * @author Viktor
 * @version 1
 *
 */
public final class AudioManagerImpl implements AudioManager {

	private boolean musicState, soundState;
	private HashSet<ManagedMusic> musicObjects;
	private HashSet<ManagedSound> soundObjects;
	private Settings settings;

	private static AudioManagerImpl instance;

	/**
	 * Privater Konstruktor für Singleton Umsetzung.
	 */
	private AudioManagerImpl() {
		settings = GameManagerFactory.getInstance().getSettings();
		settings.addObserver(this);
		musicState = settings.getMusicState();
		soundState = settings.getSoundState();
		musicObjects = new HashSet<ManagedMusic>();
		soundObjects = new HashSet<ManagedSound>();
	}

	/**
	 * Liefert eine Singleton Instanz.
	 * @return Instanz des AudioManagers
	 */
	public static AudioManager getInstance() {
		if (instance == null) {
			instance = new AudioManagerImpl();
		}
		return instance;
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (settings.getSoundState() != soundState) {
			soundState = !soundState;
			for (ManagedSound sound : soundObjects) {
				sound.setMuted(!soundState);
			}
		}

		if (settings.getMusicState() != musicState) {
			musicState = !musicState;
			for (ManagedMusic music : musicObjects) {
				music.setMuted(!musicState);
			}
		}

	}

	@Override
	public Sound getSound(final String levelId, final String name) {
		ManagedSound sound = new ManagedSound(levelId, name, !soundState, this);
		soundObjects.add(sound);
		return sound;
	}

	@Override
	public Music getMusic(final String levelId, final String name) {
		ManagedMusic music = new ManagedMusic(levelId, name, !musicState, this);
		musicObjects.add(music);
		return music;
	}

	/**
	 * Entfernt das Music Objekt aus der internen Verwaltung. 
	 * @param music Das Music Objekt
	 */
	void remove(final Music music) {
		musicObjects.remove(music);
	}

	/**
	 * Entfernt das Sound Objekt aus der internen Verwaltung. 
	 * @param sound Das Sound Objekt
	 */
	void remove(final Sound sound) {
		soundObjects.remove(sound);
	}

	@Override
	public void dispose() {
		instance = null;
	}
}
