package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public interface IGameObjectInitializerFuerGameObjectLogic {
	
	/**
	 * Berechnet auf Grundlage einer angegebenen Breite die Höhe.
	 * @param regionWidth Breite der TextureRegion.
	 * @param regionHeight Höhe der TextureRegion.
	 * @param desiredWidth Neue Breite des ->GameObjects<-
	 * @return w Die, auf Grundlage der angegebenen Höhe, berechnete Breite.
	 */
	public float calcRelativeHeight(float regionHeight, float regionWidth, float desiredWidth);
	
	/**
	 * Berechnet auf Grundlage einer angegebenen Höhe die Breite.
	 * @param regionWidth Breite der TextureRegion.
	 * @param regionHeight Höhe der TextureRegion.
	 * @param desiredHeight Neue Höhe des ->GameObjects<-
	 * @return w Die, auf Grundlage der angegebenen Höhe, berechnete Breite.
	 */
	public float calcRelativeWidth(float regionHeight, float regionWidth, float desiredHeight);
	
	/**
	 * Erzeugt und initialisiert eine Instanz vom Typ GameObject.
	 * @param r TextureRegion mit dem Bild
	 * @param name Gewünschter Name des GameObject.
	 * @param points Punkte, die bei Kollision des Players mit dem GameObject gutgeschrieben oder abgezogen werden.
	 * @param height Gewünschte Höhe des GameObjects. Breite wird automatisch gesetzt, sodass das Bild richtig Skaliert wird.
	 * @param duration Die Zeit in Sekunden die das GameObject benötigt, um von einem Ende zum anderen Ende des Screens zu gehen.
	 * @param x x-Koordinate des GameObjects.
	 * @param y y-Koordinate des GameObjects.
	 * @param collidable true, wenn mit dem GameObject kollidiert werden kann. Es wird dann ein Rectangle zur Kollisions-Detektion erzeugt.
	 * @return o Ein initialisertes GameObject wenn preconditions erfüllt sind, sonst null.

	 * @post Ein initialisertes GameObject wenn alle preconditions erfüllt sind, sonst null.
	 */
	public GameObject initGameObject(TextureRegion r, String name, int points, float height, float width, float duration, float x, float y, boolean collidable);
	
	
}
