package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

/**
 * Dieses Object Bildet eine Menü Objekt aus der GameConfig.json ab.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public interface MenuObject {

	/**
	 * Liefert die LevelID zurück.
	 * 
	 * @return {@link String}
	 */
	String getLevelID();

	/**
	 * Liefert den Klassennamen des Menüs zurück.
	 * 
	 * @return {@link String}
	 */
	String getClassName();

	/**
	 * Liefert eine Hashmap aus den in der GameConfig.json definierten
	 * Parametern.
	 * 
	 * @return {@link HashMap}
	 */
	HashMap<String, String> getParameter();

}
