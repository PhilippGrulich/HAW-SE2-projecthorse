package com.haw.projecthorse.gamemanager;

import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;
import com.haw.projecthorse.gamemanager.settings.Settings;

/**
 * Der GameManager ist ein Singelton und für die Kommunikation zwischen den einzelnen Modulen 
 * zuständig.
 * Die Konfiguration wird aus der GameConfig.json geladen.
 */
public interface GameManager {
	
	/**
	 * Navigiert zur letzten LevelID
	 * 
	 * @param String
	 *            leveLID
	 */
	void navigateBack();
	
	/**
	 * Navigiert zu einer Stadt oder einem Spiel welches anhand der LevelID
	 * (String) identifiziert wird.
	 * 
	 * @param String
	 *            leveLID
	 */
	void navigateToLevel(String levelID);
	
	/**
	 * Navigiert zur Weltkarte
	 */
	void navigateToWorldMap();
	
	/**
	 * Navigiert zum MainMenu
	 */
	void navigateToMainMenu();
	
	/** 
	 * Liefert das GameConfig Object zurück. Über dieses Object können Informationen über das Spiel abgefragt werden.
	 */
	GameConfig getGameConfig();
	
	/** 
	 * Liefert das CityObject zurück falls die LevelID existiert.
	 * Wenn nicht wird eine LevelNotFoundException geworfen.
	 * @param levelID
	 * @return {@link CityObjectImpl}
	 * @throws LevelNotFoundException
	 */
	CityObject getCityObject(String levelID) throws LevelNotFoundException;

	/** 
	 * Liefert das GameObject zurück falls die LevelID existiert.
	 * Wenn nicht wird eine LevelNotFoundException geworfen.
	 * @param levelID
	 * @return {@link GameObjecttImpl}
	 * @throws LevelNotFoundException
	 */
	GameObject getGameObject(final String levelID) throws LevelNotFoundException;
	
	/** 
	 * Liefert das MenuObject zurück falls die LevelID existiert.
	 * Wenn nicht wird eine LevelNotFoundException geworfen.
	 * @param levelID
	 * @return {@link MenuObject}
	 * @throws LevelNotFoundException
	 */
	MenuObject getMenuObject(final String levelID) throws LevelNotFoundException;
	
	
	/**
	 * Liefert eine Referenz auf das Spielstand Modul.	 * 
	 * @return Spielstand
	 */
	Object getScoreManager();

	/**
	 * Liefert eine Referenz auf das Settings Modul.	 * 
	 * @return {@link Settings}
	 */
	Settings getSettings();
	
	/**
	 * Liefert die aktuelle LevelID zurück
	 * @return
	 */
	String getCurrentLevelID();


	
}