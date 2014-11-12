package com.haw.projecthorse.level.game.parcours;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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

	public GameOperator(Stage stage, Viewport viewport, int width, int height) {
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
		
		// System.out.println in txt umleiten
		/*
		 * try { System.setOut(new PrintStream(new
		 * FileOutputStream("parcours_output.txt"))); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	public void update(float delta) {
		gameField.getPlayer().setAnimation(Direction.RIGHT, 0.2f);
		
		if(!(delta == 0)){
		gameField.actGameField(delta);

		updateGameObjects(delta);
		updateBackgroundObjects(delta);
		updateLootObjects(delta);
		checkPlayerConstraints(delta);
		}
		gameField.drawGameField();

	}

	private void checkPlayerConstraints(float delta) {
		if(player.getX() > gameField.getPlayersPointOfView()){
			player.setX(player.getX() - gameField.getGameSpeed());
		}
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

}
