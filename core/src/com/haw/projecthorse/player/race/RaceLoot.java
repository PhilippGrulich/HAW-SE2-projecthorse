package com.haw.projecthorse.player.race;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

public class RaceLoot extends Loot {
	private HorseRace race;

	/**
	 * NUR zum Laden aus der JSON-Datei
	 */
	public RaceLoot() {
		super();
	}
	
	public RaceLoot(HorseRace race) {
		super(new Race(race).name(), "");
		this.race = race;
	}

	@Override
	protected LootImage getLootImage() {
		return new LootImage("player" + race().name(), "idle-1");
	}

	@Override
	protected int doHashCode() {
		return ((race == null) ? 0 : race.hashCode());
	}

	@Override
	protected boolean doEquals(Object other) {
		RaceLoot o = (RaceLoot) other;
		if (race == null) {
			if (o.race != null)
				return false;
		} else if (!race.equals(o.race))
			return false;
		return true;
	}

	public Race race() {
		return new Race(race);
	}

	@Override
	public String getCategory() {
		return "Pferde";
	}
}
