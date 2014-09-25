package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

//Todo
/**
 * @author Lars
 * 
 *         Brainstorm: Collision Detection? - Über Action? - Damit wird einfach
 *         in "Act(delta)" des jeweiligen Objects auch die Action
 *         CollisionDetection ausgeführt und meldet sich beim getroffenen +
 *         Melden sich die Entitys beim Pferd bei Collision oder merkt das Pferd
 *         wenn es getroffen wurde?! + Event system?
 * 
 * 
 */

public class AppleRun extends Level {

	private final int MAX_FALLING_ENTITIES = 70;

	private final float MAX_SPAWN_DELAY_SEC = 1.5f; // Maximale zeit bis zum
													// nächsten entity spawn
	private final float MIN_SPAWN_DELAY_SEC = 0.2f; // Minimum time between two
													// spawns
	private float spawndelay = -1; // Instant spawn first object

	private int branch_spawn_chance = 30; // 10%:: Prozent Chance, das statt
											// einem Apfel ein Ast spawnt
	private int current_falling_entities = 0; // Um unnötige abfragen wahrend
												// doRender zu vermeiden wird
												// hier laufend die anzahl
												// mitgeschrieben.

	// private float total_playtime = 0; //Gesamt spieldauer.
	// private int score = 0; // Punktzahl

	private Group backgroundGraphics; // Stuff not interacting with player.
	private Group fallingEntities; // Falling entities
	// private Image horse; // The horse, running around

	private Player horse;

	// Add everything to this stage
	private Stage stage = new Stage(this.getViewport(), this.getSpriteBatch());

	public AppleRun() {
		initBackground();
		initHorse();
		fallingEntities = new Group();
		stage.addActor(backgroundGraphics);
		stage.addActor(horse);
		stage.addActor(fallingEntities);
		
		Gdx.input.setInputProcessor(this.stage);
	}

	private void initBackground() {
		backgroundGraphics = new Group();
		// TODO background hinzufügen
	}

	private void initHorse() {
		// horse = new Image(); // TODO irgendwas sinnvolles laden

		horse = new PlayerImpl();
		horse.setPosition(0, 110);
		horse.scaleBy(0.5F);

		horse.setAnimation(Direction.RIGHT, 0.4f);
		stage.addActor(horse);

		//horse.setBounds(0, 0, 720, 1280);
		
		stage.addListener(new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				System.out.println(" " + x);
				moveHorseTo(x);
				return true;
			}
		});

//		
//		 float moveToDuration = 2;
//		 horse.addAction(Actions.forever(Actions.sequence(Actions.moveTo(
//		 Gdx.graphics.getWidth() + 50, horse.getY(), moveToDuration), new
//		 ChangeDirectionAction(Direction.LEFT), Actions.moveTo(-100 -
//		 horse.getWidth(), horse.getY(), moveToDuration), new
//		 ChangeDirectionAction(Direction.RIGHT))));
//		 

	}

	private void moveHorseTo(float x){
		x = x - ((horse.getWidth() * horse.getScaleX())/2); //Zur mitte des Pferdes bewegen
		horse.clearActions(); //Alte bewegungen etc. entfernen
		
		if(x < 0){x = 0;} //Nicht links rauslaufen
		int breite = GameManagerFactory.getInstance().getSettings().getScreenWidth();
		if(x > breite-horse.getWidth()){x = breite-horse.getWidth();} // Nicht rechts rauslaufen
		
		//Bewegungsrichtung ermitteln
		ChangeDirectionAction directionAction = null;
		float distance = horse.getX() - x; //Positiv  = move rechts
		if(distance > 0){ //Move right
			directionAction = new ChangeDirectionAction(Direction.RIGHT);
		}else{
			directionAction = new ChangeDirectionAction(Direction.LEFT);
		}
		
		
		float moveToDuration = convertDistanceToTime(distance);
		Action move = Actions.moveTo(x, horse.getY(), moveToDuration);
		horse.addAction(directionAction);
		horse.addAction(move);
		// actor.addListener(
		
	}
	
	//Calculate running speeed
	private float convertDistanceToTime(float distance){
		if(distance < 0){distance = distance*-1;} //Absolute
		
		
		return 1.0f;
	}

	// Spawning new Apples / Falling stuff
	private void spawnEntities(float delta) {
		spawndelay -= delta;
		if (spawndelay >= 0) {
			return; // Not ready to spawn another object
		} else {

			spawndelay = (((float) Math.random()) * (MAX_SPAWN_DELAY_SEC - MIN_SPAWN_DELAY_SEC))
					+ MIN_SPAWN_DELAY_SEC;

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

	private void collisionDetection() {

		// Todo evtl. inside Entity-Objecten

		// nicht über .hit (Nur für Mouse/Touch events)
	}

	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.65f, 1); // Hintergrund malen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		spawnEntities(delta);
		fallingEntities.act(delta); // Update falling apples
		backgroundGraphics.act(delta);
		horse.act(delta);
		collisionDetection(); // Todo evtl. inside Entity-Objecten

	}

	//
	// private Image initTexture(){
	// Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
	// pixel.setColor(Color.RED);
	// pixel.fill();
	// Texture appleTexture = new Texture(pixel, Format.RGBA8888, true);
	// pixel.dispose(); // No longer needed
	//
	// //Image apple = new Image();
	// //apple.setDrawable(new TextureRegionDrawable(new
	// TextureRegion(appleTexture)));
	// Image apple = new Image(appleTexture);
	// // Image apple = new Image(Entity.loadTexture());
	// //apple.setDrawable(new TextureRegionDrawable(new
	// TextureRegion(appleTexture)));
	// return apple;
	// }

	@Override
	protected void doDispose() {
		// TODO Auto-generated method stub

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

}
