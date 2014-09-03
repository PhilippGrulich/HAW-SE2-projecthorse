package com.haw.projecthorse.gamemanager;

public abstract class GameManagerFactory {

	/**
	 * Liefert eine Instanz des Gamemanagers zur�ck. *
	 * 
	 * @return {@link GameManager}
	 */
	public static GameManager getInstance() {
		return GameManagerImpl.getInstance();
	}

}
