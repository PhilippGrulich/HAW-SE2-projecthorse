package com.haw.projecthorse.level.game.puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.game.Game;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.level.util.uielements.ButtonOwnTextImage;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;
import com.haw.projecthorse.level.util.uielements.ButtonSmall.ButtonType;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;

/**
 * Die Klasse repräsentiert die Bilderauswahl vor dem Spielanfang.
 * @author Masha
 * @version 1.0
 */
public class PuzzleManager extends Game {

	private static Stage firststage;
	private static Stage secondstage;

	private int index;
	private static Label label;

	private List<Image> imagelist;
	private Map<String, TextureRegion> regionsmap;

	private static Music musik;

	static Sound swipe;
	static Sound win;
	static Sound click;

	private static boolean endgame;

	private ButtonOwnTextImage buttonOk;
	static int myWidth;
	static int myHeight;

	static int myXPos;
	static int myYPos;
	private static Dialog replay;

	/**
	 * Konstruktor.
	 */
	public PuzzleManager() {

		super();

		firststage = new Stage(getViewport());
		InputManager.addInputProcessor(new StageGestureDetector(firststage,
				false, ControlMode.HORIZONTAL));

		addBackround();

		regionsmap = AssetManager.getAllTextureRegions("puzzleImageManager");

		imagelist = new ArrayList<Image>();

		myWidth = (width / 4) * 3; // 540
		myHeight = (height / 4) * 3; // 960

		myXPos = (width - myWidth) / 2; // 90
		myYPos = (height - myHeight) / 5 + (height - myHeight) / 2; // 224

		AssetManager.loadSounds("puzzle");
		AssetManager.loadMusic("puzzle");

		musik = audioManager.getMusic("puzzle", "bett_pcm.wav");
		musik.play();
		musik.setLooping(true);

		swipe = audioManager.getSound("puzzle", "swipe.wav");
		win = audioManager.getSound("puzzle", "win.wav");
		click = audioManager.getSound("puzzle", "click.wav");
		replay();
		fillImagelist();
		index = imagelist.size() - 1;
		createButtons();
		addToStage();

		// puzzle stage
		secondstage = new Stage(getViewport());
		InputManager.addInputProcessor(secondstage);

		new PuzzlePlayer();
		addScore();

	}

	/**
	 * erstelle eine Label und füge die in die zweite Stage. um die Anzahl der
	 * Schritte beim puzzlen anzuzeigen
	 */
	public void addScore() {

		BitmapFont font;
		font = AssetManager.getTextFont(FontSize.FORTY);
		label = new Label("", new Label.LabelStyle(font, Color.MAGENTA));
		label.setBounds(30, 45, 30, 30);
		label.setPosition(myXPos, myYPos + myHeight + 30);
		addToStage(secondstage, label);

	}

	/**
	 * speichert alle Bilder puzzleImageManager in die imagelist.
	 */

	private void fillImagelist() {

		for (String str : regionsmap.keySet()) {
			Image im = new Image(regionsmap.get(str));
			im.setName(str);
			imagelist.add(im);
		}
	}

