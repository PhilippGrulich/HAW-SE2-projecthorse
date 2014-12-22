package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.lootmanager.Chest;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;

//TODO seperate this class into Gamestate & Gamelogic

public class Gamestate {

	private ShapeRenderer shapeRenderer = new ShapeRenderer(); // Used for
																// debugging /
																// drawing
																// collision
																// rectangles
	final float MOVEMENT_PER_SECOND;// Movement in px per second
	int roll; // Temp var
	int breite; // Temp var
	float scaleX; // Temp var
	float distance;// Temp var
	float moveToDuration;// Temp var

	Direction currentAccelerometerDirection;

	private boolean endThisGame = false; // if true -> Nächster render aufruf delta > 0 -> navigate back
	private final float BASIS_GAME_TIME_SECONDS = 70; // Basis spiellaufzeit
	private float totalGameTime; // Basis laufzeit + Modifikator durch Pferderasse
	private float timeLeftSeconds;// Time to play left
	private final float TIME_LOST_PER_BRANCH_HIT_SECONDS;
	private final int MAX_FALLING_ENTITIES = 5;
	private final float MAX_SPAWN_DELAY_SEC = 1.5f; // Maximale zeit bis zum
													// nächsten entity spawn
	private final float MIN_SPAWN_DELAY_SEC = 0.2f; // Minimum time between two
													// spawns
	private float spawndelay = -1; // Delay until next spawn allowed - (Initiate
									// with -1 for first spawn = instant)

	private int branch_spawn_chance = 20; // 20%:: Prozent Chance, das statt
											// einem Apfel ein Ast spawnt
	private int current_falling_entities = 0; // Um unnötige abfragen wahrend
												// doRender zu vermeiden wird
												// hier laufend die anzahl
												// mitgeschrieben.

	private EntityGroup fallingEntities; // Falling entities
	private Stage stage;// = new Stage(this.getViewport(),
						// this.getSpriteBatch());
	private Group backgroundGraphics; // Stuff not interacting with player.
	private PlayerAppleRun horse;

	private TimeBar timeBar; // Init in background

	private int width;
	private int height;

	private int score;
	private int appleCatchSeries; // Äpfel nacheinander gefangen.

	private boolean lastAnimationDirectionLeft = false;
	private boolean lastAnimationActionIdle = false;

	private final Chest chest; // referenz auf Chest aus der Game class

	private Label scoreAnzeige;

	public Gamestate(Viewport viewport, Batch batch, int width, int heigth, Chest chest) {
		this.chest = chest;
		stage = new Stage(viewport, batch);
		this.width = width;
		this.height = heigth;

		initBackground();
		initHorse();
		initScore();

		initTimer(); // Calc: Intelligenz BonusZeit

		float speedModifikator = 1 + (horse.getAthletic() * 10 / 100); // Bis zu 10% schneller durch Stärke
		MOVEMENT_PER_SECOND = (this.width / 2.2f) * speedModifikator;

		float loseModifikator = 1 - (horse.getObedience() * 50 / 100); // Bis zu 50% weniger verlorene Zeit
		TIME_LOST_PER_BRANCH_HIT_SECONDS = 5 * loseModifikator;

		fallingEntities = new EntityGroup();

		stage.addActor(backgroundGraphics);
		stage.addActor(horse);
		stage.addActor(fallingEntities);
		stage.addActor(scoreAnzeige);

		InputManager.addInputProcessor(stage);

		score = 0;
		appleCatchSeries = 0;
		currentAccelerometerDirection = Direction.IDLELEFT;
	}

	private void initTimer() {
		float bonusTimeFactorByInt = 1 + horse.getIntelligence() * 20 / 100; // Je nach intelligenz bis zu 20% längere Spielzeit
		totalGameTime = BASIS_GAME_TIME_SECONDS * bonusTimeFactorByInt;
		timeLeftSeconds = totalGameTime;
	}

	protected void initScore() {
		BitmapFont font = AssetManager.getTextFont(FontSize.FORTY);
		LabelStyle labelStyle = new LabelStyle(font, Color.BLUE);
		scoreAnzeige = new Label("Score: " + score, labelStyle);
		scoreAnzeige.setPosition(50, 50);
	}

	protected void updateScore() {
		scoreAnzeige.setText("Score: " + score);
	}

	private void initHorse() {
		horse = new PlayerAppleRun(this);
		horse.setPosition(0, 110);
		// horse.scaleBy(0.2F);
		horse.setAnimationSpeed(0.4f);
		horse.addAction(new AnimationAction(Direction.RIGHT));
		// stage.addActor(horse); //Done inside constructor
		stage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				moveHorseTo(x);
				return true;
			}

