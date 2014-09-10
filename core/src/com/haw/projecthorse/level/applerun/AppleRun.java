package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.haw.projecthorse.level.Level;

//Todo
/**
 * @author Lars 
 * 
 * Brainstorm:
 * Collision Detection?
 * - Über Action? - Damit wird einfach in "Act(delta)" des jeweiligen Objects auch die Action CollisionDetection ausgeführt und meldet sich beim getroffenen
 *   + Melden sich die Entitys beim Pferd bei Collision oder merkt das Pferd wenn es getroffen wurde?!
 *   + Event system?
 * 
 * 
 */





public class AppleRun extends Level {

	private final int MAX_FALLING_ENTITIES = 7;
	
	private int branch_spawn_chance = 10; //10%:: Prozent Chance, das statt einem Apfel ein Ast spawnt
	private int current_falling_entities = 0; //Um unnötige abfragen wahrend doRender zu vermeiden wird hier laufend die anzahl mitgeschrieben.
	
	//private float total_playtime = 0; //Gesamt spieldauer.
	private int score = 0; //Punktzahl
	
	private Group backgroundGraphics; //Stuff not interacting with player.
	private Group fallingEntities; //Falling entities
	private Image horse; //The horse, running around
	
	private Stage stage = new Stage(this.getViewport(), this.getSpriteBatch()); //All groups / gfx go here
	
	
	public AppleRun() {
		// TODO Auto-generated constructor stub
		initBackground();
		initHorse();
		stage.addActor(backgroundGraphics);
		stage.addActor(horse);
		stage.addActor(fallingEntities);
	}

	private void initBackground(){
		backgroundGraphics = new Group();
	}
	
	private void initHorse(){
		horse = new Image(); //TODO irgendwas sinnvolles laden
	}
	
	//Spawning new Apples / Falling stuff
	private void spawnEntities(){
		//if liste < x member, spawn new
		if(current_falling_entities < MAX_FALLING_ENTITIES){
			int roll = MathUtils.random(100);
			if(roll <= branch_spawn_chance){
				//Spawn a branch
				//Increment current_falling_entities
			}
			else{
				//Spawn an apple
				//increment current_falling_entities
			}
				
		}
	}
	
	
	private void collisionDetection(){
		
		
		//Todo evtl. inside Entity-Objecten

		//nicht über .hit (Nur für Mouse/Touch events)
	}
	
	@Override
	protected void doRender(float delta) {
		//total_playtime += delta;
		
		spawnEntities();
		fallingEntities.act(delta);
		backgroundGraphics.act(delta);
		horse.act(delta);
		collisionDetection(); //Todo evtl. inside Entity-Objecten
		
		//Update falling apples
		//Inputhandling
		
		// TODO Auto-generated method stub

	}

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
