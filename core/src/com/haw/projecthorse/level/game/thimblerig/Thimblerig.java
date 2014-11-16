package com.haw.projecthorse.level.game.thimblerig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.game.thimblerig.HatGestureDetector.IOnDirection;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;
import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * Richtiges Huetchen finden, unter dem das Pferd versteckt ist
 * @author Fabian Reiber
 * TODO: alle loots sichern -> auskommentieren
 * TODO: über liste aller loots iterieren
 * TODO: pferderasse in if-abfragen und dohint aufrufen
 */

public class Thimblerig extends Level{

	private Stage stage;
	private static boolean ISPAUSED;
	
	/**
	 * Hut- und Logik-Objekte
	 */
	private Hat[] hats;
	private GestureDetector hatGestureDetector;
	private InputMultiplexer hatGameMultiplexer;
	private Group group;
	private static float[] XCORDHAT;
	private static float YCORDHAT;
	private static int HAT_NUMBER = 3;
	private Player pl;
	private Random rnd;
	private int rightNum;
	//choiceCounter zaehlt die Versuche in einer Runde
	private int choiceCounter;
	private boolean roundFinished;
	//wurde ein Hut in der Runde gewaehlt: zur Liste hinzufuegen, damit 
	//dieser nicht nochmal gewaehlt werden kann
	private List<Integer> hatIndexList;
	/**
	 * Background-Objekte
	 */
	private BitmapFont textFont;
	private Image bgTable;
	private Image bgTree1;
	private Image bgTree2;
	private Image bgBackground;
	private Image bgWitch;
	private Image bgSpeechBalloon;
	/**
	 * Dialog-Objekte
	 */
	private Label labelStart;
	private Label labelGood;
	private Label labelTryAgain;
	private Label labelOk;
	private Label labelFail;
	
	/**
	 * Score-Objekte
	 */
	private BitmapFont scoreFont;
	private String scoreStr;
	private int wins;
	private Label labelWin;
	
	/**
	 * Button-Objekt
	 */
	private Dialog newGame;

	/**
	 * Loot-Objekte
	 */
	private List<Loot> lootObj;
	private static final int MINSCORE = 10;
	private static boolean ISMINSCORED;
	private static final int MIDSCORE = 20;
	private static boolean ISMIDSCORED;
	private static final int MAXSCORE = 30;
	private static boolean ISMAXSCORED;
	/**
	 * Hint-Objekte
	 */
	private int jiggleVal;
	private int jiggleCounter;
	private static final int JIGGLETIMEMIN = 6;
	private static final int JIGGLETIMEMAX = 12;
	private static boolean JIGGLECOUNTERFLAG;
	
	/**
	 * Konstruktor
	 */
	public Thimblerig(){
		super();
		ISPAUSED = false;
		this.rnd = new Random();
		XCORDHAT = new float[HAT_NUMBER];
		this.choiceCounter = 0;
		this.roundFinished = false;
		this.scoreStr = "Score: ";
		this.wins = 0;
		this.hatIndexList = new ArrayList<Integer>();
		
		this.textFont = AssetManager.getTextFont(FontSize.DREISSIG);

		/**
		 * damit nicht direkt zu Beginn ein Hinweis kommt, den Counter bereits 
		 * vorinitialisieren.
		 */
		this.jiggleVal = 1000;
		this.jiggleCounter = 800;
		JIGGLECOUNTERFLAG = false;
		
		initStage();
		initPlayer();
		initBackground();
		initSlogans();
		initHats();
		initScore();
		generateHatNum();
		initButtons();
		addActorsToStage();
		initProcessorAndGestureDetector();
		initLoots();
	}

