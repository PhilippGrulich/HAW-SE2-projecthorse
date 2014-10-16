package com.haw.projecthorse.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.player.color.PlayerColor;

public class PlayerImpl extends Player {
	private static final int DEFAULT_WIDTH = 115, DEFAULT_HEIGHT = 140;

	// Dieser Wert reguliert die maximale Animationsgeschwindigkeit, je kleiner
	// desto schneller
	private static final float MIN_FRAMEDURATION = 0.05f;

	private static final int SPRITES_PER_ANIMATION = 4;

	private TextureRegion sprite;
	private float speed = 0f;
	private int spriteStartX, spriteStartY;
	private Color color;

	private Direction direction = Direction.RIGHT;

	private boolean flipX = false;
	
	private int[] positions = new int[4];
	private boolean black = false;

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

				flipX = false;
				switch (direction) {
				case LEFT:
					flipX = true;
				case RIGHT:
					animationIndex = 0;
					break;
				case UPLEFT:
					flipX = true;
				case UPRIGHT:
					animationIndex = 1;
					break;
				case DOWNLEFT:
					flipX = true;
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
				sprite.setRegion(spriteStartX + spriteIndex * DEFAULT_WIDTH,
						spriteStartY + animationIndex * DEFAULT_HEIGHT,
						DEFAULT_WIDTH, DEFAULT_HEIGHT);
			}

			return false;
		}
	}

	public PlayerImpl() {
		this(PlayerColor.WHITE);
	}
	
	public PlayerImpl(PlayerColor color) {
		if (color.hasBlackBase()) {
			black = true;
			sprite = AssetManager.load("notChecked", false, false, true).findRegion("white_sprites");
			positions[2] = sprite.getRegionX();
			positions[3] = sprite.getRegionY();
			sprite = AssetManager.load("notChecked", false, false, true).findRegion("black_sprites");
			positions[0] = sprite.getRegionX();
			positions[1] = sprite.getRegionY();
		} else {
			black = false;
			sprite = AssetManager.load("notChecked", false, false, true).findRegion("black_sprites");
			positions[0] = sprite.getRegionX();
			positions[1] = sprite.getRegionY();
			sprite = AssetManager.load("notChecked", false, false, true).findRegion("white_sprites");
			positions[2] = sprite.getRegionX();
			positions[3] = sprite.getRegionY();
		}
		spriteStartX = sprite.getRegionX();
		spriteStartY = sprite.getRegionY();
		sprite.setRegion(spriteStartX, spriteStartY, DEFAULT_WIDTH,
				DEFAULT_HEIGHT);
		this.color = color.getColor();

		setBounds(getX(), getY(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
		addAction(new AnimationAction());
	}

	@Override
	public void draw(Batch batch, float alpha) {
		Color batchColor = batch.getColor();
		
		batch.setColor(color);
		batch.draw(sprite.getTexture(), getX(), getY(), getOriginX(),
				getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation(), sprite.getRegionX(),
				sprite.getRegionY(), sprite.getRegionWidth(),
				sprite.getRegionHeight(), flipX, false);
		
		batch.setColor(batchColor);
	}
	
	@Override
	public void setPlayerColor(PlayerColor color) {
		this.color = color.getColor();
		
		if (black != color.hasBlackBase()) {
			toogleColor();
		}
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
	public void setAnimationSpeed(float speed) {
		this.speed = Math.min(1, Math.max(0, speed));
	}

	@Override
	public boolean isMoving() {
		return speed != 0;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}
	
	// for testing
	public void toogleColor() {
		if (black) {
			spriteStartX = positions[2];
			spriteStartY = positions[3];
			black = false;
		} else {
			spriteStartX = positions[0];
			spriteStartY = positions[1];
			black = true;
		}
	}

}
