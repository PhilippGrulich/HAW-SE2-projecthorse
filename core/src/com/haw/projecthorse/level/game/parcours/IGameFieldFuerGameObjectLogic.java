package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface IGameFieldFuerGameObjectLogic {

	List<GameObject> getGameObjects();

	Player getPlayer();

	void addToScore(int points);

	float getWidth();

	/**
	 * Da einige Objekte (u.a. das Pferd) direkt auf dem Boden stehen bzw. liegen,
	 * liefert diese Methode die y Position des Bodens.
	 * @return
	 */
	float getTopOfGroundPosition();

	/**
	 * Ruft act auf allen Actor des GameField auf.
	 * @param delta
	 */
	void actGameField(float delta);

	/**
	 * Ruft draw auf allen Actor des GameField auf.
	 */
	void drawGameField();

	Stage getStage();

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

	public void eat();
	
	float getGeneralGameSpeed();

}
