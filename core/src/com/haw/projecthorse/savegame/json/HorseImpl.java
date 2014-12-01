package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.race.HorseRace;

public class HorseImpl implements Horse {
	private HorseRace race = HorseRace.HAFLINGER;

	public HorseImpl() { }

	@Override
	public HorseRace getRace() {
		return race;
	}

	@Override
	public void setRace(HorseRace race) {
		this.race = race;
	}
}
