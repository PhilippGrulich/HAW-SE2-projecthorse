package com.haw.projecthorse.intputmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

public class DefaultInputProcessor extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
			Gdx.app.log("DefaultInputProcessor", "Back Key Detected");
			GameManagerFactory.getInstance().navigateBack();
			return true;
		}
		return false;
	}
}
