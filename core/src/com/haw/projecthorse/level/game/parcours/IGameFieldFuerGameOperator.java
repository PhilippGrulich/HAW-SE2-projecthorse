package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;

public interface IGameFieldFuerGameOperator {

	Player getPlayer();

	List<ParcoursLoot> getLoot();

	Stage getStage();

	int getScore();

	/**
	 * Zeigt in Abhängigkeit des Spielstands (GameState) ein "Gewinner" oder
	 * "Verlierer" Popup mit den Möglichkeiten, erneut zu spielen oder aufzuhören.
	 * @param g
	 */
	void showPopup(GameState g);

	void drawGameField();

	boolean isButtonYesPressed(GameState g);

	boolean isButtonNoPressed(GameState g);

	/**
	 * Startet das Spiel von vorne, setzt die Punktzahl auf 0 u. das Pferd
	 * auf die Anfangsposition.
	 */
	void restart();

	void clear();

	void removePopup();

	public void playGallop();

	public void pauseGallop();

	public void stopGallop();

	void dispose();

}
