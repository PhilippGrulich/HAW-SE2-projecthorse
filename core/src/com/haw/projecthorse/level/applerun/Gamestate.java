package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Direction;

//TODO seperate this class into Gamestate & Gamelogic

public class Gamestate {

	final float MOVEMENT_PER_SECOND;// Movement in px per second

	private final int MAX_FALLING_ENTITIES = 70;

	private final float MAX_SPAWN_DELAY_SEC = 1.5f; // Maximale zeit bis zum
													// nächsten entity spawn
	private final float MIN_SPAWN_DELAY_SEC = 0.2f; // Minimum time between two
													// spawns
	private float spawndelay = -1; // Delay until next spawn allowed - (Initiate with -1 for first spawn = instant)

	private int branch_spawn_chance = 30; // 10%:: Prozent Chance, das statt
											// einem Apfel ein Ast spawnt
	private int current_falling_entities = 0; // Um unnötige abfragen wahrend
												// doRender zu vermeiden wird
												// hier laufend die anzahl
												// mitgeschrieben.

	private EntityGroup fallingEntities; // Falling entities
	private Stage stage;// = new Stage(this.getViewport(), this.getSpriteBatch());
	private Group backgroundGraphics; // Stuff not interacting with player.
	private PlayerAppleRun horse;

	private int width;
	private int height;

	public Gamestate(Viewport viewport, Batch batch, int width, int heigth) {
		stage = new Stage(viewport, batch);
		this.width = width;
		this.height = heigth;
		MOVEMENT_PER_SECOND = this.width / 1.25f;
		
		initBackground();
		initHorse();

		fallingEntities = new EntityGroup();

		stage.addActor(backgroundGraphics);
		stage.addActor(horse);
		stage.addActor(fallingEntities);

		InputManager.addInputProcessor(stage);
		
	}

	private void initHorse() {
		horse = new PlayerAppleRun(this);
		horse.setPosition(0, 110);
		horse.scaleBy(0.5F);
		horse.setAnimation(Direction.RIGHT, 0.4f);
		//stage.addActor(horse); //Done inside constructor

		stage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				moveHorseTo(x);
				return true;
			}
		});

	}

	private void moveHorseTo(float x) {
		x = x - ((horse.getWidth() * horse.getScaleX()) / 2); // Zur mitte des Pferdes bewegen
		horse.clearActions(); // Alte bewegungen etc. entfernen
		
		if (x < 0) {
			x = 0;
		} // Nicht links rauslaufen
		int breite = this.width; // GameManagerFactory.getInstance().getSettings().getScreenWidth();

		if (x > breite - horse.getWidth()) {
			x = breite - horse.getWidth();
		} // Nicht rechts rauslaufen
		// Bewegungsrichtung ermitteln
		ChangeDirectionAction directionAction = null;
		float distance = horse.getX() - x; // Positiv = move rechts
		if (distance > 0) { // Move right
			directionAction = new ChangeDirectionAction(Direction.RIGHT);
		} else {
			directionAction = new ChangeDirectionAction(Direction.LEFT);
		}
		
		float moveToDuration = convertDistanceToTime(distance);
		Action move = Actions.moveTo(x, horse.getY(), moveToDuration);
		horse.addAction(directionAction);
		horse.addAction(move);
	}

	private float convertDistanceToTime(float distance) {
		if (distance < 0) {
			distance = distance * -1;
		} // Absolute
		return 1.0f / MOVEMENT_PER_SECOND * distance; // Calculate running speeed
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
				int roll = MathUtils.random(100);
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

	
	
	public void removeFallingEntity(Entity entity){
		fallingEntities.removeActor(entity);
	}
	
	private void collisionDetection() {
		collisionHandler.collide(horse, fallingEntities);
	}

	Stage getStage() {
		return stage;
	}

	public void update(float delta) {
		// fallingEntities.act(delta);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		horse.act(delta);
		spawnEntities(delta);
		collisionDetection(); // Todo evtl. inside Entity-Objecten

	}

	private void initBackground() {
		backgroundGraphics = new Group();
		// TODO background hinzufügen
	}

}
