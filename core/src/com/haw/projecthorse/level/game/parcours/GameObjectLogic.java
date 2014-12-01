package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.player.actions.Direction;

public class GameObjectLogic implements IGameObjectLogicFuerGameOperator, IGameObjectLogicFuerGameInputListener {

	private float freePosition;
	private IGameFieldFuerGameObjectLogic gameField;
	private boolean shouldPlayerJump;

	public GameObjectLogic(float initialFreePosition, IGameFieldFuerGameObjectLogic g) {
		freePosition = initialFreePosition;
		shouldPlayerJump = false;
		gameField = g;
	}

	private void checkPlayerConstraints() {
		if (isPlayerJumping()) {
			handleJump();
		}
	}

	public void collisionDetection() {
		List<GameObject> objects = gameField.getGameObjects();
		
		for (GameObject l : objects) {
			if (l.isCollidable()) {
				if (((CollidableGameObject) l).getRectangle().overlaps(
						gameField.getPlayer().getRectangle())) {
					l.setVisible(false);
					l.setX(-5 - l.getWidth());
					if(l.getPoints() > 0){
						gameField.eat();
						gameField.addToScore(l.getPoints() + (int)Math.ceil(l.getPoints()*gameField.getPlayer().getIntelligence()));
					}else {
						gameField.addToScore(l.getPoints());
					}
					
				}
			}
		}
	}

	public float getFreePosition() {
		return freePosition;
	}

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

	public float getRandomCoordinate(float[] interval, float gameObjectWidth, boolean colidable) {
		float rand = (float) Math.floor(Math.random()
				* (interval[1] - interval[0]) + interval[0]);
		float result = freePosition + rand;
		
		if(colidable)
			freePosition = result + gameObjectWidth;
	
		return result;
	}

	public void handleJump() {
		Vector2 v = gameField.getPlayer().getNextJumpPosition();
		float x = 0;
		float y = 0;
		boolean outOfBound1 = !willPlayerBeLesserThanGround(v.y);
		boolean outOfBound2 = !willPlayerBeOutOfGameField(v.x);
		if (outOfBound2) {
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

			if (y < gameField.getTopOfGroundPosition()) {
				y = gameField.getTopOfGroundPosition();
			}
		}

		gameField.getPlayer().moveBy(x, y);
		gameField.getPlayer().setPosition(x, y);

		if (gameField.getPlayer().getY() == gameField.getTopOfGroundPosition()) {
			setPlayerJump(false);
		}
	}

	public boolean isPlayerJumping() {
		return shouldPlayerJump;
	}

	private boolean outOfGameField(Actor o) {
		if (o.getX() + o.getWidth() < 0) {
			return true;
		}
		return false;
	}

	public void setFreePosition(float f) {
		freePosition = f;
	}

	public void setPlayerJump(boolean a) {
		if(a){
			gameField.pauseGallop();
		}else{
			gameField.playGallop();
		}
		this.shouldPlayerJump = a;
	}

	public void update(float delta) {
		updateGameObjects(delta);
		checkPlayerConstraints();
		collisionDetection();
		gameField.actGameField(delta);
		gameField.drawGameField();

	}

	public void updateGameObjects(float delta) {

		List<GameObject> objects = gameField.getGameObjects();

		for (GameObject o : objects) {
			if (!o.isVisible() && outOfGameField(o)) {
				o.setVisible(true);
				o.setX(getRandomCoordinate(getInterval(), o.getWidth(), o.isCollidable()));
			}
			if (o.isVisible() && outOfGameField(o)) {
				o.setVisible(false);
			}
			if (o.getX() + o.getWidth() == getFreePosition()) {
			  	freePosition = o.getX() - o.getDuration() * delta
						+ o.getWidth();
			}
		}
	}

	private boolean willPlayerBeLesserThanGround(float y) {

		if (y < gameField.getTopOfGroundPosition()) {
			return true;
		}
		return false;
	}
	
	public boolean willPlayerBeOutOfGameField(float x) {
		if (gameField.getPlayer().getDirection() == Direction.RIGHT) {

			float positionOnRightJump = gameField.getPlayer().getX()
					+ gameField.getPlayer().getWidth()
					+ (x - gameField.getPlayer().getX());

			if (positionOnRightJump > gameField.getWidth()) {
				return true;
			}

		} else {

			float positionOnLeftJump = 0;
			if (x < 0) {
				positionOnLeftJump = gameField.getPlayer().getX() + x;
			} else {
				positionOnLeftJump = (gameField.getPlayer().getX() - x);
			}

			if (positionOnLeftJump < 0) {
				return true;
			}
		}
		return false;
	}

}
