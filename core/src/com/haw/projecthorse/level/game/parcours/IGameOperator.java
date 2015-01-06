package com.haw.projecthorse.level.game.parcours;

/**
 * Das Interface des GameOperator. 
 * @author Francis
 * @version 1.0
 */
public interface IGameOperator {

	/**
	 * Öffnet ein Popup und fragt den Spieler ob er nochmal spielen m�chte.
	 */
	public void restart();

	/**
	 * Prüft ob die GameLogic mitteilt, dass ein Erfolg oder Misserfolg
	 * eingetreten ist.
	 * 
	 * @param delta Die Zeit, die seit dem letzten Frame vergangen ist.
	 */
	public void update(float delta);

}
