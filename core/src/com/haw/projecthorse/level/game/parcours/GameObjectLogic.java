package com.haw.projecthorse.level.game.parcours;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;

/**
 * Klasse für die Spiellogik. 
 * Enthält Methoden für die Kollisions-Ermittlung, die Bewegung des Pferdes bei
 * Anwendung des Accelerometers bzw. bei Wisch-Bewegungen und Sprüngen.
 * @author Francis
 * @version 1.0
 */
public class GameObjectLogic implements IGameObjectLogicFuerGameOperator,
		IGameObjectLogicFuerGameInputListener {

	private float freePosition; // Position des am weitesten rechts befindlichen
								// GameObjects
	private IGameFieldFuerGameObjectLogic gameField;
	private boolean shouldPlayerJump;
	private Random randomGenerator;
	private float accelerometerBound;

	/**
	 * Konstruktor.
	 * @param initialFreePosition Position ab der ein neues GameObject gesetzt werden kann.
	 * @param g GameObjectLogic.
	 */
	public GameObjectLogic(final float initialFreePosition,
			final IGameFieldFuerGameObjectLogic g) {
		freePosition = initialFreePosition;
		shouldPlayerJump = false;
		gameField = g;
		randomGenerator = new Random();
		accelerometerBound = 1.5f;
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
						gameField.setGameOverState(true);
						/*gameField.addToScore(((CollidableGameObject) l)
								.getPoints());*/
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
	public float getRandomCoordinate(final float[] interval, final float gameObjectWidth,
			final boolean colidable) {
		float rand = (float) Math.floor(Math.random()
				* (interval[1] - interval[0]) + interval[0]);
		float result = freePosition + rand;

		if (colidable){
			freePosition = result + gameObjectWidth;
		}

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
	 * Setzt die Freiposition freePosition.
	 * @param f
	 *            x-Koordinate auf ab der sich kein GameObject mehr befindet.
	 */
	public void setFreePosition(final float f) {
		freePosition = f;
	}

	/**
	 * Setzt shouldPlayerJump auf true, wenn das Pferd springt & pausiert dann gallop.
	 * Spielt gallop ab, wenn das Pferd nicht mehr springt.
	 * @param a true, wenn das Pferd spring, sonst false.
	 */
	public void setPlayerJump(final boolean a) {
		if (a) {
			gameField.pauseGallop();
		} else {
			gameField.playGallop();
		}
		this.shouldPlayerJump = a;
	}

	/**
	 * Updated die Positionen der GameObjects, prüft Neigungswinkel des Devices,
	 * prüft ob das Pferd außerhalb des Spielfeldes sein würde wenn es sich bewegt und
	 * ermittelt, ob eine Kollision mit einem GameObject stattgefunden hat.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 */
	public void update(final float delta) {
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
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 */
	private void updateAccelometer(final float delta) {
		// nur wenn das Accelerometer activiert ist wird es auch genutzt

		if (GameManagerFactory.getInstance().getSettings()
				.getAccelerometerState()) {
			float y = Gdx.input.getAccelerometerY();
			if (y >= accelerometerBound){
				movePlayerR(delta, y);
				
			}else if(y <= -accelerometerBound){
				movePlayerL(delta, y * (-1));
			}else{
				gameField.getPlayer().setAnimationSpeed(0.3f);
			}
		
		}
		
	}

	/**
	 * Bewegt das Pferd nach rechts, wenn das Device nach rechts geneigt wird.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 * @param y Neigungswert zw. [-10, 10] vom Accelerometer. 
	 */
	public void movePlayerR(final float delta, final float y) {
		float x = gameField.getPlayer().getX()
				+ gameField.getPlayer().getWidth()
				+ gameField.getGeneralGameSpeed() * delta * (y / accelerometerBound);

		if(gameField.getPlayer().getJumpDirection() != Direction.RIGHT) {
			gameField.getPlayer().clearActions();
			gameField.getPlayer().addAction(new AnimationAction(Direction.RIGHT));
		}
		
		gameField.getPlayer().setJumpDirection(Direction.RIGHT);

		if (willPlayerBeOutOfGameField(x)) {
			gameField.getPlayer().shouldMove(0, 0);
		} else {
			gameField.getPlayer().shouldMove(1, y / accelerometerBound);
			if(gameField.getPlayer().getAnimationSpeed() > y / accelerometerBound*2){
				gameField.getPlayer().changeAnimationSpeed(-y / accelerometerBound*2);
			}else{
				gameField.getPlayer().changeAnimationSpeed(y / accelerometerBound*2);	
			}
		}
		
	}

	/**
	 * Bewegt das Pferd nach links, wenn das Device nach rechts geneigt wird.
	 * @param delta Die Zeit die seit dem letzten Frame vergangen ist.
	 * @param y Neigungswert zw. [-10, 10] vom Accelerometer. 
	 */
	public void movePlayerL(final float delta, final float y) {
		float x = gameField.getPlayer().getX()
				- gameField.getGeneralGameSpeed() * delta * (y / accelerometerBound);

		if(gameField.getPlayer().getJumpDirection() != Direction.LEFT){
			gameField.getPlayer().clearActions();
			gameField.getPlayer().addAction(new AnimationAction(Direction.LEFT));
		}
		
		gameField.getPlayer().setJumpDirection(Direction.LEFT);

		if (willPlayerBeOutOfGameField(x)) {
			gameField.getPlayer().shouldMove(0, 0);
		} else {
			gameField.getPlayer().shouldMove(2, y / accelerometerBound);
			if(gameField.getPlayer().getAnimationSpeed() > y / accelerometerBound*2){
				gameField.getPlayer().changeAnimationSpeed(-y / accelerometerBound*2);
			}else{
				gameField.getPlayer().changeAnimationSpeed(y / accelerometerBound*2);	
			}
		}
		
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
	public void updateGameObjects(final float delta) {
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
				((CollidableGameObject) a).setX(gameField.getWidth() - a.getWidth());
				gameField.passBack((CollidableGameObject) a);
			} else if (a instanceof GameObject) {
				if(!((GameObject)a).isCollidable() && ((GameObject)a).isMoveable()){
					if(getRightBottomCorner(a) <= 0){
						a.setX(gameField.getWidth() + getRandomMargin());
					}
				}
			}
		}

		if (!posAssigned) {
			CollidableGameObject co = gameField.getRandomObject();
			co.setX(gameField.getWidth() + getRandomMargin());
			gameField.addCollidableGameObject(co);
		}
	}

	/**
	 * Liefert die Position der rechten unteren Ecke eines Actors.
	 * @param a Instanz von GameObject oder Pferd.
	 * @return b Die Position der rechten unteren Ecke.
	 */
	public float getRightBottomCorner(final Actor a) {
		if (a instanceof CollidableGameObject) {
			return ((CollidableGameObject) a).getX()
					+ ((CollidableGameObject) a).getWidth();
		} else if (a instanceof GameObject) {
			return ((GameObject) a).getX() + ((GameObject) a).getWidth();
		} else {
			return a.getX() + a.getWidth();
		}
	}

	/**
	 * Liefert einen Wert zwischen 35% und 45% der Spielfeldbreite.
	 * @return rm s.o.
	 */
	public float getRandomMargin() {
		return Math.max(gameField.getWidth() * 35 / 100, randomGenerator
				.nextInt((int) (gameField.getWidth() * 45 / 100)));
	}

	/**
	 * Die Distanz des CollidableGameObject zum rechten Spielfeldrand.
	 * @param co Das CollidableGameObject.
	 * @return d Die Distanz.
	 */
	public float distance(final CollidableGameObject co) {
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
	private boolean willPlayerBeLesserThanGround(final float y) {
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
	public boolean willPlayerBeOutOfGameField(final float x) {
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
