package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.math.Rectangle;

public class CollidableGameObject extends GameObject {

	private Rectangle r;
	private float x, width;
	private float y, height;
	private boolean collidable;

	private void checkIfRectangleIsInitialized() {
		if (r == null) {
			r = new Rectangle();
		}
	}

	@Override
	public float getHeight() {
		return height;
	}

	public Rectangle getRectangle() {
		return r;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean b) {
		collidable = b;
	}

	@Override
	public void setHeight(float h) {
		r.height = h;
		this.height = h;
	}

	public void setRectangle(Rectangle rt) {
		r = rt;
	}

	@Override
	public void setWidth(float w) {
		r.width = w;
		this.width = w;
	}

	@Override
	public void setX(float x) {
		checkIfRectangleIsInitialized();
		r.setX(x);
		this.x = x;
	}

	@Override
	public void setY(float y) {
		checkIfRectangleIsInitialized();
		r.setY(y);
		this.y = y;
	}

}
