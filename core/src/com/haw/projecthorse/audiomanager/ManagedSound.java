package com.haw.projecthorse.audiomanager;

import java.util.Arrays;

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
		if (state == muted)
			return;

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
		desiredVolume = 1f;
		return evalMethod(internal.play(desiredVolume, 1f, 0f));
		
	}

	@Override
	public long play(float volume) {
		return evalMethod(internal.play(desiredVolume, 1f, 0f));
		
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
		return evalMethod(internal.loop(desiredVolume, 1f, 0));
	}

	@Override
	public long loop(float volume) {
		return evalMethod(internal.loop(volume, 1f, 0));
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
		if (muted)
			internal.setVolume(soundId, 0f);
		else
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
		result = prime * result + MAXSIZE;
		result = prime * result + Float.floatToIntBits(desiredVolume);
		result = prime * result
				+ ((internal == null) ? 0 : internal.hashCode());
		result = prime * result + Arrays.hashCode(lastSounds);
		result = prime * result + (muted ? 1231 : 1237);
		result = prime * result + rbIndex;
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
		if (MAXSIZE != other.MAXSIZE)
			return false;
		if (Float.floatToIntBits(desiredVolume) != Float
				.floatToIntBits(other.desiredVolume))
			return false;
		if (internal == null) {
			if (other.internal != null)
				return false;
		} else if (!internal.equals(other.internal))
			return false;
		if (!Arrays.equals(lastSounds, other.lastSounds))
			return false;
		if (muted != other.muted)
			return false;
		if (rbIndex != other.rbIndex)
			return false;
		return true;
	}
}
