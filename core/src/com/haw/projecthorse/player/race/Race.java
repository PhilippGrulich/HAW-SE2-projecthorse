package com.haw.projecthorse.player.race;

/**
 * Klasse für die verschiedenen Pferderassen. Diese Klasse behinhaltet zu jeder
 * Rasse spezifische Daten.
 * 
 * @author Oliver
 * @version 1.0
 */

public class Race {
	private float obedience, intelligence, athletic;
	private String name;

	/**
	 * Konstruktor.
	 * 
	 * @param type
	 *            die Rasse
	 */
	public Race(final HorseRace type) {
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

		default:
			obedience = 0f;
			intelligence = 0f;
			athletic = 0f;
			name = "";
			break;
		}
	}

	/**
	 * Liefert die Gehorsamkeit eines Pferdes dieser Rasse.
	 * 
	 * @return die Gehorsamkeit
	 */
	public float obedience() {
		return obedience;
	}

	/**
	 * Liefert die Intelligenz eines Pferdes dieser Rasse.
	 * 
	 * @return die Intelligenz
	 */
	public float intelligence() {
		return intelligence;
	}

	/**
	 * Liefert die Sportlichkeit eines Pferdes dieser Rasse.
	 * 
	 * @return die Sportlichkeit
	 */
	public float athletic() {
		return athletic;
	}

	/**
	 * Liefert die den Namen dieser Rasse.
	 * 
	 * @return der Name
	 */
	public String name() {
		return name;
	}
}
