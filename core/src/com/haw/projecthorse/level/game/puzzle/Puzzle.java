package com.haw.projecthorse.level.game.puzzle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.lootmanager.Chest;

public class Puzzle {

	private static int puzzleWidth;
	private static int puzzleHeight;

	private static PuzzlePart[][] partArr;
	private static Image[][] imageArr;

	private static Image missingImage;
	private static Image emptyImage;
	static TextureRegion texture;

	private static int SHUFFLE = 4;
	private static int COL = 3;
	private static int ROW = 3;
	private static PuzzleManager puzzleManager;
	private static Chest chest;

	public Puzzle(PuzzleManager puzzleManager, Chest chest2) {

		Puzzle.setPuzzleManager(puzzleManager);

		puzzleWidth = puzzleManager.getMyWidth() / COL; // 270
		puzzleHeight = puzzleManager.getMyHeight() / ROW; // 480

		Puzzle.setChest(chest2);

		createButtons();
		createEmptyImage();
		createImageArr();

		puzzleManager.setLabelText("Anzahl: "
				+ String.valueOf(Counter.getCounter()));

		shuffle();
		addPuzzlePartsToStage();
	}

	/**
	 * teile das gewählte Bild in COL*ROW gleichförmige Teile erstelle jeweils
	 * die neue Klasse PuzzlePart erstelle an einer zufälligen Stelle ein
	 * "leeres" Bild und merke das fehlende in der Instanzvariable missingImage
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

				PuzzlePart puzzlePart = new PuzzlePart(im, xPos, yPos,
						getPuzzleManager());

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

	/**
	 * rufe auf jeden Puzzleteil die Methode addListener(...) und füde den in
	 * die erste Stage
	 */
	private void addPuzzlePartsToStage() {

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {

				Image im = imageArr[i][j];
				PuzzlePart.addListener(im);

				PuzzleManager.addToStage(PuzzleManager.getSecondstage(), im);

			}
		}
	}

	/**
	 * vermische die Puzzleteile
	 */
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

	/**
	 * prüfe, ob alle Puzzleteile an den richtigen Stellen sind
	 * 
	 * @return
	 */
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

	/**
	 * erstelle ein leeres Bild, von der größe der Puzzleteils wird für das
	 * "leere" Bild benötigt
	 */
	private void createEmptyImage() {
		Pixmap pixel = new Pixmap(puzzleWidth, puzzleHeight, Format.RGB888);
		pixel.setColor(Color.MAGENTA);
		pixel.fill();
		emptyImage = new Image(new Texture(pixel));
		emptyImage.setSize(puzzleWidth, puzzleHeight);
		emptyImage.setName("emptyImage");
		pixel.dispose();
	}

	/**
	 * lösche für alle Puzzleteile die Clicklistener wird am Ende des Spiels
	 * aufgerufen
	 */
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

	/**
	 * erstelle ein Zurückbutton, um zurück zur Bilderauswahl gelangen zu können
	 * rufe die Methode addListener(...) auf, füge den Button in die zweite
	 * Stage
	 */
	private void createButtons() {
		ImageButton button_back = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "backIcon")));

		button_back.setHeight(95);
		button_back.setWidth(95);

		button_back.setPosition(PuzzleManager.getMyXPos()
				+ getPuzzleManager().getMyWidth() - button_back.getWidth(),
				PuzzleManager.getMyYPos() + getPuzzleManager().getMyHeight());

		addListener(button_back);

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), button_back);

	}

	/**
	 * füge ein ClickListener für Zurückbutton navigiere bei einem Klick zur
	 * Bilderauswahl zurück
	 * 
	 * @param back
	 */
	private void addListener(final ImageButton back) {

		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				PuzzleManager.click.play();
				GameManagerFactory.getInstance().navigateToLevel("Puzzle");
			};
		});

	}

	/**
	 * am Ende des Spiels je nach Anzahl der benötigten Schritte werden Loots
	 * vergeben, gespeichert und kurz angezeigt
	 * 
	 * @param score
	 */
	public static void getAndShowLoot(int score) {

		if (score <= SHUFFLE * 3) {
			getChest().addLoot(
					new PuzzleLoot("croissant",
							"Die beste Leckerei für dein Pferd", "croissant"));
		} else if (score > SHUFFLE * 3 && score <= SHUFFLE * 4) {
			getChest().addLoot(
					new PuzzleLoot("brezel", "Eine Brezel", "brezel"));
		} else {
			getChest().addLoot(
					new PuzzleLoot("brötchen", "Ein Brötchen", "buns"));
		}
		// chest.saveAllLoot();
		getChest().showAllLoot();
		Overlay overlay = getPuzzleManager().getOverlay();
		overlay.showPopup(PuzzleManager.getReplay());

	}

	public static PuzzleManager getPuzzleManager() {
		return puzzleManager;
	}

	public static void setPuzzleManager(PuzzleManager puzzleManager) {
		Puzzle.puzzleManager = puzzleManager;
	}

	public static Chest getChest() {
		return chest;
	}

	public static void setChest(Chest chest) {
		Puzzle.chest = chest;
	}

}