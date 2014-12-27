package com.haw.projecthorse.level.game.parcours;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;

public class GameObjectLogic implements IGameObjectLogicFuerGameOperator,
		IGameObjectLogicFuerGameInputListener {

	private float freePosition; // Position des am weitesten rechts befindlichen
								// GameObjects
	private IGameFieldFuerGameObjectLogic gameField;
	private boolean shouldPlayerJump;
	private float lastPosStored;
	private float lastPositionTmp;
	private Random randomGenerator;
	private boolean greeting;

	public GameObjectLogic(float initialFreePosition,
			IGameFieldFuerGameObjectLogic g) {
		freePosition = initialFreePosition;
		shouldPlayerJump = false;
		gameField = g;
		lastPosStored = gameField.getWidth();
		randomGenerator = new Random();
		greeting = true;
	}

	/**
	 * Prüft ob das Pferd springt und ruft die Methode auf, die die nächset
	 * Position des Pferds berechnet, sollte es springen.
	 */
	private void checkPlayerConstraints() {
		if (isPlayerJumping()) {
			handleJump();
		}
	}

	/**
	 * Prüft für alle GameObjects mit denen das Pferd kollidieren kann, ob es
	 * mit ihnen kollidiert ist und passt den Punktestand entsprechend an.
	 */
	public void collisionDetection() {
		for (Actor l : gameField.getActors()) {
			if (l instanceof CollidableGameObject) {
				if (((CollidableGameObject) l).getRectangle().overlaps(
						gameField.getPlayer().getRectangle())) {
					((CollidableGameObject) l).remove();
					((CollidableGameObject) l).setX(-5
							- ((CollidableGameObject) l).getWidth());
					gameField.passBack((CollidableGameObject) l);
					if (((CollidableGameObject) l).getPoints() > 0) {
						gameField.eat();
						gameField.addToScore(((CollidableGameObject) l)
								.getPoints()
								+ (int) Math.round(((CollidableGameObject) l)
										.getPoints()
										* gameField.getPlayer()
												.getIntelligence()));
					} else {
						gameField.addToScore(((CollidableGameObject) l)
								.getPoints());
					}

				}
			}
		}
	}

	/**
	 * Liefert die Position auf die als nächstes ein GameObject gesetzt werden
	 * kann.
	 * 
	 * @return freePosition Die Position auf die als nächstes ein GameObject
	 *         gesetzt werden kann.
	 */
	public float getFreePosition() {
		return freePosition;
	}

	/**
	 * Liefert ein Intervall aus dem eine zufällige Zahl gewählt wird, die auf
	 * die freePosition addiert wird, damit GameObjects nicht unmittelbar
	 * hintereinander gesetzt werden.
	 * 
	 * @return intervall[2] Das Intervall.
	 */
	private float[] getInterval() {
		float[] f = new float[2];

		if (getFreePosition() >= gameField.getWidth()) {
			f[0] = gameField.getPlayer().getWidth() * 120 / 100;
			f[1] = gameField.getPlayer().getWidth() * 150 / 100;
		} else {
			f[0] = (gameField.getWidth() - getFreePosition());
			f[1] = f[0] + gameField.getPlayer().getWidth() * 120 / 100;
		}

		return f;
	}

	/**
	 * Liefert in Abhängigkeit des übergebenen Intervalls und der freePosition
	 * die x-Koordinate auf die ein GameObject gesetzt werden kann. Passt den
	 * freePosition Wert an, wenn mit dem GameObject kollidiert werden kann, da
	 * diese Methode auch von GameObjects verwendet wird, die sich bewegen u.
	 * nicht überschneiden sollen ober mit denen nicht kollidiert werden kann
	 * (wie z.B. Wolken).
	 * 
	 * @param interval
	 *            Das Intervall von getIntervall()
	 * @param gameObjectWidth
	 *            Die Breite des GameObject
	 * @param colidable
	 *            true, wenn man mit dem GameObject kollidieren kann.
	 * @return x Die berechnete x-Koordinate.
	 */
	public float getRandomCoordinate(float[] interval, float gameObjectWidth,
			boolean colidable) {
		float rand = (float) Math.floor(Math.random()
				* (interval[1] - interval[0]) + interval[0]);
		float result = freePosition + rand;

		if (colidable)
			freePosition = result + gameObjectWidth;

		return result;
	}

	/**
	 * Prüft ob das Pferd beim Sprung außerhalb des linken bzw. rechten
	 * Spielfeldrands springen würde und liefert bewegt das Pferd so, dass dies
	 * nicht geschehen kann.
	 */
	public void handleJump() {
		Vector2 v = gameField.getPlayer().getNextJumpPosition();
		float x = 0;
		float y = 0;
		boolean outOfBound1 = !willPlayerBeLesserThanGround(v.y);
		boolean outOfBound2;
		if (gameField.getPlayer().getJumpDirection() == Direction.RIGHT) {
			outOfBound2 = willPlayerBeOutOfGameField(v.x
					+ gameField.getPlayer().getWidth());
		} else {
			outOfBound2 = willPlayerBeOutOfGameField(v.x);
		}

		if (!outOfBound2) {
			x = v.x;
		} else {
			x = gameField.getPlayer().getX();
			outOfBound1 = false;
		}

		if (outOfBound1) {
			y = v.y;
		} else {
			y = gameField.getPlayer().getY()
					- gameField.getPlayer().getJumpSpeed();

			if (y < gameField.getTopOfGroundPosition() - 25) {
				y = gameField.getTopOfGroundPosition() - 25;
			}
		}

		gameField.getPlayer().moveBy(x, y);
		gameField.getPlayer().setPosition(x, y);

		if (gameField.getPlayer().getY() == gameField.getTopOfGroundPosition() - 25) {
			setPlayerJump(false);
		}
	}

	public boolean isPlayerJumping() {
		return shouldPlayerJump;
	}

	/**
	 * 
	 * @param o
	 *            In der Stage befindlicher Actor.
	 * @return true, wenn der Actor vollständig aus dem linken Spielfeldrand
	 *         ist.
	 */
	private boolean outOfGameField(Actor o) {
		if (o.getX() + o.getWidth() < 0) {
			return true;
		}
		return false;
	}

	/**
	 * Setzt die Freiposition freePosition.
	 * 
	 * @param f
	 *            x-Koordinate auf ab der sich kein GameObject mehr befindet.
	 */
	public void setFreePosition(float f) {
		freePosition = f;
	}

	public void setPlayerJump(boolean a) {
		if (a) {
			gameField.pauseGallop();
		} else {
			gameField.playGallop();
		}
		this.shouldPlayerJump = a;
	}

	public void update(float delta) {
			updateGameObjects(delta);
			updateAccelometer(delta);
			checkPlayerConstraints();
			collisionDetection();
			gameField.actGameField(delta);
			gameField.drawGameField();
	}

	/**
	 * Abfrage von Neigung des Devices und Setzen von Player-Position. Da
	 * Parcours im Landscape-Modus läuft: Abfrage von Y (Intervall [-10,10].
	 * Alles über 4 -> uninteressant).
	 */
	private void updateAccelometer(float delta) {
		// nur wenn das Accelerometer activiert ist wird es auch genutzt

		if (GameManagerFactory.getInstance().getSettings()
				.getAccelerometerState()) {
			float y = Gdx.input.getAccelerometerY();
			if (y >= 2.5f)
				movePlayerR(delta, y);

			if (y <= -2.5f)
				movePlayerL(delta, y * (-1));
		}
	}

	public void movePlayerR(float delta, float y) {
		float x = gameField.getPlayer().getX()
				+ gameField.getPlayer().getWidth()
				+ gameField.getGeneralGameSpeed() * delta * (y / 2.0f);

		gameField.getPlayer().setJumpDirection(Direction.RIGHT);

		if (willPlayerBeOutOfGameField(x)) {
			gameField.getPlayer().shouldMove(0, 0);
		} else {
			gameField.getPlayer().shouldMove(1, y / 2.0f);
		}

		gameField.getPlayer().addAction(new AnimationAction(Direction.RIGHT));

	}

	public void movePlayerL(float delta, float y) {
		float x = gameField.getPlayer().getX()
				- gameField.getGeneralGameSpeed() * delta * (y / 2.0f);

		gameField.getPlayer().setJumpDirection(Direction.LEFT);

		if (willPlayerBeOutOfGameField(x)) {
			gameField.getPlayer().shouldMove(0, 0);
		} else {
			gameField.getPlayer().shouldMove(2, y / 2.0f);
		}

		gameField.getPlayer().addAction(new AnimationAction(Direction.LEFT));

	}

	/**
	 * Setzt GameObjects die vollständig außerhalb des Spielfelds sind auf
	 * unvisible, setzt GameObjects die unvisible und vollständig außerhalb des
	 * Spielfelds sind auf eine neue, freie Position und veranlasst genau das
	 * GameObject, welches das letzte - also am weitesten rechts befindliche-
	 * GameObject ist, die freePosition auf seine neue Position anzupassen.
	 * 
	 * @param delta
	 *            Die Zeit in Sekunden, die seit dem letzten Frame vergangen
	 *            ist.
	 */
	public void updateGameObjects(float delta) {
		boolean posAssigned = false;
		for (Actor a : gameField.getActors()) {
			if (a instanceof CollidableGameObject
					&& getRightBottomCorner(a) > 0) {
				if (getRightBottomCorner(a) > gameField.getWidth()
						- getRandomMargin()) {
					posAssigned = true;
				}
			} else if (a instanceof CollidableGameObject
					&& getRightBottomCorner(a) <= 0) {
				((CollidableGameObject) a).remove();
				((CollidableGameObject) a).setX(-10000);
				gameField.passBack((CollidableGameObject) a);
			}
		}

		if (!posAssigned) {
			CollidableGameObject co = gameField.getRandomObject();
			co.setX(gameField.getWidth() + getRandomMargin());
			gameField.addCollidableGameObject(co);
		}
	}

	public float getRightBottomCorner(Actor a) {
		if (a instanceof CollidableGameObject) {
			return ((CollidableGameObject) a).getX()
					+ ((CollidableGameObject) a).getWidth();
		} else if (a instanceof GameObject) {
			return ((GameObject) a).getX() + ((GameObject) a).getWidth();
		} else {
			return a.getX() + a.getWidth();
		}
	}

	public float getRandomMargin() {
		return Math.max(gameField.getWidth() * 35 / 100, randomGenerator
				.nextInt((int) (gameField.getWidth() * 45 / 100)));
	}

	public float distance(CollidableGameObject co) {
		return (co.getX() < 0) ? gameField.getWidth() + co.getX()
				+ co.getWidth() : gameField.getWidth()
				- (co.getX() - co.getWidth());
	}

	/**
	 * Prüft auf Grundlage der berechneten y-Koordinate der
	 * getNextJumpPosition-Methode, ob das Pferd unterhalb des Bodens springen
	 * würde - also ob der y-Wert richtig berechnet wurde.
	 * 
	 * @param y
	 *            Die nächste y-Koordinate des Pferds während eines Sprungs.
	 * @return true, wenn das Pferd nicht genau auf dem Boden landen würde,
	 *         sondern darunter, sonst false.
	 */
	private boolean willPlayerBeLesserThanGround(float y) {

		if (y < gameField.getTopOfGroundPosition() - 25) {
			return true;
		}
		return false;
	}

	/**
	 * Prüft ob das Pferd beim Bewegen zur übergebnenen x-Koordinate außerhalb
	 * des linken o. rechten Spielfeldbereichs sein würde.
	 * 
	 * @param x
	 *            Die x-Koordinate zu der sich das Pferd beabsichtigt zu
	 *            bewegen.
	 * @return true, wenn das Pferd außerhalb des linken oder rechten
	 *         Spielfeldbereichs sein würde, sonst false.
	 */
	public boolean willPlayerBeOutOfGameField(float x) {
		if (gameField.getPlayer().getJumpDirection() == Direction.RIGHT) {
			if (x > gameField.getWidth()) {
				return true;
			}
			float positionOnRightJump = gameField.getPlayer().getX()
					+ gameField.getPlayer().getWidth()
					+ (x - gameField.getPlayer().getX());

			if (positionOnRightJump > gameField.getWidth()) {
				return true;
			}

		} else {
			if (x < 0) {
				return true;
			}
		}
		return false;
	}

}
