package com.haw.projecthorse.level.huetchenspiel;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * Richtiges Huetchen finden, unter dem das Pferd versteckt ist
 * @author Fabian
 *
 */

public class HuetchenSpiel extends Level{
	
	private Stage stage;
	private Group group;
	private static int HAT_NUMBER = 3;
	private Hat[] hats;
	private TextureAtlas assetAtlas;
	private Player pl;
	private Random rnd;
	protected int rightNum;
	
	/*
	private Table winTable;
	private SpriteBatch batcher;
	private int wins;
	private BitmapFont winBitMap;
	*/
	
	private VerticalGroup buttonTable;
	private Texture upTex;
	private TextureRegion upReg;
	private BitmapFont bitmapFont;
	private TextButtonStyle buttonStyle;
	private TextButton newGame;
	private TextButton back;
	
	/**
	 * Konstruktor
	 */
	public HuetchenSpiel(){
		this.assetAtlas = AssetManager.load("huetchenspiel", false, false, true);
		this.rnd = new Random();
		
		initStage();
		initPlayer();
		initHats();
		initEventHatListener();	
		generateHatNum();
		initTable();
		initButtons();
		
		this.stage.addActor(this.group);
		this.stage.addActor(this.pl);
		//this.stage.addActor(this.winTable);
		this.buttonTable.setVisible(false);
		this.stage.addActor(this.buttonTable);
		
	}

	/**
	 * Prueft, ob einer der Huete ausgewaehlt wurde
	 * @param delta
	 */
	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1); 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		
		//ueber die EventListener von den Hats iterieren und
		//abfragen ob das richtige Huetchen ausgewaehlt wurde
		for(int i = 0; i < HAT_NUMBER; i++){
			Array<EventListener> eventList = hats[i].getListeners();
			if(eventList.get(0) instanceof HatListener){
				if(((HatListener)eventList.get(0)).getFound()){
					this.pl.setPosition(this.hats[i].getX() + 20, this.hats[i].getY());
					this.pl.setVisible(true);
					for(int j = 0; j < HAT_NUMBER; j++){
						this.hats[j].setVisible(false);
					}
					((HatListener)eventList.get(0)).setFound(false);
					//this.wins++;
					this.buttonTable.setVisible(true);
				}/*
				else{
					System.out.println("inside else");
					generateHatNum();
				}*/
			}
		}
	}

	@Override
	protected void doDispose() {
		this.stage.dispose();	
		this.assetAtlas.dispose();
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
	
	/**
	 * Stage initialisieren
	 */
	private void initStage(){
		this.stage = new Stage(this.getViewport(), this.getSpriteBatch());
		Gdx.input.setInputProcessor(this.stage);
	}
	
	/**
	 * Horse initialisieren
	 */
	private void initPlayer(){
		this.pl = new PlayerImpl();
		//this.pl.setPosition(50, 200);
		this.pl.setVisible(false);
	}
	
	/**
	 * Initialisierung der Hute mit Vergabe einer ID, sowie
	 * Positionierung auf den Screen
	 */
	private void initHats(){
		Texture texHat = this.assetAtlas.findRegion("hut").getTexture();
		this.hats = new Hat[HAT_NUMBER];
		this.group = new Group();
		int xPosition = 20;
		for(int i = 0; i < HAT_NUMBER; i++){
			this.hats[i] = new Hat(texHat, i);
			this.hats[i].setPosition(xPosition, Gdx.graphics.getHeight() 
					- this.hats[i].getHeight() / 2.0f);
			this.group.addActor(this.hats[i]);
			xPosition += (Gdx.graphics.getHeight() / 2) - /*(20 * HAT_NUMBER)*/ 60;
		}
	}
	
	/**
	 * EventListener fuer die Huete anlegen
	 */
	private void initEventHatListener(){
		for(int i = 0; i < HAT_NUMBER; i++){
			this.hats[i].addListener(new HatListener(this, this.hats[i].getID()));
		}
	}
	
	/**
	 * Table initialisieren
	 */
	private void initTable(){
		this.buttonTable = new VerticalGroup();
		this.buttonTable.setPosition(0, -Gdx.graphics.getHeight() / 2);
		this.buttonTable.setFillParent(true);
		/*
		this.winTable = new Table();
		this.winTable.setPosition(400, 200);
		this.winTable.setFillParent(true);
		this.wins = 0;
		this.winBitMap = new BitmapFont();
		/*
		this.batcher = new SpriteBatch();
		this.batcher.begin(); 
		this.winBitMap.setColor(Color.RED);
		this.winBitMap.draw(this.batcher, "your score dude is: " + this.wins, 200, 100); 
		this.batcher.end();
		
		
		Label.LabelStyle textStyle = new Label.LabelStyle();
		//winBitMap.setColor(Color.RED);
		textStyle.font = winBitMap;
		textStyle.fontColor = Color.RED;
		Label text = new Label("bla", textStyle);
		//text.setBounds(0,.2f,30,20);
		//text.setFontScale(1f,1f);
		text.setPosition(200, 300);
		text.setWidth(30);
		text.setHeight(20);
		this.stage.addActor(text);
		*/
		
	}

	/**
	 * initalisierung der Buttons die gezeigt werden, wenn
	 * das korrekte Huetchen gewaehlt wurde.
	 * Weiterhin werden die Listener fuer die Buttons erstellt
	 */
	private void initButtons(){
		this.bitmapFont = new BitmapFont();
		Pixmap pix = new Pixmap(200, 100, Format.RGBA8888);
		pix.setColor(Color.GREEN);
		pix.fill();
		this.upTex = new Texture(pix, Format.RGBA8888, true);
		pix.dispose();
		
		this.upReg = new TextureRegion(this.upTex, pix.getWidth(), pix.getHeight());

		this.buttonStyle = new TextButtonStyle();
		
		this.buttonStyle.up = new TextureRegionDrawable(this.upReg);
		this.bitmapFont.setScale(2);
		this.buttonStyle.font = this.bitmapFont;
		
		this.newGame = new TextButton("Neue Runde?", this.buttonStyle);
		this.back = new TextButton("Zurück", this.buttonStyle);
		
		this.newGame.toFront();
		this.back.toFront();
		
		this.buttonTable.addActor(this.newGame);
		this.buttonTable.addActor(this.back);
		
		this.newGame.addListener(new InputListener(){
			 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				 buttonTable.setVisible(false);
				 pl.setVisible(false);
				 for(int i = 0; i < HAT_NUMBER; i++){
					 hats[i].setVisible(true);
				 }
				 generateHatNum();
				 return true;
			 }
			 public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				 
			 }
		});
		
		this.back.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
				
			}
		});
	}
	
	/**
	 * Generiert die ID des Hutes unter der das Pferd versteckt ist
	 */
	protected void generateHatNum(){
		this.rightNum = rnd.nextInt(HAT_NUMBER);
	}
	
}
