package com.haw.projecthorse.level.worldmap;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.PlayerImpl;

public class WorldMap extends Level {

	private final OrthographicCamera camera; // Zum Zentrieren des Bildes auf
												// das Pferd
	private final PlayerImpl player;
	private final Stage stage;
	private final Preferences prefs; // Zum Speichern der zuletzt besuchten
										// Stadt
	private final Image germanyImg, worldImg, pointImg, leftImg, rightImg;

	private String[] cities;
	private HashMap<String, int[]> cityInfos;
	
	private float targetX, targetY;


	public WorldMap() throws LevelNotFoundException {
		super();

		stage = new Stage(getViewport());
		InputManager.addInputProcessor(stage);
		camera = getCam();
		player = new PlayerImpl(); // TODO color auslesen und im Konstruktor
									// setzen

		getJasonCities();

		prefs = Gdx.app.getPreferences("WorldMapPrefs");
		if (!prefs.contains("lastCity"))
			prefs.putString("lastCity", cities[0]); // setze die erste Stadt als
													// Standartort falls nicht
													// gesetzt

		germanyImg = new Image(AssetManager.getTextureRegion("worldmap",
				"germanymap_scaled"));
		worldImg = new Image(AssetManager.getTextureRegion("worldmap",
				"erde-und-sterne"));
		pointImg = new Image(AssetManager.getTextureRegion("worldmap",
				"shadedLight28"));
		leftImg = new Image(AssetManager.getTextureRegion("worldmap",
				"shadedLight24"));
		rightImg = new Image(AssetManager.getTextureRegion("worldmap",
				"shadedLight25"));
		
		pointImg.setColor(Color.RED);
		pointImg.setScale(0.5f * (width / 720));

		initAnimation();
	}

<<<<<<< HEAD
	private Image addBackground() {

		AtlasRegion wolrldmapatlasregion = worldmapatlas
				.findRegion("erde-und-sterne");
		Image worldmapimage = new Image(new Texture(Gdx.files.internal("pictures/worldmap/erde-und-sterne.png")));
		worldmapimage.toBack();
		worldmapimage.setY((height - worldmapimage.getHeight()) / 2);
		stage.addActor(worldmapimage);
		return worldmapimage;
=======
	// Erstellt eine Startanimation die einmalig abgearbeitet wird
	private void initAnimation() {
		germanyImg.setColor(1, 1, 1, 0);
		worldImg.setColor(1, 1, 1, 0);
		pointImg.setColor(1, 1, 1, 0);
		player.setColor(1, 1, 1, 0);
		player.scaleBy(-0.5f);
		
		int[] cityCoordinates = cityInfos.get(prefs.getString("lastCity")); 
		
		player.setPosition(cityCoordinates[0] - player.getWidth()/2, cityCoordinates[1]);

		//player.setPosition(cityCoordinates[0] - player.getWidth()/2, 900);
		
		worldImg.setOrigin(0.643f * width, 0.65f * height); // Setzt den
															// Zielpunkt auf
															// Europa

		pointImg.setPosition(worldImg.getOriginX() - pointImg.getWidth() / 2,
				worldImg.getOriginY() - pointImg.getHeight() / 2);


		ScaleByAction scaleWorld = Actions.scaleBy(8f, 8f, 1f); // Setzt den
																// Zoomfaktor
																// und die Dauer

		SequenceAction worldMapSequence = Actions.sequence(Actions.fadeIn(1f),
				Actions.delay(1f), scaleWorld, Actions.fadeOut(0.25f));
		SequenceAction pointBlinkSequence = Actions.sequence(
				Actions.delay(1.0f), Actions.fadeIn(0.25f),
				Actions.fadeOut(0.25f), Actions.fadeIn(0.25f),
				Actions.fadeOut(0.25f));
		SequenceAction germanyMapSequence = Actions.sequence(
				Actions.delay(3.0f), Actions.fadeIn(0.25f));
		SequenceAction playerSequence = Actions.sequence(
				Actions.delay(3.0f), Actions.fadeIn(0.25f), Actions.delay(3.0f), Actions.moveBy(200, -100, 2f));
		
		worldImg.addAction(worldMapSequence);
		pointImg.addAction(pointBlinkSequence);
		germanyImg.addAction(germanyMapSequence);
		player.addAction(playerSequence);
		
		stage.addActor(worldImg);
		stage.addActor(pointImg);
		stage.addActor(germanyImg);
		stage.addActor(player);
>>>>>>> 8f0811905b1221469c5a470e8d67bd115d0c3839

	}

	private void getJasonCities() throws LevelNotFoundException {

		MenuObject menuObject = GameManagerFactory.getInstance().getMenuObject(
				"worldmap");
		String cityCoordinates = menuObject.getParameter()
				.get("cityCoordinates").replaceAll("\\s", "");

		cities = new String[cityCoordinates.split(";").length];
		cityInfos = new HashMap<String, int[]>();

		int i = 0;
		for (String cityEntry : cityCoordinates.split(";")) {
			String[] cityDetails = cityEntry.split(",");
			if (cityDetails.length == 3) {
				cities[i++] = cityDetails[0];

				int x = Integer.parseInt(cityDetails[1]);
				int y = Integer.parseInt(cityDetails[2]);

				int[] xy = new int[] { x, y };

				cityInfos.put(cityDetails[0], xy);

			} else {
				Gdx.app.log("WARNING",
						"Falscher gameconfig.json Eintrag für cityCoordinate! Ignoriere Eintrag");
			}
		}
	}

	@Override
	protected void doRender(float delta) {
		updateCamera(delta);
		stage.act(delta);
		stage.draw();
	}

	private void updateCamera(float delta) {
		
		if (germanyImg.getActions().size == 0){
			
			// Zielkoordinaten für die Kamera berechnen
			targetX = player.getX() + player.getWidth() * player.getScaleX() / 2.0f;
			targetY = player.getY() + player.getHeight() * player.getScaleY();
			
			// Kamera sanft zum Ziel schwenken		
			camera.position.set(camera.position.x + (targetX - camera.position.x) * delta * 2, 
					camera.position.y + (targetY - camera.position.y) * delta * 2, 0);
			
			if (camera.zoom > .3f)
				camera.zoom -= delta * 0.6f;
		}
		
		
	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doShow() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doHide() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doPause() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResume() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doDispose() {
		stage.dispose();
	}

}