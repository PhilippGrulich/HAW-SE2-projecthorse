package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class MoveToActionAcceleration extends MoveToAction {

	public MoveToActionAcceleration(float x, float y, float duration) {
		this(x, y, duration, Interpolation.sine);
	}

	public MoveToActionAcceleration(float x, float y, float duration, Interpolation interpolation) {
		this.setPosition(x, y);
		this.setDuration(duration);
		this.setInterpolation(interpolation);
	}

	@Override
	public void end() {
	}
}
