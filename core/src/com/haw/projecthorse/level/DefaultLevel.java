package com.haw.projecthorse.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.haw.projecthorse.assetmanager.AssetManager;

public class DefaultLevel extends Level {

	@Override
	public void doRender(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AssetManager.showTexture("pictures/cc-0/worldmap.png", 0, 0);
		AssetManager.showTexture("pictures/selfmade/logo.png", 0, 0);

	}

	@Override
	public void doResize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doShow() {
		// TODO Auto-generated method stub
		AssetManager.load();
		AssetManager.playSound("sounds/flap.wav");
		AssetManager.playMusic("music/life.mp3");

	}

	@Override
	public void doHide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDispose() {
		// TODO Auto-generated method stub

	}

}
