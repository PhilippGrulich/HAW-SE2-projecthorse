package com.haw.projecthorse.gamemanager;

import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;
import com.haw.projecthorse.gamemanager.settings.Settings;
import com.haw.projecthorse.platform.Platform;

/**
 * Der GameManager ist ein Singleton und für die Kommunikation zwischen den
 * einzelnen Modulen zuständig. Die Konfiguration wird aus der GameConfig.json
 * geladen.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public interface GameManager {

	/**
	 * Navigiert zu dem vorherigen Level.
	 */
	void navigateBack();

	/**
	 * Navigiert zu einer Stadt oder einem Spiel welches anhand der LevelID
	 * (String) identifiziert wird.
	 * 
	 * @param levelID
	 *            LevelID
	 * 
	 */
	void navigateToLevel(String levelID);

	/**
	 * Navigiert zur Worldmap. Als Verkürzung zum navigateToLevel.
	 */
	void navigateToWorldMap();

	/**
	 * Navigiert zum MainMenu. Als Verkürzung zum navigateToLevel.
	 */
	void navigateToMainMenu();

	/**
	 * Liefert das GameConfig Object zurück. über dieses Object können
	 * Informationen über das Spiel abgefragt werden.
	 * 
	 * @return GameConfig
	 */
	GameConfig getGameConfig();

	/**
	 * Liefert das CityObject zurück falls die LevelID existiert. Wenn nicht
	 * wird eine LevelNotFoundException geworfen.
	 * 
	 * @param levelID
	 *            gesuchte LevelID
	 * @return {@link CityObjectImpl}
	 * @throws LevelNotFoundException
	 *             Wenn die Stadt nicht vorhanden ist.
	 */
	CityObject getCityObject(String levelID) throws LevelNotFoundException;

	/**
	 * Liefert das GameObject zurück falls die LevelID existiert. Wenn nicht
	 * wird eine LevelNotFoundException geworfen.
	 * 
	 * @param levelID
	 *            gesuchte LevelID
	 * @return {@link GameObjecttImpl}
	 * @throws LevelNotFoundException
	 *             Wenn das Spiel nicht vorhanden ist.
	 */
	GameObject getGameObject(final String levelID) throws LevelNotFoundException;

	/**
	 * Liefert das MenuObject zurück falls die LevelID existiert. Wenn nicht
	 * wird eine LevelNotFoundException geworfen.
	 * 
	 * @param levelID
	 *            gesuchte LevelID
	 * @return {@link MenuObject}
	 * @throws LevelNotFoundException
	 *             Wenn das Menü nicht vorhanden ist.
	 */
	MenuObject getMenuObject(final String levelID) throws LevelNotFoundException;

	/**
	 * Liefert eine Referenz auf das Spielstand Modul. *
	 * 
	 * @return Spielstand
	 */
	Object getScoreManager();

	/**
	 * Liefert eine Referenz auf das Settings Modul. *
	 * 
	 * @return {@link Settings}
	 */
	Settings getSettings();

	/**
	 * Liefert die aktuelle LevelID zurück.
	 * 
	 * @return LevelID
	 */
	String getCurrentLevelID();

	/**
	 * Liefert die Implementierung des Platform Interfaces zurück.
	 * 
	 * @return Native Platform.
	 */
	Platform getPlatform();

}
