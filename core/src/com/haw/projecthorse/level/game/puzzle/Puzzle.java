package com.haw.projecthorse.level.game.puzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;

//className:"com.haw.projecthorse.level.menu.worldmap.WorldMap",
//className:"com.haw.projecthorse.level.game.puzzle.Puzzle"
//in assets/json/GameConfig.json Zeile 15 wieder ersetzen
//einkommentieren zeile 113 in level

public class Puzzle extends Level {

	private static Stage stage;
	private static Label label;

	private static int row;
	private static int col;

	private static int puzzleWidth;
	private static int puzzleHeight;

	private static int myWidth;
	private static int myHeight;

	private static PuzzlePart[][] partArr;
	private static Image[][] imageArr;

	private static Image missingImage;
	private static Image emptyImage;

	public Puzzle() {

		super();

		stage = new Stage(getViewport());
		InputManager.addInputProcessor(stage);

		addBackround();

		row = 3;
		col = 3;

		myWidth = (width / 4) * 3; // 540
		myHeight = (height / 4) * 3; // 960

		puzzleWidth = myWidth / col; // 270
		puzzleHeight = myHeight / row; // 480

		createEmptyImage();
		createImageArr();

		shuffle();
		addToStage();
		addScore();
		stage.addActor(label);

	}

	private void createImageArr() {

		TextureRegion puzzleTexReg = AssetManager.getTextureRegion("puzzle",
				"horse");

		TextureRegion[][] puzzleTexRegArrOrigin = puzzleTexReg.split(
				puzzleTexReg.getRegionWidth() / col,
				puzzleTexReg.getRegionHeight() / row);

		int zzCol = (int) (Math.random() * (col));
		int zzRow = (int) (Math.random() * (row));

		partArr = new PuzzlePart[row][col];
		imageArr = new Image[row][col];

		int x = (width - myWidth) / 2; // 90
		int y = (height - myHeight) / 3 + (height - myHeight) / 2; // 266

		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {

				int xPos = (j * puzzleWidth) + x;
				int yPos = ((col - (2 * i + 1) + i) * puzzleHeight) + y;

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

		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {

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
		while (count < 2) {
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {

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
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
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
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				partArr[i][j].getImage().clearListeners();
			}
		}

	}

	public static void addToStage(Actor actor) {
		stage.addActor(actor);
	}

	public void addScore() {
		BitmapFont font;
		font = AssetManager.getTextFont(FontSize.DREISSIG);
		System.out.println("ccc: " + Counter.getCounter());
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

	public static int getMyWidth() {
		return myWidth;
	}

	public static int getMyHeight() {
		return myHeight;
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
	
}