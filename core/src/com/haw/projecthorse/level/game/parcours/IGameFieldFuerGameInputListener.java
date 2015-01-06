package com.haw.projecthorse.level.game.parcours;

/**
 * Das Interface stellt dem GameInputListener die Methoden des GameField
 * zur Verfügung, die der GameInputListener benötigt. 
 * @author Francis
 * @version 1.0
 */
public interface IGameFieldFuerGameInputListener {

	/**
	 * Liefert das Pferd.
	 * @return p Das Pferd.
	 */
	public Player getPlayer();

}
