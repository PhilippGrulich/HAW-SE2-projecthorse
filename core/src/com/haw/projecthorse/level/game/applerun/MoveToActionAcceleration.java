package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/**
 * MoveToActionAcceleration: Action zur Benutzung im Accerelometer.
 * @author Lars
 * @version 1.0
 */
public class MoveToActionAcceleration extends MoveToAction {

	/**
	* Constructor.
	* @param x x-Coord
	* @param y y-Coord
	* @param duration dauer
	*/
	public MoveToActionAcceleration(final float x, final float y, final float duration) {
		this(x, y, duration, Interpolation.sine);
	}

	/**
	* Constructor mit Interpolation.
	* @param x x-Coord
	* @param y y-Coord
	* @param duration dauer
	* @param interpolation Wie soll der verlauf der Action sein. (Interpolation, z.B. Linear, Sinus, exp. etc.
	*/
	public MoveToActionAcceleration(final float x, final float y, final float duration, final Interpolation interpolation) {
		this.setPosition(x, y);
		this.setDuration(duration);
		this.setInterpolation(interpolation);
	}

	@Override
	public void end() {
	}
}
