package com.haw.projecthorse.audiomanager;

import com.badlogic.gdx.audio.Sound;
import com.haw.projecthorse.assetmanager.AssetManager;

/**
 * Diese Klasse stellt einen Wrapper für die LibGDX interne Sound Klasse dar.
 * 
 * @author Viktor
 * @version 1
 */
public class ManagedSound implements Sound {

	private Sound internal;
	private float desiredVolume;
	boolean muted;
	private AudioManagerImpl manager;

	// Variablen für Ringpuffer. Dieser wird genutzt um sich die
	// zuletzt abgespielten Sound zu merken
	private long[] lastSounds;
	private int rbIndex;
	private static final int MAXSIZE = 5;

	/**
	 * Default Konstruktor.
	 * 
	 * @param levelId
	 *            Der Level bzw. Ordnername in dem das Soundfile liegt
	 * @param name
	 *            Der Dateiname
	 * @param muted
	 *            Ton aus, wenn true, sonst ton an
	 * @param manager
	 *            Die AudioManager Instanz
	 */
	public ManagedSound(final String levelId, final String name,
			final boolean muted, final AudioManagerImpl manager) {
		internal = AssetManager.getSound(levelId, name);
		this.muted = muted;
		desiredVolume = 1f;
		this.manager = manager;

		lastSounds = new long[MAXSIZE];
		rbIndex = 0;
	}

	/**
	 * Verwaltet bei einem Aufruf der play(...) Methoden, die Sound IDs in der
	 * internen Struktur.
	 * 
	 * @param value
	 *            Die Sound ID auf der play(...) Methode
	 * @return Die Sound ID wird durchgeschleift
	 */
	private long evalMethod(final long value) {
		if (value == -1) {
			return value;
		}
		lastSounds[rbIndex] = value;
		rbIndex = (rbIndex + 1) % MAXSIZE;
		return value;
	}

	/**
	 * Schaltet den Ton ein oder aus.
	 * 
	 * @param state
	 *            Ton aus, wenn true, sonst ein.
	 */
	void setMuted(final boolean state) {
		muted = state;
		for (long sound : lastSounds) {
			if (muted) {
				internal.setVolume(sound, 0f);
			} else {
				internal.setVolume(sound, desiredVolume);
			}
		}
	}

	@Override
	public long play() {
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.play(realVolume));

	}

	@Override
	public long play(final float volume) {
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.play(realVolume, 1f, 0f));

	}

	@Override
	public long play(final float volume, final float pitch, final float pan) {
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
	public long loop(final float volume) {
		desiredVolume = volume;
		float realVolume;
		realVolume = muted ? 0f : desiredVolume;
		return evalMethod(internal.loop(realVolume));
	}

	@Override
	public long loop(final float volume, final float pitch, final float pan) {
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
	public void stop(final long soundId) {
		internal.stop(soundId);
	}

	@Override
	public void pause(final long soundId) {
		internal.pause(soundId);
	}

	@Override
	public void resume(final long soundId) {
		internal.resume(soundId);
	}

	@Override
	public void setLooping(final long soundId, final boolean looping) {
		internal.setLooping(soundId, looping);
	}

	@Override
	public void setPitch(final long soundId, final float pitch) {
		internal.setPitch(soundId, pitch);
	}

	@Override
	public void setVolume(final long soundId, final float volume) {
		desiredVolume = volume;
		if (!muted) {
			internal.setVolume(soundId, volume);
		}

	}

	@Override
	public void setPan(final long soundId, final float pan, final float volume) {
		if (muted) {
			internal.setPan(soundId, pan, 0f);
		} else {
			internal.setPan(soundId, pan, volume);
		}
	}

	@Override
	public void setPriority(final long soundId, final int priority) {
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
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ManagedSound other = (ManagedSound) obj;
		if (internal == null) {
			if (other.internal != null) {
				return false;
			}
		} else if (!internal.equals(other.internal)) {
			return false;
		}
		return true;
	}
}
