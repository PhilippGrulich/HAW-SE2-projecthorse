package com.haw.projecthorse.gamemanager.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;
import com.haw.projecthorse.gamemanager.GameManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

public class SettingsImpl extends Settings {

	// Konstanten für virtuelle Bildschirmgröße
	private static final int VIRTUALHIGHT = 1280;
	private static final int VIRTUALWIDTH = 720;

	// Objekt zum Laden und Speichern von Einstellungen
	private Preferences prefs;

	// Singleton Umsetzung
	private static final SettingsImpl settingsInstance = new SettingsImpl();

	private SettingsImpl() {
		prefs = Gdx.app.getPreferences("SettingsPrefs");
	};

	/**
	 * Liefert eine Instanz der Klasse Settings
	 * 
	 * @return Settings Ein Settings Object nach dem Singleton Pattern.
	 */
	public static SettingsImpl getInstance() {
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
	public void setSoundState(boolean state) {
		if (prefs.contains("SoundState"))
			if (prefs.getBoolean("SoundState") != state)
				setChanged();
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
	public void setMusicState(boolean state) {
		if (prefs.contains("MusicState"))
			if (prefs.getBoolean("MusicState") != state)
				setChanged();
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

	@Override
	public int getVirtualScreenWidth() {
		if(GameManagerFactory.getInstance().getPlatform().getOrientation() == Orientation.Portrait)
			return VIRTUALWIDTH;
		else
			return VIRTUALHIGHT;
		
	}

	
	/**
	 * Wenn Sich die Orientation gedreht hat ändert sich ändert sich auch die Höhe
	 */
	@Override
	public int getVirtualScreenHeight() {
		if(GameManagerFactory.getInstance().getPlatform().getOrientation() == Orientation.Portrait)
			return VIRTUALHIGHT;
		else
			return VIRTUALWIDTH;
	}

	@Override
	public boolean getAccelerometerState() {
		if (prefs.contains("Accelerometer"))
			return prefs.getBoolean("Accelerometer");
		boolean isPossible = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		prefs.putBoolean("Accelerometer", isPossible);
		prefs.flush();
		return isPossible;
	}

	@Override
	public void setAccelerometerState(boolean state) {
		if (prefs.contains("Accelerometer"))
			if (prefs.getBoolean("Accelerometer") != state)
				setChanged();
		prefs.putBoolean("Accelerometer", state);
		prefs.flush();
		notifyObservers();
	}

}
