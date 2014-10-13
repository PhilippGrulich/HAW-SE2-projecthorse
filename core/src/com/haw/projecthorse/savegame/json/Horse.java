package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.color.PlayerColor;

public interface Horse {
	/**
	 * @return Name des Pferdes
	 */
	String getName();

	/**
	 * Setzt den Namen
	 * 
	 * @param name
	 *            Der neue Name.
	 */
	void setName(String name);

	/**
	 * @return Die Farbe des Pferdes
	 */
	PlayerColor getColor();

	/**
	 * Ändert die Farbe des Pferdes.
	 * 
	 * @param color
	 *            Die neue Farbe.
	 */
	void setColor(PlayerColor color);
}
