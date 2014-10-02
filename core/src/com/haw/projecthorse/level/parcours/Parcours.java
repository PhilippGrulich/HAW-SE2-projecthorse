package com.haw.projecthorse.level.parcours;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * Ein Spiel bei dem man über Hindernisse Springen muss.
 * @author Francis
 *
 */
public class Parcours extends Level {

	/**
	 * Das Pferd
	 */
	private Player player;
	/**
	 * Stage kapselt Actor
	 */
	private Stage stage;
	/**
	 * Spiellogik
	 */
	private GameLogic logic;
	/**
	 * player Sprunghöhe
	 */
	private float jumpHeight = 50;
	/**
	 * Zeit bis player bei jumpHeight
	 */
	private float jumpUpTime = 0.5f;
	/**
	 * Zeit bis Player auf ground
	 */
	private float jumpDownTime = 0.5f;
	/**
	 * Hindernissgeschwindigkeit
	 */
	private float gameSpeed = 3;
	/**
	 * Group für gleich zu behandelnde Backgroundobjekte
	 */
	private Group group = new Group();
	/**
	 * Temporär nicht in Gebrauch
	 */
	private TextureAtlas gameobjectAtlas;
	/**
	 * Atlas für Backgroundgraphiken
	 */
	private TextureAtlas backgroundAtlas;
	/**
	 * Boden
	 */
	private Image ground;
	/**
	 * Himmel, Bäume
	 */
	private Image landscape;
	
	/**
	 * Konstruktor initialisiert
	 * Atlases
	 * Gamelogik
	 * Player
	 * Background
	 * Objekte
	 */
	public Parcours(){
		gameobjectAtlas = AssetManager.load("Parcours_Gameobjects", false, false, true);
		backgroundAtlas = AssetManager.load("Parcours_BackgroundGraphics", false, false, true);
		
		initGameLogic();
		installPlayer();
		
		player.setPosition(20, 0);
		player.scaleBy(0.2F);
		
		stage = new Stage(this.getViewport());
		stage.addCaptureListener(new InputListener(){
			 public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
				 jump();
				 return true;
			 }
		 });
		
		initBackgroundGraphics();
		initObstacles();
		
