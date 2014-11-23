package com.haw.projecthorse.level.game.parcours;

public interface IGameOperator {
	
	/**
	 * Öffnet ein Popup und fragt den Spieler ob er nochmal spielen möchte.
	 */
	public void restart();
	
	/**
	 * Prüft ob die GameLogic mitteilt, dass ein Erfolg oder Misserfolg eingetreten ist.
	 * @param delta
	 */
	public void update(float delta);
	

}
