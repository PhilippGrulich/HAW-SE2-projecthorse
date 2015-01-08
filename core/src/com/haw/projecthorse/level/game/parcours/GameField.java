package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.race.HorseRace;

/**
 * Enthält die GameObjects, bietet Methoden für den Zugriff auf GameObjects an
 * und ermöglicht das Erzeugen von Popups.
 * @author Francis
 * @version 1.0
 */
public class GameField implements IGameFieldFuerGameInputListener,
		IGameFieldFuerGameObjectLogic, IGameFieldFuerGameOperator {

	private Stage stage; // In der Stage befinden sich alle Actors (Pferd,
							// GameObjects)
	private int width; // Spielfeldbreite
	private int height; // Spielfeldhöhe
	private List<GameObject> gameObjects; // Bis auf das Pferd alle Objekte auf
											// dem Spielfeld
	private List<ParcoursLoot> loot; // Zu gewinnende Loot-Objekte des Spiels.
	private Player player; // Das Pferd
	private float spaceBetweenGroundcavityAndGroundtop; // Pixel die
															// zwischen den
															// Mulden des Bodens
															// u. der Gesamthöhe
															// des Bodens
															// liegen.
	private float groundHeight; // Höhe des Bodens
	private float generalGameSpeed; // Spielgeschwindigkeit
	private Text scoreInformation; // Im Spiel angezeigte Punktzahl-
	private int score; // Punktwert des Spiels.
	private float grassGroundHeight; // Höhe des Gras aus dem Hintergrund.
	private GameOverPopup popup; // Popup das erscheint, wenn das Spiel zuende
									// ist
	private boolean gameOverState;
	private AudioManager audioManager; // Zuständig für Abspielen von Sound u.
										// Musik.
	private Music gallop; // Gallopieren-Musik im Hintergrund.
	private Sound eat; // Essen-Sound bei Berührung mit essbarem Gegenstand.
	private HashMap<String, TextureRegion> regions;
	private GameObjectInitializer goi;
	private boolean greetingPopupSet;
	private GreetingPopup greetingPopup;
	private HorseSelectionPopup selectionPopup;
	//private String race;

	/**
	 * Erzeugt das Spielfeld, lädt Sound und Musik von Parcours, setzt die
	 * übergebenen Parameter, initialisiert den Spieler und Lädt die Texturen.
	 * 
	 * @param s
	 *            Stage vom Level
	 * @param p
	 *            Viewport vom Level
	 * @param w
	 *            Breite des Spielfelds
	 * @param h
	 *            Höhe des Spielfelds
	 * @param a
	 *            AudioManager
	 */
	public GameField(final Stage s, final Viewport p, final int w, final int h, final AudioManager a) {
		audioManager = a;
		greetingPopupSet = false;
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
		spaceBetweenGroundcavityAndGroundtop = 5;
		score = 0;
		gameOverState = false;
		gameObjects = new ArrayList<GameObject>();
		loot = new ArrayList<ParcoursLoot>();
		generalGameSpeed = getWidth() / 3;
		HashMap<String, TextureRegion> regions = ((HashMap<String, TextureRegion>) AssetManager
				.getAllTextureRegions("parcours"));
		this.regions = regions;
		goi = new GameObjectInitializer(regions);
		loadTextureRegions();
		initLoot();
		/*
		 * player = new Player(getWidth(), getHeight()); initPlayer(goi,
		 * player.getRace());
		 */
	}

	public boolean getGameOverState() {
		return gameOverState;
	}

	public void setGameOverState(final boolean gameOverState) {
		this.gameOverState = gameOverState;
	}

	/**
	 * Zeigt in Abhängigkeit des GameState das entsprechende Popup an.
	 * @param g GameState der GREETING, WON oder LOST ist.
	 */
	public void showPopup(final GameState g) {
		if (g == GameState.GREETING && !greetingPopupSet) {
			this.greetingPopupSet = true;
			this.greetingPopup = new GreetingPopup();
			stage.addActor(greetingPopup.getPopup());
		} else if (g == GameState.LOST || g == GameState.WON) {
			stage.addActor(popup.getPopup(g));
		} 
	}
	
	/**
	 * Zeigt ein Popup an bei dem man das Pferd auswählen kann, mit dem man spielen möchte.
	 * @param races Die Rassen die in dem Spiel ausgewählt werden können.
	 */
	public void showPopup(final HorseRace[] races){
			selectionPopup = new HorseSelectionPopup(races, stage);
			stage.addActor(selectionPopup.getPopup());
	}


	@Override
	public void showPopup(final GameState g, final String username) {
		if (g == GameState.GREETING && !greetingPopupSet) {
			this.greetingPopupSet = true;
			this.greetingPopup = new GreetingPopup(username);
			stage.addActor(greetingPopup.getPopup());
		}
	}

	@Override
	public boolean isGreetingButtonPressed() {
		return greetingPopup.isButtonPressed();
	}

	/**
	 * Ruft act auf der Stage auf.
	 * @param delta Die Zeit, die seit dem letzten Frame vergangen ist.
	 */
	public void actGameField(final float delta) {
		stage.act(delta);
	}

	/**
	 * Lädt alle Texturen und GameObjects des Spiels.
	 */
	public void loadTextureRegions() {
		// groundHeight setzen vor Objekten die auf dem "Boden" stehen.
		TextureRegion r = this.goi.getTextureRegion("crosssection_long");

		EndlessBackground endlessBackground = new EndlessBackground(
				(int) stage.getWidth(), r, r.getRegionWidth()
						/ generalGameSpeed);
		endlessBackground.setName("crosssection_long");
		groundHeight = r.getRegionHeight();

		/*addGameObjectFixedWidthHeight("Hintergrund", getWidth(), getHeight(),
				0, 0, false, 0, 0, regions, this.goi, false, false);*/

		TextureRegion cloud = regions.get("cloud_fluffy");
		addGameObjectWithRelativHeight("cloud_fluffy", cloud.getRegionHeight() ,
				getWidth() + getWidth() * 10 / 100 + cloud.getRegionWidth(), getHeight() * 70 / 100,
				false, generalGameSpeed / 3f, 0, regions, this.goi, false, true);

		addGameObjectWithRelativHeight("cloud_fluffy",
				cloud.getRegionHeight() / 3,
				getWidth() + getWidth() * 20 / 100 + cloud.getRegionWidth(), getHeight() * 50 / 100,
				false, generalGameSpeed / 5f, 0, regions, this.goi, false, true);

		addGameObjectWithRelativHeight("cloud_fluffy",
				cloud.getRegionHeight() / 2,
				getWidth() + getWidth() * 30 / 100 + cloud.getRegionWidth(), getHeight() * 60 / 100,
				false, generalGameSpeed / 4.5f, 0, regions, this.goi, false,
				true);
		
		addGameObjectWithRelativHeight("cloud_fluffy",
				cloud.getRegionHeight() / 4,
				getWidth() + getWidth() * 40 / 100 + cloud.getRegionWidth(), getHeight() * 55 / 100,
				false, generalGameSpeed / 6f, 0, regions, this.goi, false,
				true);
		
		addGameObjectWithRelativHeight("cloud_fluffy",
				cloud.getRegionHeight() ,
				getWidth() + getWidth() * 50 / 100 + cloud.getRegionWidth(), getHeight() * 55 / 100,
				false, generalGameSpeed / 3.5f, 0, regions, this.goi, false,
				true);

		addGameObjectWithRelativHeight("rainbow", regions.get("rainbow")
				.getRegionHeight(), 50, getTopOfGroundPosition(), false, 0, 0,
				regions, this.goi, false, false);

		grassGroundHeight = getTopOfGroundPosition()
				+ (getTopOfGroundPosition() * 160 / 100);
		addGameObjectFixedWidthHeight("grass_ground", getWidth(),
				grassGroundHeight, 0, 0, false, 0, 0, regions, this.goi,
				false, false);

		addBushs(this.goi, regions);

		for (int i = 1; i < 9; i++) {
			addGameObjectWithRelativHeight("Kuerbis" + i,
					regions.get("Kuerbis" + i).getRegionHeight() * 15 / 50,
					-10000, getTopOfGroundPosition(), true, generalGameSpeed,
					10, regions, this.goi, false, true);
		}

		addGameObjectWithRelativHeight("cratetex", regions.get("cratetex")
				.getRegionHeight() * 9 / 50, -10000, getTopOfGroundPosition(),
				true, generalGameSpeed, -10, regions, this.goi, false, true);

		scoreInformation = new Text(AssetManager.getTextFont(FontSize.THIRTY),
				"Punkte: 0", 10, getHeight() - FontSize.THIRTY.getVal());
		scoreInformation.setColor(0, 0, 0, 1);
		scoreInformation.setName("Score");
		stage.addActor(scoreInformation);

		stage.addActor(endlessBackground);

		CollidableGameObject co = goi.getObject();
		co.setX(getWidth());
		addCollidableGameObject(co);

	}

	@Override
	public Array<Actor> getActors() {
		return stage.getActors();
	}

	/**
	 * Lädt die zu gewinnenden Loots.
	 */
	private void initLoot() {

		// TextureRegion r = regions.get("carrot");
		// RaceLoot horse1 = new RaceLoot(new Race(HorseRace.HANNOVERANER));
		// ParcoursLoot hannoveraner = new ParcoursLoot(10, horse1,
		// "Wow! Du hast ein neues Pferd gewonnen!");
		ParcoursLoot carrot = new ParcoursLoot(5, "Möhre",
				"Eine leckere Möhre für dein Pferd.");
		// loot.add(hannoveraner);
		ParcoursLoot hannoveraner = new ParcoursLoot(5, "Hannoveraner",
				"Ein wunderschöner Hannoveraner :-)");

		loot.add(carrot);
		loot.add(hannoveraner);
	}

	/**
	 * Lädt die Texture "name" aus "regions" und erzeugt ein GameObject in
	 * Abhängigkeit der übergebenen Parameter. Berechnet die Breite des
	 * GameObject in Abhängigkeit der gewünschten Höhe.
	 * 
	 * @param name
	 *            Name der Texture im TextureAtlas. Wird Name des GameObjects.
	 * @param desiredHeight
	 *            Gewünschte Höhe des GameObjects (Breite wird orignalgetreu
	 *            angepasst).
	 * @param x
	 *            x-Koordinate des GameObjects bei Spielstart.
	 * @param y
	 *            y-Koordinate des GameObjects bei Spielstart.
	 * @param collidable
	 *            true, wenn das Pferd mit diesem GameObject kollidieren kann.
	 * @param speed
	 *            Geschwindigkeit in Pixel pro Sekunde mit der sich das
	 *            GameObject bewegt.
	 * @param points
	 *            Punkte die dem Spieler bei Berührung mit dem GameObject
	 *            gutgeschrieben oder abgezogen werden.
	 * @param regions
	 *            Enthält alle Texturen des Spiels "Parcours" aus dem
	 *            TextureAtlas.
	 * @param goi
	 *            GameObjectInitializer initialisert das GameObject mit den
	 *            übergebenen Werten.
	 * @param isLoot
	 *            true, wenn das GameObject gewonnen werden kann.
	 * @param isMoveable
	 *            true, wenn speed > 0, sonst false.
	 */
	private void addGameObjectWithRelativHeight(final String name,
			final float desiredHeight, final float x, final float y, final boolean collidable,
			final float speed, final int points, final HashMap<String, TextureRegion> regions,
			final IGameObjectInitializerFuerGameObjectLogic goi,final boolean isLoot,
			final boolean isMoveable) {
		GameObject o = goi.initGameObject(regions.get(name), name, points,
				desiredHeight, goi.calcRelativeWidth(regions.get(name)
						.getRegionHeight(), regions.get(name).getRegionWidth(),
						desiredHeight), speed, x, y, collidable, isLoot,
				isMoveable);

		// gameObjects.add(o);
		if (!collidable){
			stage.addActor(o);
		}
	}

	/**
	 * Lädt die Texture "name" aus "regions" und erzeugt ein GameObject in
	 * Abhängigkeit der übergebenen Parameter. Berechnet die Höhe des GameObject
	 * in Abhängigkeit der übergebenen Breite.
	 * 
	 * @param name
	 *            Name der Texture im TextureAtlas. Wird Name des GameObjects.
	 * @param width
	 *            Gewünschte Höhe des GameObjects (Breite wird orignalgetreu
	 *            angepasst).
	 * @param height Die tatsächliche Höhe der Texture.
	 * @param x
	 *            x-Koordinate des GameObjects bei Spielstart.
	 * @param y
	 *            y-Koordinate des GameObjects bei Spielstart.
	 * @param collidable
	 *            true, wenn das Pferd mit diesem GameObject kollidieren kann.
	 * @param speed
	 *            Geschwindigkeit in Pixel pro Sekunde mit der sich das
	 *            GameObject bewegt.
	 * @param points
	 *            Punkte die dem Spieler bei Berührung mit dem GameObject
	 *            gutgeschrieben oder abgezogen werden.
	 * @param regions
	 *            Enthält alle Texturen des Spiels "Parcours" aus dem
	 *            TextureAtlas.
	 * @param goi
	 *            GameObjectInitializer initialisert das GameObject mit den
	 *            übergebenen Werten.
	 * @param isLoot
	 *            true, wenn das GameObject gewonnen werden kann.
	 * @param isMoveable
	 *            true, wenn speed > 0, sonst false.
	 */
	private void addGameObjectFixedWidthHeight(final String name, final float width,
			final float height, final float x, final float y, final boolean collidable, final float speed,
			final int points, final  HashMap<String, TextureRegion> regions,
			final IGameObjectInitializerFuerGameObjectLogic goi, final boolean isLoot,
			final boolean isMoveable) {
		GameObject o = goi.initGameObject(regions.get(name), name, points,
				height, width, speed, x, y, collidable, isLoot, isMoveable);

		// gameObjects.add(o);
		if (!collidable){
			stage.addActor(o);
		}
	}

	/**
	 * Lädt Büsche aus regions und setzt so viele auf den Gras-Hintergrund, bis
	 * von Koordinate 0 bis GameField.width Büsche gesetzt wurden.
	 * 
	 * @param goi
	 *            GameObjectInitalizer zur Initialisierung der Büsche.
	 * @param regions
	 *            Enthält alle TextureRegions des Spiels "Parcours" aus dem
	 *            TextureAtlas.
	 */
	private void addBushs(final IGameObjectInitializerFuerGameObjectLogic goi,
			final HashMap<String, TextureRegion> regions) {
		boolean outOfGameField = false;
		int[] possibleBushs = new int[3];
		possibleBushs[0] = 1;
		possibleBushs[1] = 2;
		possibleBushs[2] = 3;
		float randomPosY;
		int randomBush;
		float bushHeight = getWidth() * 3 / 100;
		float bushWidth;
		float maxY = grassGroundHeight - (grassGroundHeight * 10 / 100);

		float x = 0;

		while (!outOfGameField) {
			randomPosY = (float) Math.floor(Math.random()
					* (maxY - getTopOfGroundPosition())
					+ getTopOfGroundPosition());
			
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

			x = x + bushWidth - (bushWidth * 30 / 100);
			gameObjects.add(a);
			stage.addActor(a);

			if (a.getX() + a.getWidth() > getWidth()) {
				outOfGameField = true;
			}
		}
	}

	/**
	 * Addiert points Punkte zur Punktzahl u. passt die Punkteanzeige an.
	 * @param points Die Punkte, die auf den aktuellen Punktestand addierten werden sollen.
	 */
	public void addToScore(final int points) {
		score += points;
		scoreInformation.setText("Punkte: " + score);
	}

	/**
	 * Ruft draw auf der Stage auf.
	 */
	public void drawGameField() {
		Gdx.gl.glClearColor(0.765f, 0.765f, 1f, 1); 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	public List<GameObject> getGameObjects() {
		return goi.getObjects();
	}

	@Override
	public void addCollidableGameObject(final CollidableGameObject o) {
		stage.addActor(o);
	}

	@Override
	public void passBack(final CollidableGameObject o) {
		goi.passBack(o);
	}

	@Override
	public CollidableGameObject getRandomObject() {
		return goi.getObject();
	}

	/**
	 * Liefert die Höhe des Spielfelds.
	 * 
	 * @return height die Höhe des Spielfelds.
	 */
	public float getHeight() {
		return height;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	/**
	 * Liefert die Punktzahl des Spiels.
	 * 
	 * @return score die Punktzahl des Spiels.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Liefert die Stage in der alle Actors (GameObjects + Pferd + Boden) des
	 * Spiels sind.
	 * 
	 * @return stage Die Stage.
	 */
	public Stage getStage() {
		return stage;
	}

	public float getTopOfGroundPosition() {
		return groundHeight - spaceBetweenGroundcavityAndGroundtop;
	}

	public float getWidth() {
		return width;
	}

	@Override
	public void initPlayer(final HorseRace race) {
		if (race == null) {
			player = new Player(getWidth(), getHeight());
		} else {
			player = new Player(getWidth(), getHeight(), race);
			//player.setScale(1.75f);
		}
		com.haw.projecthorse.player.Player p = new com.haw.projecthorse.player.PlayerImpl();
		player.setHeight(p.getHeight());
		player.setWidth(p.getWidth());
		/*player.setHeight((getHeight() / 5f));
		
		player.setWidth(goi.calcRelativeWidth(p.getHeight(), p.getWidth(),
				getHeight() / 5f));*/
		//Da bei den Pferdebildern der Abstand vom unteren Bildrand bis zum
		//Anfang des Pferdes 15 Pixel beträgt
		player.setPosition(20, getTopOfGroundPosition() - 15*player.getScaleY());
		player.setName("Player");
		// Sprunghöhe u. Sprungweite auf 5% über maximale Höhe von Hindernissen
		// setzen
		float maxHeight = 0;
		float maxWidth = 0;
		for (GameObject o : getGameObjects()) {
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
		return loot;
	}

	@Override
	public boolean isButtonYesPressed(final GameState g) {
		return popup.isButtonYesPressed(g);
	}

	@Override
	public boolean isButtonNoPressed(final GameState g) {
		return popup.isButtonNoPressed(g);
	}

	@Override
	public void restart() {
		score = 0;
		scoreInformation.setText("Punkte: 0");
		gameOverState = false;
		for (Actor a : stage.getActors()) {
			if (a.getClass() == CollidableGameObject.class) {
				if (((GameObject) a).getPoints() > 0
						|| ((GameObject) a).getPoints() < 0) {
					((GameObject) a).setX(0 - a.getWidth() * 2);
				}
			}
		}
		player.setPosition(20, getTopOfGroundPosition() - 15*player.getScaleY());
	}

	@Override
	public void clear() {
		stage.clear();
		gameObjects.clear();
	}

	@Override
	public void removePopup() {
		for (Actor a : stage.getActors()) {
			if (a.getName().equals("Popup")) {
				a.remove();
			}
		}

	};

	/**
	 * Spield den Gallop-Sound als Music-Stream am.
	 */
	public void playGallop() {
		this.gallop.play();
	}

	/**
	 * Pausiert den Gallop-Sound-Music-Stream.
	 */
	public void pauseGallop() {
		this.gallop.pause();
	}

	/**
	 * Stoppt den Gallop-Sound-Music-Stream.
	 */
	public void stopGallop() {
		this.gallop.stop();
	}

	/**
	 * Spiel den Essen-Sound ab.
	 */
	public void eat() {
		this.eat.play();
	}

	@Override
	public void dispose() {
		gallop.stop();
		audioManager.dispose();
		stage.clear();
		stage.dispose();
	}

	@Override
	public float getGeneralGameSpeed() {
		// TODO Auto-generated method stub
		return generalGameSpeed;
	}

	@Override
	public void fadePopup(final float delta, final GameState g) {
		if (g == GameState.LOST || g == GameState.WON){
			popup.getPopup(g).act(delta);
		}

		if (g == GameState.GREETING){
			greetingPopup.act(delta);
		}
		
		if(g == GameState.HORSESELECTION){
			selectionPopup.act(delta);
		}
	}

	@Override
	public void initPlayerHannoveraner() {
		initPlayer(HorseRace.HANNOVERANER);
	}

	@Override
	public boolean isHorseSelected() {
		return (selectionPopup.getRace() != null) ? true : false;
	}

	@Override
	public HorseRace getSelectedRace() {
		return selectionPopup.getRace();
	}

}
