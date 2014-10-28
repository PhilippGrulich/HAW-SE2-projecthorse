package com.haw.projecthorse.level.menu.worldmap;

import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.swipehandler.ControlMode;
import com.haw.projecthorse.swipehandler.StageGestureDetector;
import com.haw.projecthorse.swipehandler.SwipeListener;

public class WorldMap extends Level {

	// Dieses Enum stellt den aktuellen Zustand in der Worldmap dar,
	// um verschiedene Situationen unterscheiden zu können
	private enum State {
		INIT, ZOOMING, NORMAL, RUNNING
	}

	private static boolean firstStart = true;
	
	private final OrthographicCamera camera; // Zum Zentrieren des Bildes auf
												// das Pferd
	private final PlayerImpl player;
	private final Stage stage, uiStage;
	private final Preferences prefs; // Zum Speichern der zuletzt besuchten
										// Stadt
	private final Image germanyImg, worldImg, pointImg;

	private String[] cities;
	private int selectedCityIndex;
	private HashMap<String, int[]> cityInfos;
	private boolean cityChanged;
	private Image[] cityPoints; // TODO Städte mit Punkten markieren

	private float targetX, targetY;

	private State state;

	private ImageButton leftButton, rightButton, flagButton;
	private ImageButtonStyle flagStyle;
	
	private final float BUTTONALPHA = 0.3f; // Transparenz der Buttons falls
											// deaktiviert

	private final float MAXZOOM = 0.3f; // Maximaler Kamera Zoom
	
	public WorldMap() throws LevelNotFoundException {
		super();

		stage = new Stage(getViewport());
		uiStage = new Stage(new FitViewport(width, height));
		InputManager.addInputProcessor(uiStage);
		InputManager.addInputProcessor(stage);
		

		camera = getCam();
		player = new PlayerImpl(); // TODO color auslesen und im Konstruktor
									// setzen
		player.clearActions(); // TODO Workaround bis Player umgebaut

		getJasonCities();

		prefs = Gdx.app.getPreferences("WorldMapPrefs");

		if (!prefs.contains("lastCity"))
			prefs.putString("lastCity", cities[0]); // setze die erste Stadt als
													// Standartort falls nicht
													// gesetzt
		prefs.flush();

		selectedCityIndex = Arrays.asList(cities).indexOf(
				prefs.getString("lastCity"));

		Gdx.app.log("INFO", "Zuletzt gewählte Stadt hat Index " + String.valueOf(selectedCityIndex));
		
		cityChanged = true;

		germanyImg = new Image(AssetManager.getTextureRegion("worldmap",
				"germanymap_scaled"));
		germanyImg.toBack();
		worldImg = new Image(AssetManager.getTextureRegion("worldmap",
				"erde-und-sterne"));
		worldImg.toBack();
		pointImg = new Image(AssetManager.getTextureRegion("worldmap",
				"shadedLight28"));

		pointImg.setColor(Color.RED);
		pointImg.setScale(0.5f * (width / 720));

		state = State.INIT;
		
		flagStyle = new ImageButtonStyle();
		
		createButtons();
		initAnimation();
	}

	// Erstellt eine Startanimation die einmalig abgearbeitet wird
	private void initAnimation() {
		germanyImg.setColor(1, 1, 1, 0);
		worldImg.setColor(1, 1, 1, 0);
		pointImg.setColor(1, 1, 1, 0);
		player.setColor(1, 1, 1, 0);
		player.scaleBy(-0.5f);

		int[] cityCoordinates = cityInfos.get(prefs.getString("lastCity"));

		player.setPosition(
				cityCoordinates[0] - player.getWidth() / 2 * player.getScaleX(),
				cityCoordinates[1]);
		
		worldImg.setOrigin(0.643f * width, 0.65f * height); // Setzt den
															// Zielpunkt auf
															// Europa

		pointImg.setPosition(worldImg.getOriginX() - pointImg.getWidth() / 2,
				worldImg.getOriginY() - pointImg.getHeight() / 2);

		
		
		if (firstStart) {
			SequenceAction worldMapSequence = Actions.sequence(Actions.fadeIn(1f),
					Actions.delay(1f), Actions.scaleBy(8f, 8f, 1f), Actions.fadeOut(0.25f));
			SequenceAction pointBlinkSequence = Actions.sequence(
					Actions.delay(1.0f), Actions.fadeIn(0.25f),
					Actions.fadeOut(0.25f), Actions.fadeIn(0.25f),
					Actions.fadeOut(0.25f));
			SequenceAction germanyMapSequence = Actions.sequence(
					Actions.delay(3.0f), Actions.fadeIn(0.25f));
			SequenceAction playerSequence = Actions.sequence(Actions.delay(3.0f),
					Actions.fadeIn(0.25f));

			worldImg.addAction(worldMapSequence);
			pointImg.addAction(pointBlinkSequence);
			germanyImg.addAction(germanyMapSequence);
			player.addAction(playerSequence);
			
			firstStart = false;
		} else {
			camera.zoom = MAXZOOM;
			germanyImg.addAction(Actions.fadeIn(0.25f));
			player.addAction(Actions.fadeIn(0.25f));
		}


		stage.addActor(worldImg);
		stage.addActor(pointImg);
		stage.addActor(germanyImg);
		stage.addActor(player);

	}

