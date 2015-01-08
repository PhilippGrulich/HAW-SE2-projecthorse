package com.haw.projecthorse.player.race;

/**
 * Klasse für die verschiedenen Pferderassen. Diese Klasse behinhaltet zu jeder
 * Rasse spezifische Daten.
 * 
 * @author Oliver
 * @version 1.0
 */

public class Race {
	private float obedience, intelligence, athletic, size;
	private String name, description;

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
			size = 1.40f;
			name = "Haflinger";
			description = "1,40 m\nstur & vielseitig\nLichtfuchs";
			break;

		case HANNOVERANER:
			obedience = 0.75f;
			intelligence = 0.75f;
			athletic = 0.5f;
			size = 1.70f;
			name = "Hannoveraner";
			description = "1,70 m\ngelehrig & ausgeglichen\nBrauner Rappe";
			break;

		case FRIESE:
			obedience = 1f;
			intelligence = 0.25f;
			athletic = 1f;
			size = 1.65f;
			name = "Friese";
			description = "1,65 m\nkräftig & geduldig & sanft\nRappe";
			break;

		case SHETTI:
			obedience = 0.5f;
			intelligence = 1f;
			athletic = 0.25f;
			size = 1.07f;
			name = "Shetti";
			description = "1,07 m\nintelligent & frech\nSchimmelschecke";
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
	 * Liefert den Namen dieser Rasse.
	 * 
	 * @return der Name
	 */
	public String name() {
		return name;
	}

	/**
	 * Liefert die Beschreibung dieser Rasse.
	 * 
	 * @return die Beschreibug
	 */
	public String description() {
		return description;
	}

	/**
	 * Liefert die Größe eines Pferdes dieser Rasse.
	 * 
	 * @return die Größe
	 */
	public float size() {
		return size;
	}
	
}
