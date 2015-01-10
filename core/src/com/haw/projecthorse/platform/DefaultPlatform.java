package com.haw.projecthorse.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Orientation;

/**
 * Implementierung einer DefaultPlatform.
 * 
 * @author Philipp
 * @version 1.0
 */
public class DefaultPlatform implements Platform {

	@Override
	public void setOrientation(final Orientation orientation) {
		Gdx.app.log("Platform", "Diese Platform unterstützt diese setOrientation nicht");

	}

	@Override
	public Orientation getOrientation() {
		return Orientation.Portrait;
	}

}
