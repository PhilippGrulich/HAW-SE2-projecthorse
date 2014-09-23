package com.haw.projecthorse.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;

public class PlayerImpl extends Player {
	private static final int DEFAULT_WIDTH = 115, DEFAULT_HEIGHT = 140;
	private static final float MIN_FRAMEDURATION = 0.05f; // Dieser Wert
															// reguliert die
	// maximale
	// Animationsgeschwindigkeit,
	// je kleiner desto
	// schneller
	private static final int SPRITES_PER_ANIMATION = 4;

	private TextureRegion sprite = new TextureRegion(new Texture(
			"pictures/notChecked/black_sprites.png"), 0, 0, DEFAULT_WIDTH,
			DEFAULT_HEIGHT);
	private float speed = 0f;
	private Direction direction = Direction.RIGHT;

	private class AnimationAction extends Action {
		private int spriteIndex = 0, animationIndex = 0;
		private float deltaSum = 0;

		@Override
		public boolean act(float delta) {
			if (speed == 0) {
				return false;
			}

			float frameDuration = (speed == 0) ? 0f : MIN_FRAMEDURATION / speed;

			deltaSum += delta;
			if (deltaSum >= frameDuration) {
				deltaSum -= frameDuration;
				spriteIndex = ++spriteIndex % SPRITES_PER_ANIMATION;

				switch (direction) {
				case RIGHT:
				case LEFT:
					animationIndex = 0;
					break;
				case UPLEFT:
				case UPRIGHT:
					animationIndex = 1;
					break;
				case DOWNLEFT:
				case DOWNRIGHT:
					animationIndex = 2;
					break;
				case UP:
					animationIndex = 3;
					break;
				case DOWN:
					animationIndex = 4;
					break;
				}

				// Die Position der TextureRegion muss geändert werden ->
				// nächstes Sprite laden
				sprite.setRegion(spriteIndex * DEFAULT_WIDTH, animationIndex
						* DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT);
			}

			return false;
		}
	}

	public PlayerImpl() {
		setBounds(getX(), getY(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
		addAction(new AnimationAction());
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(sprite.getTexture(), getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation(), sprite.getRegionX(), sprite.getRegionY(), 
				sprite.getRegionWidth(), sprite.getRegionHeight(), false, false);
	}

	@Override
	public void setAnimation(Direction direction, float speed) {
		// TODO Bei Richtungswechsel Animation ändern
		// if (this.direction != direction){
		// this.direction != direction
		//
		// }
		this.speed = speed;
		this.direction = direction;
	}

	@Override
	public void changeAnimationSpeed(float delta) {
		speed = Math.min(1, Math.max(0, speed + delta));
	}

	@Override
	public float getAnimationSpeed() {
		return speed;
	}

	@Override
	public boolean isMoving() {
		return speed != 0;
	}
}
