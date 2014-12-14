package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface IGameFieldFuerGameObjectLogic {

	/**
	 * Liefert die GameObejcts des Spielfelds.
	 * @return gameObjects Alle GameObjects des Spielfelds.
	 */
	List<GameObject> getGameObjects();

	/**
	 * Liefert das Pferd.
	 * @return p Das Pferd.
	 */
	Player getPlayer();

	/**
	 * Addiert points-Punkte auf die Punktzahl des Spiels.
	 * @param points Die Punkte die auf das Spiel addiert werden.
	 */
	void addToScore(int points);

	/**
	 * Liefert die Spielfeldbreite.
	 * @return w Die Spielfeldbreite.
	 */
	float getWidth();

	/**
	 * Da einige Objekte (u.a. das Pferd) direkt auf dem Boden stehen bzw. liegen,
	 * liefert diese Methode die Oberflächenposition des Bodens.
	 * @return f Die Oberflächenposition des Bodens.
	 */
	float getTopOfGroundPosition();

	/**
	 * Ruft act auf allen Actor des GameField auf.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 */
	void actGameField(float delta);

	/**
	 * Ruft draw auf allen Actor des GameField auf.
	 */
	void drawGameField();

	/**
	 * Liefert die Stage in der alle Actors sind.
	 * @return s Die Stage.
	 */
	Stage getStage();

	/**
	 * Liefert die zu gewinnenden Loot-Objekte des Spiels "Parcours".
	 * @return loot Die Liste der Loot-Objekte die gewonnen werden können.
	 */
	List<ParcoursLoot> getLoot();

	/**
	 * Spielt den Gallop-Sound ab.
	 */
	public void playGallop();

	/**
	 * Pausiert den Gallop-Sound.
	 */
	public void pauseGallop();

	
	/**
	 * Stoppt den Gallop-Sound.
	 */
	public void stopGallop();

	/**
	 * Spield den Essen-Sound ab.
	 */
	public void eat();
	
	/**
	 * Liefert die Spielgeschwindigkeit.
	 * @return generalGameSpeed Die Spielgeschwindigkeit.
	 */
	float getGeneralGameSpeed();

}
