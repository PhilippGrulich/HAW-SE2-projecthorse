package com.haw.projecthorse.level.menu.worldmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.level.util.uielements.ButtonOwnImage;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;
import com.haw.projecthorse.level.util.uielements.ButtonSmall.ButtonType;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.savegame.SaveGameManager;

public class WorldMap extends Menu {

	// Dieses Enum stellt den aktuellen Zustand in der Worldmap dar,
	// um verschiedene Situationen unterscheiden zu können
	private enum State {
		INIT, ZOOMING, NORMAL, RUNNING
	}

	private static boolean firstStart = true;
	private static boolean showedTutorial = false;

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
	private Image[] lines;
	private BezierPath[] beziers;
	private ButtonOwnImage[] cityPoints;
	private float cityScaleMax, cityScaleMin;
	private boolean cityScaleDirection;

	private float targetX, targetY;

	private State state;

	private Image uiBackground;
	private ImageButton leftButton, rightButton, flagButton;
	private ImageButtonStyle flagStyle;
	private BitmapFont textFont;
	private Label flagLabel;

	private final float BUTTONALPHA = 0.3f; // Transparenz der Buttons falls
											// deaktiviert

	private final float MAXZOOM = 0.45f; // Maximaler Kamera Zoom

	private final float PLAYERSPEED = 1.5f; // Zeit die der Player von einem
											// Punkt zum nächsten benötigt

	private Music music;

	private String currentCity;

	public WorldMap() throws LevelNotFoundException {
		super();

		stage = new Stage(getViewport());
		uiStage = new Stage(new FitViewport(width, height));
		InputManager.addInputProcessor(uiStage);
		InputManager.addInputProcessor(stage);

		camera = getCam();

		player = new PlayerImpl();

		getJasonCities();

		prefs = Gdx.app.getPreferences("WorldMapPrefs");

		if (!prefs.contains("lastCity"))
			prefs.putString("lastCity", cities[0]); // setze die erste Stadt als
													// Standartort falls nicht
													// gesetzt
		prefs.flush();

		selectedCityIndex = Arrays.asList(cities).indexOf(prefs.getString("lastCity"));

		if (selectedCityIndex == -1) {
			selectedCityIndex = 0;
			prefs.putString("lastCity", cities[0]);
			prefs.flush();
		}

		Gdx.app.log("INFO", "Zuletzt gewählte Stadt hat Index " + String.valueOf(selectedCityIndex));

		cityChanged = true;

		germanyImg = new Image(AssetManager.getTextureRegion("worldmap", "germanymap_scaled"));
		germanyImg.toBack();
		worldImg = new Image(AssetManager.getTextureRegion("worldmap", "erde-und-sterne"));
		worldImg.toBack();
		pointImg = new Image(AssetManager.getTextureRegion("worldmap", "shadedLight28"));

		state = State.INIT;

		textFont = AssetManager.getTextFont(FontSize.SIXTY);

		flagStyle = new ImageButtonStyle();

		createButtons();
		createCityPoints();
		initAnimation();

		AssetManager.loadMusic("mainMenu");
		AssetManager.loadSounds("worldmap");

		music = audioManager.getMusic("mainMenu", "belotti.mp3");

		if (!music.isPlaying()) {
			music.setLooping(true);
			music.play();
		}
	}

	/**
	 * Diese Methode Zeigt ein Tutorial an fallst der User sich das Erstemal
	 * anmeldet.
	 */
	private void initTutorial() {
		// Tutorial Popup
		int userID = SaveGameManager.getLoadedGame().getID();

		if (!prefs.contains(String.valueOf(userID))) {
			overlay.showPopup(new TutorialPopup());
			prefs.putString(String.valueOf(userID), "");
			prefs.flush();
		}
		showedTutorial = true;

	}