			// Fires if the mouse is dragged - following a touchDown event
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				moveHorseTo(x);
			}
		});
	}

	private void moveHorseTo(float x) {
		x = x - ((horse.getWidth() * horse.getScaleX()) / 2); // Zur mitte des
																// Pferdes
																// bewegen
		boolean previousAnimationLeft = lastAnimationDirectionLeft;

		if (x < 0) {
			x = 0;
		} // Nicht links rauslaufen
		breite = this.width; // GameManagerFactory.getInstance().getSettings().getScreenWidth();

		if (x > breite - (horse.getWidth() * horse.getScaleX())) {
			x = breite - (horse.getWidth() * horse.getScaleX());
		} // Nicht rechts rauslaufen
			// Bewegungsrichtung ermitteln
		AnimationAction animationAction = null;
		distance = horse.getX() - x; // Positiv = move links
		if (distance > 0) { // Move links
			animationAction = new AnimationAction(Direction.LEFT);
			lastAnimationDirectionLeft = true;
			lastAnimationActionIdle = false;
		} else {
			animationAction = new AnimationAction(Direction.RIGHT);
			lastAnimationDirectionLeft = false;
			lastAnimationActionIdle = false;
		}

		moveToDuration = convertDistanceToTime(distance);

		if (lastAnimationDirectionLeft != previousAnimationLeft || horse.getActions().size == 1) {
			//Animation hat sich geändert, bzw. es war nur IDLE aktiv
			horse.clearActions(); // Alte bewegungen etc. entfernen
			horse.addAction(animationAction); // Nur bei Richtungsänderung Animation neu starten
			Action move = new MoveToActionAcceleration(x, horse.getY(), moveToDuration);
			horse.addAction(move);
		} else { //Richtung beibehalten. Animation bleibt, bewegung bekommt neues Ziel
			Action actionToDelete = null;
			for (Action action : horse.getActions()) {
				if (action instanceof AnimationAction) {
					// do nothing //Animation action drin behalten
				} else {
					actionToDelete = action;
				}
			}
			Action move = new MoveToActionAcceleration(x, horse.getY(), moveToDuration, null); // Instant movement (keine Interpolation)
			horse.removeAction(actionToDelete);
			horse.addAction(move);
		}
		horse.setAnimationSpeed(0.5f);
	}

	private float convertDistanceToTime(float distance) {
		if (distance < 0) {
			distance = distance * -1;
		} // Absolute
		return 1.0f / MOVEMENT_PER_SECOND * distance; // Calculate running
														// speeed
	}

	// Spawning new Apples / Falling stuff
	private void spawnEntities(float delta) {
		spawndelay -= delta;
		if (spawndelay >= 0) {
			return; // Not ready to spawn another object
		} else {

			spawndelay = (((float) Math.random()) * (MAX_SPAWN_DELAY_SEC - MIN_SPAWN_DELAY_SEC)) + MIN_SPAWN_DELAY_SEC;

			// if liste < x member, spawn new
			if (current_falling_entities < MAX_FALLING_ENTITIES) {
				roll = MathUtils.random(100);
				if (roll <= branch_spawn_chance) {
					// Spawn a branch
					// Increment current_falling_entities
					current_falling_entities++;
					fallingEntities.addActor(GameObjectFactory.getBranch());

				} else {
					// Spawn an apple
					// increment current_falling_entities
					current_falling_entities++;
					fallingEntities.addActor(GameObjectFactory.getApple());
				}
			}
		}
	}

	public void removeFallingEntity(Entity entity) {
		// TODO check if entity is really inside this group. Otherwise maxing
		// out available slots for new entities
		fallingEntities.removeActor(entity);
		GameObjectFactory.giveBackEntity(entity);
		current_falling_entities--;
	}

	private void collisionDetection() {
		collisionHandler.collide(horse, fallingEntities);
	}

	Stage getStage() {
		return stage;
	}

	public void update(float delta) {
		stage.act(delta);
		stage.draw();
		horse.act(delta);
		spawnEntities(delta);
		// drawCollisionRectangles(delta);
		collisionDetection(); // Todo evtl. inside Entity-Objecten
		removeDroppedDownEntities();
		updateTimer(delta);

		updateAccelerometer();

		if (!lastAnimationActionIdle && horse.getActions().size == 1) { // Player ist nicht schon auf IDLE und bewegt sich gerade nicht
			setPlayerActionToIdle();
		}
	}

	private void updateAccelerometer() {
		// nur wenn das Accelerometer activiert ist wird es auch genutzt

		if (GameManagerFactory.getInstance().getSettings().getAccelerometerState()) {
			boolean changedDirection = false;
			float adjustedX = (Gdx.input.getAccelerometerX());
			// float horseWith = ((horse.getWidth() * horse.getScaleX()) / 2);
			// float horseX = horse.getX();
			if (adjustedX > -1.5f && adjustedX < 1.5f) {
				adjustedX = 0f;
			}
			float target_x = 0;
			if (adjustedX > 0f) { // MOVE left
				if (currentAccelerometerDirection != Direction.LEFT && horse.getX() > 0) {
					currentAccelerometerDirection = Direction.LEFT;
					changedDirection = true;
					target_x = 0;
					lastAnimationDirectionLeft = true;
					lastAnimationActionIdle = false;
				}
				// adjustedX = (float) (Math.pow(adjustedX, 2));

			} else if (adjustedX == 0f) { // IDLE
				if (currentAccelerometerDirection != Direction.IDLELEFT) {
					// changedDirection = true;
					// currentAccelerometerDirection = Direction.IDLELEFT;
					// target_x = horse.getX();
					// lastAnimationActionIdle = true;
					setPlayerActionToIdle();
				}

				// adjustedX = (float) (Math.pow(adjustedX, 2));
			}

			else {
				if (currentAccelerometerDirection != Direction.RIGHT && horse.getX() < (this.width - horse.getWidth() * horse.getScaleX())) {
					changedDirection = true;
					currentAccelerometerDirection = Direction.RIGHT;
					target_x = this.width;
					lastAnimationDirectionLeft = false;
					lastAnimationActionIdle = false;
				}
				// adjustedX = (float) (Math.pow(adjustedX, 2)) * -1;
			}

			if (changedDirection) { // Bewegung nur wenn
				// moveHorseTo((float) (horseX + horseWith - adjustedX));
				moveHorseTo(target_x);
			}

		}
	}

	private void removeDroppedDownEntities() {
		// TODO remove with actor as ground object.
		// Splatter on ground contact?
		for (Actor actor : fallingEntities.getChildren()) {
			if (actor.getY() < (1 - (actor.getHeight() * actor.getScaleY()))) {
				this.removeFallingEntity((Entity) actor);
			}
		}
	}

	private void updateTimer(float delta) {
		if (delta == 0) {
			return;
		}
		timeLeftSeconds -= delta;
		if (endThisGame) {
			GameManagerFactory.getInstance().navigateBack();
		}
		if (timeLeftSeconds < 0) {
			endThisGame();
		}

		// updateTimeBar Scale
		scaleX = timeLeftSeconds / totalGameTime; // Time left as
													// ratio. (Between
													// 0.0 and 1.0)
		timeBar.setScaleX(scaleX);
	}

	private void endThisGame() {
		// TODO BUGFIX, derzeit stürzt er noch ab wenn einmal ein Friese im savegame drin ist.
		/*
		 * double roll = Math.random(); if(roll <= 0.2f &&
		 * !SaveGameManager.getLoadedGame().getSpecifiedLoot(RaceLoot.class).contains(HorseRace.FRIESE)){
		 * this.chest.addLootAndShowAchievment(new RaceLoot(new Race(HorseRace.FRIESE))); }
		 * 
		 * this.chest.showAllLoot(); this.chest.saveAllLoot();
		 */

		endThisGame = true;
	}

	private void drawCollisionRectangles(float delta) {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.CYAN);
		shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
		for (Actor actor : fallingEntities.getChildren()) {
			Entity entity = (Entity) actor;
			shapeRenderer.rect(entity.getHitbox().x, entity.getHitbox().y, entity.getHitbox().width, entity.getHitbox().height);
		}
		shapeRenderer.rect(horse.getHitbox().x, horse.getHitbox().y, horse.getHitbox().width, horse.getHitbox().height);
		shapeRenderer.end();
	}

	private void initBackground() {
		backgroundGraphics = new Group();

		TextureRegion tree = AssetManager.getTextureRegion("appleRun", "tree");
		TextureRegion ground = AssetManager.getTextureRegion("appleRun", "ground");

		Image treeImage = new Image(tree);
		treeImage.setY(144);
		timeBar = new TimeBar();
		timeBar.setX(32);
		timeBar.setY(32);

		backgroundGraphics.addActor(treeImage);
		backgroundGraphics.addActor(new Image(ground));

		backgroundGraphics.addActor(timeBar);
	}

	public void dispose() {
		GameObjectFactory.dispose();
		// atlas.dispose(); //Removed due to AssetManager-Management
	}

	void playerHitByBranch() {
		appleCatchSeries = appleCatchSeries / 2;
		Gdx.input.vibrate(200);
		timeLeftSeconds -= TIME_LOST_PER_BRANCH_HIT_SECONDS;
	}

	void playerHitByApple() {
		appleCatchSeries++;
		score += appleCatchSeries;
		updateScore();
	}

	public void setPlayerActionToIdle() {
		horse.clearActions();
		if (lastAnimationDirectionLeft) {
			horse.addAction(new AnimationAction(Direction.IDLELEFT));
		} else {
			horse.addAction(new AnimationAction(Direction.IDLERIGHT));
		}
		lastAnimationActionIdle = true;

		horse.setAnimationSpeed(0.1f);

		currentAccelerometerDirection = Direction.IDLELEFT;
	}
}