		stage.addActor(player);
		InputManager.addInputProcessor(stage);
	//	Gdx.input.setInputProcessor(this.stage);
		 
	}
	
	/*private void initBackgroundGraphics(){
		AtlasRegion atlasRegion= backgroundAtlas.findRegion("ground");
		Texture page = atlasRegion.getTexture();
		TextureRegion ground = new TextureRegion(page, atlasRegion.getRegionX(),
								atlasRegion.getRegionY(), atlasRegion.getRegionWidth(),
								atlasRegion.getRegionHeight());
		
		atlasRegion= backgroundAtlas.findRegion("landscape");
		page = atlasRegion.getTexture();
		TextureRegion landscape = new TextureRegion(page, atlasRegion.getRegionX(),
								atlasRegion.getRegionY(), atlasRegion.getRegionWidth(),
								atlasRegion.getRegionHeight());
		
		ground.scroll(20, 0);
		
		GameObject groundAsGameObject = new GameObject(new TextureRegionDrawable(ground));
		GameObject landscapeAsGameObject = new GameObject(new TextureRegionDrawable(landscape));
		
		groundAsGameObject.setPosition(0, 0);
		landscapeAsGameObject.setPosition(0, 105);
		
		groundAsGameObject.toFront();
		landscapeAsGameObject.toFront();
		
		group.addActor(groundAsGameObject);
		group.addActor(landscapeAsGameObject);
		
		stage.addActor(group);
		
		
	}*/
	
	/**
	 * Extraktion von ground aus page bestehend aus landscape&ground,
	 * analog landscape, dann beides = Image für group,
	 * group in stage.
	 * Background moveable o. scrollable o. animated (todo)
	 */
	private void initBackgroundGraphics(){
		AtlasRegion atlasRegion= backgroundAtlas.findRegion("ground");
		Texture page = atlasRegion.getTexture();
		TextureRegion ground = new TextureRegion(page, atlasRegion.getRegionX(),
								atlasRegion.getRegionY(), atlasRegion.getRegionWidth(),
								atlasRegion.getRegionHeight());
		
		atlasRegion= backgroundAtlas.findRegion("landscape");
		page = atlasRegion.getTexture();
		TextureRegion landscape = new TextureRegion(page, atlasRegion.getRegionX(),
								atlasRegion.getRegionY(), atlasRegion.getRegionWidth(),
								atlasRegion.getRegionHeight());
		
		this.ground = new Image(new TextureRegion(ground));
		this.landscape = new Image(landscape);
		
		this.ground.setWidth(this.width); 
		this.ground.setHeight(this.height - (this.height - 105));
		this.ground.setPosition(0, 0);
		this.landscape.setWidth(this.width);
		this.landscape.setHeight(this.height - 105);
		this.landscape.setPosition(0, 105);
		group.addActor(this.ground);
		group.addActor(this.landscape);
		stage.addActor(group);

		
	
	}
	
	/**
	 * init. Gamelogik, init Hindernisse,
	 * setzen von min/max Dist. zw. Hindernissen
	 */
	private void initGameLogic(){
		logic = new GameLogic();
		logic.initGameObjects(AssetManager.load("Parcours_Gameobjects", false, false, true));
		logic.setMinMaxDistancesX(20, 50);
	}
	
	/**
	 * Setzen der Hindernisse aufs Spielfeld,
	 * Randbedingung:
	 * Keine Überschneidungen,
	 * nicht mitten aufs Spielfeld setzen (todo)
	 */
	private void initObstacles(){
		int i = 0;
		for(GameObject o : logic.getObjects()){
			//o.setBounds(logic.getRandomX(o), 0, 20, 20);
			if(i == 0){
				i++;
				//System.out.println("1 : " + (this.width));
				o.setBounds(this.width, 0, o.getWidth1(), o.getHeight1());
				//o.setOrigin(this.width - o.getWidth1(), o.getHeight());
				o.setScale(0.5F);
				//System.out.println("" + o.getHeight() + " " + o.getWidth() + " " + o.getScaleX());
			}else{
				float rand = logic.getRandomX(o);
				//System.out.println("2: " + rand);
				o.setBounds(rand, 0, o.getWidth1(), o.getHeight1());
				//o.setOrigin(rand, o.getHeight());
				o.setScale(0.5F);
			}
			//stage.addActor(o);
			group.addActor(o);
		}
		
	}
	
	/**
	 * Sprung von player,
	 * smoothing (todo),
	 * nicht aus Spielfeld raus (todo),
	 * Kameramove wenn Vorwärtssprung (todo)
	 */
	private void jump(){
	//	System.out.println("jump");
		float runUp = player.getWidth() * 1.3f;
		player.addAction(Actions.moveBy(player.getX() + runUp, 0, 0.5f));
		player.setX(player.getX() + runUp);
		player.addAction(Actions.moveTo(player.getX(), player.getY() + player.getHeight() * jumpHeight, jumpUpTime));
		player.addAction(Actions.moveTo(player.getX(), 0, jumpDownTime));

	}
	
	
	/**
	 * player erzeugen, x,y, breite, höhe setzen,
	 * skalieren
	 */
	private void installPlayer(){
		 player = new PlayerImpl();
		 player.setBounds(0,0,player.getWidth(), player.getHeight());
		 player.setScale(1.5F);
	}
	
	/**
	 * Actors zeichnen,
	 * Hindernisse außerhalb stage neu setzen,
	 * unregelmäßige Abstände (todo)
	 */
	@Override
	protected void doRender(float delta) {
		//Gdx.gl.glClearColor(0.4f, 0.4f, 0.65f, 1); // Hintergrund malen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.setAnimation(Direction.RIGHT, 0.2f);
		
		for(GameObject o : logic.getObjects()){
			if(o.getX() + o.getWidth() < 0){
				o.setX((float) (this.width + Math.random() * (logic.getMaxDistanceX() - logic.getMinDistanceX()) + logic.getMinDistanceX()));
			}else{
				o.addAction(Actions.moveTo(o.getX() - gameSpeed, 0));
			}
			
		}
		group.act(delta);
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	protected void doDispose() {
		backgroundAtlas.dispose();
		gameobjectAtlas.dispose();
		
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
