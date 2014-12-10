package com.haw.projecthorse.level.game.parcours;

public interface IGameObjectLogicFuerGameOperator {

	public void update(float delta);

	/**
	 * Wenn der Wert von Gdx.input.getAccelerometerY() einen festgelegten Wert
	 * übrschreibt, wird der Spieler in dieser Funktion nach rechts bewegt.
	 * @param delta
	 */
	public void movePlayerR(float delta);

	/**
	 * Wenn der Wert von Gdx.input.getAccelerometerY() einen festgelegten Wert
	 * übrschreibt, wird der Spieler in dieser Funktion nach links bewegt.
	 * @param delta
	 */
	public void movePlayerL(float delta);
}
