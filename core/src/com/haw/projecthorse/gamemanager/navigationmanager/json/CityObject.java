package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

/**
 * Dieses Object representiert ein City Element aus der GameConfig.json
 * @author Philipp
 *
 */
public interface CityObject {
	/**
	 * @return Name der Stadt  als {@link String}
	 */
	String getCityName();
	
	/**
	 * 
	 * @return LevelID als {@link String}
	 */
	String getLevelID();

	/**
	 * 
	 * @return GameObject Array mit alles GameObject der Spiele dieser Stadt
	 */
	GameObject[] getGames();
	
	/**
	 * @return  Liefert eine {@link HashMap} aus den in der GameConfig.json definierten Parametern. 
	 */
	HashMap<String, String> getParameter();

	
}
