package com.haw.projecthorse.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.swipehandler.ControlMode;
import com.haw.projecthorse.swipehandler.StageGestureDetector;
import com.haw.projecthorse.swipehandler.SwipeListener;

/**
 * Klasse zum Simulieren und Testen von Wisch-Bewegungen (Swipe-Gesten)
 * und der Pferdanimationen.
 * 
 * @author Oliver
 *
 */
public class MovementTest extends Level {
	private Stage stage;
	private final ControlMode mode = ControlMode.FOUR_AXIS;

	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(0.7f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
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
		stage = new Stage(getViewport());

		Player player = new PlayerImpl();
		
		player.addListener(new SwipeListener() {
			
			@Override
			public void swiped(SwipeEvent event, Actor actor) {
				if (!(actor instanceof Player))
					return;
				
				Player player = (Player) actor;
				if (player.getDirection() == event.getDirection()) {
					player.changeAnimationSpeed(0.1f);
				} else {
					player.setAnimationSpeed(0.3f);
					player.addAction(new ChangeDirectionAction(event.getDirection()));
				}
			}
		});

		player.scaleBy(3.0f);
		player.setPosition(200, 200);
		player.setAnimationSpeed(0.3f);
		stage.addActor(player);
		stage.setKeyboardFocus(player);

		/*
		 *  Wichtig: Eine Instanz vom StageGestureDetector muss als InputProcessor gesetzt werden,
		 *  er leitet die Standard-Events (z.B. keyDown oder mouseMoved) an die angegebene
		 *  Stage weiter. Der übergebene SwipeListener - hier der Player - reagiert auf die Swipe-Events.
		 */
		Gdx.input.setInputProcessor(new StageGestureDetector(stage, true, mode));
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
