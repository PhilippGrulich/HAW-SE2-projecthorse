package com.haw.projecthorse.level.game.parcours;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.util.background.EndlessBackground;

public class GameField {

	private Player player;
	private List<GameObject> gameObjects;
	private List<BackgroundObject> cloudObjects;
	private List<LootObject> lootObjects;
	private Stage stage;
	public static int width;
	public static int height;
	private float minDistanceX;
	private float maxDistanceX;
	private float minDistanceY;
	private float maxDistanceY;
	private float playersPointOfView;
	private float gameSpeed;
	private float heightInRelationToScreenHeight;
	public static final float SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP = 5;
	public int visibleGameObjects = 0;
	public int visibleLootObjects = 0;
	private BigDecimal maxPosXForObjectsOnTheGround;
	private BigDecimal maxPosXForObjectsInTheAir;
	private BigDecimal maxPosYForObjectsInTheAir;
	TextureRegion ground;
	EndlessBackground groundObj;
	Map<String, List<Float>> cloudPos = new HashMap<String, List<Float>>();
	public int visibleCloudObjects = 0;

	public GameField(Stage s, Viewport p, int width, int height) {
		gameObjects = new ArrayList<GameObject>();
		cloudObjects = new ArrayList<BackgroundObject>();
		lootObjects = new ArrayList<LootObject>();
		GameField.width = width;
		GameField.height = height;

		maxPosXForObjectsOnTheGround = new BigDecimal(0).setScale(5,
				RoundingMode.HALF_UP);
		maxPosXForObjectsInTheAir = new BigDecimal(0).setScale(5,
				RoundingMode.HALF_UP);
		maxPosYForObjectsInTheAir = new BigDecimal(0).setScale(5,
				RoundingMode.HALF_UP);

		heightInRelationToScreenHeight = (float) GameField.height / 7.0f;

		player = new Player();

		minDistanceX = 0;
		maxDistanceX = 0;
		minDistanceY = 0;
		maxDistanceY = 0;
		gameSpeed = 3;
		playersPointOfView = 20;

		stage = s;
		stage.setViewport(p);
		stage.addCaptureListener(new InputListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointer, int button) {
				if (!(player.getY() > getPlayerYDefault())) {
					player.jump();
				}
				return true;
			}
		});

		InputManager.addInputProcessor(stage);

	}

	/**************************************************************************************/
	/**************** Initialisierung Spieler / GameObjects / BackgroundObjects *************/
	/**************************************************************************************/
	public void loadBackgroundObjects() {
		// Boden erstellen
		ground = AssetManager.getTextureRegion("parcours", "crosssection_long");
		groundObj = new EndlessBackground((int) stage.getWidth(), ground,
				getGameSpeed());
		groundObj.setName("ground");

		// Hintergrund erstellen
		TextureRegion hintergrund = AssetManager.getTextureRegion("parcours",
				"Hintergrund");
		BackgroundObject hintergrundObj = new BackgroundObject(hintergrund, 0);
		hintergrundObj.setWidth(GameField.width);
		hintergrundObj.setHeight(GameField.height);
		hintergrundObj.setPosition(0, 0);
		hintergrundObj.setName("Hintergrund");

		TextureRegion groundGrass = AssetManager.getTextureRegion("parcours",
				"grass_ground");
		BackgroundObject groundGrassObj = new BackgroundObject(groundGrass, 0);
		groundGrassObj.setWidth(GameField.width);
		groundGrassObj.setHeight(70);
		groundGrassObj.setPosition(0, 0);
		groundGrassObj.setName("groundGrass");
		/********** Wolken ****************/
		float y1 = GameField.height * 80 / 100;
		float y2 = GameField.height * 72 / 100;
		float y3 = GameField.height * 64 / 100;
		float y4 = GameField.height * 56 / 100;
		float x1 = GameField.width * 100 / 100;
		float x2 = GameField.width * 140 / 100;
		float x3 = GameField.width * 110 / 100;
		float x4 = GameField.width * 160 / 100;
		generateCloud("cloud1", x1, y1, 1.3f);
		generateCloud("cloud2", x2, y2, 0.9f, 100);
		generateCloud("cloud3", x3, y3, 0.7f, 50);
		generateCloud("cloud4", x4, y4, 1.3f);

		/***** Regenbogen *****/
		TextureRegion rainbow = AssetManager.getTextureRegion("parcours",
				"rainbow");
		BackgroundObject rainbowObj = new BackgroundObject(rainbow, 0);
		float[] rainbowWidthHeight = getRelativeSize(rainbow,
				groundGrassObj.getHeight());
		rainbowObj.setWidth(rainbowWidthHeight[0]);
		rainbowObj.setHeight(rainbowWidthHeight[1]);
		float x = GameField.width * 70 / 100;
		rainbowObj.setPosition(x, groundGrassObj.getHeight());
		rainbowObj.setName("rainbow");

		// Reihenfolge des Hinzufuegens relevant: Spaeter hinzugefuegtes liegt
		// auf vorherigem.
		stage.addActor(hintergrundObj);
		stage.addActor(groundGrassObj);
		// Hinzufügen der Büsche in Stage im Methodenaufruf
		generateBushs(groundGrassObj.getHeight(), groundGrassObj.getWidth());
		stage.addActor(rainbowObj);

		for (int i = 0; i < cloudObjects.size(); i++) {
			stage.addActor(cloudObjects.get(i));
		}

		stage.addActor(groundObj);
	}

	private void generateBushs(float grassHeight, float grassWidth) {
		boolean outOfField = false;
		int[] possibleRelativePositions = new int[3];
		int[] possibleBushs = new int[3];
		possibleBushs[0] = 1;
		possibleBushs[1] = 2;
		possibleBushs[2] = 3;
		possibleRelativePositions[0] = 50;
		possibleRelativePositions[1] = 30;
		possibleRelativePositions[2] = 70;
		int randomPos;
		int randomBush;
		TextureRegion bush;
		BackgroundObject bushObj;
		float[] widthHeight;
		float y;
		float x = 0;

		for (int i = 0; !(outOfField); i++) {
			randomPos = (int) Math.floor(Math.random()
					* (possibleRelativePositions.length));
			randomBush = (int) Math.floor(Math.random()
					* (possibleBushs.length));

			bush = AssetManager.getTextureRegion("parcours", "bush"
					+ possibleBushs[randomBush]);
			bushObj = new BackgroundObject(bush, 0);
			widthHeight = getRelativeSize(bush, bush.getRegionHeight() / 2);
			bushObj.setWidth(widthHeight[0]);
			bushObj.setHeight(widthHeight[1]);
			y = grassHeight * possibleRelativePositions[randomPos] / 100;

			bushObj.setPosition(x, y);
			x = x + bushObj.getWidth() / 2;
			bushObj.setName("bush" + i);

			stage.addActor(bushObj);

			if (bushObj.getX() + bushObj.getWidth() > GameField.width) {
				outOfField = true;
			}

		}
	}

	/**
	 * Diese Methode verwendet die original Maße der Wolke.
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param speed
	 */
	private void generateCloud(String name, float x, float y, float speed) {
		TextureRegion wolke = AssetManager.getTextureRegion("parcours",
				"cloud_fluffy");
		BackgroundObject wolkeObj = new BackgroundObject(wolke, speed);
		wolkeObj.setName(name);
		float[] newWidthHeight = getRelativeSize(wolke, wolke.getRegionHeight());
		wolkeObj.setWidth(newWidthHeight[0]);
		wolkeObj.setHeight(newWidthHeight[1]);
		wolkeObj.setPosition(x, y);
		wolkeObj.setVisible(false);
		saveCloudPos(wolkeObj);
		cloudObjects.add(wolkeObj);
	}

	/**
	 * Diese Methode erlaubt die Vorgabe der Hoehe der Wolke. Die Breite passt
	 * sich automatisch an.
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param speed
	 * @param height
	 */
	private void generateCloud(String name, float x, float y, float speed,
			float height) {
		TextureRegion wolke = AssetManager.getTextureRegion("parcours",
				"cloud_fluffy");
		BackgroundObject wolkeObj = new BackgroundObject(wolke, speed);
		wolkeObj.setName(name);
		float[] newWidthHeight = getRelativeSize(wolke, height);
		wolkeObj.setWidth(newWidthHeight[0]);
		wolkeObj.setHeight(newWidthHeight[1]);
		wolkeObj.setPosition(x, y);
		wolkeObj.setVisible(false);
		saveCloudPos(wolkeObj);
		cloudObjects.add(wolkeObj);
	}

	private void saveCloudPos(Actor o) {
		cloudPos.put(o.getName(), new ArrayList<Float>());
		cloudPos.get(o.getName()).add(o.getX());
		cloudPos.get(o.getName()).add(o.getY());
	}

	public void loadGameObjects() {
		TextureRegion kiste = AssetManager.getTextureRegion("parcours",
				"cratetex");

		GameObject kisteObj = new GameObject(kiste, getGameSpeed());
		float playerWidthInRelationToScreenHeight = kiste.getRegionWidth()
				/ (float) this.getHeight();
		float playerHeightInRelationToScreenHeight = kiste.getRegionHeight()
				/ (float) this.getHeight();
		float seventhPartHeight = getHeightInRelationToScreenHeight();
		float relativelWidthOfPlayer = seventhPartHeight
				* playerWidthInRelationToScreenHeight
				/ playerHeightInRelationToScreenHeight;

		kisteObj.setHeight(seventhPartHeight);
		kisteObj.setWidth(relativelWidthOfPlayer);
		kisteObj.setName("Kiste");
		kisteObj.setX(-1);
		kisteObj.setY(getGroundHeight()
				- SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP);
		kisteObj.setVisible(false);

		setMinMaxDistancesX(player.getWidth() + (player.getWidth() / 2),
				player.getWidth() + player.getWidth());

		setMinMaxDistancesY(GameField.height / 2f, (GameField.height / 2)
				+ (GameField.height / 4));

		gameObjects.add(kisteObj);

		for (int i = 0; i < gameObjects.size(); i++) {
			stage.addActor(gameObjects.get(i));
		}
	}

	public void initializeLootObjects() {
		loadKuerbisse("Kuerbis1", true, false, -1);

		loadKuerbisse("Kuerbis2", false, false, -1);

		loadKuerbisse("Kuerbis3", false, false, -1);

		loadKuerbisse("Kuerbis4", false, false, -1);

		loadKuerbisse("Kuerbis5", false, false, -1);

		loadKuerbisse("Kuerbis6", false, false, -1);

		loadKuerbisse("Kuerbis7", false, false, -1);

		loadKuerbisse("Kuerbis8", false, false, -1);

	}

	public void loadKuerbisse(String name, boolean flipX, boolean flipY, float x) {
		TextureRegion pumpkin = AssetManager.getTextureRegion("parcours", name);
		pumpkin.flip(flipX, flipY);
		LootObject pumpkinObj = new LootObject(pumpkin, getGameSpeed());
		float[] newWidthHeight = getRelativeSize(pumpkin, GameField.height / 12);

		pumpkinObj.setHeight(newWidthHeight[1]);
		pumpkinObj.setWidth(newWidthHeight[0]);
		pumpkinObj.setX(GameField.width - x);
		pumpkinObj.setY(getGroundHeight()
				- SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP);
		pumpkinObj.setVisible(false);

		pumpkinObj.setName(name);

		lootObjects.add(pumpkinObj);
		stage.addActor(pumpkinObj);
	}

	public void initializePlayer() {

		float[] newWidthHeight = getRelativeSize(player,
				getHeightInRelationToScreenHeight());
		player.setHeight(newWidthHeight[1]);
		player.setWidth(newWidthHeight[0]);

		player.setPosition(playersPointOfView, getPlayerYDefault());
		player.setName("Player");

		stage.addActor(player);
	}

	/******************************************************************************/
	/************************ Delegation an Stage ***********************************/
	/******************************************************************************/

	public void actGameField(float delta) {
		stage.act(delta);
	}

	public void drawGameField() {
		stage.draw();
	}

	/******************************************************************************/
	/************************ Methoden fuer Spiellogik ******************************/
	/******************************************************************************/
	public void setMinMaxDistancesX(float minDistance, float maxDistance) {
		this.minDistanceX = minDistance;
		this.maxDistanceX = maxDistance;
	}

	public void setMinMaxDistancesY(float minDistance, float maxDistance) {
		this.minDistanceY = minDistance;
		this.maxDistanceY = maxDistance;
	}

	public float getMinDistanceX() {
		return minDistanceX;
	}

	public float getMinDistanceY() {
		return minDistanceY;
	}

	public float getMaxDistanceX() {
		return maxDistanceX;
	}

	public float getMaxDistanceY() {
		return maxDistanceY;
	}

	public float getRandomYForObjectInTheAir(Actor o) {
		float randY = (float) Math.floor(Math.random()
				* (maxDistanceY - minDistanceY) + minDistanceY);

		BigDecimal b_randY = new BigDecimal(randY).setScale(5,
				RoundingMode.HALF_UP);

		maxPosYForObjectsInTheAir = new BigDecimal(b_randY.floatValue()
				+ o.getHeight()).setScale(5, RoundingMode.HALF_UP);

		return b_randY.floatValue();
	}

	public float getRandomXForObjectInTheAir(Actor o) {

		return maxPosXForObjectsOnTheGround.floatValue();
	}

	public float getRandomXForObjectOnTheGround(Actor o) {
		float rand = (float) Math.floor(Math.random()
				* (maxDistanceX - minDistanceX) + minDistanceX);

		BigDecimal b_rand = new BigDecimal(rand).setScale(5,
				RoundingMode.HALF_UP);

		if (maxPosXForObjectsOnTheGround.floatValue() > width) {
			b_rand = new BigDecimal(maxPosXForObjectsOnTheGround.floatValue()
					+ b_rand.floatValue()).setScale(5, RoundingMode.HALF_UP);
		} else {
			b_rand = new BigDecimal(width + b_rand.floatValue()).setScale(5,
					RoundingMode.HALF_UP);
		}
		maxPosXForObjectsOnTheGround = new BigDecimal(b_rand.floatValue()
				+ o.getWidth()).setScale(5, RoundingMode.HALF_UP);
		return b_rand.floatValue();
	}

	public float getRandomY(GameObject o) {
		float y = GameField.height
				+ (float) Math.floor(Math.random()
						* (maxDistanceY - minDistanceY) + minDistanceY);
		return y;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public float getGameSpeed() {
		return gameSpeed;
	}

	public void setActorUnvisible(Actor a) {
		a.setVisible(false);
		if (a instanceof LootObject) {
			visibleLootObjects -= 1;
		} else if (a instanceof GameObject) {
			visibleGameObjects -= 1;
		} else if (a instanceof BackgroundObject) {
			visibleCloudObjects -= 1;
		}
	}

	public void setActorVisbile(Actor a, float x) {
		a.setVisible(true);
		a.setX(x);
		if (a instanceof LootObject) {
			visibleLootObjects += 1;
		} else if (a instanceof GameObject) {
			visibleGameObjects += 1;
		} else if (a instanceof BackgroundObject) {
			visibleCloudObjects += 1;
		} else {
			System.out.println("false");
		}
	}

	public void setActorCloudObjectUnvisible(Actor a) {
		a.setVisible(false);
		visibleCloudObjects -= 1;

	}

	public void setActorCloudObjectVisible(Actor a, float x, float y) {
		a.setVisible(true);
		a.setPosition(x, y);
		visibleCloudObjects += 1;
	}

	public int getRandomActorPosition(Actor a) {
		if (a instanceof LootObject) {
			return (int) Math.floor(Math.random() * (lootObjects.size()));
		} else if (a instanceof GameObject) {
			return (int) Math.floor(Math.random() * (gameObjects.size()));
		} else if (a instanceof BackgroundObject) {
			return (int) Math.floor(Math.random() * (cloudObjects.size()));
		} else {
			return -1;
		}
	}

	public void setObjectsOnGroundMaxPos(float x, float width) {
		BigDecimal b_x = new BigDecimal(x).setScale(4, RoundingMode.HALF_UP);
		BigDecimal b_width = new BigDecimal(width).setScale(4,
				RoundingMode.HALF_UP);
		maxPosXForObjectsOnTheGround = new BigDecimal(b_x.floatValue()
				+ b_width.floatValue()).setScale(5, RoundingMode.HALF_UP);
	}

	public void setObjectsInTheAirMaxPosXY(float x, float y, float width) {
		BigDecimal b_x = new BigDecimal(x).setScale(4, RoundingMode.HALF_UP);
		BigDecimal b_y = new BigDecimal(y).setScale(4, RoundingMode.HALF_UP);
		BigDecimal b_width = new BigDecimal(width).setScale(4,
				RoundingMode.HALF_UP);

		maxPosXForObjectsInTheAir = new BigDecimal(b_x.floatValue()
				+ b_width.floatValue()).setScale(5, RoundingMode.HALF_UP);
		maxPosYForObjectsInTheAir = new BigDecimal(b_y.floatValue()
				+ b_width.floatValue()).setScale(5, RoundingMode.HALF_UP);
	}

	/**
	 * Liefert die neu berechnete Hoehe und Breite einer TextureRegion, wenn
	 * erforderlich ist, dass die Groesse der TextureRegion angepasst werden
	 * soll, aber die Seitenverhaeltnisse gleich bleiben sollen. Anzugeben ist
	 * nur die neue Hoehe. Die Breite wird so berechnet, dass das Verhaeltnis
	 * von alter Hoehe zu alter Breite dasselbe ist wie von neuer Hoehe zu neuer
	 * Breite.
	 * 
	 * @param object
	 *            Die TextureRegion deren Groesse veraendert werden soll.
	 * @param newHeight
	 *            Die neue Hoehe
	 * @return
	 */
	public float[] getRelativeSize(TextureRegion object, float newHeight) {
		float widthInRelationToScreenHeight = object.getRegionWidth()
				/ (float) this.getHeight();
		float heightInRelationToScreenHeight = object.getRegionHeight()
				/ (float) this.getHeight();
		// float relativeHeight = newHeight;
		float relativeWidth = newHeight * widthInRelationToScreenHeight
				/ heightInRelationToScreenHeight;
		float[] widthHeight = new float[2];
		widthHeight[0] = relativeWidth;
		widthHeight[1] = newHeight;
		return widthHeight;
	}

	/**
	 * BREITE und HOEHE müssen vor der Verwendung dieser Methode beim Actor
	 * gesetzt wordens sein. Liefert die neu berechnete Hoehe und Breite einer
	 * TextureRegion, wenn erforderlich ist, dass die Groesse der TextureRegion
	 * angepasst werden soll, aber die Seitenverhaeltnisse gleich bleiben
	 * sollen. Anzugeben ist nur die neue Hoehe. Die Breite wird so berechnet,
	 * dass das Verhaeltnis von alter Hoehe zu alter Breite dasselbe ist wie von
	 * neuer Hoehe zu neuer Breite.
	 * 
	 * @param object
	 *            Die TextureRegion deren Groesse veraendert werden soll.
	 * @param newHeight
	 *            Die neue Hoehe
	 * @return
	 */
	public float[] getRelativeSize(Actor object, float newHeight) {
		float widthInRelationToScreenHeight = object.getWidth()
				/ (float) this.getHeight();
		float heightInRelationToScreenHeight = object.getHeight()
				/ (float) this.getHeight();
		// float relativeHeight = newHeight;
		float relativeWidth = newHeight * widthInRelationToScreenHeight
				/ heightInRelationToScreenHeight;
		float[] widthHeight = new float[2];
		widthHeight[0] = relativeWidth;
		widthHeight[1] = newHeight;
		return widthHeight;
	}

	/******************************************************************************/
	/************************ Getter / Setter **************************************/
	/******************************************************************************/

	public Player getPlayer() {
		return player;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<BackgroundObject> getBackgroundObjects() {
		return cloudObjects;
	}

	public List<LootObject> getLootObjects() {
		return lootObjects;
	}

	public float getGroundHeight() {
		return ground.getRegionHeight();
	}

	public float getPlayerYDefault() {
		return getGroundHeight() - SPACE_BETWEEN_GROUNDCAVITY_AND_GROUNDTOP
				- 12.5f;
	}

	public float getPlayersPointOfView() {
		return playersPointOfView;
	}

	public void setGroundX(float x) {
		groundObj.setX(x);
	}

	public float getGroundX() {
		return groundObj.getX();
	}

	private float getHeightInRelationToScreenHeight() {
		return heightInRelationToScreenHeight;
	}

	public float getObjectsOnGroundMaxPos() {
		return maxPosXForObjectsOnTheGround.floatValue();
	}

	public float getObjectsInTheAirMaxPosX() {
		return maxPosXForObjectsInTheAir.floatValue();
	}

	public float getObjectsInTheAirMaxPosY() {
		return maxPosYForObjectsInTheAir.floatValue();
	}

	public float getCloudsX(Actor o) {
		return cloudPos.get(o.getName()).get(0);
	}

	public float getCloudsY(Actor o) {
		return cloudPos.get(o.getName()).get(1);
	}

}
