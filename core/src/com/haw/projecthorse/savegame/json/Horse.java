package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.race.HorseRace;

/**
 * Horse ist die Repräsentation des Pferdes der Spielerin zum Speichern und
 * Laden im Spielstand.
 * 
 * @author Oliver
 * @version 1.1
 */

public interface Horse {
	/**
	 * Liefert die Rasse des Pferdes.
	 * 
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
