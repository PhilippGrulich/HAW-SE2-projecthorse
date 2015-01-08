package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

/**
 * Dieses Object repräsentiert ein City Element aus der GameConfig.json.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public interface CityObject {

	/**
	 * Name der Stadt.
	 * 
	 * @return {@link String}
	 */
	String getCityName();

	/**
	 * Text der Stadt Info.
	 * 
	 * @return {@link String}
	 */
	String getCityInfo();

	/**
	 * LevelID.
	 * 
	 * @return {@link String}.
	 */
	String getLevelID();

	/**
	 * GameObject Array mit alles GameObject der Spiele dieser Stadt.
	 * 
	 * @return Array von GameObject
	 */
	GameObject[] getGames();

	/**
	 * Liefert eine {@link HashMap} aus den in der GameConfig.json definierten
	 * Parametern.
	 * 
	 * @return Hashmap
	 */
	HashMap<String, String> getParameter();

}
