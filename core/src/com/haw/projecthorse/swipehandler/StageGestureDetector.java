package com.haw.projecthorse.swipehandler;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageGestureDetector extends GestureDetector {
	private static class DirectionGestureListener extends GestureAdapter {
		SwipeListener swipeListener;

		public DirectionGestureListener(SwipeListener swipeListener) {
			super();
			this.swipeListener = swipeListener;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			float ratio = Math.abs(velocityX / velocityY);
			if (0.6 < ratio && ratio < 1.7) {
				// Bewegung in eine Ecke
				if (velocityX > 0) {
					if (velocityY > 0) {
						swipeListener.swipeDownRight();
					} else {
						swipeListener.swipeUpRight();
					}
				} else {
					if (velocityY > 0) {
						swipeListener.swipeDownLeft();
					} else {
						swipeListener.swipeUpLeft();
					}
				}
			} else {
				// relativ gerade Bewegung (hoch, runter, links, rechts)
				if (Math.abs(velocityX) > Math.abs(velocityY)) {
					if (velocityX > 0) {
						swipeListener.swipeRight();
					} else {
						swipeListener.swipeLeft();
					}
				} else {
					if (velocityY > 0) {
						swipeListener.swipeDown();
					} else {
						swipeListener.swipeUp();
					}
				}
			}
			return super.fling(velocityX, velocityY, button);
		}
	}

	private Stage stage;

	public StageGestureDetector(Stage stage, SwipeListener listener) {
		super(new DirectionGestureListener(listener));

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
