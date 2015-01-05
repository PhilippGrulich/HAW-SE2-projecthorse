package com.haw.projecthorse.player.race;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

public class RaceLoot extends Loot {
	private Race race;

	public RaceLoot(Race race) {
		super();
		this.race = race;
	}

	@Override
	protected LootImage getLootImage() {
		// TODO Richtiges Bild einbauen
		return new LootImage("ui", "buttonCancel");
	}

	@Override
	protected int doHashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((race == null) ? 0 : race.hashCode());
		return result;
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
		return race;
	}

	@Override
	public String getCategory() {
		return "Pferde";
	}
}
