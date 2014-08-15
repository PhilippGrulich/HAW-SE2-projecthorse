package com.haw.projecthorse.gamemanager;

public abstract class GameManagerFactory {

	private static GameManager instance;

	/**
	 * Liefert eine Instanz des Gamemanagers zurück. *
	 * 
	 * @return {@link GameManager}
	 */
	public static GameManager getInstance() {
		return instance;
	}

	static void setInstance(final GameManager gm) {
		instance = gm;
	}
}
