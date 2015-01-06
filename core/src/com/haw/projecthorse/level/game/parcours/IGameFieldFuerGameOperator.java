package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.player.race.HorseRace;

/**
 * Das Interface stellt dem GameOperator die Methoden des GameField
 * zur Verfügung, die der GameOperator benötigt. 
 * @author Francis
 * @version 1.0
 */
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

	/**
	 * Liefert den aktuellen Punktestand.
	 * @return p Der aktuelle Punktestand.
	 */
	int getScore();

	/**
	 * Zeigt in Abhängigkeit des Spielstands (GameState) ein "Gewinner" oder
	 * "Verlierer" Popup mit den Möglichkeiten, erneut zu spielen oder aufzuhören.
	 * @param g Der GameState.
	 */
	void showPopup(GameState g);
	
	/**
	 * Zeigt für den GameState Greeting den Begrüßungsbildschirm und die Pferdeauswahl vor.
	 * @param g Der GameState.
	 * @param userName Der Spielername.
	 */
	void showPopup(GameState g, String userName);

	/**
	 * Ruft draw auf der Stage auf.
	 */
	void drawGameField();

	/**
	 * Prüft, ob der Button zur Absichtserklärung eines 
	 * neuen Spiels beim WON- bzw. LOST-Popups gedrückt wurde.
	 * @param g GameState.WON oder GameState.LOST
	 * @return true, wenn User auf den yes-Button gedrückt hat, sonst false.
	 */
	boolean isButtonYesPressed(GameState g);

	/**
	 * Prüft, ob der Button der Absichtserklärung des Spiellabbruchs des WON- bzw. LOST-Popups
	 * gedrückt wurde.
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
	 * @param g Der GameState.
	 */
	public void fadePopup(float delta, GameState g);

	/**
	 * Prüft ob der Button des Begrüßungspopups gedrückt wurde.
	 * @return true, wenn der Button gedrückt wurde, sonst false.
	 */
	boolean isGreetingButtonPressed();

	/**
	 * Initialisert das Pferd "Hannoveraner".
	 */
	void initPlayerHannoveraner();

	/**
	 * Initialisert das Pas Pferd mit der Rasse "race".
	 * @param race Die Pferderasse von HorseRace.
	 */
	void initPlayer(HorseRace race);

	/**
	 * Prüft, ob ein zur Auswahl stehendes Pferd selektiert wurde.
	 * @return true, wenn ein Pferd ausgewählt wurde, sonst false.
	 */
	boolean isHorseSelected();

	/**
	 * Liefert die Rasse des Pferdes, dass selektiert wurde.
	 * @return race Die Pferderasse von HorseRace.
	 */
	HorseRace getSelectedRace();

	/**
	 * Zeigt das Pferdeauswahl-Popup an mit den Pferderassen in "races" an.
	 * @param races Die zur Auswahl stehenden Pferderassen.
	 */
	void showPopup(HorseRace[] races);
	
	/**
	 * Liefert true, wenn das Spiel in den GameOver state gewechselt ist, sonst false.
	 * @return true, wenn das Spiel im GameOver-Zustand ist, sonst false.
	 */
	public boolean getGameOverState();

}
