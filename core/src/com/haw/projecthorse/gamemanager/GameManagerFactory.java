package com.haw.projecthorse.gamemanager;

/**
 * Factory Klasse für den GameManager.
 * 
 * @author Philipp
 * @version 1.0
 */
public abstract class GameManagerFactory {

	/**
	 * Liefert eine Instanz des {@link GameManager} zurück.
	 * 
	 * @return {@link GameManager}
	 */
	public static GameManager getInstance() {
		return GameManagerImpl.getInstance();
	}

}
