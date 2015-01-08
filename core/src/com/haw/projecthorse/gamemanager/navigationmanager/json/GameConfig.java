package com.haw.projecthorse.gamemanager.navigationmanager.json;

/**
 * Dieses Object repräsentiert die GameConfig.json.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public interface GameConfig {

	/**
	 * Liefert den Namen des Spiels.
	 * 
	 * @return Name des Spiels
	 */
	String getGameTitle();

	/**
	 * Liefert eine Liste aller Citys.
	 * 
	 * @return String[]
	 */
	String[] getCityNames();

}
