package com.haw.projecthorse.player;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.haw.projecthorse.player.color.PlayerColor;

/**
 * Player ist eine spezielle Implemetierung der LibGDX Klasse Actor. Um sie
 * sinnvoll nutzen zu können, sollte die Scene2D Stage Klasse als Parent
 * verwendet werden.
 * 
 * Siehe
 * http://www.gamefromscratch.com/post/2013/12/09/LibGDX-Tutorial-9-Scene2D
 * -Part-2-Actions.aspx https://github.com/libgdx/libgdx/wiki/Scene2d
 * 
 * @author Olli, Viktor
 * 
 */

public abstract class Player extends Actor {

	/**
	 * Verändert die Färbung des Spielers
	 * @param color
	 *            Die neue Farbe
	 */
	@Deprecated
	public abstract void setPlayerColor(PlayerColor color);

	/**
	 * Setzt und startet eine endlose Bewegungsanimation für den Spieler
	 * 
	 * @param direction
	 *            Richtung der Bewegung
	 * @param speed
	 *            Geschwindigkeit zwischen 0 und 1
	 */
	public abstract void setAnimation(Direction direction, float speed);

	/**
	 * Verändert die Geschwindigkeit der Bewegungsanimation um ein angegegbenes
	 * Delta, die Richtung wird hierbei berücksichtigt
	 * 
	 * @param delta
	 *            zwischen -1 und 1, negative Werte bremsen, positive Werte
	 *            beschleunigen
	 */
	public abstract void changeAnimationSpeed(float delta);

	/**
	 * @return Geschwindigkeit der aktuellen Bewegungsanimation
	 */
	public abstract float getAnimationSpeed();

	/**
	 * Setzt die Geschwindigkeit der aktuellen Bewegungsanimation
	 * 
	 * @param speed
	 *            Geschwindigkeit zwischen 0 und 1
	 */
	public abstract void setAnimationSpeed(float speed);

	/**
	 * 
	 * @return True bei Bewegung, ansonsten False
	 */
	public abstract boolean isMoving();

	/**
	 * @return Die aktuelle Richtung des Spielers
	 */
	public abstract Direction getDirection();
	
	/**
	 * Liefert die Gehorsamkeit des Pferdes in Prozent als Wert zwischen 0 und 1.
	 * 1 beudetet sehr gehorsam, 0 gar nicht gehorsam.
	 * 
	 * @return Wie gehorsam das Pferd ist.
	 */
	public abstract float getObedience();
	
	/**
	 * Liefert die Intelligenz des Pferdes in Prozent als Wert zwischen 0 und 1.
	 * 1 beudetet sehr klug, 0 eher nicht so schlau.
	 * 
	 * @return Wie intelligent das Pferd ist.
	 */
	public abstract float getIntelligence();
	
	/**
	 * Gibt an, wie athletisch das Pferdes ist. Die Angabe ist in Prozent als Wert zwischen 0 und 1.
	 * 1 beudetet athletisch, 0 nicht athletisch.
	 * 
	 * @return Wie athletisch das Pferd ist.
	 */
	public abstract float getAthletic();
	
	/**
	 * @return Der Name der Rasse des Pferdes.
	 */
	public abstract String getRasse();
}
