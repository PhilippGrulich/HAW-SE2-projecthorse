package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

/**
 * Dieses Object Bildet eine Spiel Object aus der GameConfig.json ab.
 * 
 * @author Philipp
 * @version 1.0
 */
public interface GameObject {

	/**
	 * Liefert die LevelID zurück.
	 * 
	 * @return {@link String}
	 */
	String getLevelID();

	/**
	 * Liefert den Titel des Spiels zurück.
	 * 
	 * @return {@link String}
	 */
	String getGameTitle();

	/**
	 * Liefert eine Hashmap aus den in der GameConfig.json definierten
	 * Parametern.
	 * 
	 * @return {@link HashMap}
	 */
	HashMap<String, String> getParameter();

}