	// Erstellt eine Startanimation die einmalig abgearbeitet wird
	private void initAnimation() {
		germanyImg.setColor(1, 1, 1, 0);
		worldImg.setColor(1, 1, 1, 0);
		pointImg.setColor(1, 1, 1, 0);
		pointImg.setScale(0.5f * (width / 720));
		player.setColor(1, 1, 1, 0);
		player.scaleBy(-1.0f);
		currentCity = prefs.getString("lastCity");
		int[] cityCoordinates = cityInfos.get(currentCity);

		player.setPosition(cityCoordinates[0] - player.getWidth() / 2 * player.getScaleX(), cityCoordinates[1]);

		worldImg.setOrigin(0.643f * width, 0.65f * height); // Setzt den
															// Zielpunkt auf
															// Europa

		pointImg.setPosition(worldImg.getOriginX() - pointImg.getWidth() / 2,
				worldImg.getOriginY() - pointImg.getHeight() / 2);

		if (firstStart) {
			SequenceAction worldMapSequence = Actions.sequence(Actions.fadeIn(1f), Actions.delay(1f),
					Actions.scaleBy(8f, 8f, 1f), Actions.fadeOut(0.25f));
			SequenceAction pointBlinkSequence = Actions.sequence(Actions.delay(1.0f), Actions.fadeIn(0.25f),
					Actions.fadeOut(0.25f), Actions.fadeIn(0.25f), Actions.fadeOut(0.25f));
			SequenceAction germanyMapSequence = Actions.sequence(Actions.delay(3.0f), Actions.fadeIn(0.25f));
			SequenceAction playerSequence = Actions.sequence(Actions.delay(3.0f), Actions.fadeIn(0.25f));

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

		germanyImg.toBack();
		worldImg.toBack();

	}

	private void createCityPoints() {
		cityScaleMax = 0.4f * (width / 720) * 1.2f;
		cityScaleMin = 0.4f * (width / 720) * 0.8f;
		cityScaleDirection = true;
		cityPoints = new ButtonOwnImage[cities.length];
		lines = new Image[cities.length - 1];
		beziers = new BezierPath[cities.length - 1];
		// Linien anlegen
		for (int i = 1; i < cities.length; i++) {
			Image lineImg = new Image(AssetManager.getTextureRegion("worldmap", "flatLight09"));
			lineImg.setColor(1, 1, 1, 0);

			BezierPath bezierPath = new BezierPath(lineImg.getDrawable(), new Vector2(cityInfos.get(cities[i - 1])[0],
					cityInfos.get(cities[i - 1])[1]), new Vector2(cityInfos.get(cities[i])[0],
					cityInfos.get(cities[i])[1]));

			stage.addActor(bezierPath);
			beziers[i - 1] = bezierPath;
			// benötige Größe und Drehwinkel finden und setzen, ausgehend von
			// einem rechtwinkligen Dreieck
			double triangleWidth = cityInfos.get(cities[i])[0] - cityInfos.get(cities[i - 1])[0];
			double triangleHeigth = cityInfos.get(cities[i])[1] - cityInfos.get(cities[i - 1])[1];
			double hypotenuse = Math.sqrt(Math.pow(triangleWidth, 2.0) + Math.pow(triangleHeigth, 2.0));
			double angle = Math.asin(triangleWidth / hypotenuse) * 180 / Math.PI - 90;

			lineImg.setSize((float) hypotenuse, height * 0.005f);
			lineImg.rotateBy((float) angle);
			lineImg.setPosition(cityInfos.get(cities[i - 1])[0], cityInfos.get(cities[i - 1])[1]);

			lineImg.toFront();
			lines[i - 1] = lineImg;
			stage.addActor(lines[i - 1]);
		}

		// Punkte anlegen
		for (int i = 0; i < cities.length; i++) {
			cityPoints[i] = new ButtonOwnImage(new TextureRegionDrawable(AssetManager.getTextureRegion("worldmap",
					"shadedLight28")));
			cityPoints[i].setName(cities[i]);
			cityPoints[i].setScale(0.5f * (width / 720));
			cityPoints[i].setColor(1, 1, 1, 0);
			cityPoints[i].setPosition(
					cityInfos.get(cities[i])[0] - cityPoints[i].getWidth() * cityPoints[i].getScaleX() / 2,
					cityInfos.get(cities[i])[1] - cityPoints[i].getHeight() * cityPoints[i].getScaleX() / 2);

			cityPoints[i].addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (actor.getColor().a == 1f) {
						prefs.putString("lastCity", actor.getName());
						prefs.flush();
						GameManagerFactory.getInstance().navigateToLevel(actor.getName());
					}
				}
			});

