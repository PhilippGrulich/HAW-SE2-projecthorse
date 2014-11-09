package com.haw.projecthorse.level.util.swipehandler;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.player.Direction;

public class StageGestureDetector extends GestureDetector {
	private static class DirectionGestureListener extends GestureAdapter {
		Stage stage;
		ControlMode mode;
		boolean fullScreen;
		float posX, posY;

		public DirectionGestureListener(Stage stage, boolean fullScreen,
				ControlMode controlMode) {
			super();
			this.stage = stage;
			mode = controlMode;
			this.fullScreen = fullScreen;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			posX = x;
			posY = y;
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
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
				if ((mode == ControlMode.HORIZONTAL) ||
					((mode != ControlMode.VERTICAL) && (Math.abs(velocityX) > Math.abs(velocityY)))) {
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
		
		private boolean fireSwipeEvent(Actor target, Direction dir) {
			if (target == null)
				return false;
			
			if (target.fire(new SwipeListener.SwipeEvent(dir)))
				return true;
			
			if (target instanceof Group) {
				Group group = (Group) target;
				Actor[] children = group.getChildren().items;
				for (Actor child : children) {
					if (fireSwipeEvent(child, dir))
						return true;
				}
			}
			
			return false;
		}
	}

	private Stage stage;

	public StageGestureDetector(Stage stage, boolean fullScreen) {
		this(stage, fullScreen, ControlMode.FOUR_AXIS);
	}

	/**
	 * 
	 * @param stage
	 * @param fullScreen
	 * @param controlMode
	 */
	public StageGestureDetector(Stage stage, boolean fullScreen,
			ControlMode controlMode) {
		super(new DirectionGestureListener(stage, fullScreen, controlMode));

		this.stage = stage;
	}

	@Override
	public boolean keyDown(int keycode) {
		stage.keyDown(keycode);
		super.keyDown(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		stage.keyUp(keycode);
		super.keyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		stage.keyTyped(character);
		super.keyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		stage.touchDown(screenX, screenY, pointer, button);
		super.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		stage.touchUp(screenX, screenY, pointer, button);
		super.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		stage.touchDragged(screenX, screenY, pointer);
		super.touchDragged(screenX, screenY, pointer);

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		stage.mouseMoved(screenX, screenY);
		super.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		stage.scrolled(amount);
		super.scrolled(amount);
		return false;
	}

}
