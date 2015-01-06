package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;

/**
 * Das Interface stellt der GameObjectLogic die Methoden des GameField
 * zur Verfügung, die die GameObjectLogic benötigt. 
 * @author Francis
 * @version 1.0
 */
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
	
	/**
	 * Fügt der Stage ein GameObject hinzu.
	 * @param o Das CollidableGameObject.
	 */
	public void addCollidableGameObject(CollidableGameObject o);

	/**
	 * Liefert ein zufälliges CollidableGameObject.
	 * @return co CollidableGameObject.
	 */
	public CollidableGameObject getRandomObject();

	/**
	 * Legt ein CollidableGameObject in den ObjectPool zurück, wenn dieses berührt wurde
	 * oder außerhalb des sichtbaren Spielfeldbereichs ist.
	 * @param o CollidableGameObject.
	 */
	void passBack(CollidableGameObject o);

	/**
	 * Liefert die Actor-Objekte des Spielfelds, die in der Stage sind.
	 * @return a Array mit allen Actor-Objekten.
	 */
	public Array<Actor> getActors();

	/**
	 * Legt das Popup auf die Stage das dem GameState entspricht.
	 * @param greeting GameState
	 */
	public void showPopup(GameState greeting);

	/**
	 * Prüft ob der Button des Begrüßungspopups gedrückt wurde.
	 * @return true, wenn der Button gedrückt wurde, sonst false.
	 */
	public boolean isGreetingButtonPressed();
	
	/**
	 * Setzt den Spielstand auf GameOver wenn b = true.
	 * @param b true oder false.
	 */
	public void setGameOverState(boolean b);
}
