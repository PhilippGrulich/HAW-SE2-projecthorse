package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

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
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.65f, 1); // Hintergrund malen
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
		// TODO Auto-generated method stub

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
