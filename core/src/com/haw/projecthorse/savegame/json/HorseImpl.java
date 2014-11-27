package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.race.HorseRace;

public class HorseImpl implements Horse {
	private String name = "";
	private HorseRace race = HorseRace.HAFLINGER;

	public HorseImpl() { }
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public HorseRace getRace() {
		return race;
	}

	@Override
	public void setRace(HorseRace race) {
		this.race = race;
	}
}