	private void createButtons() {
		flagButton = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("flaggen",
						cities[selectedCityIndex])));
		flagButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actor.getColor().a == 1f) {
					prefs.putString("lastCity", cities[selectedCityIndex]);
					prefs.flush();
					GameManagerFactory.getInstance().navigateToLevel(
							cities[selectedCityIndex]);
				}
			}
		});
		flagButton.setHeight(180 * 1.4f);
		flagButton.setWidth(280 * 1.4f);
		flagButton.setPosition(width / 2 - flagButton.getWidth() / 2,
				height * 0.75f - flagButton.getHeight() / 2);
		flagButton.toFront();
		
		InputManager.addInputProcessor(new StageGestureDetector(stage, true));
		germanyImg.addListener(new SwipeListener() {

					@Override
					public void swiped(SwipeEvent event, Actor actor) {
						
						switch (event.getDirection()) {
						case LEFT:
							rightButton.fire(new ChangeEvent());
							return;
							
						case RIGHT:
							leftButton.fire(new ChangeEvent());
							return;
						default:
							return;
						}
					}
					
				});
		
		uiStage.addActor(flagButton);

		updateFlag();

		leftButton = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("worldmap", "shadedLight24")));
		leftButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actor.getColor().a >= 0.9f && selectedCityIndex > 0) {
					selectedCityIndex--;
					cityChanged = true;
					movePlayer(cities[selectedCityIndex]);
				}
			}
		});
		leftButton.setName("leftButton");
		leftButton.setHeight(100);
		leftButton.setWidth(100);

		leftButton.setPosition(flagButton.getX() / 2 - leftButton.getWidth()
				/ 2, flagButton.getY() + flagButton.getHeight() / 2
				- leftButton.getHeight() / 2);
		leftButton.toFront();

		rightButton = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("worldmap", "shadedLight25")));
		rightButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actor.getColor().a >= 0.9f
						&& selectedCityIndex < cities.length - 1) {
					selectedCityIndex++;
					cityChanged = true;
					movePlayer(cities[selectedCityIndex]);
				}
			}
		});
		rightButton.setName("rightButton");
		rightButton.setHeight(100);
		rightButton.setWidth(100);

		rightButton.setPosition(
				width - flagButton.getX() / 2 - rightButton.getWidth() / 2,
				flagButton.getY() + flagButton.getHeight() / 2
						- rightButton.getHeight() / 2);
		rightButton.toFront();

		uiStage.addActor(leftButton);
		uiStage.addActor(rightButton);
				
	}

	private void movePlayer(String city) {
		int[] cityCoordinates = cityInfos.get(city);

		MoveByAction movement = new MoveByAction();
		movement.setAmount(
				cityCoordinates[0] - player.getX() - player.getWidth() / 2
						* player.getScaleX(),
				cityCoordinates[1] - player.getY());
		movement.setDuration(1.5f);

		player.clearActions();
		player.addAction(movement);

	}

	private void updateFlag() {
		if (cityChanged) {
			flagStyle.imageUp = new TextureRegionDrawable(
					AssetManager.getTextureRegion("flaggen",
					cities[selectedCityIndex]));
			flagButton.setStyle(flagStyle);
			flagButton.setName(cities[selectedCityIndex]);
			cityChanged = false;
		}
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
		updateState();
		updateUI(delta);
		updateCamera(delta);
		stage.act(delta);
		uiStage.act(delta);
		stage.draw();
		uiStage.draw();
	}

	private void updateState() {
		// INIT ist durch wenn Deutschlandkarte fertig animiert
		if (germanyImg.getActions().size == 0) {
			if (camera.zoom > MAXZOOM)
				state = State.ZOOMING;
			else if (player.getActions().size > 0)
				state = State.RUNNING;
			else
				state = State.NORMAL;
		}
	}

	private void updateUI(float delta) {

		// state = State.NORMAL;

		updateFlag();

		switch (state) {
		case NORMAL:
			// Alle Elemente anzeigen wenn möglich
			if (selectedCityIndex > 0)
				leftButton.setColor(1, 1, 1, 1);
			else
				leftButton.setColor(1, 1, 1, BUTTONALPHA);

			if (selectedCityIndex < cities.length - 1)
				rightButton.setColor(1, 1, 1, 1);
			else
				rightButton.setColor(1, 1, 1, BUTTONALPHA);

			flagButton.setColor(1, 1, 1, 1);
			break;

		case RUNNING:
			// Buttons transparent, Flagge ausblenden
			leftButton.setColor(1, 1, 1, BUTTONALPHA);
			rightButton.setColor(1, 1, 1, BUTTONALPHA);
			flagButton.setColor(1, 1, 1, 0);
			break;

		default:
			// Alle Elemente unsichtbar machen
			leftButton.setColor(1, 1, 1, 0);
			rightButton.setColor(1, 1, 1, 0);
			flagButton.setColor(1, 1, 1, 0);
			break;
		}

	}

	private void updateCamera(float delta) {
		switch (state) {
		case INIT:
			return; // tue nichts während der Init Animation
		case ZOOMING:
			// sanft heranzoomen
			camera.zoom -= delta * 0.6f;
			// kein Break, damit die anderen Anweisungen ebenfalls durchgeführt
			// werden
		default:
			// Zielkoordinaten für die Kamera berechnen
			targetX = player.getX() + player.getWidth() * player.getScaleX()
					/ 2.0f;
			targetY = player.getY() + player.getHeight() * player.getScaleY();

			// Kamera sanft zum Ziel schwenken
			camera.position.set(camera.position.x
					+ (targetX - camera.position.x) * delta * 2,
					camera.position.y + (targetY - camera.position.y) * delta
							* 2, 0);
			break;
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
		uiStage.dispose();
	}

}