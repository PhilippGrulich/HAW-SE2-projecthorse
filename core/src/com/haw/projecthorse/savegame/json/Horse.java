package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.race.HorseRace;

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
	 * @return Die Rasse des Pferdes
	 */
	HorseRace getRace();

	/**
	 * Ändert die Rasse des Pferdes.
	 * 
	 * @param race
	 *            Die neue Rasse.
	 */
	void setRace(HorseRace race);
}
