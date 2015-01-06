package com.haw.projecthorse.level.game.thimblerig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.game.Game;
import com.haw.projecthorse.level.game.thimblerig.HatGestureDetector.IOnDirection;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;
import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.RaceLoot;
import com.haw.projecthorse.savegame.SaveGameManager;

/**
 * Richtiges Huetchen finden, unter dem das Pferd versteckt ist.
 * @author Fabian Reiber
 * @version 1.0
 */

public class Thimblerig extends Game{

	private Stage stage;
	private static boolean isPaused;
	
	/**
	 * Musik und Sound Objekte.
	 */
	private Music bgMusic;
	private Sound firstSax;
	private Sound secondSax;
	private Sound thirdSax;
	private Sound horseNeighing;
	/**
	 * Hut- und Logik-Objekte.
	 */
	private Hat[] hats;
	private GestureDetector hatGestureDetector;
	private InputMultiplexer hatGameMultiplexer;
	private Group group;
	private static float[] xCordHat;
	private static float yCordHat;
	private static int hatNumber = 3;
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
	 * Background-Objekte.
	 */
	private BitmapFont textFont;
	private Image bgTable;
	private Image bgTree1;
	private Image bgTree2;
	private Image bgBackground;
	private Image bgWitch;
	private Image bgSpeechBalloon;
	/**
	 * Dialog-Objekte.
	 */
	private Label labelStart;
	private Label labelGood;
	private Label labelTryAgain;
	private Label labelOk;
	private Label labelFail;
	
	/**
	 * Score-Objekte.
	 */
	private BitmapFont scoreFont;
	private String scoreStr;
	private int wins;
	private Label labelWin;
	
	/**
	 * Button-Objekt.
	 */
	private ButtonLarge newGame;
	private ButtonLarge exitGame;

	/**
	 * Loot-Objekte.
	 */
	private List<Loot> possibleWinLoots;
	private List<ThimblerigLoot> justWonLoots;
	private static final int MINSCORE = 10;
	private static boolean isMinscored;
	private static final int MIDSCORE = 20;
	private static boolean isMidScored;
	private static final int MAXSCORE = 30;
	private static boolean isMaxScored;
	/**
	 * Hint-Objekte.
	 */
	private int jiggleVal;
	private int jiggleCounter;
	private static final int JIGGLETIMEMIN = 6;
	private static final int JIGGLETIMEMAX = 12;
	private static boolean jiggleCounterFlag;
	
