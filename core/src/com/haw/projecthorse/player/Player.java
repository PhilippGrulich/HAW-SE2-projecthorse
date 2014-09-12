package com.haw.projecthorse.player;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.collision.BoundingBox;

public interface Player {
	
	/**
	 * Methode zum Rendern des Spielers (Pferd)
	 * @param timeDeltaSum Aufsummierte Zeit seit Rendering Beginn 
	 */
	public void draw(float timeDeltaSum);
	
	/**
	 * Methode zum setzen der X Koordinate
	 * @param x
	 */
	public void setX(float x);
	
	/**
	 * Methode zum setzen der Y Koordinate
	 * @param y
	 */
	public void setY(float y);
	
	/**
	 * Gibt die X Koordinate des Spielers zurück
	 */
	public float getX();
	
	/**
	 * Gibt die Y Koordinate des Spielers zurück
	 */
	public float getY();
	
	/**
	 * Bewegt den Spieler in eine Richtung, steuert Animation und
	 * ggf. auch die Koordinaten (es sei denn der Spieler ist unbeweglich)
	 * @param direction Richtung der Bewegung
	 * @param speed Geschwindigkeit zwischen 0 und 1
	 */
	public void setMovement(Direction direction, float speed);
	
	/**
	 * Verändert die Geschwindigkeit um ein angegegbenes Delta, die
	 * Richtung wird hierbei berücksichtigt
	 * @param delta zwischen -1 und 1, negative Werte bremsen, positive Werte beschleunigen
	 */
	public void changeSpeed(float delta);
	
	/**
	 * @return Geschwindigkeit der aktuellen Bewegung
	 */
	public float getSpeed();
	
	/**
	 * 
	 * @return True bei Bewegung, ansonsten False
	 */
	public boolean isMoving();
	
	/**
	 * Liefert ein Polygon, das als Bounding Box verwendet werden kann
	 * @return 
	 */
	public Polygon getBoundingPolygon();
	
	/**
	 * Liefert eine rechteckige Bounding Box, kann sehr ungenau sein!
	 * Für genauere Berechnungen sollte getBoundingPolygon() genutzt werden
	 * @return
	 */
	public BoundingBox getBoundingBox();
	
	

}
