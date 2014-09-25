package com.haw.projecthorse.gamemanager.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsImpl implements Settings {
	
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
		prefs.putBoolean("SoundState", state);
		prefs.flush();
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
