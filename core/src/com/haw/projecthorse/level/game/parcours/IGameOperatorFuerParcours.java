package com.haw.projecthorse.level.game.parcours;

public interface IGameOperatorFuerParcours {

	/**
	 * update = Level.render()
	 * @param delta
	 */
	void update(float delta);

	/**
	 * pause = Level.pause()
	 */
	void pause();

	/**
	 * Setzt das boolean Flag, welches den Pause-Status im Spiel indiziert.
	 * @param b
	 */
	void setPause(boolean b);

	/**
	 * dispose = Level.dispose()
	 */
	void dispose();

}
