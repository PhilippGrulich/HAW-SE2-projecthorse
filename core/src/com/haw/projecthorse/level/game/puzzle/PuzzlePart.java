package com.haw.projecthorse.level.game.puzzle;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PuzzlePart extends Image {

	private int xPos, yPos;
	private Image image;

	private static PuzzleManager puzzleManager;
	private static int score = 0;

	public PuzzlePart(Image im, int x, int y, PuzzleManager puzzleManager) {
		super();
		PuzzlePart.puzzleManager = puzzleManager;
		this.xPos = x;
		this.yPos = y;

		this.image = im;
		setSize();

	}

	/**
	 * setzt die Größe(die Breite und die Weite) des PuzzleTeils
	 */
	private void setSize() {

		image.setSize(Puzzle.getPuzzleWidth(), Puzzle.getPuzzleHeight());
	}

	/**
	 * jeder Puzzle Teil reagiert auf ein Click, und bewegt sich, falls erlaubt,
	 * nach leere Stelle hier wird die Anzahl der Schritte hochgezählt
	 * 
	 * @param image
	 */
	public static void addListener(final Image image) {

		image.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				Counter.setCounter(1);
				puzzleManager.setLabelText("Anzahl: "
						+ String.valueOf(Counter.getCounter()));

				int lokX = (int) image.getX();
				int lokY = (int) image.getY();

				if (checkImage(lokX, lokY)) {

					PuzzleManager.swipe.play();
					image.setPosition(Puzzle.getEmptyImage().getX(), Puzzle
							.getEmptyImage().getY());

					Puzzle.getEmptyImage().setPosition(lokX, lokY);
					/**
					 * hier ist das Spiel zu Ende das fehlende PuzzleTeil wird
					 * eingeblendet keine Teile werden ab hier auf Klick
					 * reagieren Anzahl der Schritte wird zwischengespeichert
					 * und wieder auf null gesetzt es wird eingeblende was der
					 * Spieler gewonnen hat
					 */
					if (Puzzle.check()) {
						Puzzle.getEmptyImage().setVisible(false);
						Puzzle.getMissingImage().setVisible(true);
						Puzzle.removeClickListener();
						PuzzleManager.win.play(0.9f);

						score = Counter.getCounter();

						PuzzlePlayer.setActorSpeech("Mit nur "
								+ String.valueOf(score) + " \nSchritten!");
						Counter.setCounter(0);
						Puzzle.getAndShowLoot(score);

					}

				}
			}

		});
	}

	/**
	 * prüfe ob das geklickte Bild eine leere Nachbarstelle hat
	 * 
	 * @param xKoor
	 * @param yKoor
	 * @return
	 */
	public static boolean checkImage(int xKoor, int yKoor) {

		if (yKoor - Puzzle.getPuzzleHeight() == Puzzle.getEmptyImage().getY()
				& xKoor == Puzzle.getEmptyImage().getX()) {
			return true;// "unten";
		} else if ((yKoor + Puzzle.getPuzzleHeight() == Puzzle.getEmptyImage()
				.getY() & xKoor == Puzzle.getEmptyImage().getX())) {
			return true;// "oben";
		} else if ((xKoor - Puzzle.getPuzzleWidth() == Puzzle.getEmptyImage()
				.getX() && yKoor == Puzzle.getEmptyImage().getY())) {
			return true;// "links";
		} else if ((xKoor + Puzzle.getPuzzleWidth() == Puzzle.getEmptyImage()
				.getX() && yKoor == Puzzle.getEmptyImage().getY())) {
			return true;// "rechts";

		} else {
			return false;// "";
		}

	}

	public Image getImage() {
		return image;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

}
