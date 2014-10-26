package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

/**
 * Dieses Object Bildet eine Men� Objekt aus der GameConfig.json ab.
 * @author Viktor
 *
 */
public interface MenuObject {

	/**
	 * Liefert die LevelID zur�ck
	 * @return {@link String}
	 */
	String getLevelID();

	/**
	 * Liefert den Klassennamen des Men�s zur�ck
	 * @return {@link String}
	 */
	String getClassName();
	
	/**
	 * Liefert eine Hashmap aus den in der GameConfig.json definierten Parametern. 
	 * @return {@link HashMap}
	 */
	HashMap<String, String> getParameter();

}