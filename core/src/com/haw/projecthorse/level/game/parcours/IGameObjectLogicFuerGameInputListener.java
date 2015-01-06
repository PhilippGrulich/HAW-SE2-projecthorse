package com.haw.projecthorse.level.game.parcours;

/**
 * Das Interface stellt dem GameInputListener die Methoden der GameObjectLogic
 * zur Verfügung, die der GameInputListener benötigt. 
 * @author Francis
 * @version 1.0
 */
public interface IGameObjectLogicFuerGameInputListener {

	/**
	 * Liefert true, wenn das Pferd springt, sonst false.
	 * @return b true, wenn das Pferd springt, sonst false.
	 */
	public boolean isPlayerJumping();

	/**
	 * Setzt eine Variable auf b, welche darüber entscheidet,
	 * ob die GameLogic in der Funktion die den Sprung berechnet,
	 * die neue y-Position des Pferds berechnet. Dies ist genau dann
	 * erforderlich, wenn das Pferd noch in der Luft ist.
	 * @param b true, wenn das Pferd springt, sonst false.
	 */
	public void setPlayerJump(boolean b);

}
