package com.haw.projecthorse.level.game.puzzle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;

public class PuzzlePart extends Image {

	private int xPos, yPos;
	private Image image;
	private static Dialog replay;

	public PuzzlePart(Image im, int x, int y) {
		super();
		this.xPos = x;
		this.yPos = y;

		this.image = im;
		setSize();
		replay();
	}

	/**
	 * setzt die Größe(die Breite und die Weite) des PuzzleTeils
	 */
	private void setSize() {

		image.setSize(Puzzle.getPuzzleWidth(), Puzzle.getPuzzleHeight());
	}

	/**
	 * jeder Puzzle Teil reagiert auf ein Click, und bewegt sich, falls erlaubt,
	 * nach leere Stelle hier wird Anzahl der Schritte hochgezählt falls das
	 * PuzzleBild fertig ist, wird Overlay aufgerufen
	 * 
	 * @param image
	 */
	public static void addListener(final Image image) {

		image.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				/**
				 * hier ist das Spiel zu Ende
				 */
				if (image.getName() == "emptyImage") {

					if (Puzzle.check()) {

						image.setVisible(false);
						Puzzle.getMissingImage().setVisible(true);
						Puzzle.removeClickListener();
						ImageManager.win.play(0.9f);

						ImageManager
								.setLabelText("Anzahl deiner Schritte : \n     "
										+ String.valueOf(Counter.getCounter()));
						Counter.setCounter(0);
						ImageManager.getOverlay().showPopup(replay);

					}

				} else {

					Counter.setCounter(1);
					ImageManager.setLabelText("Anzahl: \n"
							+ String.valueOf(Counter.getCounter()));

					int lokX = (int) image.getX();
					int lokY = (int) image.getY();

					if (checkImage(lokX, lokY)) {

						ImageManager.swipe.play();
						image.setPosition(Puzzle.getEmptyImage().getX(), Puzzle
								.getEmptyImage().getY());

						Puzzle.getEmptyImage().setPosition(lokX, lokY);

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
		if ((yKoor - Puzzle.getPuzzleHeight() == Puzzle.getEmptyImage().getY() & xKoor == Puzzle
				.getEmptyImage().getX())
				|| (yKoor + Puzzle.getPuzzleHeight() == Puzzle.getEmptyImage()
						.getY() & xKoor == Puzzle.getEmptyImage().getX())
				|| (xKoor - Puzzle.getPuzzleWidth() == Puzzle.getEmptyImage()
						.getX() && yKoor == Puzzle.getEmptyImage().getY())
				|| (xKoor + Puzzle.getPuzzleWidth() == Puzzle.getEmptyImage()
						.getX() && yKoor == Puzzle.getEmptyImage().getY())) {
			return true;
		}
		return false;
	}

	/**
	 * Overlay mit zwei Buttons, fürs Weiterspielen oder Zurückgehen
	 */
	private void replay() {
		replay = new Dialog("Du hast gewonnen!!!\n Noch eine Runde?");

		replay.addButton("ja", new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateToLevel("Puzzle");
			}

		});

		replay.addButton("nein", new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
			}

		});

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
