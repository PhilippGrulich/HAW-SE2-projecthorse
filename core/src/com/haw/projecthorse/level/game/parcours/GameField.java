package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.player.Direction;

/**
 * Container-Klasse f�r GameObjects.
 * 
 * @author Francis
 *
 */
public class GameField implements IGameFieldFuerGameInputListener, IGameFieldFuerGameObjectLogic, IGameFieldFuerGameOperator{

	private Stage stage;
	private int width;
	private int height;
	private List<GameObject> gameObjects;
	private Player player;
	private float SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP;
	private float groundHeight; // init wenn ground geladen
	private float generalGameSpeed;
	private Text scoreInformation;
	private int score;
	private float grass_ground_height;

	public GameField(Stage s, Viewport p, int w, int h) {
		stage = s;
		width = w;
		height = h;
		SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP = 5;
		score = 0;
		gameObjects = new ArrayList<GameObject>();
		generalGameSpeed = getWidth() / 3;
		loadTextureRegions(new GameObjectInitializer());
		player = new Player(getWidth(), getHeight());
		initPlayer(new GameObjectInitializer());
	}

	public void actGameField(float delta) {
		stage.act(delta);
	}

	public void loadTextureRegions(IGameObjectInitializerFuerGameObjectLogic goi) {
		HashMap<String, TextureRegion> regions = (HashMap) AssetManager
				.getAllTextureRegions("parcours");

		// groundHeight setzen vor Objekten die auf dem "Boden" stehen.
		TextureRegion r = regions.get("crosssection_long");
		EndlessBackground endlessBackground = new EndlessBackground(
				(int) stage.getWidth(), r, generalGameSpeed);
		endlessBackground.setName("crosssection_long");
		groundHeight = r.getRegionHeight();

		addGameObjectFixedWidthHeight("Hintergrund", getWidth(), getHeight(),
				0, 0, false, 0, 0, regions, goi, false);
		
		addGameObjectWithRelativHeight("cloud_fluffy",
				regions.get("cloud_fluffy").getRegionHeight(), -10,
				getHeight() * 40 / 100, false, generalGameSpeed / 5 , 0, regions,
				goi, false);

		addGameObjectWithRelativHeight("cloud_fluffy",
				regions.get("cloud_fluffy").getRegionHeight() / 3, -10,
				getHeight() * 30 / 100, false, generalGameSpeed / 6, 0, regions,
				goi, false);

		addGameObjectWithRelativHeight("cloud_fluffy",
				regions.get("cloud_fluffy").getRegionHeight() / 2, -10,
				getHeight() * 35 / 100, false, generalGameSpeed / 5.5f, 0, regions,
				goi, false);

		addGameObjectWithRelativHeight("rainbow", regions.get("rainbow")
				.getRegionHeight(), 50, getTopOfGroundPosition(), false, 0, 0,
				regions, goi, false);

		grass_ground_height = getTopOfGroundPosition()
				+ (getTopOfGroundPosition() * 160 / 100);
		addGameObjectFixedWidthHeight("grass_ground", getWidth(),
				grass_ground_height, 0, 0, false, 0, 0, regions, goi, false);

		addBushs(goi, regions);

		for (int i = 1; i < 9; i++) {
			addGameObjectWithRelativHeight("Kuerbis" + i,
					regions.get("Kuerbis" + i).getRegionHeight() * 15 / 50,
					-100, getTopOfGroundPosition(), true, generalGameSpeed, 1,
					regions, goi, false);
		}

		addGameObjectWithRelativHeight("cratetex", regions.get("cratetex")
				.getRegionHeight() * 9 / 50, -100, getTopOfGroundPosition(),
				true, generalGameSpeed, -10, regions, goi, false);

		scoreInformation = new Text(AssetManager.getTextFont(FontSize.DREISSIG),
				"Punkte: 0", 10, getHeight() * 50 / 60);
		scoreInformation.setColor(0, 0, 0, 1);
		stage.addActor(scoreInformation);
		
		stage.addActor(endlessBackground);

	}

	private void addGameObjectWithRelativHeight(String name,
			float desiredHeight, float x, float y, boolean collidable,
			float speed, int points, HashMap<String, TextureRegion> regions,
			IGameObjectInitializerFuerGameObjectLogic goi, boolean isLoot) {
		GameObject o = goi.initGameObject(regions.get(name), name, points,
				desiredHeight, goi.calcRelativeWidth(regions.get(name)
						.getRegionHeight(), regions.get(name).getRegionWidth(),
						desiredHeight), speed, x, y, collidable,isLoot);

		gameObjects.add(o);
		stage.addActor(o);
	}

	private void addGameObjectFixedWidthHeight(String name, float width,
			float height, float x, float y, boolean collidable, float speed,
			int points, HashMap<String, TextureRegion> regions,
			IGameObjectInitializerFuerGameObjectLogic goi, boolean isLoot) {
		GameObject o = goi.initGameObject(regions.get(name), name, points,
				height, width, speed, x, y, collidable, isLoot);

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
					bushWidth, 0, x, randomPosY, false, false);

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
		player.setHeight(getHeight() /3);
		com.haw.projecthorse.player.Player p = new com.haw.projecthorse.player.PlayerImpl();
		player.setWidth(goi.calcRelativeWidth(p.getHeight(), p.getWidth(),
				getHeight() / 3));
		player.setPosition(20, getTopOfGroundPosition());

		// Sprungh�he u. Sprungweite auf 5% �ber maximale H�he von Hindernissen
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
		player.setAnimation(Direction.RIGHT, 0.3f);
		stage.addActor(player);
	}

	@Override
	public List<Loot> getLoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
