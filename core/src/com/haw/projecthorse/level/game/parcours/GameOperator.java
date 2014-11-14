package com.haw.projecthorse.level.game.parcours;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.player.Direction;

public class GameOperator {

	private GameField gameField;
	private Sanction sanction;
	private int maxVisibleLootObjects;
	private int maxVisibleGameObjects;
	public static int maxVisibleBackgroundObjects;
	private Player player;
	float epsilon;
	boolean shouldPlayerJump;
	float tmpPlayerY;
	private int score;

	public GameOperator(Stage stage, Viewport viewport, int width, int height) {
		score = 0;
		maxVisibleLootObjects = 3;
		maxVisibleGameObjects = 2;
		maxVisibleBackgroundObjects = 4;
		epsilon = 0.01f; // 0.0001f zu klein
		gameField = new GameField(stage, viewport, width, height);
		// Reihenfolge der Methodenaufrufe bestimmt die z-Order in der Stage
		gameField.loadBackgroundObjects();
		gameField.loadGameObjects();
		gameField.initializeLootObjects();
		gameField.initializePlayer();
		player = gameField.getPlayer();
		shouldPlayerJump = false;
		tmpPlayerY = 0;

		// System.out.println in txt umleiten
		/*
		 * try { System.setOut(new PrintStream(new
		 * FileOutputStream("parcours_output.txt"))); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	public void update(float delta) {
		// gameField.getPlayer().setAnimation(Direction.RIGHT, 0.2f);

		if (!(delta == 0)) {
			gameField.actGameField(delta);

			updateGameObjects(delta);
			updateBackgroundObjects(delta);
			updateLootObjects(delta);
			checkPlayerConstraints(delta);
			collisionDetection();
		}
		gameField.drawGameField();

	}

	private void checkPlayerConstraints(float delta) {
		// System.out.println("player jump  " + shouldPlayerJump);
		// Zurück auf Ursprungsposition bewegen
		if (!isPlayerJumping()) {
			if (player.getX() > gameField.getPlayersPointOfView()) {
				player.setX(player.getX() - gameField.getGameSpeed());
			}
		} else {
			handleJump();

		}

	}

	public void collisionDetection() {
		for (LootObject l : gameField.getLootObjects()) {
			if (l.getRectangle().overlaps(player.getRectangle())) {
				gameField.setActorUnvisible(l);
				score += l.getPoints();
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
			float positionOnLeftJump = player.getX() + player.getWidth()
					- x;
			
			float a = (player.getX() + player.getWidth() - (x - player.getX())); //garnicht
			float b = (player.getX() - player.getWidth() - (x - player.getX())); //zu früh
			float c = (player.getX() - player.getWidth() - (x + player.getX())); //garnicht
			float d = (player.getX() + player.getWidth() - (x + player.getX())); //garnicht
			float e = (player.getX() -player.getWidth() + (x - player.getX())); //garnicht
			
			float f = 0;
			if(x < 0){
				f = player.getX() + x;
			}else {
				f = (player.getX() - x);
			}
			
			
			
			
			if (f < 0) {
				return true;
			}
		}
		return false;
	}

	public void updateGameObjects(float delta) {
		List<GameObject> objects = gameField.getGameObjects();
		BigDecimal x, width, b_delta;
		b_delta = new BigDecimal(delta).setScale(4, RoundingMode.HALF_UP);

		// Wenn Objekt ausserhalb d. Spielfelds -> unsichtbar setzen. Sonst
		// bewegen.
		for (GameObject o : objects) {
			if (o.isVisible() && outOfGameField(o)) {
				gameField.setActorUnvisible(o);
			} else if (o.isVisible()) {

				x = new BigDecimal(o.getX() - o.getDuration()).setScale(4,
						RoundingMode.HALF_UP);
				o.setX(x.floatValue());

			}
		}

		// Pruefen, ob maxPos angepasst werden muss
		BigDecimal oldPos;
		for (GameObject o : objects) {
			oldPos = new BigDecimal(o.getX() + o.getWidth() + o.getDuration()
					+ o.getDuration() * b_delta.floatValue()).setScale(5,
					RoundingMode.HALF_UP);
			if (o.isVisible()
					&& oldPos.floatValue()
							- gameField.getObjectsOnGroundMaxPos() < epsilon
					&& gameField.getObjectsOnGroundMaxPos()
							- oldPos.floatValue() < epsilon) {
				x = new BigDecimal(o.getX()).setScale(4, RoundingMode.HALF_UP);
				width = new BigDecimal(o.getWidth()).setScale(4,
						RoundingMode.HALF_UP);
				gameField.setObjectsOnGroundMaxPos(x.floatValue(),
						width.floatValue());
			}
		}

		// Wenn weniger sichtbare Objekte auf d. Spielfeld als erlaubt -> neue
		// Objekte setzen,
		// sofern vorhanden.
		if (gameField.visibleGameObjects < maxVisibleGameObjects) {
			while (gameField.visibleGameObjects < maxVisibleGameObjects
					&& objects.size() - gameField.visibleGameObjects > 0) {
				int listPos = gameField.getRandomActorPosition(objects.get(0));
				if (!objects.get(listPos).isVisible()) {
					gameField.setActorVisbile(objects.get(listPos), gameField
							.getRandomXForObjectOnTheGround(objects
									.get(listPos)));
				}

			}
		}
	}

	public void updateBackgroundObjects(float delta) {
		List<BackgroundObject> objects = gameField.getBackgroundObjects();
		BigDecimal x;
		// Wenn Objekt ausserhalb d. Spielfelds -> unsichtbar setzen. Sonst
		// bewegen.
		for (BackgroundObject o : objects) {
			if (o.isVisible() && outOfGameField(o)) {
				gameField.setActorCloudObjectUnvisible(o);
			} else if (o.isVisible()) {
				x = new BigDecimal(o.getX() - o.getDuration()).setScale(4,
						RoundingMode.HALF_UP);
				o.setX(x.floatValue());

			}
		}
		// Wenn weniger sichtbare Objekte auf d. Spielfeld als erlaubt -> neue
		// Objekte setzen,
		// sofern vorhanden.
		if (gameField.visibleCloudObjects < maxVisibleBackgroundObjects) {
			while (gameField.visibleCloudObjects < maxVisibleBackgroundObjects
					&& objects.size() - gameField.visibleCloudObjects > 0) {
				int listPos = gameField.getRandomActorPosition(objects.get(0));
				if (!objects.get(listPos).isVisible()) {
					gameField.setActorCloudObjectVisible(objects.get(listPos),
							gameField.getCloudsX(objects.get(listPos)),
							gameField.getCloudsY(objects.get(listPos)));
				}
			}
		}
	}

	public void updateLootObjects(float delta) {
		List<LootObject> objects = gameField.getLootObjects();
		BigDecimal x, width, b_delta;
		b_delta = new BigDecimal(delta).setScale(4, RoundingMode.HALF_UP);
		// Wenn Objekt ausserhalb d. Spielfelds -> unsichtbar setzen. Sonst
		// bewegen.
		for (LootObject o : objects) {
			if (o.isVisible() && outOfGameField(o)) {
				gameField.setActorUnvisible(o);
			} else if (o.isVisible()) {
				x = new BigDecimal(o.getX() - o.getDuration()).setScale(4,
						RoundingMode.HALF_UP);
				o.setX(x.floatValue());
			}
		}

		// Pruefen, ob maxPos angepasst werden muss
		BigDecimal oldPos;
		for (LootObject o : objects) {
			oldPos = new BigDecimal(o.getX() + o.getWidth() + o.getDuration()
					+ o.getDuration() * b_delta.floatValue()).setScale(5,
					RoundingMode.HALF_UP);
			if (o.isVisible()
					&& oldPos.floatValue()
							- gameField.getObjectsOnGroundMaxPos() < epsilon
					&& gameField.getObjectsOnGroundMaxPos()
							- oldPos.floatValue() < epsilon) {
				x = new BigDecimal(o.getX()).setScale(4, RoundingMode.HALF_UP);
				width = new BigDecimal(o.getWidth()).setScale(4,
						RoundingMode.HALF_UP);
				gameField.setObjectsOnGroundMaxPos(x.floatValue(),
						width.floatValue());
			}
		}

		// Wenn weniger sichtbare Objekte auf d. Spielfeld als erlaubt -> neue
		// Objekte setzen,
		// sofern vorhanden.
		if (gameField.visibleLootObjects < maxVisibleLootObjects) {
			while (gameField.visibleLootObjects < maxVisibleLootObjects
					&& objects.size() - gameField.visibleLootObjects > 0) {
				int listPos = gameField.getRandomActorPosition(objects.get(0));
				if (!objects.get(listPos).isVisible()) {
					gameField.setActorVisbile(objects.get(listPos), gameField
							.getRandomXForObjectOnTheGround(objects
									.get(listPos)));
				}

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
