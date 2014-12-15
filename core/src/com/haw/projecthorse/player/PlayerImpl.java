package com.haw.projecthorse.player;

import java.util.Map;

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
//	private static final int DEFAULT_WIDTH = 115, DEFAULT_HEIGHT = 140;

	// Dieser Wert reguliert die maximale Animationsgeschwindigkeit, je kleiner
	// desto schneller
	
	private String imgFolder;
	private Map<String, TextureRegion> spriteMap;
	private TextureRegion activeSprite;
	private float speed = 0;
//	private int spriteStartX, spriteStartY;
	private Direction direction = Direction.RIGHT;
	private boolean flipX = false;

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
//		this.scaleBy(-0.12f);
		this.setScale(1.75f);
	}
	
	/**
	 * @param horseRace
	 *            Die Rasse des Pferdes, welchen den Spieler darstellt.
	 */
	public PlayerImpl(HorseRace horseRace) {
		race = new Race(horseRace);
		imgFolder = "player" + race.name();
		spriteMap = AssetManager.getAllTextureRegions(imgFolder);
		activeSprite = spriteMap.get("side-1");
		
		flipX = true;
		
		setBounds(getX(), getY(), activeSprite.getRegionWidth(), activeSprite.getRegionHeight());
	}

	@Deprecated
	public PlayerImpl(PlayerColor color) {
		this(getSaveGameRace());
	}
	
	public void chageDirection(Direction newDirection) {
		direction = newDirection;
	}

	public void changeSprite(String spriteName, Direction direction, boolean flipX) {
		activeSprite = spriteMap.get(spriteName);
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
		batch.draw(activeSprite.getTexture(), getX(), getY(), getOriginX(),
				getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation(), activeSprite.getRegionX(),
				activeSprite.getRegionY(), activeSprite.getRegionWidth(),
				activeSprite.getRegionHeight(), flipX, false);

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
