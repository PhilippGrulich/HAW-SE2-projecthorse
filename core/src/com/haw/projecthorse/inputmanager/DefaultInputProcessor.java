package com.haw.projecthorse.inputmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

/**
 * Dies ist ein DefaultInputProcessor um das zurücknavigieren zur realisieren.
 * 
 * @author Philipp
 * @version 1.0
 */
public class DefaultInputProcessor extends InputAdapter {

	@Override
	public boolean keyDown(final int keycode) {
		if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
			Gdx.app.log("DefaultInputProcessor", "Back Key Detected");
			GameManagerFactory.getInstance().navigateBack();
			return true;
		}
		return false;
	}
}