	/**
	 * erstelle drei Buttons(vor, zurück und ok) setze jeweils die Größe und die
	 * Position fest. srufe die Methode addListener(...) auf füge alle drei in
	 * die erste Stage
	 * 
	 */
	private void createButtons() {
		ButtonSmall buttonNext = new ButtonSmall(ButtonType.RIGHT);
		ButtonSmall buttonPrev = new ButtonSmall(ButtonType.LEFT);

		Drawable buttonImg = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "panel_brown"));
		this.buttonOk = new ButtonOwnTextImage("OK",

		new ImageTextButton.ImageTextButtonStyle(
				new TextButton.TextButtonStyle(buttonImg, buttonImg, buttonImg,
						AssetManager.getTextFont(FontSize.FORTY))));

		buttonOk.setHeight(80);
		buttonOk.setWidth(300);

		buttonNext.setPosition(width - buttonNext.getWidth() - 5, height / 2);
		buttonPrev.setPosition(5, height / 2);

		buttonOk.setPosition(myXPos + myWidth / 2 - buttonOk.getWidth() / 2,
				myYPos + myHeight + 5);
		blinc(buttonOk);
		buttonNext.setName("next");
		buttonPrev.setName("prev");
		buttonPrev.setName("ok");

		addListener(buttonNext, buttonOk, buttonPrev);

		firststage.addActor(buttonNext);
		firststage.addActor(buttonPrev);
		firststage.addActor(buttonOk);
	}

	/**
	 * rufe auf jedes Bild addListener(...) auf.
	 * 
	 * fügt alle Puzzlebilder an der Position(90,22) mit der Größe(540,960) in
	 * erste die Stage, keine Buttons
	 * 
	 * @param buttonname
	 */

	private void addToStage() {

		for (Image im : imagelist) {

			im.setWidth(myWidth);
			im.setHeight(myHeight);
			im.setPosition(myXPos, myYPos);
			addListener(im);
			im.toFront();
			firststage.addActor(im);

		}

	}

	/**
	 * SwipeListener für einzelne Bilder. damit man die einzelne Bilder
	 * wischen kann
	 * 
	 * @param im  
	 */
	private void addListener(final Image im) {

		im.addListener(new SwipeListener() {

			@Override
			public void swiped(final SwipeEvent event, final Actor actor) {
				switch (event.getDirection()) {
				case LEFT:
					changeImage(1);
					blinc(buttonOk);
					swipe.play();
					break;
				case RIGHT:
					changeImage(-1);
					blinc(buttonOk);
					swipe.play();
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * Listener für vor-und zurück-Button. damit man die einzelne Bilder aussuchen
	 * kann
	 * @param next Button zum nächsten Bild
	 * @param buttonok Button, um das aktuelle Bild auszuwählen
	 * @param prev Buttonum das vorherige Bild auszuwählen
	 */
	private void addListener(final ImageButton next,
			final ButtonOwnTextImage buttonok, final ImageButton prev) {

		next.addListener(new ClickListener() {

			@Override
			public void clicked(final InputEvent event, final float x,
					final float y) {
				click.play();
				changeImage(1);
				blinc(buttonok);
			}
		});
		buttonok.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x,
					final float y) {
				click.play();
				Puzzle.texture = regionsmap.get(imagelist.get(index).getName());
				next.setVisible(false);
				prev.setVisible(false);
				buttonok.setVisible(false);
				buttonok.removeListener(this);
				PuzzlePlayer.setActorSpeech("Es geht los!");
				createPuzzle();
			};
		});

		prev.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x,
					final float y) {
				click.play();
				changeImage(-1);
				blinc(buttonok);
			};
		});
	}

	/**
	 * erzeuge die Klasse Puzzle.
	 */
	public void createPuzzle() {
		new Puzzle(this, this.chest);
	}

	/**
	 * eine Hilfsmethode. die in addListener(...) benötigt wird, um das nächste
	 * bzw. das vorherige Bild zu holen
	 * 
	 * @param delta Index, nach dem unterschieden wird, ob das nächste oder vorherige Bild ausgewählt wurde
	 */
	private void changeImage(final int delta) {
		index = (index + delta) % imagelist.size();

		if (index < 0) {
			index = imagelist.size() - 1;
		}

		imagelist.get(index).toFront();
	}

	/**
	 * lade das Hintergrundbild und füge das in die erste Stage.
	 */
	private void addBackround() {

		Image backround = new Image(AssetManager.getTextureRegion("puzzle",
				"bilderrahmen-pferd"));
		backround.toBack();
		backround.setName("backround");

		firststage.addActor(backround);

	}

	/**
	 * füge actor in die stage.
	 * 
	 * @param stage Stage,in die Aktor hingefügt werden soll
	 * @param actor Actor 
	 */
	public static void addToStage(final Stage stage, final Actor actor) {
		stage.addActor(actor);
	}

	@Override
	protected void doRender(final float delta) {
		/**
		 * am Ende des Spiels wird endgame auf true gesetzt solange OkButton von
		 * ChestOverlay nicht gedrückt wurde ist delta>0
		 */
		if (isEndgame() && delta != 0) {
			overlay.showPopup(replay);
			setEndgame(false);
		}

		firststage.act();
		firststage.draw();

		secondstage.act();
		secondstage.draw();
	}

	@Override
	protected void doDispose() {
		musik.pause();
		firststage.dispose();
		secondstage.dispose();

	}

	@Override
	protected void doResize(final int width, final int height) {
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

	public static Stage getSecondstage() {
		return secondstage;
	}

	/**
	 * wird benötigt, um hochgezählte Anzahl der Schritte beim puzzlen
	 * anzuzeigen.
	 * 
	 * @param newText steht für die Anzahl der Schritte
	 */
	public void setLabelText(final String newText) {
		label.setText(newText);
	}

	/**
	 * simuliert ein einmaliges Blinken des ok_button's bei der Bilderauswahl.
	 * 
	 * @param button hier wird okButton übergeben
	 */
	private void blinc(final ButtonOwnTextImage button) {
		button.addAction(Actions.sequence(Actions.fadeIn(0.2f),
				Actions.fadeOut(0.2f), Actions.fadeIn(0.2f)));
	}

	/**
	 * Overlay mit zwei Buttons, fürs Weiterspielen oder Zurückgehen.
	 */
	private void replay() {
		replay = new Dialog("Du hast gewonnen!!!\n Noch eine Runde?");
		replay.addButton("ja", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				click.play();
				GameManagerFactory.getInstance().navigateToLevel("Puzzle");
			}

		});

		replay.addButton("nein", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				click.play();
				GameManagerFactory.getInstance().navigateBack();
			}

		});

	}

	public int getMyWidth() {
		return myWidth;
	}

	public int getMyHeight() {
		return myHeight;
	}

	public static int getMyXPos() {
		return myXPos;
	}

	public static int getMyYPos() {
		return myYPos;
	}

	public static Stage getFirststage() {
		return firststage;
	}

	public static Music getMusik() {
		return musik;
	}

	public Overlay getOverlay() {
		return overlay;
	}

	public static Dialog getReplay() {
		return replay;
	}

	public static boolean isEndgame() {
		return endgame;
	}

	public static void setEndgame(final boolean endgame) {
		PuzzleManager.endgame = endgame;
	}

}
