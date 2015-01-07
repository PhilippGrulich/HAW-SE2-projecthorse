package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.race.HorseRace;

/**
 * Konkrete Implementierung des {@link Horse}Horse-Interfaces.
 * 
 * @author Oliver
 * @version 1.1
 */

public class HorseImpl implements Horse {
	private HorseRace race = HorseRace.HAFLINGER;

	/**
	 * Defaultkonstruktor (unter anderem zum Laden aus der JSON-Datei).
	 */
	public HorseImpl() { }

	@Override
	public HorseRace getRace() {
		return race;
	}

	@Override
	public void setRace(final HorseRace race) {
		this.race = race;
	}
}
