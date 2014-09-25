package com.haw.projecthorse.player;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.haw.projecthorse.swipehandler.SwipeListener;

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

public abstract class Player extends Actor implements SwipeListener {
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
	 * @param speed Geschwindigkeit zwischen 0 und 1
	 */
	public abstract void setAnimationSpeed(float speed);

	/**
	 * 
	 * @return True bei Bewegung, ansonsten False
	 */
	public abstract boolean isMoving();

	/**
	 * Handler für eine Swipe-Bewegung nach oben auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeUp() {}

	/**
	 * Handler für eine Swipe-Bewegung nach unten auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeDown() {}

	/**
	 * Handler für eine Swipe-Bewegung nach links auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeLeft() {}

	/**
	 * Handler für eine Swipe-Bewegung nach rechts auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeRight() {}

	/**
	 * Handler für eine Swipe-Bewegung nach links-oben auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeUpLeft() {}

	/**
	 * Handler für eine Swipe-Bewegung nach rechts-oben auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeUpRight() {}

	/**
	 * Handler für eine Swipe-Bewegung nach links-unten auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeDownLeft() {}

	/**
	 * Handler für eine Swipe-Bewegung nach rechts-unten auf dem Bildschirm.
	 * Kann bzw. sollte überschrieben werden, wenn man auf dieses Event
	 * reagieren will.
	 * 
	 */
	@Override
	public void swipeDownRight() {}
}
