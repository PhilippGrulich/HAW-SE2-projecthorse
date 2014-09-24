package com.haw.projecthorse.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.swipehandler.StageGestureDetector;

/**
 * Klasse zum Simulieren und Testen von Wisch-Bewegungen (Swipe-Gesten)
 * und der Pferdanimationen.
 * 
 * @author Oliver
 *
 */
public class MovementTest extends Level {
	private Stage stage;

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

		/*
		 * Beim Erstellen eines Players, kann die PlayerImpl erweitert werden.
		 * So können für die einzelnen Bewegungsrichtungen angepasste Methoden
		 * geschrieben werden. Alle "swipe"-Handler Methoden haben eine leere
		 * default Implementation.
		 */
		Player player = new PlayerImpl() {
			private void swipe(Direction dir) {
				if (direction == dir) {
					changeAnimationSpeed(0.1f);
				} else {
					/* 
					 * Alter Code: setAnimation(dir, 0.3f);
					 * Ist zwar in diesem Fall kürzer und funktioniert auch,
					 * aber das Arbeiten mit den Actions ist mehr im Sinne des
					 * LibGdX Actor Konzepts.
					 */
				
					if (getAnimationSpeed() == 0) {
						changeAnimationSpeed(0.3f);
					}
					addAction(new ChangeDirectionAction(dir));
				}
			}
			
			@Override
			public void swipeDown() {
				swipe(Direction.DOWN);
			}
			
			@Override
			public void swipeDownLeft() {
				swipe(Direction.DOWNLEFT);
			}
			
			@Override
			public void swipeDownRight() {
				swipe(Direction.DOWNRIGHT);
			}
			
			@Override
			public void swipeLeft() {
				swipe(Direction.LEFT);
			}
			
			@Override
			public void swipeRight() {
				swipe(Direction.RIGHT);
			}
			
			@Override
			public void swipeUp() {
				swipe(Direction.UP);
			}
			
			@Override
			public void swipeUpLeft() {
				swipe(Direction.UPLEFT);
			}
			
			@Override
			public void swipeUpRight() {
				swipe(Direction.UPRIGHT);
			}
			
		};

		player.scaleBy(3.0f);
		player.setPosition(200, 200);
		stage.addActor(player);
		stage.setKeyboardFocus(player);

		/*
		 *  Wichtig: Eine Instanz vom StageGestureDetector muss als InputProcessor gesetzt werden,
		 *  er leitet die Standard-Events (z.B. keyDown oder mouseMoved) an die angegebene
		 *  Stage weiter. Der übergebene SwipeListener - hier der Player - reagiert auf die Swipe-Events.
		 */
		Gdx.input.setInputProcessor(new StageGestureDetector(stage, player));
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
