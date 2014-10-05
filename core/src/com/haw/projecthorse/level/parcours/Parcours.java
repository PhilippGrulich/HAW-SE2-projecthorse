package com.haw.projecthorse.level.parcours;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * Ein Spiel bei dem man ¸ber Hindernisse Springen muss.
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
	 * player Sprunghˆhe
	 */
	private float jumpHeight = 50;
	/**
	 * Zeit bis player bei jumpHeight
	 */
	private float jumpUpTime = 0.4f;
	/**
	 * Zeit bis Player auf ground
	 */
	private float jumpDownTime = 0.4f;
	/**
	 * Hindernissgeschwindigkeit
	 */
	private float gameSpeed = 3;
	/**
	 * Group f¸r gleich zu behandelnde Backgroundobjekte
	 */
	private Group fixedBackgroundGroup = new Group();
	private Group moveableBackgroundGroup = new Group();
	private Group moveableObstacleGroup = new Group();
	/**
	 * Tempor‰r nicht in Gebrauch
	 */
	private TextureAtlas gameobjectAtlas;
	/**
	 * Atlas f¸r Backgroundgraphiken
	 */
	private TextureAtlas backgroundAtlas;
	/**
	 * Boden
	 */
	private Image ground, groundCopy;
	/**
	 * Himmel, B‰ume
	 */
	private Image landscape;
	
	/**
	 * Startpunkt player
	 */
	private float playersPointOfView = 20;
	
	/**
	 * Players "Hochspringen"
	 */
	private float runUp;
	
	/**
	 * Players Fallen
	 */
	private float runDown;
	
	/**
	 * Delta f¸r Jump-Methode
	 */
	private float delta;
	
	/**
	 * Action + x-Position f¸r Vorwaertsbewegung beim Sprung
	 * (Setzen in installPlayer())
	 */
	private MoveByAction vorwaerts;
	private float vorwaertsPos;
	
	/**
	 * Action + x-Position f¸r Vorwaertsbewegung beim Fallen
	 * (Setzen in installPlayer())
	 */
	private MoveToAction fallen;
	private float fallenPos;
	
	/**
	 * Action bei Sprung (Setzen in installPlayer())
	 */
	private MoveToAction springen;
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
		
		
		installPlayer();
		initGameLogic();
		
		stage = new Stage(this.getViewport());
		stage.addCaptureListener(new InputListener(){
			 public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
				 if(!(player.getY() > 0 )){
				 jump();
				 }
				 return true;
			 }
		 });
		
		initBackgroundGraphics();
		initObstacles();
		
		stage.addActor(player);
		InputManager.addInputProcessor(stage);
	//	Gdx.input.setInputProcessor(this.stage);
		 
	}
	

	
	/**
	 * Extraktion von ground aus page bestehend aus landscape&ground,
	 * analog landscape, dann beides = Image f¸r group,
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
		
		this.ground = new Image(ground);
		this.groundCopy = new Image(ground);
		this.landscape = new Image(landscape);
		
	
		this.ground.setWidth(this.width); 
		this.ground.setHeight(this.height - (this.height - 105));
		this.ground.setPosition(0, 0);
		this.groundCopy.setWidth(this.width); 
		this.groundCopy.setHeight(this.height - (this.height - 105));
		this.groundCopy.setPosition(this.width - 1, 0);
		
		this.landscape.setWidth(this.width);
		this.landscape.setHeight(this.height - 105);
		this.landscape.setPosition(0, 104);
		
		this.ground.setName("FirstGround");
		this.groundCopy.setName("SecondGround");
		
		moveableBackgroundGroup.addActor(this.ground);
		moveableBackgroundGroup.addActor(groundCopy);
		fixedBackgroundGroup.addActor(this.landscape);
		stage.addActor(this.fixedBackgroundGroup);
		stage.addActor(this.moveableBackgroundGroup);
		
	
	}
	
	/**
	 * init. Gamelogik, init Hindernisse,
	 * setzen von min/max Dist. zw. Hindernissen
	 */
	private void initGameLogic(){
		logic = new GameLogic();
		logic.initGameObjects(AssetManager.load("Parcours_Gameobjects", false, false, true), this.width, this.height);
		//logic.setMinMaxDistancesX(20,50);
		//System.out.println("player: " + player.getWidth());
		logic.setMinMaxDistancesX(player.getWidth()+(player.getWidth()/2), player.getWidth()+player.getWidth());
	}
	
	/**
	 * Setzen der Hindernisse aufs Spielfeld,
	 * Randbedingung:
	 * Keine ‹berschneidungen,
	 * nicht mitten aufs Spielfeld setzen (todo)
	 */
	private void initObstacles(){
		int i = 0;
		float tmpWidth = 0;
		for(GameObject o : logic.getObjects()){
			//o.setBounds(logic.getRandomX(o), 0, 20, 20);
			if(i == 0){
				i++;
				
				//System.out.println("1 : " + (this.width));
				o.setBounds(this.width, 0, o.getWidth1(), o.getHeight1());
				//o.setOrigin(this.width - o.getWidth1(), o.getHeight());
				o.setScale(0.5F);
				tmpWidth = o.getWidth();
				//System.out.println("" + o.getHeight() + " " + o.getWidth() + " " + o.getScaleX());
			}else{
				float rand = logic.getRandomX(o);
				//System.out.println("2: " + rand);
				o.setBounds(this.width + tmpWidth + ((float)Math.floor(Math.random() * (logic.getMaxDistanceX()- logic.getMinDistanceX()) + logic.getMinDistanceX())) , 0, o.getWidth1(), o.getHeight1());
				//o.setOrigin(rand, o.getHeight());
				tmpWidth = o.getWidth();
				o.setScale(0.5F);
			}
			//stage.addActor(o);
			o.setName("Huerde_" + i);
			
			moveableObstacleGroup.addActor(o);
			stage.addActor(moveableObstacleGroup);
		}
		
	}
	
	
	/**
	 * Sprung von player,
	 * smoothing (todo),
	 * nicht aus Spielfeld raus (todo),
	 * Kameramove wenn Vorw‰rtssprung (todo)
	 */
	private void jump(){
		initPlayerActions();
		ParallelAction a = new ParallelAction(vorwaerts, springen);
		player.addAction(vorwaerts);
		player.setX(vorwaertsPos);
		
		//Springen
		player.addAction(springen);

		
		//Fallen
		player.addAction(fallen);
	//	player.setX(fallenPos);
		
		
		
		
	}
	
	
	/**
	 * player erzeugen, x,y, breite, hˆhe setzen,
	 * skalieren
	 */
	private void installPlayer(){
		 player = new PlayerImpl();
		 player.setScale(1.5F);
		 runUp = player.getWidth() * 1.3f;
		 runDown =  runUp * 1.0f;
		 player.setPosition(playersPointOfView, 0);
		 player.scaleBy(0.2F);
		 
		initPlayerActions();
	}
	
	private void initPlayerActions(){
		 vorwaertsPos = player.getX() + runUp;
	     fallenPos = vorwaertsPos + runUp / 1.5f;
		
	     //Vorwaerts Action
	 	vorwaerts = Actions.moveBy(vorwaertsPos, 0, 0.5f + delta, new Interpolation() {

	 		@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
				}
			});
	 	
	 	//Springen Action
	 	springen = Actions.moveTo(vorwaertsPos, player.getY() + player.getHeight() * jumpHeight, 
				jumpUpTime + delta, new Interpolation() {
			
			@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
			}
		});
	 	
	 	//Fallen Action
	 	//Fallen
	 	fallen = Actions.moveTo(fallenPos, 0, jumpDownTime + delta, new Interpolation() {
	 				
	 				@Override
	 				public float apply(float a) {
	 					// TODO Auto-generated method stub
	 					return a;
	 				}
	 			});
	}
	
	/**
	 * Actors zeichnen,
	 * Hindernisse auﬂerhalb stage neu setzen,
	 * unregelm‰ﬂige Abst‰nde (todo)
	 */
	@Override
	protected void doRender(float delta) {
		this.delta = delta;
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.setAnimation(Direction.RIGHT, 0.2f);
		
		//Bewege Gameobjects
		for(GameObject o : logic.getObjects()){
			o.moveBy(0-gameSpeed, 0);	
			
			//Gameobject auﬂerhalb rand? -> Neu setzen
			if(o.getX() + o.getWidth() < 0){
				float rand = logic.getRandomX(o);
				o.setPosition(rand, 0);
			}
			
		}
		
		if(player.getX() + player.getWidth() >= this.width){
			//System.out.println("Player out of screen view -- implement");
		}
		
	
		if(player.getX() > playersPointOfView){
			player.moveBy(0 - gameSpeed, 0);
		}
		
		//Boden auﬂerhalb Rand? Neu setzen
		if(moveableBackgroundGroup.getX() + ground.getImageWidth() < 0){
			moveableBackgroundGroup.setPosition(0, 0);
		}
		//Boden bewegen
		moveableBackgroundGroup.moveBy(0-gameSpeed, 0);
	
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
