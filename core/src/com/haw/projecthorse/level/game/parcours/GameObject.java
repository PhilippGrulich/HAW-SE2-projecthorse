package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {

	private TextureRegion tr; //Die TextureRegion die dieses GameObject darstellt.
	private float duration; //Die Geschwindigkeit in Pixel pro Sekunde mit der sich das GameObject bewegt.
	private int points; //Die Punkte die bei Kollision mit dem Pferd gutgeschrieben bzw. abgezogen werden.
	private boolean collidable; //true, wenn man mit dem GameObject kollidieren kan.
	private boolean loot; //true, wenn man das GameObject gewinnen kann.
	private boolean isMoveable; //true, wenn duration > 0.

	
	public boolean isMoveable() {
		return isMoveable;
	}

	public void setMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
	}

	public boolean isLoot() {
		return loot;
	}

	public void setLoot(boolean loot) {
		this.loot = loot;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (duration > 0) {
			setX(this.getX() - duration * delta);
		} else if (duration < 0) {
			setX(this.getX() + duration * delta);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
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

	public void setCollidable(boolean b) {
		collidable = b;
	}

	public void setDuration(float d) {
		duration = d;
	}

	public void setPoints(int p) {
		points = p;
	}

	public void setTextureRegion(TextureRegion t) {
		tr = t;
	}

}
