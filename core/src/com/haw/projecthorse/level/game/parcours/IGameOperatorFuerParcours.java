package com.haw.projecthorse.level.game.parcours;

/**
 * Das Interface stellt der Klasse Parcours die Methoden des GameOperator
 * zur Verfügung, die die Klasse Parcours benötigt. 
 * @author Francis
 * @version 1.0
 */
public interface IGameOperatorFuerParcours {

	/**
	 * update = Level.render().
	 * @param delta Die Zeit, die seit dem letzten Frame vergangen ist.
	 */
	void update(float delta);

	/**
	 * pause = Level.pause().
	 */
	void pause();

	/**
	 * Setzt das boolean Flag, welches den Pause-Status im Spiel indiziert.
	 * @param b true, wenn das Spiel pausieren soll.
	 */
	void setPause(boolean b);

	/**
	 * dispose = Level.dispose().
	 */
	void dispose();

}
