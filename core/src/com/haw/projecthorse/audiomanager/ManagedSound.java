package com.haw.projecthorse.audiomanager;

import com.badlogic.gdx.audio.Sound;
import com.haw.projecthorse.assetmanager.AssetManager;

public class ManagedSound implements Sound {

	private Sound internal;
	private float desiredVolume;
	boolean muted;
	private AudioManagerImpl manager; 
	
	// Variablen für Ringpuffer. Dieser wird genutzt um sich die 
	// zuletzt abgespielten Sound zu merken
	private long[] lastSounds;
	private int rbIndex;
	private final int MAXSIZE = 5;

	public ManagedSound(String levelId, String name, boolean muted, AudioManagerImpl manager) {
		internal = AssetManager.getSound(levelId, name);
		this.muted = muted;
		desiredVolume = 1f;
		this.manager = manager;
		
		lastSounds = new long[MAXSIZE];
		rbIndex = 0;
	}

	private long evalMethod(long value){
		if (value == -1)
			return value;
		lastSounds[rbIndex] = value;
		rbIndex = (rbIndex + 1) % MAXSIZE;
		return value;
	}
	
	void setMuted(boolean state) {
		muted = state;
		for (long sound : lastSounds){
			if (muted)
				internal.setVolume(sound, 0f);
			else
				internal.setVolume(sound, desiredVolume);
		}
	}
	
	@Override
	public long play() {
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.play(realVolume));
		
	}

	@Override
	public long play(float volume) {
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.play(realVolume, 1f, 0f));
		
	}

	@Override
	public long play(float volume, float pitch, float pan) {
		desiredVolume = volume;
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.play(realVolume, pitch, pan));
	}

	@Override
	public long loop() {
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.loop(realVolume));
	}

	@Override
	public long loop(float volume) {
		desiredVolume = volume;
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.loop(realVolume));
	}

	@Override
	public long loop(float volume, float pitch, float pan) {
		desiredVolume = volume;
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.loop(realVolume, pitch, pan));
	}

	@Override
	public void stop() {
		internal.stop();

	}

	@Override
	public void pause() {
		internal.pause();

	}

	@Override
	public void resume() {
		internal.resume();

	}

	@Override
	public void dispose() {
		manager.remove(this);
		internal.dispose();
	}

	@Override
	public void stop(long soundId) {
		internal.stop(soundId);
	}

	@Override
	public void pause(long soundId) {
		internal.pause(soundId);
	}

	@Override
	public void resume(long soundId) {
		internal.resume(soundId);
	}

	@Override
	public void setLooping(long soundId, boolean looping) {
		internal.setLooping(soundId, looping);
	}

	@Override
	public void setPitch(long soundId, float pitch) {
		internal.setPitch(soundId, pitch);
	}

	@Override
	public void setVolume(long soundId, float volume) {
		desiredVolume = volume;
		if (!muted)
			internal.setVolume(soundId, volume);

	}

	@Override
	public void setPan(long soundId, float pan, float volume) {
		if (muted)
			internal.setPan(soundId, pan, 0f);
		else
			internal.setPan(soundId, pan, volume);
	}

	@Override
	public void setPriority(long soundId, int priority) {
		internal.setPriority(soundId, priority);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((internal == null) ? 0 : internal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagedSound other = (ManagedSound) obj;
		if (internal == null) {
			if (other.internal != null)
				return false;
		} else if (!internal.equals(other.internal))
			return false;
		return true;
	}
}
