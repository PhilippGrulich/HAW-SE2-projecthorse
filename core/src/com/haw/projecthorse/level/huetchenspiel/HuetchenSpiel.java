package com.haw.projecthorse.level.huetchenspiel;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * Richtiges Huetchen finden, unter dem das Pferd versteckt ist
 * @author Fabian Reiber
 * 
 * TODO: Huetchen nicht anklicken, sondern ein Stueck hochziehen -> Swipe
 * TODO: "Neue Runde?"-Button generell fuer alle Spiele?
 *
 */

public class HuetchenSpiel extends Level{

	private Stage stage;
	
	/**
	 * Hut und Logik Objekte
	 */
	private Hat[] hats;
	private Group group;
	private static float[] XCORDHAT;
	private static float YCORDHAT;
	private static int HAT_NUMBER = 3;
	private Player pl;
	private Random rnd;
	protected int rightNum;
	//choiceCounter zaehlt die Versuche in einer Runde
	private int choiceCounter;
	private boolean roundFinished;
	/**
	 * Backgroundobjekte
	 */
	private BitmapFont labelFont;
	private Image bgTable;
	private Image bgTree1;
	private Image bgTree2;
	private Image bgBackground;
	private Image bgWitch;
	private Image bgSpeechBalloon;
	/**
	 * Dialogobjekte
	 */
	private Label labelStart;
	private Label labelGood;
	private Label labelTryAgain;
	private Label labelOk;
	private Label labelFail;
	
	/**
	 * Scoreobjekte
	 */
	private String scoreStr;
	private int wins;
	private Label labelWin;
	
	/**
	 * Buttonobjekte -> kommen spaeter weg
	 */
	private VerticalGroup buttonTable;
	private Texture upTex;
	private TextureRegion upReg;
	private TextButtonStyle buttonStyle;
	private TextButton newGame;
//	private TextButton back;
	
	/**
	 * Konstruktor
	 */
	public HuetchenSpiel(){
		super();
		AssetManager.loadSounds("huetchenspiel");
		this.rnd = new Random();
		XCORDHAT = new float[HAT_NUMBER];
		this.choiceCounter = 0;
		this.roundFinished = false;
		this.scoreStr = "Score: ";
		this.wins = 0;
		
		//Sobald es fonts gibt mit Ziffern, diese verwenden.
	//	this.labelFont = new BitmapFont(Gdx.files.internal("pictures/selfmade/font.txt"));
		this.labelFont = new BitmapFont();
		
		initStage();
		initPlayer();
		initBackground();
		initSlogans();
		initHats();
		initScore();
		initEventHatListener();	
		generateHatNum();
		initButtons();

		this.stage.addActor(this.bgBackground);
		this.stage.addActor(this.bgTree1);
		this.stage.addActor(this.bgTree2);
		this.stage.addActor(this.bgWitch);
		this.stage.addActor(this.bgSpeechBalloon);
		
		this.stage.addActor(this.labelStart);
		this.stage.addActor(this.labelGood);
		this.stage.addActor(this.labelTryAgain);
		this.stage.addActor(this.labelOk);
		this.stage.addActor(this.labelFail);
		
		this.stage.addActor(this.bgTable);
		this.stage.addActor(this.group);
		this.stage.addActor(this.pl);
		this.stage.addActor(this.buttonTable);
		this.stage.addActor(this.labelWin);
	}