			stage.addActor(cityPoints[i]);
		}

	}

	private void updateCityPoints(float delta) {
		if (state == State.INIT)
			return;

		if (cityPoints[0].getScaleX() > cityScaleMax)
			cityScaleDirection = false;
		else if (cityPoints[0].getScaleX() < cityScaleMin)
			cityScaleDirection = true;

		for (ButtonOwnImage city : cityPoints) {
			city.setColor(1, 1, 1, 1);
			if (cityScaleDirection) {
				city.scaleBy(delta * 0.3f);
			} else {
				city.scaleBy(delta * -0.3f);
			}

			city.setPosition(cityInfos.get(city.getName())[0] - city.getWidth() * city.getScaleX() / 2,
					cityInfos.get(city.getName())[1] - city.getHeight() * city.getScaleX() / 2);
		}

		for (Image line : lines) {
			line.setColor(Color.LIGHT_GRAY);
		}

	}

	private void createButtons() {
		uiBackground = new Image(AssetManager.getTextureRegion("ui", "panel_beige"));
		uiBackground.setHeight(height * 0.3f);
		uiBackground.setWidth(width * 0.9f);
		uiBackground
				.setPosition(width / 2 - uiBackground.getWidth() / 2, height * 0.77f - uiBackground.getHeight() / 2);

		flagButton = new ButtonOwnImage(new TextureRegionDrawable(AssetManager.getTextureRegion("flaggen",
				cities[selectedCityIndex])));
		flagButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actor.getColor().a == 1f) {
					prefs.putString("lastCity", cities[selectedCityIndex]);
					prefs.flush();
					GameManagerFactory.getInstance().navigateToLevel(cities[selectedCityIndex]);
				}
			}
		});
		flagButton.setHeight(uiBackground.getHeight() * 0.68f);
		flagButton.setWidth(uiBackground.getWidth() * 0.5f);
		flagButton.setPosition(width / 2 - flagButton.getWidth() / 2, uiBackground.getY() + uiBackground.getHeight()
				* 0.075f);

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

		flagLabel = new Label(cities[selectedCityIndex], new LabelStyle(textFont, Color.LIGHT_GRAY));

		updateFlag();

		leftButton = new ButtonSmall(ButtonType.LEFT);
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

		leftButton.setPosition(flagButton.getX() - leftButton.getWidth() * 1.2f,
				uiBackground.getY() + uiBackground.getHeight() / 2 - leftButton.getHeight() / 2);

		rightButton = new ButtonSmall(ButtonType.RIGHT);
		rightButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actor.getColor().a >= 0.9f && selectedCityIndex < cities.length - 1) {
					selectedCityIndex++;
					cityChanged = true;
					movePlayer(cities[selectedCityIndex]);
				}
			}
		});
		rightButton.setName("rightButton");
		rightButton.setHeight(100);
		rightButton.setWidth(100);

		rightButton.setPosition(flagButton.getX() + flagButton.getWidth() + leftButton.getWidth() * 0.2f,
				uiBackground.getY() + uiBackground.getHeight() / 2 - rightButton.getHeight() / 2);

		uiStage.addActor(uiBackground);
		uiStage.addActor(flagButton);
		uiStage.addActor(leftButton);
		uiStage.addActor(rightButton);
		uiBackground.toBack();
		flagButton.toFront();
		leftButton.toFront();
		rightButton.toFront();

	}

	private Action createMoveAction(Vector2 source, Vector2 target, float duration) {
		MoveByAction movement = new MoveByAction();
		movement.setAmount(target.x - source.x, target.y - source.y);
		movement.setDuration(duration);
		// MoveToAction movement = Actions.moveTo(pathToFollow.get(i +
		// 1).x, pathToFollow.get(i + 1).y,
		// PLAYERSPEED / pathToFollow.size());
		// Direction animationDirection = WorldMapUtils.getDirection(source,
		// target);
		// AnimationAction animationRun = new
		// AnimationAction(animationDirection, duration);

		ParallelAction actionGroup = new ParallelAction();
		actionGroup.addAction(movement);

		// actionGroup.addAction(animationRun);
		return actionGroup;
	}

	private void movePlayer(String city) {
		int[] cityCoordinates = cityInfos.get(city);

		int[] oldCityCoordinates = cityInfos.get(currentCity);
		Vector2 source = new Vector2(oldCityCoordinates[0], oldCityCoordinates[1]);
		Vector2 target = new Vector2(cityCoordinates[0], cityCoordinates[1]);

		List<Vector2> pathToFollow = null;
		for (BezierPath bezier : beziers) {
			if (source.equals(bezier.getSource()) && target.equals(bezier.getTarget())) {
				pathToFollow = bezier.getPoins();
			}
		}

		if (pathToFollow == null) {
			for (BezierPath bezier : beziers) {
				if (source.equals(bezier.getTarget()) && target.equals(bezier.getSource())) {
					pathToFollow = new ArrayList<Vector2>(bezier.getPoins());
					Collections.reverse(pathToFollow);
				}
			}
		}
		player.clearActions();
		player.setAnimationSpeed(1 - PLAYERSPEED / 2.5f);
		if (pathToFollow != null) {
			SequenceAction sequence = new SequenceAction();
			for (int i = 0; i < pathToFollow.size() - 1; i++) {
				Action action = createMoveAction(pathToFollow.get(i), pathToFollow.get(i + 1), PLAYERSPEED
						/ pathToFollow.size());
				sequence.addAction(action);
			}
			Action action = Actions.moveTo(target.x - player.getWidth() / 2 * player.getScaleX(), target.y, PLAYERSPEED
					/ pathToFollow.size());
			sequence.addAction(action);
			player.addAction(sequence);

		}

		// Vector2 source = new Vector2(player.getX() + player.getWidth() / 2 *
		// player.getScaleX(), player.getY());
		// Vector2 target = new Vector2(cityCoordinates[0], cityCoordinates[1]);
		//
		Direction animationDirection = WorldMapUtils.getDirection(source, target);
		AnimationAction animationRun = new AnimationAction(animationDirection, PLAYERSPEED);
		player.addAction(animationRun);
		//
		// // Weggelassen, da alleine schon die Existens einer Idle
		// AnimationAction
		// // während der Abarbeitung einer anderen AnimationAction
		// // stand 14.12.14 zum Fehler führt
		// // AnimationAction animationIdle = new
		// //
		// AnimationAction(WorldMapUtils.getIdleDirection(animationDirection));
		// // SequenceAction animationSequence = new
		// SequenceAction(animationRun,
		// // animationIdle);
		//
		// MoveByAction movement = new MoveByAction();
		// movement.setAmount(target.x - source.x, target.y - source.y);
		// movement.setDuration(PLAYERSPEED);
		//
		//
		// player.addAction(movement);
		// // player.addAction(animationSequence);
		// player.addAction(animationRun);
		currentCity = city;
	}

	private void updateFlag() {
		if (cityChanged) {
			flagStyle.imageUp = new TextureRegionDrawable(AssetManager.getTextureRegion("flaggen",
					cities[selectedCityIndex]));
			flagButton.setStyle(flagStyle);
			flagButton.setName(cities[selectedCityIndex]);

			flagLabel.remove();
			try {
				flagLabel = new Label(GameManagerFactory.getInstance().getCityObject(cities[selectedCityIndex])
						.getCityName(), new LabelStyle(textFont, Color.MAGENTA));
			} catch (LevelNotFoundException e) {
				flagLabel = new Label("NotFound", new LabelStyle(textFont, Color.MAGENTA));
				Gdx.app.log("WARNING", "City name for city " + cities[selectedCityIndex] + " not found!");
			}

			flagLabel.setPosition(width / 2 - flagLabel.getWidth() / 2, flagButton.getY() + flagButton.getHeight());
			uiStage.addActor(flagLabel);

			cityChanged = false;
		}
	}

	private void getJasonCities() throws LevelNotFoundException {

		MenuObject menuObject = GameManagerFactory.getInstance().getMenuObject("worldmap");
		String cityCoordinates = menuObject.getParameter().get("cityCoordinates").replaceAll("\\s", "");

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
				Gdx.app.log("WARNING", "Falscher gameconfig.json Eintrag für cityCoordinate! Ignoriere Eintrag");
			}
		}
	}

	@Override
	protected void doRender(float delta) {
		updateState();
		updateCityPoints(delta);
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
			if (camera.zoom > MAXZOOM) {
				if (!showedTutorial)
					initTutorial();
				state = State.ZOOMING;
			} else if (WorldMapUtils.isPlayerMoving(player))
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
			flagLabel.setColor(flagLabel.getColor().r, flagLabel.getColor().g, flagLabel.getColor().b, 1);
			uiBackground.setColor(1, 1, 1, 1);
			break;

		case RUNNING:
			// Buttons transparent, Flagge ausblenden
			leftButton.setColor(1, 1, 1, BUTTONALPHA);
			rightButton.setColor(1, 1, 1, BUTTONALPHA);
			uiBackground.setColor(1, 1, 1, BUTTONALPHA);
			flagButton.setColor(1, 1, 1, 0);
			flagLabel.setColor(flagLabel.getColor().r, flagLabel.getColor().g, flagLabel.getColor().b, 0);
			break;

		default:
			// Alle Elemente unsichtbar machen
			leftButton.setColor(1, 1, 1, 0);
			rightButton.setColor(1, 1, 1, 0);
			flagButton.setColor(1, 1, 1, 0);
			uiBackground.setColor(1, 1, 1, 0);
			flagLabel.setColor(flagLabel.getColor().r, flagLabel.getColor().g, flagLabel.getColor().b, 0);
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
			targetX = player.getX() + player.getWidth() * player.getScaleX() / 2.0f;
			targetY = player.getY() + player.getHeight() * player.getScaleY();

			// Kamera sanft zum Ziel schwenken
			camera.position.set(camera.position.x + (targetX - camera.position.x) * delta * 2, camera.position.y
					+ (targetY - camera.position.y) * delta * 2, 0);
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
