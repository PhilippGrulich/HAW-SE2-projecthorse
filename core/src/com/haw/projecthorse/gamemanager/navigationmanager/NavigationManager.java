package com.haw.projecthorse.gamemanager.navigationmanager;

import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;

/**
 * Der Navigation Manager regelt die Navigationsschritte innerhalb des Spiels.
 * z.B. das Navigieren von einem Level zu einem anderen. Außerdem liefert der
 * NavigationManager auch Informationen über die einzelnen Levels.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public interface NavigationManager {

	/**
	 * Navigiert von einem Level zu einem anderen. *
	 * 
	 * @param levelID
	 *            Level ID des Levels
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
	 * Navigiert zu dem vorherigen Level.
	 */
	void navigateBack();

	/**
	 * Über diese Methode können Informationen zu einer Stadt erhalten werden.
	 * 
	 * @param levelID
	 *            LevelID der Stadt
	 * @return CityObject Datei der Stadt
	 * @throws LevelNotFoundException
	 *             Wenn die Stadt nicht vorhanden ist. *
	 */
	CityObject getCityObject(String levelID) throws LevelNotFoundException;

	/**
	 * Über diese Methode können Informationen zu einem Spiel erhalten werden.
	 * 
	 * @param levelID
	 *            LevelID des Spiels
	 * @return GameObject Datei des Spiels
	 * @throws LevelNotFoundException
	 *             Wenn das Spiel nicht vorhanden ist. *
	 */
	GameObject getGameObject(String levelID) throws LevelNotFoundException;

	/**
	 * Über diese Methode können Informationen zu einem Menü erhalten werden.
	 * 
	 * @param levelID
	 *            LevelID des Menüs
	 * @return GameObject Datei des Menüs
	 * @throws LevelNotFoundException
	 *             Wenn das Menü nicht vorhanden ist. *
	 */
	MenuObject getMenuObject(String levelID) throws LevelNotFoundException;

	/**
	 * Liefert das Root Config element. Dies entfällt alle Information aus der
	 * GameConfig.json
	 * 
	 * @return GameConfig
	 */
	GameConfig getGameConfig();

	/**
	 * Liefert die aktuelle Level ID.
	 * 
	 * @return LevelID String
	 */
	String getCurrentLevelID();

}
