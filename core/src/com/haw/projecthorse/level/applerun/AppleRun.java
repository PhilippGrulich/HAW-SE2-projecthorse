package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.haw.projecthorse.level.Level;

/**
 * @author Lars: Stage und Input listener auslagern in die Level.abstract?
 * 
 * 
 */

public class AppleRun extends Level {
	private Gamestate gamestate;


	// Add everything to this stage
	// private Stage stage = new Stage(this.getViewport(), this.getSpriteBatch());

	public AppleRun() {
		gamestate = new Gamestate(this.getViewport(), this.getSpriteBatch(), width, height);
		
	}

	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(0.8f, 0.9f, 0.99f, 1); // Hintergrund malen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gamestate.update(delta);
		
		
		// stage.act(Gdx.graphics.getDeltaTime());
		// stage.draw();

		// fallingEntities.act(delta); // Update falling apples
		// backgroundGraphics.act(delta);
		// horse.act(delta);
		//
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
