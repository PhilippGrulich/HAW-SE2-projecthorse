package com.haw.projecthorse.audiomanager;

import java.util.HashSet;
import java.util.Observable;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.settings.Settings;

public class AudioManagerImpl implements AudioManager{

	private boolean musicState, soundState;
	private HashSet<ManagedMusic> musicObjects;
	private HashSet<ManagedSound> soundObjects;
	private Settings settings;
	
	
	private static AudioManagerImpl instance = new AudioManagerImpl(); 
	private AudioManagerImpl(){
		settings = GameManagerFactory.getInstance().getSettings();
		settings.addObserver(this);
		musicState = settings.getMusicState();
		soundState = settings.getSoundState();
		musicObjects = new HashSet<ManagedMusic>();
		soundObjects = new HashSet<ManagedSound>();
	}
	
	@Override
	public void update(Observable o, Object arg) {
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
	public Sound getSound(String levelId, String name) {
		ManagedSound sound = new ManagedSound(levelId, name, !soundState, this);
		soundObjects.add(sound);
		return sound;
	}
	
	@Override
	public Music getMusic(String levelId, String name) {
		ManagedMusic music = new ManagedMusic(levelId, name, !musicState, this);
		musicObjects.add(music);
		return music;
	}
	
	public static AudioManager getInstance(){
		return instance;
	}
	
	void remove(Music music){
		musicObjects.remove(music);
	}
	
	void remove(Sound sound){
		soundObjects.remove(sound);
	}	
}
