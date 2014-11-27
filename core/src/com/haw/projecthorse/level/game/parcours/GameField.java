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
 * Container-Klasse für GameObjects.
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
	private List<Loot> loot;
	private boolean gameAlive = true;

	public List<Loot> getLoot() {
		return loot;
	}

	public GameField(Stage s, Viewport p, int w, int h) {
		s.setViewport(p);
		stage = s;
		width = w;
		height = h;
		SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP = 5;
		score = 0;
		gameObjects = new ArrayList<GameObject>();
		generalGameSpeed = getWidth() / 3;
		loot = new ArrayList<Loot>();
		loadTextureRegions(new GameObjectInitializer());
		player = new Player(getWidth(), getHeight());
		initPlayer(new GameObjectInitializer());
	}

	public void actGameField(float delta) {
		stage.act(delta);
	}

	public void loadTextureRegions(IGameObjectInitializerFuerGameObjectLogic goi) {
		HashMap<String, TextureRegion> regions = (HashMap<String, TextureRegion>) AssetManager
				.getAllTextureRegions("parcours");

		//********************Hintergrundobjekte erzeugen****************************//
		// groundHeight setzen vor Objekten die auf dem "Boden" stehen.
		TextureRegion r = regions.get("crosssection_long");
		EndlessBackground endlessBackground = new EndlessBackground(
				(int) stage.getWidth(), r, generalGameSpeed);
		endlessBackground.setName("crosssection_long");
		groundHeight = r.getRegionHeight();

		addGameObjectFixedWidthHeight("Hintergrund", getWidth(), getHeight(),
				0, 0, false, 0, 0, regions, goi, false);
		
		addGameObjectWithRelativHeight("cloud_fluffy",
				regions.get("cloud_fluffy").getRegionHeight(), -100,
				getHeight() * 82 / 100, false, generalGameSpeed / 5 , 0, regions,
				goi, false);

		addGameObjectWithRelativHeight("cloud_fluffy",
				regions.get("cloud_fluffy").getRegionHeight() / 3, -100,
				getHeight() * 76 / 100, false, generalGameSpeed / 6, 0, regions,
				goi, false);

		addGameObjectWithRelativHeight("cloud_fluffy",
				regions.get("cloud_fluffy").getRegionHeight() / 2, -100,
				getHeight() * 70 / 100, false, generalGameSpeed / 5.5f, 0, regions,
				goi, false);

		addGameObjectWithRelativHeight("rainbow", regions.get("rainbow")
				.getRegionHeight(), 62, getTopOfGroundPosition(), false, 0, 0,
				regions, goi, false);

		grass_ground_height = getTopOfGroundPosition()
				+ (getTopOfGroundPosition() * 160 / 100);
		addGameObjectFixedWidthHeight("grass_ground", getWidth(),
				grass_ground_height, 0, 0, false, 0, 0, regions, goi, false);

		addBushs(goi, regions);

		//**************Punktestand verändernde Objekte erzeugen*************//
		//Kürbisse
		for (int i = 1; i < 9; i++) {
			addGameObjectWithRelativHeight("Kuerbis" + i,
					getPercentOfHeight(5),
					-100, getTopOfGroundPosition(), true, generalGameSpeed, 1,
					regions, goi, false);
		}

		//Hindernisse
		addGameObjectWithRelativHeight("cratetex", getPercentOfHeight(9) , -100, getTopOfGroundPosition(),
				true, generalGameSpeed, -10, regions, goi, false);
		
		addGameObjectWithRelativHeight("dirt_mountain_dirt_cap", getPercentOfHeight(9), -100, getTopOfGroundPosition() - 15,
				true, generalGameSpeed, -10, regions, goi, false);

		//**********************Loot-Objekte erzeugen*******************************//
		addGameObjectWithRelativHeight("dirt_mountain_dirt_cap", regions.get("dirt_mountain_dirt_cap")
				.getRegionHeight() * 9 / 100, -100, getTopOfGroundPosition(),
				false, 0, 1, regions, goi, true);
		//*************************************************************************//
		//Punktestand erzeugen
		scoreInformation = new Text(AssetManager.getTextFont(FontSize.DREISSIG),
				"Punkte: 0", 10, getHeight());
		scoreInformation.setY(scoreInformation.getY() - scoreInformation.getHeight());
		scoreInformation.setColor(0, 0, 0, 1);
		stage.addActor(scoreInformation);
		
		stage.addActor(endlessBackground);

	}

	private void addGameObjectWithRelativHeight(String name,
			float desiredHeight, float x, float y, boolean collidable,
			float speed, int points, HashMap<String, TextureRegion> regions,
			IGameObjectInitializerFuerGameObjectLogic goi, boolean isLoot) {

		GameObject o;
		if(isLoot){
			
			 o = goi.initGameObject(regions.get(name), name, points,
						desiredHeight, goi.calcRelativeWidth(regions.get(name)
								.getRegionHeight(), regions.get(name).getRegionWidth(),
								desiredHeight), speed, x, y, collidable, isLoot);
			 System.out.println("collid: " + o.getName());
			loot.add((Loot) o);
		}else{
			 o =  goi.initGameObject(regions.get(name), name, points,
						desiredHeight, goi.calcRelativeWidth(regions.get(name)
								.getRegionHeight(), regions.get(name).getRegionWidth(),
								desiredHeight), speed, x, y, collidable, isLoot);
			gameObjects.add(o);
		}
		
		stage.addActor(o);
	}

	private void addGameObjectFixedWidthHeight(String name, float width,
			float height, float x, float y, boolean collidable, float speed,
			int points, HashMap<String, TextureRegion> regions,
			IGameObjectInitializerFuerGameObjectLogic goi, boolean isLoot) {
		
		GameObject o;
		if(isLoot){
			 o = goi.initGameObject(regions.get(name), name, points,
					height, width, speed, x, y, collidable, isLoot);
			loot.add((Loot) o);
		}else{
			 o = goi.initGameObject(regions.get(name), name, points,
					height, width, speed, x, y, collidable, isLoot);
			gameObjects.add(o);
		}
		stage.addActor(o);
	}
	
	private float getPercentOfHeight(float f){
		return getHeight() * f / 100;
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
		com.haw.projecthorse.player.Player p = new com.haw.projecthorse.player.PlayerImpl();
		float h = p.getHeight();
		float w = p.getWidth();
		float nh = getHeight() * 15 / 100;
		player.setHeight(nh);
		player.setWidth(goi.calcRelativeWidth(h, w,
				nh));
		player.setPosition(20, getTopOfGroundPosition());

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
		player.setAnimation(Direction.RIGHT, 0.3f);
		stage.addActor(player);
	}
}
