package com.haw.projecthorse.gamemanager;

import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManager;

public class GameManager {
	private NavigationManager naviationManager;

	private static GameManager instance;

	private GameManager() {

	}

	/**
	 * Liefert eine Instanz des Gamemanagers zurück.
	 * 
	 * @return
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
	 * @param levelID
	 */
	public void navigateToLevel(String levelID) {
		// TODO
	}

	/**
	 * Navigiert zur Weltkarte
	 */
	public void navigateToWorldMap() {
		// TODO
	}

	/**
	 * liefert eine Referenz auf das Spielstand Modul.
	 * 
	 * @return Spielstand
	 */
	public Object getScoreManager() {
		// TODO Spielstand muss zurück gegeben werden.
		return null;
	}

	void setNavigationManager(NavigationManager naviationManager) {
		this.naviationManager = naviationManager;
	}

	void setScoreManager() {
		// TODO
	}

}
