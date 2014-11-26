package com.haw.projecthorse.level.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.color.PlayerColor;

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
	public static Color color = Color.WHITE;
	private Player player1;
	private BitmapFont textFont;
	private boolean black = false;

	@Override
	protected void doRender(float delta) {
		player1.setPlayerColor(new PlayerColor(black, color.r, color.g, color.b, ""));
//		player2.setColor(color);
		
		Gdx.gl.glClearColor(0.7f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		SpriteBatch batch = getSpriteBatch();
		batch.begin();
		textFont.setColor(color);
		textFont.draw(batch, color.toString().substring(0, 6).toUpperCase(), 150, 1100);
		batch.end();
		
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
		textFont = new BitmapFont();
		textFont.setScale(3);
		
		stage = new Stage(getViewport(), getSpriteBatch());

		SwipeListener listener = new SwipeListener() {
			
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
		}; 
		
		ColorActor colorsActor = new ColorActor();
		
		colorsActor.setPosition(100, 800);
		colorsActor.setSize(256, 256);
		colorsActor.addListener(new InputListener() {
			private boolean changeColor(InputEvent event, int x, int y) {
				Actor a = event.getListenerActor();
				if (!(a instanceof ColorActor)) {
					return false;
				}
				
				ColorActor ca = (ColorActor) a;
				Color c = ca.getColorForPosition((int) x, (int) y);
				MovementTest.color = c;
				
				return true;
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return changeColor(event, (int) x, (int) y);
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				changeColor(event, (int) x, (int) y);
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				changeColor(event, (int) x, (int) y);
			}
		});
		
		
		player1 = new PlayerImpl(PlayerColor.WHITE);
		
		player1.addListener(listener);
		player1.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Actor a = event.getListenerActor();
				if (!(a instanceof PlayerImpl)) {
					return false;
				}
				
				PlayerImpl p = (PlayerImpl) a;
				p.toggleColor();
				black = !black;
				return true;
			}
		});

		player1.scaleBy(3.0f);
		player1.setPosition(100, 50);
		player1.setAnimationSpeed(0.3f);
		stage.addActor(player1);
		stage.addActor(colorsActor);

		/*
		 *  Wichtig: Eine Instanz vom StageGestureDetector muss als InputProcessor gesetzt werden,
		 *  er leitet die Standard-Events (z.B. keyDown oder mouseMoved) an die angegebene
		 *  Stage weiter. Der �bergebene SwipeListener - hier der Player - reagiert auf die Swipe-Events.
		 */
		InputManager.addInputProcessor(new StageGestureDetector(stage, true, mode));
		
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
