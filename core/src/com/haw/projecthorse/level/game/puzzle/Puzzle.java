package com.haw.projecthorse.level.game.puzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.game.Game;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;

//className:"com.haw.projecthorse.level.menu.worldmap.WorldMap",
//className:"com.haw.projecthorse.level.game.puzzle.Puzzle"
//in assets/json/GameConfig.json Zeile 15 wieder ersetzen
//einkommentieren zeile 113 in level

public class Puzzle extends Game {

	private static Stage stage;
	private static Label label;

	private static int puzzleWidth;
	private static int puzzleHeight;

	private static PuzzlePart[][] partArr;
	private static Image[][] imageArr;

	private static Image missingImage;
	private static Image emptyImage;
	static TextureRegion texture;
	static Sound swipe;
	static Sound win;
	private Music bett;
	private int SHUFFLE = 2;
	private static int COL = 3;
	private static int ROW = 3;

	public Puzzle() {

		super();

		stage = new Stage(getViewport());
		// InputManager.addInputProcessor(stage);
		InputManager.addInputProcessor(new StageGestureDetector(stage, false,
				ControlMode.HORIZONTAL));

		addBackround();

		addMusic();
		swipe = audioManager.getSound("puzzle","swipe.wav");
		win = Gdx.audio.newSound(Gdx.files.internal("sounds/puzzle/win.wav"));
		bett = Gdx.audio.newMusic(Gdx.files
				.internal("music/puzzle/bett_pcm.wav"));
		if (!bett.isPlaying()) {
			bett.setLooping(true);
			bett.play();
		}

		puzzleWidth = ImageManager.myWidth / COL; // 270
		puzzleHeight = ImageManager.myHeight / ROW; // 480

		createEmptyImage();
		createImageArr();

		shuffle();
		addToStage();
		addScore();

		stage.addActor(label);

	}

	/**
	 * splitte das gewählte Bild,
	 */
	private void createImageArr() {
		TextureRegion[][] puzzleTexRegArrOrigin = texture
				.split(texture.getRegionWidth() / COL,
						texture.getRegionHeight() / ROW);

		int zzCol = (int) (Math.random() * (COL));
		int zzRow = (int) (Math.random() * (ROW));

		partArr = new PuzzlePart[ROW][COL];
		imageArr = new Image[ROW][COL];

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {

				int xPos = (j * puzzleWidth) + ImageManager.myXPos;
				int yPos = ((COL - (2 * i + 1) + i) * puzzleHeight)
						+ ImageManager.myYPos;

				Image im = new Image(puzzleTexRegArrOrigin[i][j]);

				PuzzlePart puzzlePart = new PuzzlePart(im, xPos, yPos);

				if (i == zzRow && j == zzCol) {

					missingImage = im;
					missingImage.setVisible(false);
					missingImage.setPosition(xPos, yPos);

					stage.addActor(missingImage);

					emptyImage.setPosition(xPos, yPos);

					partArr[i][j] = puzzlePart;
					imageArr[i][j] = emptyImage;

				} else {
					im.setPosition(xPos, yPos);
					partArr[i][j] = puzzlePart;
					imageArr[i][j] = im;
				}
			}
		}
	}

	private void addToStage() {

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {

				Image im = imageArr[i][j];
				PuzzlePart.addListener(im);

				stage.addActor(im);

			}
		}
	}

	private void addBackround() {

		Image horse = new Image(AssetManager.getTextureRegion("puzzle",
				"bilderrahmen-pferd"));
		horse.toBack();
		horse.setName("backround");

		stage.addActor(horse);

	}

	private void shuffle() {
		int count = 0;
		while (count < SHUFFLE) {
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {

					// PuzzlePart part = partArr[i][j];

					// int xKoor = part.getXPos();
					// int yKoor = part.getYPos();
					Image im = imageArr[i][j];
					int xKoor = (int) im.getX();
					int yKoor = (int) im.getY();

					if (PuzzlePart.checkImage(xKoor, yKoor)) {

						imageArr[i][j].setPosition(emptyImage.getX(),
								emptyImage.getY());

						emptyImage.setPosition(xKoor, yKoor);
					}
				}
			}
			count += 1;
		}
		System.out.println();
	}

	public static boolean check() {
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				PuzzlePart part = partArr[i][j];
				Image image = part.getImage();
				if (part.getXPos() != image.getX()
						|| part.getYPos() != image.getY()) {
					return false;
				}
			}
		}
		return true;
	}

	private void createEmptyImage() {
		Pixmap pixel = new Pixmap(puzzleWidth, puzzleHeight, Format.RGB888);
		pixel.setColor(Color.MAGENTA);
		pixel.fill();
		emptyImage = new Image(new Texture(pixel));
		emptyImage.setSize(puzzleWidth, puzzleHeight);
		emptyImage.setName("emptyImage");
		pixel.dispose();
	}

	public static void removeClickListener() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				partArr[i][j].getImage().clearListeners();
			}
		}

	}

	public static void addToStage(Actor actor) {
		stage.addActor(actor);
	}

	/**
	 * Anzahl der Schritte ausgeben
	 */
	public void addScore() {
		BitmapFont font;
		font = AssetManager.getTextFont(FontSize.DREISSIG);
		label = new Label("Anzahl: " + String.valueOf(Counter.getCounter()),
				new Label.LabelStyle(font, Color.MAGENTA));
		label.setBounds(30, 45, 30, 30);

	}

	@Override
	protected void doRender(float delta) {
		stage.act();
		stage.draw();
	}

	@Override
	protected void doDispose() {
		stage.dispose();
		swipe.dispose();
		bett.dispose();
		win.dispose();

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

	public static int getPuzzleWidth() {
		return puzzleWidth;
	}

	public static int getPuzzleHeight() {
		return puzzleHeight;
	}

	public static Image getMissingImage() {
		return missingImage;
	}

	public static Image getEmptyImage() {
		return emptyImage;
	}

	public Label getLabel() {
		return label;
	}

	public static void setLabelText(String newText) {
		label.setText(newText);
	}

	private void addMusic() {

		;
		// bett.play();
		// swipe.play();
	}

}