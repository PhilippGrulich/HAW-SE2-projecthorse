package com.haw.projecthorse.player.race;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

/**
 * Loot-Klasse für die Pferderassen.
 * 
 * @author Oliver
 * @version 1.0
 */

public class RaceLoot extends Loot {
	private HorseRace race;

	/**
	 * Konstruktor NUR zum Laden aus der JSON-Datei.
	 */
	public RaceLoot() {
		super();
	}

	/**
	 * Konsturktor.
	 * 
	 * @param race
	 *            Die Rasse, die gelootet werden soll.
	 */
	public RaceLoot(final HorseRace race) {
		super(new Race(race).name(), "");
		this.race = race;
	}

	@Override
	protected LootImage getLootImage() {
		return new LootImage("player" + new Race(race).name(), "idle-1");
	}

	@Override
	protected int doHashCode() {
		return ((race == null) ? 0 : race.hashCode());
	}

	@Override
	protected boolean doEquals(final Object other) {
		RaceLoot o = (RaceLoot) other;
		if (race == null) {
			if (o.race != null) {
				return false;
			}
		} else if (!race.equals(o.race)) {
			return false;
		}
		return true;
	}

	public HorseRace getRace() {
		return race;
	}

	@Override
	public String getCategory() {
		return "Pferde";
	}
}
