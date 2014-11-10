package com.haw.projecthorse.level.game.puzzle;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PuzzlePart extends Image {

	private int xPos, yPos;
	private Image image;

	public PuzzlePart(Image im, int x, int y) {
		super();
		this.xPos = x;
		this.yPos = y;
		this.image = im;
		setSize();
	}

	private void setSize() {

		image.setSize(Puzzle.getPuzzleWidth(), Puzzle.getPuzzleHeight());
	}

	public static void addListener(final Image image) {

		image.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (image.getName() == "emptyImage") {

					if (Puzzle.check()) {
						System.out.println("Fertig");
						image.setVisible(false);
						Puzzle.getMissingImage().setVisible(true);
						Puzzle.removeClickListener();
					}

				} else {

					Counter.setCounter(1);
					Puzzle.setLabelText("Anzahl: "
							+ String.valueOf(Counter.getCounter()));

					int lokX = (int) image.getX();
					int lokY = (int) image.getY();

					if (checkImage(lokX, lokY)) {

						image.setPosition(Puzzle.getEmptyImage().getX(), Puzzle
								.getEmptyImage().getY());

						Puzzle.getEmptyImage().setPosition(lokX, lokY);

					}
				}
			}
		});
	}

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
