package com.haw.projecthorse.level.game.puzzle;

public class Counter {

	private static int counter;

	public Counter() {

		counter = 0;

	}

	public static void setCounter(int neucounter) {
		counter += neucounter;
		System.out.println("counter: " + counter);
	}

	public static int getCounter() {
		return counter;
	}

}
