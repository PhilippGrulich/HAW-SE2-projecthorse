package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.level.Level;

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
	private final float MIN_SPAWN_DELAY_SEC = 0.2f; // Minimum time between two spawns
	private float spawndelay = -1; //Instant spawn first object

	private int branch_spawn_chance = 30; // 10%:: Prozent Chance, das statt
											// einem Apfel ein Ast spawnt
	private int current_falling_entities = 0; // Um unnötige abfragen wahrend
												// doRender zu vermeiden wird
												// hier laufend die anzahl
												// mitgeschrieben.

	// private float total_playtime = 0; //Gesamt spieldauer.
	//private int score = 0; // Punktzahl

	private Group backgroundGraphics; // Stuff not interacting with player.
	private Group fallingEntities; // Falling entities
	private Image horse; // The horse, running around

	//Add everything to this stage
	private Stage stage = new Stage(this.getViewport(), this.getSpriteBatch());

	public AppleRun() {
		initBackground();
		initHorse();
		fallingEntities = new Group();
		stage.addActor(backgroundGraphics);
		stage.addActor(horse);
		stage.addActor(fallingEntities);
	}

	private void initBackground() {
		backgroundGraphics = new Group();
		//TODO background hinzufügen
	}

	private void initHorse() {
		horse = new Image(); // TODO irgendwas sinnvolles laden
	}

	// Spawning new Apples / Falling stuff
	private void spawnEntities(float delta) {
		spawndelay -= delta;
		if (spawndelay >= 0) {
			return; // Not ready to spawn another object
		} else {

			spawndelay = (((float) Math.random()) * (MAX_SPAWN_DELAY_SEC-MIN_SPAWN_DELAY_SEC)) + MIN_SPAWN_DELAY_SEC;   
			
			
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
