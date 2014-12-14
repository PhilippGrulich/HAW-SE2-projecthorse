package com.haw.projecthorse.level.game.parcours;

public interface IGameObjectLogicFuerGameInputListener {

	public boolean isPlayerJumping();

	/**
	 * Setzt eine Variable auf b, welche darüber entscheidet,
	 * ob die GameLogic in der Funktion die den Sprung berechnet,
	 * die neue y-Position des Pferds berechnet. Dies ist genau dann
	 * erforderlich, wenn das Pferd noch in der Luft ist.
	 * @param b
	 */
	public void setPlayerJump(boolean b);

}
