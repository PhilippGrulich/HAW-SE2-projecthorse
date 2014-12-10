package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.level.game.parcours.ParcoursLoot;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.actions.AnimationAction;

/**
 * Container-Klasse für GameObjects.
 * 
 * @author Francis
 *
 */
public class GameField implements IGameFieldFuerGameInputListener,
		IGameFieldFuerGameObjectLogic, IGameFieldFuerGameOperator {

	private Stage stage;
	private int width;
	private int height;
	private List<GameObject> gameObjects;
	private List<ParcoursLoot> loot;
	private Player player;
	private float SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP;
	private float groundHeight; // init wenn ground geladen
	private float generalGameSpeed;
	private Text scoreInformation;
	private int score;
	private float grass_ground_height;
	private GameOverPopup popup;
	private boolean gameOverState;
	private AudioManager audioManager;
	private Music gallop;
	private Sound eat;

	public GameField(Stage s, Viewport p, int w, int h, AudioManager a) {
		audioManager = a;
		AssetManager.loadMusic("parcours");
		AssetManager.loadSounds("parcours");
		gallop = this.audioManager.getMusic("parcours", "gallop.wav");
		gallop.setLooping(true);
		gallop.setVolume(0.3f);
		eat = this.audioManager.getSound("parcours", "eat.wav");
		stage = s;
		width = w;
		height = h;
		popup = new GameOverPopup();
		SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP = 5;
		score = 0;
		gameOverState = false;
		gameObjects = new ArrayList<GameObject>();
		loot = new ArrayList<ParcoursLoot>();
		generalGameSpeed = getWidth() / 3;
		loadTextureRegions(new GameObjectInitializer());
		player = new Player(getWidth(), getHeight());
		initPlayer(new GameObjectInitializer());
	}

	public boolean isGameOverState() {
		return gameOverState;
	}

	public void setGameOverState(boolean gameOverState) {
		this.gameOverState = gameOverState;
	}

	public void showPopup(GameState g) {
		stage.addActor(popup.getPopup(g));
	}

	public void actGameField(float delta) {
		stage.act(delta);
	}

	public void loadTextureRegions(IGameObjectInitializerFuerGameObjectLogic goi) {
		HashMap<String, TextureRegion> regions = ((HashMap<String, TextureRegion>) AssetManager
				.getAllTextureRegions("parcours"));

		// groundHeight setzen vor Objekten die auf dem "Boden" stehen.
		TextureRegion r = regions.get("crosssection_long");

		EndlessBackground endlessBackground = new EndlessBackground(
				(int) stage.getWidth(), r, r.getRegionWidth()
						/ generalGameSpeed);
		endlessBackground.setName("crosssection_long");
		groundHeight = r.getRegionHeight();

		addGameObjectFixedWidthHeight("Hintergrund", getWidth(), getHeight(),
				0, 0, false, 0, 0, regions, goi, false, false);

		TextureRegion cloud = regions.get("cloud_fluffy");
		addGameObjectWithRelativHeight("cloud_fluffy", cloud.getRegionHeight(),
				getWidth() - cloud.getRegionWidth(), getHeight() * 40 / 100,
				false, generalGameSpeed / 5, 0, regions, goi, false, true);

		addGameObjectWithRelativHeight("cloud_fluffy",
				cloud.getRegionHeight() / 3,
				getWidth() - cloud.getRegionWidth(), getHeight() * 30 / 100,
				false, generalGameSpeed / 6, 0, regions, goi, false, true);

		addGameObjectWithRelativHeight("cloud_fluffy",
				cloud.getRegionHeight() / 2,
				getWidth() - cloud.getRegionWidth(), getHeight() * 35 / 100,
				false, generalGameSpeed / 5.5f, 0, regions, goi, false, true);

		addGameObjectWithRelativHeight("rainbow", regions.get("rainbow")
				.getRegionHeight(), 50, getTopOfGroundPosition(), false, 0, 0,
				regions, goi, false, false);

		grass_ground_height = getTopOfGroundPosition()
				+ (getTopOfGroundPosition() * 160 / 100);
		addGameObjectFixedWidthHeight("grass_ground", getWidth(),
				grass_ground_height, 0, 0, false, 0, 0, regions, goi, false,
				false);

		addBushs(goi, regions);

		for (int i = 1; i < 9; i++) {
			addGameObjectWithRelativHeight("Kuerbis" + i,
					regions.get("Kuerbis" + i).getRegionHeight() * 15 / 50,
					-1000, getTopOfGroundPosition(), true, generalGameSpeed, 1,
					regions, goi, false, true);
		}

		addGameObjectWithRelativHeight("cratetex", regions.get("cratetex")
				.getRegionHeight() * 9 / 50, -1000, getTopOfGroundPosition(),
				true, generalGameSpeed, -10, regions, goi, false, true);

		initLoot(regions);

		scoreInformation = new Text(
				AssetManager.getTextFont(FontSize.THIRTY), "Punkte: 0", 10,
				getHeight() * 50 / 60);
		scoreInformation.setColor(0, 0, 0, 1);
		scoreInformation.setName("Score");
		stage.addActor(scoreInformation);

		stage.addActor(endlessBackground);

	}

	private void initLoot(HashMap<String, TextureRegion> regions) {
		System.out.println("Lade Loot");
		TextureRegion r = regions.get("carrot");
		ParcoursLoot carrot = new ParcoursLoot(10, "carrot",
				"Eine leckere Möhre für dein Pferd.");
		loot.add(carrot);
	}

	private void addGameObjectWithRelativHeight(String name,
			float desiredHeight, float x, float y, boolean collidable,
			float speed, int points, HashMap<String, TextureRegion> regions,
			IGameObjectInitializerFuerGameObjectLogic goi, boolean isLoot,
			boolean isMoveable) {
		GameObject o = goi.initGameObject(regions.get(name), name, points,
				desiredHeight, goi.calcRelativeWidth(regions.get(name)
						.getRegionHeight(), regions.get(name).getRegionWidth(),
						desiredHeight), speed, x, y, collidable, isLoot,
				isMoveable);

		gameObjects.add(o);
		stage.addActor(o);
	}

	private void addGameObjectFixedWidthHeight(String name, float width,
			float height, float x, float y, boolean collidable, float speed,
			int points, HashMap<String, TextureRegion> regions,
			IGameObjectInitializerFuerGameObjectLogic goi, boolean isLoot,
			boolean isMoveable) {
		GameObject o = goi.initGameObject(regions.get(name), name, points,
				height, width, speed, x, y, collidable, isLoot, isMoveable);

		gameObjects.add(o);
		stage.addActor(o);
	}

	private void addBushs(IGameObjectInitializerFuerGameObjectLogic goi,
			HashMap<String, TextureRegion> regions) {
		boolean outOfGameField = false;
		int[] possibleBushs = new int[3];
		possibleBushs[0] = 1;
		possibleBushs[1] = 2;
		possibleBushs[2] = 3;
		float randomPosY;
		int randomBush;
		float bushHeight = getWidth() * 3 / 100;
		float bushWidth;
		float maxY = grass_ground_height - (grass_ground_height * 5 / 100);

		float x = 0;

		while (!outOfGameField) {
			randomPosY = (float) Math.floor(Math.random()
					* (maxY - getTopOfGroundPosition())
					+ getTopOfGroundPosition());
			;
			randomBush = (int) Math.floor(Math.random()
					* (possibleBushs.length));
			bushWidth = goi.calcRelativeWidth(
					regions.get("bush" + possibleBushs[randomBush])
							.getRegionHeight(),
					regions.get("bush" + possibleBushs[randomBush])
							.getRegionWidth(), bushHeight);

			GameObject a = goi.initGameObject(
					regions.get("bush" + possibleBushs[randomBush]), "bush"
							+ possibleBushs[randomBush], 0, bushHeight,
					bushWidth, 0, x, randomPosY, false, false, false);

			x = x + bushWidth;
			gameObjects.add(a);
			stage.addActor(a);

			if (a.getX() + a.getWidth() > getWidth()) {
				outOfGameField = true;
			}
		}
	}

	public void addToScore(int points) {
		score += points;
		scoreInformation.setText("Punkte: " + score);
	}

	public void drawGameField() {
		stage.draw();
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public float getHeight() {
		return height;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	public int getScore() {
		return score;
	}

	public Stage getStage() {
		return stage;
	}

	public float getTopOfGroundPosition() {
		return groundHeight - SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP;
	}

	public float getWidth() {
		return width;
	}

	public void initPlayer(GameObjectInitializer goi) {
		player.setHeight(getHeight() / 5f);
		com.haw.projecthorse.player.Player p = new com.haw.projecthorse.player.PlayerImpl();
		player.setWidth(goi.calcRelativeWidth(p.getHeight(), p.getWidth(),
				getHeight() / 5f));
		player.setPosition(20, getTopOfGroundPosition() - 25);
		player.setName("Player");
		// Sprunghöhe u. Sprungweite auf 5% über maximale Höhe von Hindernissen
		// setzen
		float maxHeight = 0;
		float maxWidth = 0;
		for (GameObject o : gameObjects) {
			if (o.isCollidable()) {
				if (o.getHeight() > maxHeight) {
					maxHeight = o.getHeight();
				}
				if (o.getWidth() > maxWidth) {
					maxWidth = o.getWidth();
				}
			}
		}

		maxHeight = maxHeight * 250 / 100;
		maxWidth = maxWidth * 300 / 100;

		player.setJumpHeight(maxHeight);
		player.setJumpWitdh(maxWidth);
		player.setJumpSpeed(15);
		player.setupJumpFunction();
		player.setName("Player");
		player.setAnimationSpeed(0.3f);
		player.setDuration(generalGameSpeed * 1.5f);
		player.addAction(new AnimationAction(Direction.RIGHT));
		stage.addActor(player);
	}

	@Override
	public List<ParcoursLoot> getLoot() {
		// TODO Auto-generated method stub
		return loot;
	}

	@Override
	public boolean isButtonYesPressed(GameState g) {
		return popup.isButtonYesPressed(g);
	}

	@Override
	public boolean isButtonNoPressed(GameState g) {
		return popup.isButtonNoPressed(g);
	}

	@Override
	public void restart() {
		score = 0;
		scoreInformation.setText("Punkte: 0");
		gameOverState = false;
		getGameObjects().clear();
		loadTextureRegions(new GameObjectInitializer());
		initPlayer(new GameObjectInitializer());
	}

	@Override
	public void clear() {
		stage.clear();
		gameObjects.clear();
	}

	@Override
	public void removePopup() {
		for (Actor a : stage.getActors()) {
			System.out.println(a.getX());
			if (a.getName().equals("Popup")) {
				a.remove();
			}
		}

	};

	public void playGallop() {
		this.gallop.play();
	}

	public void pauseGallop() {
		this.gallop.pause();
	}

	public void stopGallop() {
		this.gallop.stop();
	}

	public void eat() {
		this.eat.play();
	}

	@Override
	public void dispose() {
		stage.clear();
		stage.dispose();
	}

	@Override
	public float getGeneralGameSpeed() {
		// TODO Auto-generated method stub
		return generalGameSpeed;
	}


}
