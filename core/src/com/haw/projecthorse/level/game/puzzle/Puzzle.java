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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

//className:"com.haw.projecthorse.level.menu.worldmap.WorldMap",
//className:"com.haw.projecthorse.level.game.puzzle.Puzzle"
//in assets/json/GameConfig.json Zeile 15 wieder ersetzen
//einkommentieren zeile 113 in level

public class Puzzle {

	private static int puzzleWidth;
	private static int puzzleHeight;

	private static PuzzlePart[][] partArr;
	private static Image[][] imageArr;

	private static Image missingImage;
	private static Image emptyImage;
	static TextureRegion texture;

	private int SHUFFLE = 2;
	private static int COL = 3;
	private static int ROW = 3;

	public Puzzle() {

		puzzleWidth = PuzzleManager.myWidth / COL; // 270
		puzzleHeight = PuzzleManager.myHeight / ROW; // 480

		createButtons();
		createEmptyImage();
		createImageArr();

		PuzzleManager.setLabelText("Anzahl: "
				+ String.valueOf(Counter.getCounter()));

		shuffle();
		addPuzzlePartsToStage();
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

				int xPos = (j * puzzleWidth) + PuzzleManager.myXPos;
				int yPos = ((COL - (2 * i + 1) + i) * puzzleHeight)
						+ PuzzleManager.myYPos;

				Image im = new Image(puzzleTexRegArrOrigin[i][j]);

				PuzzlePart puzzlePart = new PuzzlePart(im, xPos, yPos);

				if (i == zzRow && j == zzCol) {

					missingImage = im;
					missingImage.setVisible(false);
					missingImage.setPosition(xPos, yPos);

					PuzzleManager.addToStage(PuzzleManager.getSecondstage(),
							missingImage);

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

	private void addPuzzlePartsToStage() {

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {

				Image im = imageArr[i][j];
				PuzzlePart.addListener(im);

				PuzzleManager.addToStage(PuzzleManager.getSecondstage(), im);

			}
		}
	}

	private void shuffle() {
		int count = 0;
		while (count < SHUFFLE) {
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {

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

	// falls ich zurück zur bilderauswahl möchte
	// GameManagerFactory.getInstance().navigateToLevel("puzzleGame");
	private void createButtons() {
		ImageButton button_back = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "backIcon")));

		button_back.setHeight(95);
		button_back.setWidth(95);

		button_back.setPosition(
				PuzzleManager.getMyXPos() + PuzzleManager.getMyWidth()
						- button_back.getWidth(), PuzzleManager.getMyYPos()
						+ PuzzleManager.getMyHeight());
		// button_back.setPosition(0, 0);
		addListener(button_back);

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), button_back);

	}

	private void addListener(final ImageButton back) {

		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				PuzzleManager.click.play();
				GameManagerFactory.getInstance().navigateToLevel("Puzzle");
			};
		});

	}

}