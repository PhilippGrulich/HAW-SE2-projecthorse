package com.haw.projecthorse.level.game.parcours;

import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;

public interface IGameObjectLogicFuerGameOperator {

	public void update(float delta);

	/**
	 * Wenn der Wert von Gdx.input.getAccelerometerY() einen festgelegten Wert
	 * übersteigt, wird der Spieler in dieser Funktion nach rechts bewegt.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 */
	public void movePlayerR(float delta);

	/**
	 * Wenn der Wert von Gdx.input.getAccelerometerY() einen festgelegten Wert
	 * übersteigtt, wird der Spieler in dieser Funktion nach links bewegt.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 */
	public void movePlayerL(float delta);
}
