package com.haw.projecthorse.gamemanager.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsImpl extends Settings {
	
	// Konstanten für virtuelle Bildschirmgröße 
	private static final int VIRTUALHIGHT = 1280; 
	private static final int VIRTUALWIDTH = 720; 

	
	// Objekt zum Laden und Speichern von Einstellungen
	private Preferences prefs;
		
	// Singleton Umsetzung
	private static final SettingsImpl settingsInstance = new SettingsImpl();
	
	private SettingsImpl(){
		prefs = Gdx.app.getPreferences("SettingsPrefs");
	};
	
	/**
	 * Liefert eine Instanz der Klasse Settings
	 * @return Settings Ein Settings Object nach dem Singleton Pattern.
	 */
	public static SettingsImpl getInstance(){
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
		return VIRTUALWIDTH;
	}

	@Override
	public int getVirtualScreenHeight() {
		return VIRTUALHIGHT;
	}

		

}
