package com.haw.projecthorse.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.collision.BoundingBox;

public abstract class AbstractPlayer implements Player {

	private final float MIN_FRAMEDURATION = 0.05f; // Dieser Wert reguliert die maximale Animationsgeschwindigkeit, je kleiner desto schneller
	
	private float x,y,speed;
	private Direction direction;
	private Animation animation;
	
	public AbstractPlayer(float x, float y, Direction direction, float speed) {
		float frameDuration = (speed == 0) ? 0f : MIN_FRAMEDURATION / speed;
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
		animation = new Animation(frameDuration, new TextureRegion(new Texture("")));
	}

	public AbstractPlayer(float x, float y) {
		this(x, y, Direction.RIGHT, 0f);
	}

	@Override
	public void draw(float timeDeltaSum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setMovement(Direction direction, float speed) {
		// TODO Bei Richtungswechsel Animation ändern
//		if (this.direction != direction){
//			this.direction != direction	
//			
//		}
		this.speed = speed;
	}
	
	/**
	 * Setzt eine andere Animation und startet diese neu
	 * @param direction Richtung der Animation
	 */
	protected void changeAnimation(Direction direction){
		// TODO Animation für Richtung zurücksetzen und als aktuelle Animation setzen
		
	}

	@Override
	public void changeSpeed(float delta) {
		speed = Math.min(1, Math.max(0, speed + delta));
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public boolean isMoving() {
		return speed != 0; 
	}

	@Override
	public Polygon getBoundingPolygon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

}
