package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.math.Rectangle;

/**
 * Klasse für GameObjects mit denen kollidiert werden kann.
 * @author Francis
 * @version 1.0
 *
 */
public class CollidableGameObject extends GameObject {

	private Rectangle r; //Das Rectangle, welches für die Collision-Detection verwendet wird.
	private float x, width; //x-Koordinate des GameObjects, Breite des GameObjects
	private float y, height; //y-Koordinate des GameObjects, Höhe des GameObjects
	private boolean collidable; //true, wenn das Pferd mit dem GameObject kollidieren kann.

	/**
	 * Prüft ob ein Rechteck zur Kollisions-Detektion initialisert ist,
	 * und initialisiert eins, wenn dies nicht der Fall ist.
	 */
	private void checkIfRectangleIsInitialized() {
		if (r == null) {
			r = new Rectangle();
		}
	}
	
	@Override
	public void act(final float delta) {
		if (getDuration() > 0) {
			
			setX(this.getX() - getDuration() * delta);
		} else if (getDuration() < 0) {
			setX(this.getX() + getDuration() * delta);
		}
		//super.act(delta);
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

	public void setCollidable(final boolean b) {
		collidable = b;
	}

	@Override
	public void setHeight(final float h) {
		r.height = h;
		this.height = h;
	}

	public void setRectangle(final Rectangle rt) {
		r = rt;
	}

	@Override
	public void setWidth(final float w) {
		r.width = w;
		this.width = w;
	}

	@Override
	public void setX(final float x) {
		checkIfRectangleIsInitialized();
		r.setX(x);
		this.x = x;
	}

	@Override
	public void setY(final float y) {
		checkIfRectangleIsInitialized();
		r.setY(y);
		this.y = y;
	}

}
