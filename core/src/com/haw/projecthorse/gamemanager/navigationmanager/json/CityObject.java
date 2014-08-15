package com.haw.projecthorse.gamemanager.navigationmanager.json;

public interface CityObject {
	/**
	 * @return Name der Stadt 
	 */
	String getCityName();
	
	/**
	 * 
	 * @return LevelID als String
	 */
	String getLevelID();

	/**
	 * 
	 * @return String Array mit alles LevelIDs der Spiele dieser Stadt
	 */
	String[] getGameLevelIDs();

	
}
