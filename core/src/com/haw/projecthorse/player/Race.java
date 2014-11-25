package com.haw.projecthorse.player;

public class Race {
	protected float obedience, intelligence, athletic;
	protected String name;

	public Race(HorseRace type) {
		switch (type) {
		case HAFLINGER:
			obedience = 0.25f;
			intelligence = 0.5f;
			athletic = 0.75f;
			name = "Haflinger";
			break;
			
		case HANNOVERANER:
			obedience = 0.75f;
			intelligence = 0.75f;
			athletic = 0.5f;
			name = "Hannoveraner";
			break;
			
		case FRIESE:
			obedience = 1f;
			intelligence = 0.25f;
			athletic = 1f;
			name = "Friese";
			break;
			
		case SHETTI:
			obedience = 0.5f;
			intelligence = 1f;
			athletic = 0.25f;
			name = "Shetti";
			break;
		}
	}
}
