package com.haw.projecthorse.level.game.puzzle;
/**
 * Die Klasse Counter wird gebraucht, um Score beim puzzlen zu zählen.
 * @author Masha
 *@version 1.0
 */
public class Counter {

	private static int counter;
/**
 * Konstruktor.
 */
	protected Counter() {

		counter = 0;

	}
/**
 * setze den Zähler auf 0 oder zähle ein hoch.
 * @param neucounter kann 1 oder 0 sein
 */
	public static void setCounter(final int neucounter) {
		if (neucounter == 0) {
			counter = 0;
		} else {
			counter += neucounter;
		}
	}

	public static int getCounter() {
		return counter;
	}

}
