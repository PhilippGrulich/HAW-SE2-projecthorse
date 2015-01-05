package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.lootmanager.Chest;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.race.HorseRace;

public interface IGameFieldFuerGameOperator {

	/**
	 * Liefert das Pferd.
	 * @return player Das Pferd.
	 */
	Player getPlayer();

	/**
	 * Liefert die zu gewinnenden Loot-Objekte des Spiels.
	 * @return loot Liste mit zu gewinnenden Loot-Objekten.
	 */
	List<ParcoursLoot> getLoot();

	/**
	 * Liefert die Stage des Spiels.
	 * @return s die Stage.
	 */
	Stage getStage();

	int getScore();

	/**
	 * Zeigt in Abhängigkeit des Spielstands (GameState) ein "Gewinner" oder
	 * "Verlierer" Popup mit den Möglichkeiten, erneut zu spielen oder aufzuhören.
	 * @param g
	 */
	void showPopup(GameState g);
	
	/**
	 * Zeigt für den GameState Greeting den Begrüßungsbildschirm und die Pferdeauswahl vor.
	 * @param g
	 * @param userName
	 * @param p
	 */
	void showPopup(GameState g, String userName);

	/**
	 * Ruft draw auf der Stage auf.
	 */
	void drawGameField();

	/**
	 * @param g GameState.WON oder GameState.LOST
	 * @return true, wenn User auf den yes-Button gedrückt hat, sonst false.
	 */
	boolean isButtonYesPressed(GameState g);

	/**
	 * 
	 * @param g GameState.Won oder GameState.LOST
	 * @return true, wenn User auf yes-Button gedrückt hat, sonst false.
	 */
	boolean isButtonNoPressed(GameState g);

	/**
	 * Startet das Spiel von vorne, setzt die Punktzahl auf 0 u. das Pferd
	 * auf die Anfangsposition.
	 */
	void restart();

	/**
	 * Löscht die Stage u. Liste der GameObjects.
	 */
	void clear();

	/**
	 * Entfernt zuvor gezeigtes Popup von der Stage.
	 */
	void removePopup();

	/**
	 * Spielt die Gallop-Musik ab.
	 */
	public void playGallop();

	/**
	 * Pausiert die Gallop-Musik.
	 */
	public void pauseGallop();

	/**
	 * Stoppt die Gallop-Musik.
	 */
	public void stopGallop();

	/**
	 * Disposed die Stage.
	 */
	void dispose();
	
	/**
	 * Ruft act nur auf Popup auf, damit es eingeblendet / ausgeblendet wird.
	 * Da alle anderen GameObjects bei erscheinen von Popup sich nicht bewegen sollen,
	 * wird act auf ihnen nicht aufgerufen.
	 * @param delta Zeit, die seit dem letzten Frame vergangen ist.
	 */
	public void fadePopup(float delta, GameState g);

	boolean isGreetingButtonPressed();

	void initPlayerHannoveraner();

	void initPlayer(HorseRace race);

	boolean isHorseSelected();

	HorseRace getSelectedRace();

	void showPopup(GameState horseselection, HorseRace[] races);
	
	public boolean isGameOverState();

}
