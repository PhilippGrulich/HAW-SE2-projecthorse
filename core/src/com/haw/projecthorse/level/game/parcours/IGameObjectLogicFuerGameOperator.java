package com.haw.projecthorse.level.game.parcours;

/**
 * Das Interface stellt dem GameOperator die Methoden der GameObjectLogic
 * zur Verfügung, die der GameOperator benötigt. 
 * @author Francis
 * @version 1.0
 */
public interface IGameObjectLogicFuerGameOperator {

	/**
	 * Updated den GameOperator.
	 * @param delta Die Zeit, die seit dem letzten Frame vergangen ist.
	 */
	public void update(float delta);

	/**
	 * Wenn der Wert von Gdx.input.getAccelerometerY() einen festgelegten Wert
	 * übersteigt, wird der Spieler in dieser Funktion nach rechts bewegt.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 * @param y Der Neigungswinkel des Devices zwischen -10 u. +10.
	 */
	public void movePlayerR(float delta, float y);

	/**
	 * Wenn der Wert von Gdx.input.getAccelerometerY() einen festgelegten Wert
	 * übersteigtt, wird der Spieler in dieser Funktion nach links bewegt.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 * @param y Der Neigungswinkel des Devices zwischen -10 u. +10.
	 */
	public void movePlayerL(float delta, float y);
}
