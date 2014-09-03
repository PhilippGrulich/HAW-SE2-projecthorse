package com.haw.projecthorse.gamemanager;

public abstract class GameManagerFactory {

	/**
	 * Liefert eine Instanz des Gamemanagers zurück. *
	 * 
	 * @return {@link GameManager}
	 */
	public static GameManager getInstance() {
		return GameManagerImpl.getInstance();
	}

}
