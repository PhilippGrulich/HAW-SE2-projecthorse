package com.haw.projecthorse.player.race;

public class Race {
	private float obedience, intelligence, athletic, size;
	private String name;

	public Race(HorseRace type) {
		switch (type) {
		case HAFLINGER:
			obedience = 0.25f;
			intelligence = 0.5f;
			athletic = 0.75f;
			size = 1.40f;
			name = "Haflinger";
			break;
			
		case HANNOVERANER:
			obedience = 0.75f;
			intelligence = 0.75f;
			athletic = 0.5f;
			size = 1.70f;
			name = "Hannoveraner";
			break;
			
		case FRIESE:
			obedience = 1f;
			intelligence = 0.25f;
			athletic = 1f;
			size = 1.65f;
			name = "Friese";
			break;
			
		case SHETTI:
			obedience = 0.5f;
			intelligence = 1f;
			athletic = 0.25f;
			size = 1.07f;
			name = "Shetti";
			break;
		}
	}

	public float obedience() {
		return obedience;
	}

	public float intelligence() {
		return intelligence;
	}

	public float athletic() {
		return athletic;
	}

	public String name() {
		return name;
	}

	public float size() {
		return size;
	}
}
