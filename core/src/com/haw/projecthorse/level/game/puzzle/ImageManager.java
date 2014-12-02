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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
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

public class ImageManager extends Game {

	private static Stage firststage;
	private static Stage secondstage;

	private int index;
	private static Label label;

	private List<Image> imagelist;
	private Map<String, TextureRegion> regionsmap;

	private static Music musik;

	static Sound swipe;
	static Sound win;

	static int myWidth;
	static int myHeight;

	static int myXPos;
	static int myYPos;
	
	private PuzzlePlayer puzzlePlayer;

	public ImageManager() {

		super();

		firststage = new Stage(getViewport());
		InputManager.addInputProcessor(new StageGestureDetector(firststage,
				false, ControlMode.HORIZONTAL));

		addBackround();

		puzzlePlayer=new PuzzlePlayer();

		regionsmap = AssetManager.getAllTextureRegions("puzzleImageManager");

		imagelist = new ArrayList<Image>();

		myWidth = (width / 4) * 3; // 540
		myHeight = (height / 4) * 3; // 960

		myXPos = (width - myWidth) / 2; // 90
		myYPos = (height - myHeight) / 5 + (height - myHeight) / 2; // 224

		System.out.println("meine koor: " + myXPos + myYPos);

		AssetManager.loadSounds("puzzle");
		AssetManager.loadMusic("puzzle");
		addMusic("bett_pcm.wav", true);
		swipe = audioManager.getSound("puzzle", "swipe.wav");
		win = audioManager.getSound("puzzle", "win.wav");

		fillImagelist();
		index = imagelist.size() - 1;
		createButtons();
		addToStage();

		// puzzle stage
		secondstage = new Stage(getViewport());
		InputManager.addInputProcessor(secondstage);

		addScore();

		//

	}

	public void addScore() {
		BitmapFont font;
		font = AssetManager.getTextFont(FontSize.VIERZIG);
		label = new Label("", new Label.LabelStyle(font, Color.MAGENTA));
		label.setBounds(30, 45, 30, 30);
label.setPosition(puzzlePlayer.getPlayer().getWidth()+30, 50);
		addToStage(secondstage, label);

	}

	/**
	 * speichert alle Bilder aus asset/pictures/puzzleImageManager in die
	 * imagelist
	 */

	private void fillImagelist() {

		for (String str : regionsmap.keySet()) {
			Image im = new Image(regionsmap.get(str));
			im.setName(str);
			imagelist.add(im);
		}
	}

	private void createButtons() {
		ButtonSmall button_next = new ButtonSmall(ButtonType.RIGHT);
		ButtonSmall button_prev = new ButtonSmall(ButtonType.LEFT);

		Drawable button_img = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "panel_brown"));
		ButtonOwnTextImage button_ok = new ButtonOwnTextImage("OK",

				new ImageTextButton.ImageTextButtonStyle(
						new TextButton.TextButtonStyle(button_img, button_img,
								button_img,
								AssetManager.getTextFont(FontSize.VIERZIG))));

		button_ok.setHeight(80);
		button_ok.setWidth(300);

		button_next.setPosition(width - button_next.getWidth() - 5, height / 2);
		button_prev.setPosition(5, height / 2);

		button_ok.setPosition(myXPos + myWidth / 2 - button_ok.getWidth() / 2,
				myYPos + myHeight + 5);

		button_next.setName("next");
		button_prev.setName("prev");
		button_prev.setName("ok");

		addListener(button_next, button_ok, button_prev);

		firststage.addActor(button_next);
		firststage.addActor(button_prev);
		firststage.addActor(button_ok);
	}

	/**
	 * fügt alle Puzzlebilder an der Position(90,22) mit der Größe(540,960) in
	 * die Stage, keine Buttons
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
	 * Listener für pre-und next-Button, damit man die einzelne Bilder aussuchen
	 * kann
	 * 
	 * @param button
	 */
	private void addListener(final Image im) {

		im.addListener(new SwipeListener() {

			@Override
			public void swiped(SwipeEvent event, Actor actor) {
				switch (event.getDirection()) {
				case LEFT:
					changeImage(1);
					break;
				case RIGHT:
					changeImage(-1);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * Listener für pre-und next-Button, damit man die einzelne Bilder aussuchen
	 * kann
	 * 
	 * @param button
	 */
	private void addListener(final ImageButton next,
			final ButtonOwnTextImage button_ok, final ImageButton prev) {

		next.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeImage(1);
			}
		});
		button_ok.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				Puzzle.texture = regionsmap.get(imagelist.get(index).getName());
				next.setVisible(false);
				prev.setVisible(false);
				button_ok.setVisible(false);
				button_ok.removeListener(this);
				new Puzzle();
				// firststage.dispose();

			};
		});

		prev.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeImage(-1);
			};
		});
	}

	private void changeImage(int delta) {
		index = (index + delta) % imagelist.size();

		if (index < 0)
			index = imagelist.size() - 1;

		imagelist.get(index).toFront();
	}

	private void addBackround() {

		Image horse = new Image(AssetManager.getTextureRegion("puzzle",
				"bilderrahmen-pferd"));
		horse.toBack();
		horse.setName("backround");

		firststage.addActor(horse);

	}

	public static void addToStage(Stage stage, Actor actor) {
		stage.addActor(actor);
	}

	@Override
	protected void doRender(float delta) {

		firststage.act();
		firststage.draw();

		secondstage.act();
		secondstage.draw();
	}

	@Override
	protected void doDispose() {
		musik.pause();
		// win.dispose();
		// swipe.dispose();
		firststage.dispose();
		secondstage.dispose();

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

	public static Stage getSecondstage() {
		return secondstage;
	}

	public static void setLabelText(String newText) {
		label.setText(newText);
	}

	private void addMusic(String musicname, boolean loop) {

		musik = audioManager.getMusic("puzzle", musicname);
		// musik.setPosition(musikpos);
		musik.play();
		musik.setLooping(loop);

	}

	public static int getMyWidth() {
		return myWidth;
	}

	public static int getMyHeight() {
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

	public static Overlay getOverlay() {
		return overlay;
	}
}
