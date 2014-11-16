package com.haw.projecthorse.level.game.parcours;


import java.util.List;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.player.Direction;

public class GameOperator {

	private GameField gameField;
	public static int maxVisibleBackgroundObjects;
	private Player player;
	boolean shouldPlayerJump;
	private int score;

	public GameOperator(Stage stage, Viewport viewport, int width, int height) {
		score = 0;
		maxVisibleBackgroundObjects = 4;
		gameField = new GameField(stage, viewport, width, height);
		// Reihenfolge der Methodenaufrufe bestimmt die z-Order in der Stage
		gameField.loadBackgroundObjects();
		gameField.loadGameObjects();
		gameField.initializeLootObjects();
		gameField.initializePlayer();
		player = gameField.getPlayer();
		shouldPlayerJump = false;

		// System.out.println in txt umleiten
		/*
		 * try { System.setOut(new PrintStream(new
		 * FileOutputStream("parcours_output.txt"))); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	public void update(float delta) {

		if (!(delta == 0)) {
			// gameField.actGameField(delta);

			updateGameObjects(delta);
			updateBackgroundObjects(delta);
			checkPlayerConstraints();
			collisionDetection();
			gameField.actGameField(delta);
		}
		gameField.drawGameField();

	}

	private void checkPlayerConstraints() {
		if (isPlayerJumping()) {
			handleJump();
		}
	}

	public void collisionDetection() {

		List<GameObject> objects = gameField.getGameObjects();
		objects.addAll(gameField.getLootObjects());
		for (GameObject l : objects) {
			if (l.getRectangle().overlaps(player.getRectangle())) {
				score += l.getPoints();
				gameField.setActorUnvisible(l);
				l.setX(-5 - l.getWidth());
				gameField.setScore(score);
			}
		}
	}

	public void handleJump() {
		Vector2 v = player.getNextJumpPosition();
		float x = 0;
		float y = 0;
		boolean outOfBound1 = !willPlayerBeLesserThanGround(v.y);
		boolean outOfBound2 = !willPlayerBeOutOfGameField(v.x);
		if (outOfBound2) {
			x = v.x;
		} else {
			x = player.getX();
			outOfBound1 = false;
		}

		if (outOfBound1) {
			y = v.y;
		} else {
			y = player.getY() - player.getJumpSpeed();

			if (y < gameField.getPlayerYDefault()) {
				y = gameField.getPlayerYDefault();
			}
		}

		player.moveBy(x, y);
		player.setPosition(x, y);

		if (player.getY() == gameField.getPlayerYDefault()) {
			setPlayerJump(false);
		}
	}

	private boolean willPlayerBeLesserThanGround(float y) {

		if (y < gameField.getPlayerYDefault()) {
			return true;
		}
		return false;
	}

	public boolean willPlayerBeOutOfGameField(float x) {
		if (player.getDirection() == Direction.RIGHT) {

			float positionOnRightJump = player.getX() + player.getWidth()
					+ (x - player.getX());

			if (positionOnRightJump > gameField.getWidth()) {
				return true;
			}

		} else {

			float positionOnLeftJump = 0;
			if (x < 0) {
				positionOnLeftJump = player.getX() + x;
			} else {
				positionOnLeftJump = (player.getX() - x);
			}

			if (positionOnLeftJump < 0) {
				return true;
			}
		}
		return false;
	}

	public void updateGameObjects(float delta) {

		List<GameObject> objects = gameField.getGameObjects();
		objects.addAll(gameField.getLootObjects());

		for (GameObject o : objects) {
			if (!o.isVisible() && outOfGameField(o)) {
				o.setVisible(true);
				o.setX(gameField.getRandomXForObjectOnTheGround(o));
			}
			if (o.isVisible() && outOfGameField(o)) {
				o.setVisible(false);
			}
			if (o.getX() + o.getWidth() == gameField.getObjectsOnGroundMaxPos()) {
				gameField.setObjectsOnGroundMaxPos(o.getX() - o.getDuration()
						* delta, o.getWidth());
			}

		}
	}

	public void updateBackgroundObjects(float delta) {

		List<BackgroundObject> objects = gameField.getBackgroundObjects();

		for (BackgroundObject b : objects) {
			if (!b.isVisible() && outOfGameField(b)) {
				b.setVisible(true);
				b.setPosition(gameField.getCloudsX(b), gameField.getCloudsY(b));
			}
			if (b.isVisible() && outOfGameField(b)) {
				b.setVisible(false);
			}
		}
	}

	private boolean outOfGameField(Actor o) {
		if (o.getX() + o.getWidth() < 0) {
			return true;
		}
		return false;
	}

	public void setPlayerJump(boolean a) {
		this.shouldPlayerJump = a;
	}

	public boolean isPlayerJumping() {
		return shouldPlayerJump;
	}
}
