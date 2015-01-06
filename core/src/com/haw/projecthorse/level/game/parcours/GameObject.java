package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Klasse für GameObjects.
 * @author Francis
 * @version 1.0
 */
public class GameObject extends Actor {

	private TextureRegion tr; //Die TextureRegion die dieses GameObject darstellt.
	private float duration; //Die Geschwindigkeit in Pixel pro Sekunde mit der sich das GameObject bewegt.
	private int points; //Die Punkte die bei Kollision mit dem Pferd gutgeschrieben bzw. abgezogen werden.
	private boolean collidable; //true, wenn man mit dem GameObject kollidieren kan.
	private boolean loot; //true, wenn man das GameObject gewinnen kann.
	private boolean isMoveable; //true, wenn duration > 0.
	private float y;
	private float x;

	
	public boolean isMoveable() {
		return isMoveable;
	}

	public void setMoveable(final boolean isMoveable) {
		this.isMoveable = isMoveable;
	}

	public boolean isLoot() {
		return loot;
	}

	public void setLoot(final boolean loot) {
		this.loot = loot;
	}

	@Override
	public void act(final float delta) {
		if (duration > 0) {
			
			setX(this.getX() - duration * delta);
		} else if (duration < 0) {
			setX(this.getX() + duration * delta);
		}
		//super.act(delta);
	}
	
	@Override
	public void setX(final float x){
		this.x = x;
	}
	
	@Override
	public void setY(final float y){
		this.y = y;
	}
	
	@Override
	public void setPosition(final float x, final float y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public float getX(){
		return x;
	}
	
	@Override
	public float getY(){
		return y;
	}

	@Override
	public void draw(final Batch batch, final float parentAlpha) {
		/*
		 * batch.draw(tr, getX(), getY(), this.getScaleX() * this.getWidth(),
		 * this.getScaleY() * this.getHeight());
		 */
		batch.draw(tr, getX(), getY(), getWidth(), getHeight());
	}

	public float getDuration() {
		return duration;
	}

	public int getPoints() {
		return points;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(final boolean b) {
		collidable = b;
	}

	public void setDuration(final float d) {
		duration = d;
	}

	public void setPoints(final int p) {
		points = p;
	}

	public void setTextureRegion(final TextureRegion t) {
		tr = t;
	}

}