	/**
	 * Prueft, ob einer der Huete ausgewaehlt wurde
	 * @param delta
	 */
	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0); 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.stage.act(delta);
		this.stage.draw();
		
		//eigentliche Spiellogik
		if(!this.roundFinished && !ISPAUSED){
			//if(pferderasse == ein stoerrisches pferd) then dohint...
			doHint(delta);
			
			for(int i = 0; i < HAT_NUMBER; i++){
				if(this.hats[i].isFlinged() && this.hats[i].isChoosed()){
					//Positionen von Pferd und korrektem Hut setzen
					this.pl.setPosition(this.hats[i].getX() + 50, this.hats[i].getY());
					
					Action moveTo = Actions.moveTo(this.hats[i].getX(),
							this.hats[i].getY() + 80);
					this.hats[i].addAction(moveTo);
					this.pl.setVisible(true);
					this.labelStart.setVisible(false);
					
					/*
					 * wurde Pferd beim ersten Mal gefunden, dann entsprechende
					 * Ausgabe und Score hochzaehlen. 
					 * wurde Pferd beim zweiten Mal gefunden, dann entsprechende
					 * Ausgabe.
					 */
					switch(this.choiceCounter){
					case 0:
						AssetManager.playSound(this.getLevelID(), "jingles_SAX10.ogg");
						this.labelGood.setVisible(true);					
						this.wins++;
						this.labelWin.setText(this.scoreStr + this.wins);
						checkPrize();
						break;
					case 1:
						AssetManager.playSound(this.getLevelID(), "jingles_SAX10.ogg");
						this.labelTryAgain.setVisible(false);
						this.labelOk.setVisible(true);
						break;
					default:
						break;
					}
					
					this.newGame.setVisible(true);
					this.roundFinished = true;
					//springe aus Schleife, wenn Pferd gefunden wurde
					break;
				}
				//Wurde Hut gewaehlt, Pferd nicht gefunden und wurde der Hut vorher in gleicher
				//Runde noch nicht gewaehlt
				else if(this.hats[i].isFlinged() && !this.hats[i].isChoosed()
						&& !this.hatIndexList.contains(i)){
					
					AssetManager.playSound(this.getLevelID(), "jingles_SAX04.ogg");
					Action moveTo = Actions.moveTo(this.hats[i].getX(), 
							this.hats[i].getY() + 80);
					this.hats[i].addAction(moveTo);
					this.choiceCounter++;
					this.hatIndexList.add(i);
					/*
					 * Sind alle zwei Versuche gescheitert, dann wird Score um
					 * einen runtergezaehlt und Runde beendet. Weniger als 0 geht aber nicht
					 */
					if(this.choiceCounter == 2){
						AssetManager.playSound(this.getLevelID(), "jingles_SAX07.ogg");
						this.labelTryAgain.setVisible(false);
						this.labelFail.setVisible(true);
						this.newGame.setVisible(true);
						
						
						if(this.wins > 0){
							this.wins--;
							this.labelWin.setText(this.scoreStr + this.wins);
						}
						this.roundFinished = true;
						//ein dritter Versuch in einer Runde ist nicht moegliche
						//aus der Schleife springen
						break;
					}
					else{
						this.labelStart.setVisible(false);
						this.labelTryAgain.setVisible(true);
						}
					}
				}
			}	
		}

	@Override
	protected void doDispose() {
		//this.chest.saveAllLoot();
		this.stage.dispose();	
		this.textFont.dispose();
		AssetManager.turnMusicOff("thimblerig", "Little_Bits.mp3");
	}

	@Override
	protected void doResize(int width, int height) {
		
	}

	@Override
	protected void doShow() {
		AssetManager.loadSounds("thimblerig");
		AssetManager.changeSoundVolume(0.4f);
		AssetManager.loadMusic("thimblerig");
		AssetManager.playMusic("thimblerig", "Little_Bits.mp3");
		AssetManager.changeMusicVolume("thimblerig", "Little_Bits.mp3", 0.2f);
		AssetManager.setMusicLooping("thimblerig", "Little_Bits.mp3", true);
		
		
	}

	@Override
	protected void doHide() {
	}

	@Override
	protected void doPause() {		
		ISPAUSED = true;
	}

	@Override
	protected void doResume() {
		ISPAUSED = false;
	}
	
	/**
	 * Stage initialisieren
	 */
	private void initStage(){
		this.stage = new Stage(this.getViewport(), this.getSpriteBatch());
	}
	
	/**
	 * Horse initialisieren
	 */
	private void initPlayer(){
		this.pl = new PlayerImpl();
		this.pl.setVisible(false);
	}
	
	/**
	 * Background initialisieren und positionieren
	 */
	private void initBackground(){
		TextureRegion bgTableTexReg = AssetManager.getTextureRegion("thimblerig", "table");
		TextureRegion bgTreeTexReg1 = AssetManager.getTextureRegion("thimblerig", "obj_trees1");
		TextureRegion bgTreeTexReg2 = AssetManager.getTextureRegion("thimblerig", "obj_trees2");
		TextureRegion bgBackground = AssetManager.getTextureRegion("thimblerig", "hintergrund");
		TextureRegion bgWitch = AssetManager.getTextureRegion("thimblerig", "Garden_Witch");
		TextureRegion bgSpeechBalloon = AssetManager.getTextureRegion("thimblerig", "nicubunu_Callout_cloud_center");
		
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
	 * Dialoge der Hexe initialisieren und positionieren
	 */
	private void initSlogans(){
		String start = "Entscheide dich\nfür einen Hut\nund 'wische' ihn\nhoch.";
		String tryAgain = "Schade!\nDu hast noch\neinen Versuch!";
		String good = "Sehr gut\ngemacht!\nDeine Wahl war\nsofort korrekt!";
		String ok = "Deine Wahl\nwar richtig!";
		String fail = "Leider hast du\ndas Pferd nicht\ngefunden.";
		
		this.textFont.setScale(1f, 1f);
		this.textFont.setColor(Color.BLUE);
		
		LabelStyle labelStyle = new LabelStyle(this.textFont,Color.BLUE);

		this.labelStart = new Label(start, labelStyle);
		this.labelGood = new Label(good, labelStyle);
		this.labelTryAgain = new Label(tryAgain, labelStyle);
		this.labelOk = new Label(ok, labelStyle);
		this.labelFail = new Label(fail, labelStyle);
		
		this.labelStart.setPosition(this.bgSpeechBalloon.getX() + 20, this.bgSpeechBalloon.getY() + 120);
		this.labelGood.setPosition(this.bgSpeechBalloon.getX() + 30, this.bgSpeechBalloon.getY() + 130);
		this.labelGood.setVisible(false);
		this.labelTryAgain.setPosition(this.bgSpeechBalloon.getX() + 30, this.bgSpeechBalloon.getY() + 150);
		this.labelTryAgain.setVisible(false);
		this.labelOk.setPosition(this.bgSpeechBalloon.getX() + 50, this.bgSpeechBalloon.getY() + 150);
		this.labelOk.setVisible(false);
		this.labelFail.setPosition(this.bgSpeechBalloon.getX() + 30, this.bgSpeechBalloon.getY() + 150);
		this.labelFail.setVisible(false);
	}
	
	/**
	 * Initialisierung der Huete mit Vergabe einer ID, sowie
	 * Positionierung auf den Screen
	 */
	private void initHats(){
		TextureRegion texHat = AssetManager.getTextureRegion("thimblerig", "purple-witch-hat");
		this.hats = new Hat[HAT_NUMBER];
		this.group = new Group();
		int xPosition = 0;
		YCORDHAT = this.bgTable.getHeight() / 2.12f;
		for(int i = 0; i < HAT_NUMBER; i++){
			XCORDHAT[i] = xPosition;
			this.hats[i] = new Hat(texHat);
			this.hats[i].setWidth(200);
			this.hats[i].setHeight(150);
			this.hats[i].setPosition(xPosition, YCORDHAT);
			this.group.addActor(this.hats[i]);
			xPosition += (this.width / 2) - 100;
		}
	}
	
	/**
	 * Score initialisieren
	 */
	private void initScore(){
		this.scoreFont = AssetManager.getHeadlineFont(FontSize.VIERZIG);
		this.scoreFont.setScale(1f, 1.5f);
		LabelStyle labelStyle = new LabelStyle(this.scoreFont, Color.BLACK);
		this.labelWin = new Label(this.scoreStr + this.wins, labelStyle);
		this.labelWin.setPosition(this.width  / 2.0f - this.labelWin.getWidth() / 2, 
			this.height - 2 * this.labelWin.getHeight());
	}

	/**
	 * initalisierung der Buttons die gezeigt werden, wenn
	 * das korrekte Huetchen gewaehlt wurde.
	 * Weiterhin werden die Listener fuer die Buttons erstellt
	 */
	private void initButtons(){
		this.newGame = new Dialog("Möchtest du eine\nweitere Runde spielen?");
		this.newGame.setSize(10, 10);
		this.newGame.addButton("jaa, sehr gerne", new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				 if(!ISPAUSED){
					 newGame.setVisible(false);
					 pl.setVisible(false);
					 labelStart.setVisible(true);
					 labelGood.setVisible(false);
					 labelTryAgain.setVisible(false);
					 labelOk.setVisible(false);
					 labelFail.setVisible(false);
					 choiceCounter = 0;
					 roundFinished = false;
					 hatIndexList.clear();
					 hats[rightNum].setChoosed(false);
					 for(int i = 0; i < HAT_NUMBER; i++){
						 hats[i].setPosition(XCORDHAT[i], YCORDHAT);
						 hats[i].setFlinged(false);
					 }
					 generateHatNum();	
				 }
			}
			
		});
		
		this.newGame.addButton("och nö, lieber nicht", new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
			}
			
		});

		this.newGame.setPosition(0,  YCORDHAT);
		this.newGame.setVisible(false);
	}
	
	/**
	 * fuegt alle Actor der Stage hinzu
	 */
	private void addActorsToStage(){
		this.stage.addActor(this.bgBackground);
		this.stage.addActor(this.bgTree1);
		this.stage.addActor(this.bgTree2);
		this.stage.addActor(this.bgWitch);
		this.stage.addActor(this.newGame);
		this.stage.addActor(this.bgSpeechBalloon);
		
		this.stage.addActor(this.labelStart);
		this.stage.addActor(this.labelGood);
		this.stage.addActor(this.labelTryAgain);
		this.stage.addActor(this.labelOk);
		this.stage.addActor(this.labelFail);
		
		this.stage.addActor(this.bgTable);
		this.stage.addActor(this.group);
		this.stage.addActor(this.pl);
		this.stage.addActor(this.labelWin);
	}
	
	/**
	 * initialisiert den GestureDetector fuer die Swipes und fuegt diesen und
	 * die Stage dem InputProcessor hinzu
	 */
	private void initProcessorAndGestureDetector(){
		this.hatGestureDetector = new HatGestureDetector(new IOnDirection() {
			
			@Override
			public void onUp(float actualX, float actualY) {				
				if(!ISPAUSED){
					for(int i = 0; i < hats.length; i++){
						if(actualX > hats[i].getX()
		 						&& actualX < (hats[i].getX() + hats[i].getWidth())
								&& actualY > hats[i].getY()
								&& actualY < hats[i].getY() + hats[i].getHeight()){
							
		 					hats[i].setFlinged(true);
							break;
						}
					}
				}
			}

			/**
			 * nicht implementiert, da ungenutzt
			 */
			@Override
			public void onLeft() {				
			}

			/**
			 * nicht implementiert, da ungenutzt
			 */
			@Override
			public void onRight() {
			}

			/**
			 * nicht implementiert, da ungenutzt
			 */
			@Override
			public void onDown() {
			}
		}, this.stage);

		this.hatGameMultiplexer = new InputMultiplexer();
		this.hatGameMultiplexer.addProcessor(this.hatGestureDetector);
		this.hatGameMultiplexer.addProcessor(this.stage);
		InputManager.addInputProcessor(this.hatGameMultiplexer);
	}
	
	private void initLoots(){
		this.lootObj = new ArrayList<Loot>();
		
		this.lootObj.add(new ThimblerigLoot("lolli", "kleiner Dauerlutscher"
				, "noonespillow_Lollipop"));
		this.lootObj.add(new ThimblerigLoot("suger", "etwas Würfelzucker"
				, "Sugar_cube"));
		this.lootObj.add(new ThimblerigLoot("ballon", "leichter Ballon"
				, "Balionai"));
		this.lootObj.add(new ThimblerigLoot("möhre", "gesunde Möhre"
				, "2011-02-15_Cartoon_carrot"));
		this.lootObj.add(new ThimblerigLoot("miniPferd", "süßes Mini-Pferd"
				, "WOOD_HORSE"));
		this.lootObj.add(new ThimblerigLoot("hufeisen", "schweres Hufeisen"
				, "horseshoe"));
		this.lootObj.add(new ThimblerigLoot("hut", "glückbringender Hut"
				, "liftarn_Green_hat"));
		this.lootObj.add(new ThimblerigLoot("heu", "kraftbringendes Heu"
				, "mcol_haystack"));
		
		ISMINSCORED = false;
		ISMIDSCORED = false;
		ISMAXSCORED = false;
	}
	
	/**
	 * ermittelt, ob eine der Scoregrenzen erreicht wurde. Wenn ja, dann wird der Reihe
	 * nach geprueft, ob die einzelnen Loots, die in diesem Spiel gewonnen werden
	 * koennen, bereits in der Loot-Galerie vorhanden sind. Wenn ja wird das Loot
	 * hinzugefuegt, sonst eine entsprechende Ausgabe erzeugt. Wurd der Maxscore erreicht
	 * kann eine Pferderasse gewonnen werden.
	 */
	private void checkPrize(){
		/**
		 * wurden beide Werte auf -2 gesetzt, dann darf entsprechend keine Ausgabe
		 * oder ein hinzufuegen von Loot/Pferd erfolgen
		 */
		int indexOfLoot = -1;
		int indexOfHorseLoot = -1;
		int maxNumberOfLootsMinscore = (this.lootObj.size() / 2) - 1;
		int maxNumberOfLootsMidscore = (this.lootObj.size() / 2);
		switch(this.wins){
		case MINSCORE:
			if(!ISMINSCORED){
				ISMINSCORED = true;
				indexOfLoot = getLootIndexMinscore(maxNumberOfLootsMinscore);
				indexOfHorseLoot = -2;
			}
			//Wurde bereits der Minscore einmal erreicht, dann darf keine weitere 
			//Pruefung getaetigt werden bzw. Ausgabe der Loots/Pferde erscheinen
			else{
				indexOfLoot = -2;
				indexOfHorseLoot = -2;
			}
			break;
		case MIDSCORE:
			if(!ISMIDSCORED){
				ISMIDSCORED = true;
				indexOfLoot = getLootIndexMidscore(maxNumberOfLootsMinscore, 
						maxNumberOfLootsMidscore);		
				indexOfHorseLoot = -2;
			}
			//Wurde bereits der Minscore einmal erreicht, dann darf keine weitere 
			//Pruefung getaetigt werden bzw. Ausgabe der Loots/Pferde erscheinen
			else{
				indexOfLoot = -2;
				indexOfHorseLoot = -2;
			}
			break;
		case MAXSCORE:
			if(!ISMAXSCORED){
				ISMAXSCORED = true;
				indexOfHorseLoot = getHorseIndexMaxscore();
				indexOfLoot = -2;
				//neue Pferderasse gewinnen, sofern noch nicht vorhanden
				//ggf. über Loots
			}
			//Wurde bereits der Minscore einmal erreicht, dann darf keine weitere 
			//Pruefung getaetigt werden bzw. Ausgabe der Loots/Pferde erscheinen
			else{
				indexOfLoot = -2;
				indexOfHorseLoot = -2;
			}
			break;
		default:
			//keine Ausgabe oder hinzufuegen eines Loots/Pferdes, da Score keinen 
			//der Scoregrenzen erreicht hat
			indexOfLoot = -2;
			indexOfHorseLoot = -2;
			break;
		}
		
		/**
		 * Wurde entweder der Minscore oder Midscore erreicht und ein entsprechendes
		 * Loot gewaehlt wurde, wird dieses in die Galeri eingefuegt.
		 * Sonst wurden alle Loots, die in diesem Spiel gewonnen werden konnten,
		 * bereits gewonnen und ein entsprechender Dialog erscheint
		 */
		if (indexOfLoot > -1){
			//this.chest.addLoot(this.lootObj.get(indexOfLoot));
			System.out.println("inside switch");
			//loot-dialog anzeigen....
		}
		else if(indexOfLoot == -1){
			final Dialog d = new Dialog("Bei dieser Scoregrenze\n kannst du leider\n"
					+ "nichts mehr gewinnen!");
			d.addButton("na gut...", new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(!ISPAUSED){
						d.setVisible(false);
					}
				}
			});
			d.setVisible(true);
			this.stage.addActor(d);
		}
		
		/**
		 * Wenn Maxscore erreicht wurde, dann kann eine Pferderasse gewonnen werden.
		 * Sonst, erscheint ein Dialog-Fenster mit dem Hinweis, dass keine Pferderasse
		 * mehr gewonnen werden kann
		 */
		if(indexOfHorseLoot > -1){
			//pferderasse holen, abspeichern und loot-dialog anzeigen
		}
		else if(indexOfHorseLoot == -1){
			final Dialog d = new Dialog("Du hast bereits\nalle Pferderassen gewonnen.\n"
					+ "Aber vielleicht kannst\n du noch weitere Gegenstände\n"
					+ "in diesem Minispiel gewinnen!");
			d.addButton("ist ok...", new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(!ISPAUSED){
						d.setVisible(false);
					}
				}
			});
			d.setVisible(true);
			this.stage.addActor(d);
		}	
	}
	
	/**
	 * wurde der Minscore erreicht, kann hier nur ((n / 2) - 1)-mal ein neues
	 * Loot gewonnen werden. n ist die Listengroesse der moeglichen Loots die in 
	 * diesem Spiel gewonnen werden koennen
	 * @return Index des Loots, welches gewonnen wurde. -1, sonst, wenn bereits
	 * 			maxNumberOfLootsMinscore erreicht wurde.
	 */
	private int getLootIndexMinscore(int maxNumberOfLootsMinscore){
		int index = -1;
		for(int i = 0; i < maxNumberOfLootsMinscore; i++){
			//if(!"liste der gewonnenen loots".contains(this.lootObj.get(i))) 
			//then index = i; break;
			//liste der loots erhalten und schauen ob item enthalten
		}
		return index;
	}
	
	/**
	 * wurde der Midscore erreicht, kann hier nur (n / 2)-mal ein neues
	 * Loot gewonnen werden. n ist die Listengroesse der moeglichen Loots die in 
	 * diesem Spiel gewonnen werden koennen.
	 * @return Index des Loots, welches gewonnen wurde. -1, sonst, wenn bereits 
	 * 			maxNumberOfLootsMaxScore erreicht wurde.
	 */
	private int getLootIndexMidscore(int maxNumberOfLootsMinscore, 
			int maxNumberOfLootsMidscore){
		int index = -1;
		for(int i = maxNumberOfLootsMinscore; i < maxNumberOfLootsMidscore; i++){
			//if(!"liste der gewonnenen loots".contains(this.lootObj.get(i))) 
			//then index = i; break;
			//liste der loots erhalten und schauen ob item enthalten
		}
		return index;
	}
	
	/**
	 * Wurde der Maxscore erreicht, wird ermittelt, ob eine Pferderasse gewonnen 
	 * werden kann. 
	 * @return Index des Pferde, welches gewonnen wurde. -1, sonst, wenn bereits
	 * alle Pferderassen in der Loot-Galerie vorhanden sind
	 */
	private int getHorseIndexMaxscore(){
		int index = -1;
		//ueber Lootliste iterieren und pruefen, ob die naechste Pferderasse
		//bereits vorhanden.
		return index;
	}
	
	/**
	 * Generiert die ID des Hutes unter der das Pferd versteckt ist
	 */
	private void generateHatNum(){
		this.rightNum = this.rnd.nextInt(HAT_NUMBER);
		this.hats[this.rightNum].setChoosed(true);
	}
	
	/**
	 * generiert einen Faktor um den jiggleCounter in Abhaengigkeit des delta's
	 * zu berechnen. Der Faktor liegt zwischen JIGGLETIMEMIN und JIGGLETIMEMAX. 
	 * Der Faktor kann auch als Zeitspanne in Sekunden gesehen werden. Nach Ablauf
	 * dieser Zeit bewegt sich der Hut einmal.
	 * @param delta
	 */
	private void generateJiggleCounter(float delta){
		if(!JIGGLECOUNTERFLAG && Math.round(1/delta) > 0){
			int factor = this.rnd.nextInt(JIGGLETIMEMAX - JIGGLETIMEMIN)
					+ JIGGLETIMEMIN;
			this.jiggleCounter = factor * Math.round(1/delta);
			this.jiggleVal = this.jiggleCounter;
			JIGGLECOUNTERFLAG = true;
		}
	}
	
	/**
	 * Abhaengig von der Pferderasse gibt es einen Hinweis an den/die Spieler/in.
	 * Dieser sieht so aus, dass der Hut, unter dem das Pferd versteckt ist, einmal
	 * kurz wackelt.
	 * @param delta
	 */
	private void doHint(float delta){
		if(this.jiggleCounter <= 0){
			JIGGLECOUNTERFLAG = false;
			generateJiggleCounter(delta);
		}
		this.jiggleCounter--;
		rotateHats();
	}
	
	/**
	 * laesst den jeweiligen Hut unter dem das Pferd ist wackeln.
	 */
	private void rotateHats(){
		if(this.jiggleCounter == this.jiggleVal){
			this.hats[this.rightNum].setRotation(10f);
		}
		else if(this.jiggleCounter == Math.round(this.jiggleVal * 0.96f)){
			this.hats[this.rightNum].setRotation(0f);
			AssetManager.playSound("thimblerig", "Wiehern.ogg");
		}
		else if(this.jiggleCounter == Math.round(this.jiggleVal * 0.94f)){
			this.hats[this.rightNum].setRotation(10f);
		}
		else if(this.jiggleCounter == Math.round(this.jiggleVal * 0.92f)){
			this.hats[this.rightNum].setRotation(0f);
		}
	}
}
