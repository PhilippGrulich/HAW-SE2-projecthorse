package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.haw.projecthorse.level.game.Game;

/**
 * @author Lars: Stage und Input listener auslagern in die Level.abstract?
 * 
 * 
 */

public class AppleRun extends Game {
	private Gamestate gamestate;

	public AppleRun() {
		gamestate = new Gamestate(this.getViewport(), this.getSpriteBatch(), width, height);
	}

	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(0.8f, 0.9f, 0.99f, 1); // Hintergrund malen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gamestate.update(delta);
	}

	@Override
	protected void doDispose() {
		gamestate.dispose();
	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doShow() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doHide() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doPause() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResume() {
		// TODO Auto-generated method stub

	}

}
