package com.haw.projecthorse.player;

import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.Race;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.savegame.json.SaveGame;

/**
 * Die Implementierung zum {@link Player Player-Interface}.
 * 
 * @author Oliver und Viktor
 * @version 1.3
 */

public class PlayerImpl extends Player {
	private static final float SCALEFACTOR = 1.2f; // hiermit wird erreicht,
													// dass nicht alle Spiele an
													// das neue Scaling
													// angepasst werden müseen

	
	private String imgFolder;
	private Map<String, TextureRegion> spriteMap;
	private TextureRegion activeSprite;
	private float speed = 0;
	// private int spriteStartX, spriteStartY;
	private Direction direction = Direction.RIGHT;
	private boolean flipX = false;

	/**
	 * Liefert die im aktuellen Spiel gespeicherte Rasse.
	 * 
	 * @return die Rasse
	 */
	private static HorseRace getSaveGameRace() {
		SaveGame game = SaveGameManager.getLoadedGame();
		if (game == null) {
			return HorseRace.HAFLINGER;
		} else {
			return game.getHorseRace();
		}
	}

	/**
	 * Defaultkonstruktor für einen Player mit der im aktuellen Spiel
	 * gespeicherten Rasse.
	 */
	public PlayerImpl() {
		this(getSaveGameRace());
	}

	/**
	 * Konsturktor für ein Player-Pferdchen.
	 * 
	 * @param horseRace
	 *            Die Rasse des Pferdes, welchen den Spieler darstellt.
	 */
	public PlayerImpl(final HorseRace horseRace) {
		race = new Race(horseRace);
		imgFolder = "player" + race.name();
		spriteMap = AssetManager.getAllTextureRegions(imgFolder);
		activeSprite = spriteMap.get("side-1");

		this.setScale(race.size() * SCALEFACTOR);

		flipX = true;
		setBounds(getX(), getY(), activeSprite.getRegionWidth(), activeSprite.getRegionHeight());
	}

	/**
	 * Ändert das aktuell Animationsbild des Players.
	 * 
	 * @param spriteName
	 *            Name des Animationsbildes
	 * @param direction
	 *            Richtung des Animationsbildes
	 * @param flipX
	 *            True, wenn das Bild gespiegelt werden soll
	 */
	public void changeSprite(final String spriteName,
			final Direction direction, final boolean flipX) {
		activeSprite = spriteMap.get(spriteName);
		this.flipX = flipX;
		this.direction = direction;
	}

	@Override
	public void act(final float delta) {
		super.act(delta);
	}

	@Override
	public void draw(final Batch batch, final float alpha) {
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
	public void changeAnimationSpeed(final float delta) {
		speed = Math.min(1, Math.max(0, speed + delta));
	}

	@Override
	public float getAnimationSpeed() {
		return speed;
	}

	@Override
	public void setAnimationSpeed(final float speed) {
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
