package com.haw.projecthorse.gamemanager.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsImpl implements Settings {
	
	// Objekt zum Laden und Speichern von Einstellungen
	private Preferences prefs;
	
	// Singleton Umsetzung
	private static final SettingsImpl settingsInstance = new SettingsImpl();
	private static final int heigth = 1280; //Not included in Prefs to avoid changes (final)
	private static final int width = 720; //Not included in Prefs to avoid changes (final)
	
	
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
		prefs.putBoolean("SoundState", state);
		prefs.flush();
	}

	@Override
	public int getScreenWidth() {
		return width;
		//return Gdx.graphics.getWidth(); //Current resolution . game should have fixed size

	}

	@Override
	public int getScreenHeight() {
		return heigth;
		//return Gdx.graphics.getHeight();
	}

}
