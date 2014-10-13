package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.color.PlayerColor;

public interface SaveGame {
	/**
	 * @return ID of the score as integer
	 */
	int getID();

	/**
	 * @return experience points of the score as integer
	 */
	int getEP();

	/**
	 * @param toAdd
	 *            EP to add to the score
	 */
	void addEP(int toAdd);

	/**
	 * @return name of the horse in this score
	 */
	String getHorseName();

	/**
	 * Ändert den Namen des Pferdes
	 * 
	 * @param names
	 *            Der neue Name.
	 */
	void setHorseName(String name);
	
	/**
	 * @return Die Farbe des Pferdes
	 */
	PlayerColor getHorseColor();

	/**
	 * Ändert die Farbe des Pferdes.
	 * 
	 * @param color
	 *            Die neue Farbe.
	 */
	void setHorseColor(PlayerColor color);
}
