package com.haw.projecthorse.level.game.puzzle;

public class Counter {

	private static int counter;

	public Counter() {

		counter = 0;

	}

	public static void setCounter(int neucounter) {
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
