package com.haw.projecthorse.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.player.color.PlayerColor;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.Race;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.savegame.json.SaveGame;

public class PlayerImpl extends Player {
	private static final int DEFAULT_WIDTH = 115, DEFAULT_HEIGHT = 140;

	// Dieser Wert reguliert die maximale Animationsgeschwindigkeit, je kleiner
	// desto schneller
	private static final float MIN_FRAMEDURATION = 0.05f;

	private static final int SPRITES_PER_ANIMATION = 4;

	private Race race;
	
	private TextureRegion sprite;
	private float speed = 0f;
	private int spriteStartX, spriteStartY;

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
			while (deltaSum >= frameDuration) {
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

				// Die Position der TextureRegion muss ge�ndert werden ->
				// n�chstes Sprite laden
				sprite.setRegion(spriteStartX + spriteIndex * DEFAULT_WIDTH,
						spriteStartY + animationIndex * DEFAULT_HEIGHT,
						DEFAULT_WIDTH, DEFAULT_HEIGHT);
			}

			return false;
		}
	}
	private static HorseRace getSaveGameRace() {
		SaveGame game = SaveGameManager.getLoadedGame();
		if (game == null) {
			return HorseRace.HAFLINGER;
		} else {
			return game.getHorseRace();
		}
	}

	public PlayerImpl() {
		this(getSaveGameRace());
	}
	
	@Deprecated
	public PlayerImpl(PlayerColor color) {
		this(getSaveGameRace());
	}
	
	public PlayerImpl(HorseRace horseRace) {
		race = new Race(horseRace);
		
		sprite = AssetManager.getTextureRegion("notChecked", "white_sprites");
		spriteStartX = sprite.getRegionX();
		spriteStartY = sprite.getRegionY();
		sprite.setRegion(spriteStartX, spriteStartY, DEFAULT_WIDTH,
				DEFAULT_HEIGHT);

		setBounds(getX(), getY(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
		addAction(new AnimationAction());
	}

	@Override
	public void draw(Batch batch, float alpha) {
		Color batchColor = batch.getColor();
	
		batch.setColor(getColor().mul(1, 1, 1, alpha));
		batch.draw(sprite.getTexture(), getX(), getY(), getOriginX(),
				getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation(), sprite.getRegionX(),
				sprite.getRegionY(), sprite.getRegionWidth(),
				sprite.getRegionHeight(), flipX, false);
		
		batch.setColor(batchColor);
	}
	
	@Override
	@Deprecated
	public void setPlayerColor(PlayerColor color) {
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
	@Deprecated
	public void toggleColor() {
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

	@Override
	public float getObedience() {
		return race.obedience();
	}

	@Override
	public float getIntelligence() {
		return race.intelligence();
	}

	@Override
	public float getAthletic() {
		return race.athletic();
	}

	@Override
	public String getRasse() {
		return race.name();
	}

}
