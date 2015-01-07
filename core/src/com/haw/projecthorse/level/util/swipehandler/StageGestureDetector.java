package com.haw.projecthorse.level.util.swipehandler;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.player.actions.Direction;

/**
 * Klasse, um Gesten auf einer Stage zu erkennen.
 * 
 * @author Oliver
 * @version 1.0
 */

public class StageGestureDetector extends GestureDetector {
	/**
	 * Hilfsklasse.
	 * 
	 * @author Oliver
	 */
	private static class DirectionGestureListener extends GestureAdapter {
		Stage stage;
		ControlMode mode;
		boolean fullScreen;
		float posX, posY;

		/**
		 * Konstruktor.
		 * 
		 * @param stage
		 *            Stage
		 * @param fullScreen
		 *            Gibt an, ob auf der gesamten Stage gehört werden soll oder
		 *            jeder Actor für sich
		 * @param controlMode
		 *            der ControlMode
		 */
		public DirectionGestureListener(final Stage stage,
				final boolean fullScreen, final ControlMode controlMode) {
			super();
			this.stage = stage;
			mode = controlMode;
			this.fullScreen = fullScreen;
		}

		@Override
		public boolean touchDown(final float x, final float y,
				final int pointer, final int button) {
			Vector2 stageCoordinates = stage
					.screenToStageCoordinates(new Vector2(x, y));
			posX = stageCoordinates.x;
			posY = stageCoordinates.y;
			return false;
		}

		@Override
		public boolean fling(final float velocityX, final float velocityY,
				final int button) {
			float ratio = Math.abs(velocityX / velocityY);
			Direction dir = null;
			boolean ret = false;

			if ((mode == ControlMode.FOUR_AXIS) && (0.6 < ratio && ratio < 1.7)) {
				// Bewegung in eine Ecke
				if (velocityX > 0) {
					if (velocityY > 0) {
						dir = Direction.DOWNRIGHT;
					} else {
						dir = Direction.UPRIGHT;
					}
				} else {
					if (velocityY > 0) {
						dir = Direction.DOWNLEFT;
					} else {
						dir = Direction.UPLEFT;
					}
				}
			} else {
				// relativ gerade Bewegung (hoch, runter, links, rechts)
				if ((mode == ControlMode.HORIZONTAL)
						|| ((mode != ControlMode.VERTICAL) && (Math
								.abs(velocityX) > Math.abs(velocityY)))) {
					if (velocityX > 0) {
						dir = Direction.RIGHT;
					} else {
						dir = Direction.LEFT;
					}
				} else {
					if (velocityY > 0) {
						dir = Direction.DOWN;
					} else {
						dir = Direction.UP;
					}
				}
			}

			if (fullScreen) {
				ret = fireSwipeEvent(stage.getRoot(), dir);
			} else {
				Actor actor = stage.hit(posX, posY, false);
				if (actor != null) {
					ret = fireSwipeEvent(actor, dir);
				}
			}

			return ret;
		}

		/**
		 * Löst ein Swipe-Event aus.
		 * 
		 * @param target
		 *            Ziel des Swipes
		 * @param dir
		 *            Richtung des Swipes
		 * @return true, wenn das Event behandelt wurde.
		 */
		private boolean fireSwipeEvent(final Actor target, final Direction dir) {
			if (target == null) {
				return false;
			}

			if (target.fire(new SwipeListener.SwipeEvent(dir))) {
				return true;
			}

			if (target instanceof Group) {
				Group group = (Group) target;
				Actor[] children = group.getChildren().items;
				for (Actor child : children) {
					if (fireSwipeEvent(child, dir)) {
						return true;
					}
				}
			}

			return false;
		}
	}

	private Stage stage;

	/**
	 * Konstruktor.
	 * 
	 * @param stage
	 *            Stage für den Detector
	 * @param fullScreen
	 *            Gibt an, ob auf der gesamten Stage gehört werden soll oder
	 *            jeder Actor für sich
	 */
	public StageGestureDetector(final Stage stage, final boolean fullScreen) {
		this(stage, fullScreen, ControlMode.FOUR_AXIS);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param stage
	 *            Stage für den Detector
	 * @param fullScreen
	 *            Gibt an, ob auf der gesamten Stage gehört werden soll oder
	 *            jeder Actor für sich
	 * @param controlMode
	 *            der ControlMode
	 */
	public StageGestureDetector(final Stage stage, final boolean fullScreen,
			final ControlMode controlMode) {
		super(new DirectionGestureListener(stage, fullScreen, controlMode));

		this.stage = stage;
	}

	@Override
	public boolean keyDown(final int keycode) {
		stage.keyDown(keycode);
		super.keyDown(keycode);
		return false;
	}

	@Override
	public boolean keyUp(final int keycode) {
		stage.keyUp(keycode);
		super.keyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(final char character) {
		stage.keyTyped(character);
		super.keyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
		stage.touchDown(screenX, screenY, pointer, button);
		super.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
		stage.touchUp(screenX, screenY, pointer, button);
		super.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
		stage.touchDragged(screenX, screenY, pointer);
		super.touchDragged(screenX, screenY, pointer);

		return false;
	}

	@Override
	public boolean mouseMoved(final int screenX, final int screenY) {
		stage.mouseMoved(screenX, screenY);
		super.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(final int amount) {
		stage.scrolled(amount);
		super.scrolled(amount);
		return false;
	}

}
