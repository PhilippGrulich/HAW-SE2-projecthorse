package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.PlayerImpl;

public class Player extends PlayerImpl {

	private float jumpSpeed;
	private float a, b, c;
	private float player_jumpspeed = 10;
	private float player_jumpheight = 150;
	private float player_jumpwidth = 300;
	private boolean jumpDirectionRight = true;
	private Rectangle r;
	float x, y;

	public Player() {
		super();
		toFront();
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Berechnung von nächstem Punkt (x,y) des Spielersprunges
	 */
	public Vector2 getNextJumpPosition() {
		Vector2 v = new Vector2();
		float x = 0;

		if (isJumpDirectionRight()) {
			x = getX() + player_jumpspeed;
			v.x = x;
			v.y = a * (x * x) + b * x + c;
		} else {
			x = getX() - player_jumpspeed;
			v.x = x;
			v.y = a * (x * x) + b * x + c;
		}

		return v;
	}

	/**
	 * Berechnung der Sprungfunktion in abhängigkeit des aktuellen x und y.
	 */
	public void setupJumpFunction() {
		float x1 = 0;
		float y1 = 0;
		float x2 = 0;
		float y2 = 0;
		float x3 = 0;
		float y3 = 0;

		if (isJumpDirectionRight()) {
			x1 = getX();
			y1 = getY();
			x2 = getX() + (player_jumpwidth / 2f);
			y2 = getY() + player_jumpheight;
			x3 = getX() + player_jumpwidth;
			y3 = getY();
			System.out.println("hier");

		} else {
			System.out.println("dort");
			/*
			 * x1 = getX() + player_jumpwidth; y1 = getY(); x2 = getX() +
			 * (player_jumpwidth / 2f); y2 = getY() + player_jumpheight; x3 =
			 * getX(); y3 = getY();
			 */

			x1 = getX();
			y1 = getY();
			x2 = getX() - (player_jumpwidth / 2f);
			y2 = getY() + player_jumpheight;
			x3 = getX() - player_jumpwidth;
			y3 = getY();
		}

		a = (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2))
				/ ((x1 - x2) * (x1 - x3) * (x3 - x2));
		b = ((x1 * x1) * (y2 - y3) + (x2 * x2) * (y3 - y1) + (x3 * x3)
				* (y1 - y2))
				/ ((x1 - x2) * (x1 - x3) * (x2 - x3));
		c = ((x1 * x1) * (x2 * y3 - x3 * y2) + x1
				* ((x3 * x3) * y2 - (x2 * x2) * y3) + x2 * x3 * y1 * (x2 - x3))
				/ ((x1 - x2) * (x1 - x3) * (x2 - x3));
	}

	public void setJumpHeight(float y) {
		this.player_jumpheight = y;
	}

	public void setJumpWitdh(float x) {
		this.player_jumpwidth = x;
	}

	public void setJumpSpeed(float duration) {
		this.jumpSpeed = duration;
	}

	public float getJumpSpeed() {
		return this.jumpSpeed;
	}

	public void applyRactangle() {
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public void setJumpDirection(Direction d) {
		if (d == Direction.RIGHT) {
			setJumpDirectionRight(true);
		} else {
			setJumpDirectionRight(false);
		}
	}

	private void setJumpDirectionRight(boolean b) {
		this.jumpDirectionRight = b;
	}

	private boolean isJumpDirectionRight() {
		return jumpDirectionRight;
	}

	@Override
	public void setX(float x) {
		r.setX(x);
		this.x = x;
	}

	@Override
	public void setY(float y) {
		r.setY(y);
		this.y = y;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	public Rectangle getRectangle() {
		return r;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