	/**
	 * Prueft, ob einer der Huete ausgewaehlt wurde
	 * @param delta
	 */
	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0); 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		
		//ueber die EventListener von den Hats iterieren und
		//abfragen ob das richtige Huetchen ausgewaehlt wurde
		if(!this.roundFinished){
			for(int i = 0; i < HAT_NUMBER; i++){
				Array<EventListener> eventList = hats[i].getListeners();
				if(eventList.get(0) instanceof HatListener){
					if(((HatListener)eventList.get(0)).getFound() 
							&& this.choiceCounter < 2){
						
						//Positionen von Pferd und korrektem Hut setzen
						this.pl.setPosition(this.hats[i].getX() + 50, this.hats[i].getY());
						this.hats[i].setPosition(this.hats[i].getX(), this.hats[i].getY() + 80);
						
						this.pl.setVisible(true);
						this.labelStart.setVisible(false);
						((HatListener)eventList.get(0)).setFound(false);
						
						/*
						 * wurde Pferd beim ersten Mal gefunden, dann entsprechende
						 * Ausgabe und Score hochzaehlen. 
						 * wurde Pferd beim zweiten Mal gefunden, dann entsprechende
						 * Ausgabe.
						 */
						switch(this.choiceCounter){
						case 0:
							AssetManager.playSound(this.getLevelID(), "jingles_STEEL10.ogg");
							this.labelGood.setVisible(true);					
							this.wins++;
							this.labelWin.setText(this.scoreStr + this.wins);
							break;
						case 1:
							AssetManager.playSound(this.getLevelID(), "jingles_STEEL10.ogg");
							this.labelTryAgain.setVisible(false);
							this.labelOk.setVisible(true);
							break;
						default:
							break;
						}
						this.buttonTable.setVisible(true);
						this.roundFinished = true;
						//springe aus Schleife, wenn Pferd gefunden wurde
						break;
					}
					//Wurde Pferd nicht gefunden und Hut gewaehlt, entsprechende Ausgabe
					else if(((HatListener)eventList.get(0)).getPressed()
							&& !((HatListener)eventList.get(0)).getFound()){
						AssetManager.playSound(this.getLevelID(), "jingles_STEEL04.ogg");
						this.hats[i].setPosition(this.hats[i].getX(), this.hats[i].getY() + 80);
						((HatListener)eventList.get(0)).setPressed(false);
						this.choiceCounter++;
						
						/*
						 * Sind alle zwei Versuche gescheitert, dann wird Score um
						 * einen runtergezaehlt. Weniger als 0 geht aber nicht
						 */
						if(this.choiceCounter == 2){
							AssetManager.playSound(this.getLevelID(), "jingles_STEEL07.ogg");
							this.labelTryAgain.setVisible(false);
							this.labelFail.setVisible(true);
							this.buttonTable.setVisible(true);
							this.choiceCounter++;
							if(this.wins > 0){
								this.wins--;
								this.labelWin.setText(this.scoreStr + this.wins);
							}
							this.roundFinished = true;
						}
						else{
							this.labelStart.setVisible(false);
							this.labelTryAgain.setVisible(true);
						}
					}
				}
			}
		}
	}

	@Override
	protected void doDispose() {
		this.stage.dispose();	
		this.labelFont.dispose();
		this.upTex.dispose();
	}

	@Override
	protected void doResize(int width, int height) {		
	}

	@Override
	protected void doShow() {
	}

	@Override
	protected void doHide() {
	}

	@Override
	protected void doPause() {		
	}

	@Override
	protected void doResume() {
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
		this.pl.setVisible(false);
	}
	
	/**
	 * Background initialisieren
	 */
	private void initBackground(){
		TextureRegion bgTableTexReg = AssetManager.getTextureRegion("huetchenspiel", "table");
		TextureRegion bgTreeTexReg1 = AssetManager.getTextureRegion("huetchenspiel", "obj_trees1");
		TextureRegion bgTreeTexReg2 = AssetManager.getTextureRegion("huetchenspiel", "obj_trees2");
		TextureRegion bgBackground = AssetManager.getTextureRegion("huetchenspiel", "hintergrund");
		TextureRegion bgWitch = AssetManager.getTextureRegion("huetchenspiel", "Garden_Witch");
		TextureRegion bgSpeechBalloon = AssetManager.getTextureRegion("huetchenspiel", "nicubunu_Callout_cloud_center");
		
		this.bgTable = new Image(bgTableTexReg);
		this.bgTree1 = new Image(bgTreeTexReg1);
		this.bgTree2 = new Image(bgTreeTexReg2);
		this.bgBackground = new Image(bgBackground);
		this.bgWitch = new Image(bgWitch);
		this.bgSpeechBalloon = new Image(bgSpeechBalloon);
		
		this.bgTree1.setPosition(this.bgTable.getWidth() / 4, this.bgTable.getHeight());
		this.bgTree2.setPosition(-this.bgTable.getWidth() / 4, this.bgTable.getHeight() / 1.5f);
		this.bgWitch.setPosition(this.bgTree1.getX() * 2, this.bgTree1.getY() / 1.4f);
		this.bgSpeechBalloon.setPosition(this.bgWitch.getX(), this.bgWitch.getY() + 250);
		
		this.bgTable.toBack();
		this.bgWitch.toBack();
		this.bgSpeechBalloon.toBack();
		this.bgTree1.toBack();
		this.bgTree2.toBack();
		this.bgBackground.toBack();
	}
	
	/**
	 * Dialoge der Hexe initialisieren
	 */
	private void initSlogans(){
		String start = "Waehle einen Hut\nund tippe ihn an";
		String tryAgain = "Schade!\nDu hast noch\neinen Versuch!";
		String good = "Sehr gut gemacht!\nDeine Wahl war\nsofort korrekt!";
		String ok = "Deine Wahl\nwar richtig!";
		String fail = "Leider hast du\ndas Pferd nicht\ngefunden.";
		
		//this.labelFont.setScale(0.5f, 0.6f);
		//labelFont.setScale(3f, 3f);
		this.labelFont.setScale(2f, 2f);
		this.labelFont.setColor(Color.BLUE);
		
		LabelStyle labelStyle = new LabelStyle(this.labelFont,Color.BLUE);

		this.labelStart = new Label(start, labelStyle);
		this.labelGood = new Label(good, labelStyle);
		this.labelTryAgain = new Label(tryAgain, labelStyle);
		this.labelOk = new Label(ok, labelStyle);
		this.labelFail = new Label(fail, labelStyle);
		
		this.labelStart.setPosition(this.bgSpeechBalloon.getX() + 20, this.bgSpeechBalloon.getY() + 150);
		this.labelGood.setPosition(this.bgSpeechBalloon.getX() + 30, this.bgSpeechBalloon.getY() + 120);
		this.labelGood.setVisible(false);
		this.labelTryAgain.setPosition(this.bgSpeechBalloon.getX() + 30, this.bgSpeechBalloon.getY() + 150);
		this.labelTryAgain.setVisible(false);
		this.labelOk.setPosition(this.bgSpeechBalloon.getX() + 50, this.bgSpeechBalloon.getY() + 150);
		this.labelOk.setVisible(false);
		this.labelFail.setPosition(this.bgSpeechBalloon.getX() + 30, this.bgSpeechBalloon.getY() + 120);
		this.labelFail.setVisible(false);
	}
	
	/**
	 * Initialisierung der Hute mit Vergabe einer ID, sowie
	 * Positionierung auf den Screen
	 */
	private void initHats(){
		TextureRegion texHat = AssetManager.getTextureRegion("huetchenspiel", "purple-witch-hat");
		this.hats = new Hat[HAT_NUMBER];
		this.group = new Group();
		int xPosition = 0;
		for(int i = 0; i < HAT_NUMBER; i++){
			XCORDHAT[i] = xPosition;
			this.hats[i] = new Hat(texHat, i);
			this.hats[i].setWidth(200);
			this.hats[i].setHeight(150);
			YCORDHAT = this.bgTable.getHeight() / 2.12f;
			this.hats[i].setPosition(xPosition, YCORDHAT);
			this.group.addActor(this.hats[i]);
			xPosition += (this.width / 2) - 100;
		}
	}
	
	/**
	 * EventListener fuer die Huete anlegen
	 * hier noch fling einbauen, irgendwie
	 */
	private void initEventHatListener(){
		for(int i = 0; i < HAT_NUMBER; i++){
			this.hats[i].addListener(new HatListener(this, this.hats[i].getID()));
			/*Gdx.input.setInputProcessor(new GestureDetector(new HatFlingListener()));
			this.hats[i].addListener(new InputListener(){
			    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					if(hats[i].getID() == rightNum){
						this.found = true;
					}
					else{
						this.pressed = true;
					}
			        return true;
			    }
			});*/
		}
	}
	
	/**
	 * Score initialisieren
	 */
	private void initScore(){
		//labelFont.setScale(1f, 1f);
		this.labelFont.setColor(Color.BLACK);
		LabelStyle labelStyle = new LabelStyle(this.labelFont, Color.BLACK);
		this.labelWin = new Label(this.scoreStr + this.wins, labelStyle);
		this.labelWin.setPosition(this.width  / 2.0f - this.labelWin.getWidth() / 2, 
			this.height - 2 * this.labelWin.getHeight());
	}

	/**
	 * initalisierung der Buttons die gezeigt werden, wenn
	 * das korrekte Huetchen gewaehlt wurde.
	 * Weiterhin werden die Listener fuer die Buttons erstellt
	 */
	//kommt wieder raus, wenn buttons erstellt wurden
	private void initButtons(){
		this.buttonTable = new VerticalGroup();
		this.buttonTable.setPosition(0,  (YCORDHAT - this.height / 3));
		this.buttonTable.setFillParent(true);	
		
		Pixmap pix = new Pixmap(200, 100, Format.RGBA8888);
		pix.setColor(Color.GREEN);
		pix.fill();
		this.upTex = new Texture(pix, Format.RGBA8888, true);
		pix.dispose();
		
		this.upReg = new TextureRegion(this.upTex, pix.getWidth(), pix.getHeight());

		this.buttonStyle = new TextButtonStyle();
		
		this.buttonStyle.up = new TextureRegionDrawable(this.upReg);
		this.labelFont.setScale(2f, 2f);
		this.buttonStyle.font = this.labelFont;
		
		this.newGame = new TextButton("Neue Runde?", this.buttonStyle);
		//this.back = new TextButton("Zurück", this.buttonStyle);
		
		this.newGame.toFront();
		//this.back.toFront();
		
		this.buttonTable.addActor(this.newGame);
		//this.buttonTable.addActor(this.back);
		
		this.newGame.addListener(new InputListener(){
			 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				 buttonTable.setVisible(false);
				 pl.setVisible(false);
				 labelStart.setVisible(true);
				 labelGood.setVisible(false);
				 labelTryAgain.setVisible(false);
				 labelOk.setVisible(false);
				 labelFail.setVisible(false);
				 choiceCounter = 0;
				 roundFinished = false;
				 for(int i = 0; i < HAT_NUMBER; i++){
					 //hats[i].setVisible(true);
					 hats[i].setPosition(XCORDHAT[i], YCORDHAT);
				 }
				 generateHatNum();
				 return true;
			 }
			 public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				 
			 }
		});
		/*
		this.back.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
				
			}
		});*/

		this.buttonTable.setVisible(false);
	}
	
	/**
	 * Generiert die ID des Hutes unter der das Pferd versteckt ist
	 */
	protected void generateHatNum(){
		this.rightNum = rnd.nextInt(HAT_NUMBER);
	}
	
	/**
	 * Info, ob eine Runde beendet wurde
	 * @return Wurde Runde beendet true, sonst false
	 */
	protected boolean getRoundFinished(){
		return this.roundFinished;
	}
	
}
