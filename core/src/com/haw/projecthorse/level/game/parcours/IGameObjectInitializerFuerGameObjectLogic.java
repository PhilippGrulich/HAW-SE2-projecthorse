package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Das Interface stellt der GameObjectLogic die Methoden des GameObjectInitializer
 * zur Verfügung, die die GameObjectLogic benötigt. 
 * @author Francis
 * @version 1.0
 */
public interface IGameObjectInitializerFuerGameObjectLogic {

	/**
	 * Berechnet auf Grundlage einer angegebenen Breite die Höhe.
	 * 
	 * @param regionWidth
	 *            Breite der TextureRegion.
	 * @param regionHeight
	 *            Höhe der TextureRegion.
	 * @param desiredWidth
	 *            Neue Breite des ->GameObjects<-
	 * @return w Die, auf Grundlage der angegebenen H�he, berechnete Breite.
	 */
	public float calcRelativeHeight(float regionHeight, float regionWidth,
			float desiredWidth);

	/**
	 * Berechnet auf Grundlage einer angegebenen H�he die Breite.
	 * 
	 * @param regionWidth
	 *            Breite der TextureRegion.
	 * @param regionHeight
	 *            Höhe der TextureRegion.
	 * @param desiredHeight
	 *            Neue Höhe des ->GameObjects<-
	 * @return w Die, auf Grundlage der angegebenen Höhe, berechnete Breite.
	 */
	public float calcRelativeWidth(float regionHeight, float regionWidth,
			float desiredHeight);

	/**
	 * Erzeugt und initialisiert eine Instanz vom Typ GameObject.
	 * 
	 * @param r
	 *            TextureRegion mit dem Bild
	 * @param name
	 *            Gew�nschter Name des GameObject.
	 * @param points
	 *            Punkte, die bei Kollision des Players mit dem GameObject
	 *            gutgeschrieben oder abgezogen werden.
	 * @param width Die Breite des GameObjects.
	 * @param height
	 *            Gew�nschte H�he des GameObjects. Breite wird automatisch
	 *            gesetzt, sodass das Bild richtig Skaliert wird.
	 * @param duration
	 *            Die Zeit in Sekunden die das GameObject ben�tigt, um von einem
	 *            Ende zum anderen Ende des Screens zu gehen.
	 * @param x
	 *            x-Koordinate des GameObjects.
	 * @param y
	 *            y-Koordinate des GameObjects.
	 * @param collidable
	 *            true, wenn mit dem GameObject kollidiert werden kann. Es wird
	 *            dann ein Rectangle zur Kollisions-Detektion erzeugt.
	 * @param isMoveable true, wenn sich das GameObject bewegen können soll, sonst false.
	 * @param loot true, wenn das GameObject gewonnen werden kann, sonst false.
	 * @return o Ein initialisertes GameObject wenn preconditions erf�llt sind,
	 *         sonst null.
	 * 
	 * @post Ein initialisertes GameObject wenn alle preconditions erf�llt sind,
	 *       sonst null.
	 */
	public GameObject initGameObject(final TextureRegion r, final String name, final int points,
			final float height, final float width, final float duration, final float x, final float y,
			final boolean collidable, final boolean loot, final boolean isMoveable);

}
