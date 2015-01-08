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
import com.haw.projecthorse.lootmanager.Chest;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.RaceLoot;
import com.haw.projecthorse.savegame.SaveGameManager;

/**
 * Hier beginnt das eigentliche Puzzlespiel.
 * das gewählte Bild wird aufgeteilt, vermischt und PuzzlePart erstellt,
 * hier wird auch geprüft, ob das Bild fertig gepuzzelt wurde.
 * @author Masha
 * @version 1.0
 */
public class Puzzle {

	private static int puzzleWidth;
	private static int puzzleHeight;

	private static PuzzlePart[][] partArr;
	private static Image[][] imageArr;

	private static Image missingImage;
	private static Image emptyImage;
	static TextureRegion texture;

	final static int SHUFFLE = 2;
	final static int COL = 3;
	final static int ROW = 3;
	private static PuzzleManager puzzleManager;
	private static Chest chest;

	/**
	 * Konstruktor.
	 * 
	 * @param puzzleManager2 Instanz der Klasse PuzzleManager
	 * @param chest2 Instanz der Klasse Chest
	 */
	protected Puzzle(final PuzzleManager puzzleManager2, final Chest chest2) {
		Puzzle.setPuzzleManager(puzzleManager2);

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
	 * teile das gewählte Bild in COL*ROW gleichförmige Teile. erstelle jeweils
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
				im.setName("");
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
	 * rufe auf jeden Puzzleteil die Methode addListener(...). füde den in die
	 * erste Stage
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
	 * vermische die Puzzleteile.
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
	 * prüfe, ob alle Puzzleteile an den richtigen Stellen sind.
	 * 
	 * @return boolean
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
	 * erstelle ein leeres Bild. von der größe der Puzzleteils wird für das
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
	 * lösche für alle Puzzleteile die Clicklistener. wird am Ende des Spiels
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
	 * erstelle ein Zurückbutton. um zurück zur Bilderauswahl gelangen zu können
	 * rufe die Methode addListener(...) auf, füge den Button in die zweite
	 * Stage
	 */
	private void createButtons() {
		ImageButton buttonBack = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "backIcon")));

		buttonBack.setHeight(95);
		buttonBack.setWidth(95);

		buttonBack.setPosition(PuzzleManager.getMyXPos()
				+ getPuzzleManager().getMyWidth() - buttonBack.getWidth(),
				PuzzleManager.getMyYPos() + getPuzzleManager().getMyHeight());

		addListener(buttonBack);

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), buttonBack);

	}

	/**
	 * füge ein ClickListener für Zurückbutton. navigiere bei einem Klick zur
	 * Bilderauswahl zurück
	 * 
	 * @param back Button
	 */
	private void addListener(final ImageButton back) {

		back.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x,
					final float y) {
				PuzzleManager.click.play();
				Counter.setCounter(0);
				GameManagerFactory.getInstance().navigateToLevel("Puzzle");
			};
		});

	}

	/**
	 * am Ende des Spiels. je nach Anzahl der benötigten Schritte werden Loots
	 * vergeben, gespeichert und kurz angezeigt
	 * 
	 * @param score Anzahl der benötigte Schritte
	 */
	public static void getAndShowLoot(final int score) {

		double roll = Math.random();
		if (roll <= 0.2f
				&& !SaveGameManager.getLoadedGame()
						.getSpecifiedLoot(RaceLoot.class)
						.contains(HorseRace.FRIESE)) {
			getChest().addLootAndShowAchievment(new RaceLoot(HorseRace.FRIESE));
		}

		if (score <= SHUFFLE * 3) {
			getChest().addLootAndShowAchievment(
					new PuzzleLoot("croissant",
							"Die beste Leckerei für dein Pferd", "croissant"));
		} else if (score > SHUFFLE * 3 && score <= SHUFFLE * 4) {
			getChest().addLootAndShowAchievment(
					new PuzzleLoot("brezel", "Eine leckere Brezel", "brezel"));
		} else {
			getChest().addLootAndShowAchievment(
					new PuzzleLoot("brötchen", "Leckere Brötchen", "buns"));
		}
		getChest().showAllLoot();
		getChest().saveAllLoot();
		PuzzleManager.setEndgame(true);
	}

	public static PuzzleManager getPuzzleManager() {
		return puzzleManager;
	}

	public static void setPuzzleManager(final PuzzleManager puzzleManager) {
		Puzzle.puzzleManager = puzzleManager;
	}

	public static Chest getChest() {
		return chest;
	}

	public static void setChest(final Chest chest) {
		Puzzle.chest = chest;
	}

}
