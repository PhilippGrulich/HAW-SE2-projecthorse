package com.haw.projecthorse.gamemanager;

import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManager;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;

/**
 * Der GameManager ist ein Singelton und für die Kommunikation zwischen den einzelnen Modulen 
 * zuständig.
 * Die Konfiguration wird aus der GameConfig.json geladen.
 */
public final class GameManager {
	private NavigationManager naviationManager;

	private static GameManager instance;

	private GameManager() {

	}

	/**
	 * Liefert eine Instanz des Gamemanagers zurück.	 * 
	 * @return {@link GameManager}
	 */
	public static GameManager getInstance() {
		if (GameManager.instance == null) {
			GameManager.instance = new GameManager();
		}
		return GameManager.instance;
	}

	/**
	 * Navigiert zu einer Stadt oder einem Spiel welches anhand der LevelID
	 * (String) identifiziert wird.
	 * 
	 * @param String leveLID
	 */
	public void navigateToLevel(final String levelID) {
		naviationManager.navigateToLevel(levelID);
	}

	/**
	 * Navigiert zur Weltkarte
	 */
	public void navigateToWorldMap() {
		naviationManager.navigateToWorldMap();
	}
	
	/** 
	 * Liefert das CityObject zurück falls die LevelID existiert.
	 * Wenn nicht wird eine LevelNotFoundException geworfen.
	 * @param levelID
	 * @return {@link CityObject}
	 * @throws LevelNotFoundException
	 */
	public CityObject getCityObject(final String levelID) throws LevelNotFoundException{
		return naviationManager.getCityObject(levelID);
	}
	
	/** 
	 * Liefert das GameObject zurück falls die LevelID existiert.
	 * Wenn nicht wird eine LevelNotFoundException geworfen.
	 * @param levelID
	 * @return {@link GameObject}
	 * @throws LevelNotFoundException
	 */
	public GameObject getGameObject(final String levelID) throws LevelNotFoundException{
		return naviationManager.getGameObject(levelID);
	}

	/**
	 * Liefert eine Referenz auf das Spielstand Modul.	 * 
	 * @return Spielstand
	 */
	public Object getScoreManager() {
		// TODO Spielstand muss zurück gegeben werden.
		return null;
	}

	void setNavigationManager(final NavigationManager newNaviationManager) {
		this.naviationManager = newNaviationManager;
	}
	
	void setScoreManager() {
		// TODO
	}

}
