package com.haw.projecthorse.audiomanager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.haw.projecthorse.assetmanager.AssetManager;

public class ManagedMusic implements Music{

	private Sound internal;
	private float desiredVolume;
	boolean muted;
	private AudioManagerImpl manager; 
	
	// Variablen für Ringpuffer. Dieser wird genutzt um sich die 
	// zuletzt abgespielten Sound zu merken
	private long[] lastMusics;
	private int rbIndex;
	private final int MAXSIZE = 5;

	public ManagedMusic(String levelId, String name, boolean muted, AudioManagerImpl manager) {
		internal = AssetManager.getSound(levelId, name);
		this.muted = muted;
		desiredVolume = 1f;
		this.manager = manager;
		
		lastMusics = new long[MAXSIZE];
		rbIndex = 0;
	}

	private long evalMethod(long value){
		if (value == -1)
			return value;
		lastMusics[rbIndex] = value;
		rbIndex = (rbIndex + 1) % MAXSIZE;
		return value;
	}
	
	void setMuted(boolean state) {
		if (state == muted)
			return;

		muted = state;
		for (long music : lastMusics){
			if (muted)
				internal.setVolume(music, 0f);
			else
				internal.setVolume(music, desiredVolume);
		}
	}

	
	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLooping(boolean isLooping) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLooping() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setVolume(float volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPan(float pan, float volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOnCompletionListener(OnCompletionListener listener) {
		// TODO Auto-generated method stub
		
	}

}
