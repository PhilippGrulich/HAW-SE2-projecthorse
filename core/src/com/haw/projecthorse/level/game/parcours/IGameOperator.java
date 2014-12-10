package com.haw.projecthorse.level.game.parcours;

public interface IGameOperator {

	/**
	 * �ffnet ein Popup und fragt den Spieler ob er nochmal spielen m�chte.
	 */
	public void restart();

	/**
	 * Pr�ft ob die GameLogic mitteilt, dass ein Erfolg oder Misserfolg
	 * eingetreten ist.
	 * 
	 * @param delta
	 */
	public void update(float delta);

}
