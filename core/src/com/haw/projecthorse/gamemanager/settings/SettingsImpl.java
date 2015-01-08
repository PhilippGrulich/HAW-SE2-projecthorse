package com.haw.projecthorse.gamemanager.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Preferences;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

/**
 * Implementierung der Settings Klasse.
 * 
 * @author Viktor
 * @version 1
 */
public final class SettingsImpl extends Settings {

	// Konstanten für virtuelle Bildschirmgröße
	private static final int VIRTUALHIGHT = 1280;
	private static final int VIRTUALWIDTH = 720;

	// Objekt zum Laden und Speichern von Einstellungen
	private Preferences prefs;

	// Singleton Umsetzung
	private static SettingsImpl settingsInstance;

	/**
	 * Singleton Umsetzung Konstruktor.
	 */
	private SettingsImpl() {
		prefs = Gdx.app.getPreferences("SettingsPrefs");
	};

	/**
	 * Liefert eine Instanz der Klasse Settings.
	 * 
	 * @return Settings Ein Settings Object nach dem Singleton Pattern.
	 */
	public static SettingsImpl getInstance() {
		if (settingsInstance == null) {
			settingsInstance = new SettingsImpl();
		}
		return settingsInstance;
	};

	@Override
	public boolean getSoundState() {
		if (!prefs.contains("SoundState")) {
			setSoundState(true);
		}
		return prefs.getBoolean("SoundState");
	}

	@Override
	public void setSoundState(final boolean state) {
		if (prefs.contains("SoundState") && prefs.getBoolean("SoundState") != state) {
			setChanged();
		}
		prefs.putBoolean("SoundState", state);
		prefs.flush();
		notifyObservers();
	}

	@Override
	public boolean getMusicState() {
		if (!prefs.contains("MusicState")) {
			setMusicState(true);
		}
		return prefs.getBoolean("MusicState");
	}

	@Override
	public void setMusicState(final boolean state) {
		if (prefs.contains("MusicState") && prefs.getBoolean("MusicState") != state) {
			setChanged();
		}
		prefs.putBoolean("MusicState", state);
		prefs.flush();
		notifyObservers();
	}

	@Override
	public int getScreenWidth() {
		return Gdx.graphics.getWidth();

	}

	@Override
	public int getScreenHeight() {
		return Gdx.graphics.getHeight();
	}

	/**
	 * Wenn Sich die Orientation gedreht hat ändert sich ändert sich auch die
	 * Breite.
	 * 
	 * @return Breite
	 */
	@Override
	public int getVirtualScreenWidth() {
		if (GameManagerFactory.getInstance().getPlatform().getOrientation() == Orientation.Portrait) {
			return VIRTUALWIDTH;
		} else {
			return VIRTUALHIGHT;
		}

	}

	/**
	 * Wenn Sich die Orientation gedreht hat ändert sich ändert sich auch die
	 * Höhe.
	 * 
	 * @return Höhe
	 */
	@Override
	public int getVirtualScreenHeight() {
		if (GameManagerFactory.getInstance().getPlatform().getOrientation() == Orientation.Portrait) {
			return VIRTUALHIGHT;
		} else {
			return VIRTUALWIDTH;
		}
	}

	@Override
	public boolean getAccelerometerState() {
		if (prefs.contains("Accelerometer")) {
			return prefs.getBoolean("Accelerometer");
		}
		boolean isPossible = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		prefs.putBoolean("Accelerometer", isPossible);
		prefs.flush();
		return isPossible;
	}

	@Override
	public void setAccelerometerState(final boolean state) {
		if (prefs.contains("Accelerometer") && prefs.getBoolean("Accelerometer") != state) {
			setChanged();
		}
		prefs.putBoolean("Accelerometer", state);
		prefs.flush();
		notifyObservers();
	}

	@Override
	public void dispose() {
		settingsInstance = null;
	}

}
