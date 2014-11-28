package com.haw.projecthorse.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.color.PlayerColor;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.Race;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.savegame.json.SaveGame;

public class PlayerImpl extends Player {
	private static final int DEFAULT_WIDTH = 115, DEFAULT_HEIGHT = 140;

	// Dieser Wert reguliert die maximale Animationsgeschwindigkeit, je kleiner
	// desto schneller

	private static HorseRace getSaveGameRace() {
		SaveGame game = SaveGameManager.getLoadedGame();
		if (game == null) {
			return HorseRace.HAFLINGER;
		} else {
			return game.getHorseRace();
		}
	}

	private TextureRegion sprite;
	private float speed = 0;
	private int spriteStartX, spriteStartY;

	private Direction direction = Direction.RIGHT;

	private boolean flipX = false;

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
	}
	
	public void chageDirection(Direction newDirection) {
		direction = newDirection;
	}

	public void changeSprite(Direction direction, int newSpriteX, int newSpriteY, boolean flipX) {
		sprite.setRegion(spriteStartX + newSpriteX * DEFAULT_WIDTH,
				spriteStartY + newSpriteY * DEFAULT_HEIGHT, DEFAULT_WIDTH,
				DEFAULT_HEIGHT);
		this.flipX = flipX;
		this.direction = direction;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
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
}