	/**
	 * Konstruktor.
	 */
	@SuppressWarnings("unchecked")
	public Thimblerig(){
		super();
		isPaused = false;
		this.rnd = new Random();
		xCordHat = new float[hatNumber];
		this.choiceCounter = 0;
		this.roundFinished = false;
		this.scoreStr = "Score: ";
		this.wins = 0;
		this.hatIndexList = new ArrayList<Integer>();
		
		this.justWonLoots = (List<ThimblerigLoot>) getThimblerigLoots();		
		this.textFont = AssetManager.getTextFont(FontSize.THIRTY);

		/**
		 * damit nicht direkt zu Beginn ein Hinweis kommt, den Counter bereits 
		 * vorinitialisieren.
		 */
		this.jiggleVal = 1000;
		this.jiggleCounter = 800;
		jiggleCounterFlag = false;
		
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
	 * Prueft, ob einer der Huete ausgewaehlt wurde.
	 * @param delta 
	 */
	@Override
	protected void doRender(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0); 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.stage.act(delta);
		this.stage.draw();
		
		//eigentliche Spiellogik
		if(!this.roundFinished && !isPaused){
			//es gibt nur Hinweise fuer die Spieler_innen, wenn das Pferd
			//etwas ungehorsam ist
			if(this.pl.getObedience() <= 0.5f){
				doHint(delta);
			}
			
			for(int i = 0; i < hatNumber; i++){
				if(this.hats[i].isFlinged() && this.hats[i].isChoosed()){
					//Positionen von Pferd und korrektem Hut setzen
					this.pl.setPosition(this.hats[i].getX() + 15, this.hats[i].getY());
					
					Action moveTo = Actions.moveTo(this.hats[i].getX(),
							this.hats[i].getY() + 80);
					this.hats[i].addAction(moveTo);
					this.pl.setVisible(true);
					this.labelStart.setVisible(false);
					showSloganAndPlaySound();
					//springe aus Schleife, wenn Pferd gefunden wurde
					break;
				}
				//Wurde Hut gewaehlt, Pferd nicht gefunden und wurde der Hut vorher in gleicher
				//Runde noch nicht gewaehlt
				else if(this.hats[i].isFlinged() && !this.hats[i].isChoosed()
						&& !this.hatIndexList.contains(i)){
					
					this.secondSax.play(0.4f);
					Action moveTo = Actions.moveTo(this.hats[i].getX(), 
							this.hats[i].getY() + 80);
					this.hats[i].addAction(moveTo);
					this.choiceCounter++;
					this.hatIndexList.add(i);
					
					if(this.choiceCounter == 2){
						showSloganAndPlaySound();
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
	
	/**
	 * Prueft bei dem wie vielten Versuch das Pferd gefunden bzw. nicht gefunden wurde
	 * und gibt dementsprechend ein Slogan aus und spielt einen Sound ab.
	 */
	private void showSloganAndPlaySound(){		
		switch(this.choiceCounter){
		case 0:
			this.firstSax.play(0.4f);
			this.labelGood.setVisible(true);					
			this.wins++;
			this.labelWin.setText(this.scoreStr + this.wins);
			checkPrize();
			break;
		case 1:
			this.firstSax.play(0.4f);
			this.labelTryAgain.setVisible(false);
			this.labelOk.setVisible(true);
			break;
		case 2:
			this.thirdSax.play(0.4f);
			this.labelTryAgain.setVisible(false);
			this.labelFail.setVisible(true);

			/*
			 * Sind alle zwei Versuche gescheitert, dann wird Score um
			 * einen runtergezaehlt und Runde beendet. Weniger als 0 geht aber nicht
			 */
			if(this.wins > 0){
				this.wins--;
				this.labelWin.setText(this.scoreStr + this.wins);
			}
			break;
		default:
			break;
		}

		this.newGame.setVisible(true);
		this.exitGame.setVisible(true);
		this.roundFinished = true;
	}

	@Override
	protected void doDispose() {
		this.chest.saveAllLoot();
		this.stage.dispose();
		this.bgMusic.stop();
	}

	@Override
	protected void doResize(final int width, final int height) {
		
	}

	@Override
	protected void doShow() {
		AssetManager.loadSounds("thimblerig");
		AssetManager.loadMusic("thimblerig");
		this.bgMusic = this.audioManager.getMusic("thimblerig", "Little_Bits.mp3");
		this.bgMusic.setLooping(true);
		this.bgMusic.play();
		this.bgMusic.setVolume(0.2f);
		this.firstSax = this.audioManager.getSound(this.getLevelID(), "jingles_SAX10.ogg");
		this.secondSax = this.audioManager.getSound(this.getLevelID(), "jingles_SAX04.ogg");
		this.thirdSax = this.audioManager.getSound(this.getLevelID(), "jingles_SAX07.ogg");
		this.horseNeighing = this.audioManager.getSound("thimblerig", "Wiehern.ogg");
	}

	@Override
	protected void doHide() {
	}

	@Override
	protected void doPause() {		
		isPaused = true;
	}

	@Override
	protected void doResume() {
		isPaused = false;
	}
	
	/**
	 * Stage initialisieren.
	 */
	private void initStage(){
		this.stage = new Stage(this.getViewport(), this.getSpriteBatch());
	}
	
	/**
	 * Horse initialisieren.
	 */
	private void initPlayer(){
		this.pl = new PlayerImpl();
		this.pl.scaleBy(-0.5f);
		this.pl.setVisible(false);
	}
	
	/**
	 * Background initialisieren und positionieren.
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
	 * Dialoge der Hexe initialisieren und positionieren.
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
	 * Positionierung auf den Screen.
	 */
	private void initHats(){
		TextureRegion texHat = AssetManager.getTextureRegion("thimblerig", "purple-witch-hat");
		this.hats = new Hat[hatNumber];
		this.group = new Group();
		int xPosition = 0;
		yCordHat = this.bgTable.getHeight() / 2.12f;
		for(int i = 0; i < hatNumber; i++){
			xCordHat[i] = xPosition;
			this.hats[i] = new Hat(texHat);
			this.hats[i].setWidth(200);
			this.hats[i].setHeight(150);
			this.hats[i].setPosition(xPosition, yCordHat);
			this.group.addActor(this.hats[i]);
			xPosition += (this.width / 2) - 100;
		}
	}
	
	/**
	 * Score initialisieren.
	 */
	private void initScore(){
		this.scoreFont = AssetManager.getHeadlineFont(FontSize.FORTY);
		this.scoreFont.setScale(1f, 1.5f);
		LabelStyle labelStyle = new LabelStyle(this.scoreFont, Color.BLACK);
		this.labelWin = new Label(this.scoreStr + this.wins, labelStyle);
		this.labelWin.setPosition(this.width  / 2.0f - this.labelWin.getWidth() / 2, 
			this.height - 2 * this.labelWin.getHeight() + 50);
	}

	/**
	 * Initalisierung der Buttons die gezeigt werden, wenn
	 * das korrekte Huetchen gewaehlt wurde.
	 * Weiterhin werden die Listener fuer die Buttons erstellt
	 */
	private void initButtons(){
		
		this.newGame = new ButtonLarge("noch eine spannende Runde?", new ChangeListener() {
			
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
					 newGame.setVisible(false);
					 exitGame.setVisible(false);
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
					 for(int i = 0; i < hatNumber; i++){
						 hats[i].setPosition(xCordHat[i], yCordHat);
						 hats[i].setFlinged(false);
					 }
					 generateHatNum();		
			}
		});
		
		this.exitGame = new ButtonLarge("nö, nicht noch einmal..", new ChangeListener() {
			
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
			}
			
		});

		
		this.newGame.setPosition(this.width / 2.0f - this.newGame.getWidth() / 2.0f, 
				this.height - 2 * this.newGame.getHeight() - this.labelWin.getHeight() + 80);
		this.exitGame.setPosition(this.width / 2.0f - this.exitGame.getWidth() / 2.0f, 
				this.newGame.getY() - this.newGame.getHeight());
		
		this.newGame.setVisible(false);
		this.exitGame.setVisible(false);
	}
	
	/**
	 * Alle Actor der Stage hinzufügen.
	 */
	private void addActorsToStage(){
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
		this.stage.addActor(this.labelWin);
		
		this.stage.addActor(this.newGame);
		this.stage.addActor(this.exitGame);
	}
	
	/**
	 * Initialisierung des GestureDetector fuer die Swipes und fuegt diesen und
	 * die Stage dem InputProcessor hinzu.
	 */
	private void initProcessorAndGestureDetector(){
		this.hatGestureDetector = new HatGestureDetector(new IOnDirection() {
			
			@Override
			public void onUp(final float actualX, final float actualY) {				
				if(!isPaused){
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
	
	/**
	 * Initialisierung der Loots.
	 */
	private void initLoots(){
		this.possibleWinLoots = new ArrayList<Loot>();
		
		this.possibleWinLoots.add(new ThimblerigLoot("Lolli", "für zwischendurch"
				, "noonespillow_Lollipop"));
		this.possibleWinLoots.add(new ThimblerigLoot("Zucker", "etwas süßes fürs Pferd"
				, "Sugar_cube"));
		this.possibleWinLoots.add(new ThimblerigLoot("Ballons", "eine Hand voll davon"
				, "Balionai"));
		this.possibleWinLoots.add(new ThimblerigLoot("Möhre", "eine gesunde Mahlzeit"
				, "2011-02-15_Cartoon_carrot"));
		this.possibleWinLoots.add(new ThimblerigLoot("MiniPferd", "ein putziges Spielzeug"
				, "WOOD_HORSE"));
		this.possibleWinLoots.add(new ThimblerigLoot("Hufeisen", "als Glücksbringer"
				, "horseshoe"));
		this.possibleWinLoots.add(new ThimblerigLoot("Hut", "eines frechen Koboldes"
				, "liftarn_Green_hat"));
		this.possibleWinLoots.add(new ThimblerigLoot("Heu", "kraftbringendes Futter"
				, "mcol_haystack"));
		
		isMinscored = false;
		isMidScored = false;
		isMaxScored = false;
	}
	
	/**
	 * Ermittlung aller bereits gewonnen Loots aus diesem Spiel die unter dem verwendeten
	 * Spielstand gesichert sind.
	 * @return Liste aller Thimblerig-Loots
	 */
	private List<? extends Loot> getThimblerigLoots(){
		return SaveGameManager.getLoadedGame().getSpecifiedLoot(ThimblerigLoot.class);
	}
	
	/**
	 * Ermittlung, ob eine der Scoregrenzen erreicht wurde. Wenn ja, dann wird der Reihe
	 * nach geprueft, ob die einzelnen Loots, die in diesem Spiel gewonnen werden
	 * koennen, bereits in der Loot-Galerie vorhanden sind. Wenn ja wird das Loot
	 * hinzugefuegt, sonst eine entsprechende Ausgabe erzeugt. Wurd der Maxscore erreicht
	 * kann eine Pferderasse gewonnen werden.
	 */
	private void checkPrize(){
		/**
		 * wurden indexOfLoot auf -2 gesetzt, dann darf entsprechend keine Ausgabe
		 * oder ein hinzufuegen eines Loots erfolgen
		 */
		int indexOfLoot = -2;
		int maxNumberOfLootsMinscore = (this.possibleWinLoots.size() / 2) - 1;
		switch(this.wins){
		case MINSCORE:
			if(!isMinscored){
				isMinscored = true;
				indexOfLoot = getLootIndexMinscore(maxNumberOfLootsMinscore);
			}
			break;
		case MIDSCORE:
			if(!isMidScored){
				isMidScored = true;
				indexOfLoot = getLootIndexMidscore(maxNumberOfLootsMinscore);		
			}
			break;
		case MAXSCORE:
			if(!isMaxScored){
				float probability = this.rnd.nextFloat();
				isMaxScored = true;
				indexOfLoot = -2;
				
				//eine 20%ige Wahrscheinlichkeit, dass eine Pferderasse gewonnen werden kann
				//das Pferd darf natuerlich noch nicht gewonnen worden sein
				if(probability <= 0.2f && !SaveGameManager.getLoadedGame().getSpecifiedLoot(RaceLoot.class).contains(new RaceLoot(HorseRace.SHETTI))){
					this.chest.addLootAndShowAchievment(new RaceLoot(HorseRace.SHETTI));
				}
				else{
					final Dialog d = new Dialog("Entwerder hattest du\nkein Glück,\noder du bist\nbereits "
							+ "im Besitz\neines Shettis.");
					d.addButton("na gut..", new ChangeListener(){
						@Override
						public void changed(final ChangeEvent event, final Actor actor) {
							if(!isPaused){
								d.setVisible(false);
							}
						}
					});
					d.setVisible(true);
					this.stage.addActor(d);
				}
				
			}
			break;
		default:
			break;
		}
		
		/**
		 * Wurde entweder der Minscore oder Midscore erreicht und ein entsprechendes
		 * Loot gewaehlt wurde, wird dieses in die Galeri eingefuegt.
		 * Sonst wurden alle Loots, die in diesem Spiel gewonnen werden konnten,
		 * bereits gewonnen und ein entsprechender Dialog erscheint
		 */
		if (indexOfLoot > -1){
			this.chest.addLootAndShowAchievment(this.possibleWinLoots.get(indexOfLoot));
		}
		else if(indexOfLoot == -1){
			final Dialog d = new Dialog("Bei dieser Scoregrenze\n kannst du leider\n"
					+ "nichts mehr gewinnen!");
			d.addButton("na gut...", new ChangeListener() {
				@Override
				public void changed(final ChangeEvent event, final Actor actor) {
					if(!isPaused){
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
	 * @param maxNumberOfLootsMinscore maximale Anzahl an Loots die fuer den Minscore 
	 * 			erreicht werden duerfen
	 * @return Index des Loots, welches gewonnen wurde. -1, sonst, wenn bereits
	 * 			maxNumberOfLootsMinscore erreicht wurde.
	 */
	private int getLootIndexMinscore(final int maxNumberOfLootsMinscore){
		int index = -1;
		for(int i = 0; i < maxNumberOfLootsMinscore; i++){
			if(this.justWonLoots != null){
				if(!this.justWonLoots.contains(this.possibleWinLoots.get(i))){
					index = i;
					break;
				}
			}
		}
		return index;
	}
	
	/**
	 * wurde der Midscore erreicht, kann hier nur (n / 2)-mal ein neues
	 * Loot gewonnen werden. n ist die Listengroesse der moeglichen Loots die in 
	 * diesem Spiel gewonnen werden koennen.
	 * @param maxNumberOfLootsMinscore maximale Anzahl an Loots die fuer den Minscore 
	 * 			erreicht werden duerfen
	 * @return Index des Loots, welches gewonnen wurde. -1, sonst, wenn bereits 
	 * 			maxNumberOfLootsMaxScore erreicht wurde.
	 */
	private int getLootIndexMidscore(final int maxNumberOfLootsMinscore){
		int index = -1;
		for(int i = maxNumberOfLootsMinscore; i < this.possibleWinLoots.size(); i++){
			if(this.justWonLoots != null){
				if(!this.justWonLoots.contains(this.possibleWinLoots.get(i))){
					index = i;
					break;
				}
			}
		}
		return index;
	}
	
	/**
	 * Generiert die ID des Hutes unter der das Pferd versteckt ist.
	 */
	private void generateHatNum(){
		this.rightNum = this.rnd.nextInt(hatNumber);
		this.hats[this.rightNum].setChoosed(true);
	}
	
	/**
	 * Abhaengig von der Pferderasse gibt es einen Hinweis an den/die Spieler/in.
	 * Dieser sieht so aus, dass der Hut, unter dem das Pferd versteckt ist, einmal
	 * kurz wackelt.
	 * @param delta 
	 */
	private void doHint(final float delta){
		if(this.jiggleCounter <= 0){
			jiggleCounterFlag = false;
			generateJiggleCounter(delta);
		}
		this.jiggleCounter--;
		jiggleHats();
	}
	
	/**
	 * Generiert einen Faktor um den jiggleCounter in Abhaengigkeit des delta's
	 * zu berechnen. Der Faktor liegt zwischen JIGGLETIMEMIN und JIGGLETIMEMAX. 
	 * Der Faktor kann auch als Zeitspanne in Sekunden gesehen werden. Nach Ablauf
	 * dieser Zeit bewegt sich der Hut einmal.
	 * @param delta 
	 */
	private void generateJiggleCounter(final float delta){
		if(!jiggleCounterFlag && Math.round(1/delta) > 0){
			int factor = this.rnd.nextInt(JIGGLETIMEMAX - JIGGLETIMEMIN)
					+ JIGGLETIMEMIN;
			this.jiggleCounter = factor * Math.round(1/delta);
			this.jiggleVal = this.jiggleCounter;
			jiggleCounterFlag = true;
		}
	}
	
	/**
	 * Den jeweiligen Hut unter dem das Pferd steckt wackeln lassen.
	 */
	private void jiggleHats(){
		if(this.jiggleCounter == this.jiggleVal){
			this.hats[this.rightNum].setRotation(10f);
		}
		else if(this.jiggleCounter == Math.round(this.jiggleVal * 0.96f)){
			this.hats[this.rightNum].setRotation(0f);
			this.horseNeighing.play(0.4f);
		}
		else if(this.jiggleCounter == Math.round(this.jiggleVal * 0.94f)){
			this.hats[this.rightNum].setRotation(10f);
		}
		else if(this.jiggleCounter == Math.round(this.jiggleVal * 0.92f)){
			this.hats[this.rightNum].setRotation(0f);
		}
	}
}
