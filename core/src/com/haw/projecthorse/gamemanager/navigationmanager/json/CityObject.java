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
	 * @return String Array mit alles LevelIDs der Spiele dieser Stadt
	 */
	String[] getGameLevelIDs();
	
	/**
	 * @return  Liefert eine {@link HashMap} aus den in der GameConfig.json definierten Parametern. 
	 */
	HashMap<String, String> getParameter();

	
}
