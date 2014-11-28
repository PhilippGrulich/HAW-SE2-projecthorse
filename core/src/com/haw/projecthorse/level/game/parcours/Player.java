package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.actions.AnimationAction;

public class Player extends PlayerImpl {

	private float player_jumpspeed;
	private float a, b, c;
	private float player_jumpheight;
	private float player_jumpwidth;
	private boolean jumpDirectionRight = true;
	private Rectangle r;
	private float x, y;
	private float SWIPEMOVE;
	private float SWIPEDURATION = 0.2f;
	private float playerHeight, playerWidth, gameWidth, gameHeight;

	public float getGameHeight() {
		return gameHeight;
	}

	public float getGameWidth() {
		return gameWidth;
	}

	public Player(float gameWidth, float gameHeight) {
		super();
		toFront();
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		initSwipeListener();
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.SWIPEMOVE = getGameWidth() * 35 / 100;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void applyRactangle() {
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	private void checkIfRectangleIsInitialized() {
		if (r == null) {
			r = new Rectangle();
		}
	}

	@Override
	public float getHeight() {
		return playerHeight;
	}

	public float getJumpSpeed() {
		return this.player_jumpspeed;
	}

	private float getLeftSwipePosition() {
		if (getX() - SWIPEMOVE < 0) {
			return 0;
		}
		return getX() - SWIPEMOVE;
	}

	/**
	 * Berechnung von n�chstem Punkt (x,y) des Spielersprunges
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

	public Rectangle getRectangle() {
		return r;
	}

	private float getRightSwipePosition() {
		if (getX() + getWidth() + SWIPEMOVE > getGameWidth()) {
			return getX() + (getGameWidth() - (getX() + getWidth()));
		}
		return getX() + SWIPEMOVE;
	}

	@Override
	public float getWidth() {
		return playerWidth;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	private void initSwipeListener() {
		SwipeListener listener = new SwipeListener() {

			@Override
			public void swiped(SwipeEvent event, Actor actor) {
				// Vormals Pr�fung auf instanceof APlayer
				if (getDirection() == event.getDirection()) {
					if (getDirection() == Direction.RIGHT) {
						addAction(Actions.moveTo(getRightSwipePosition(),
								getY(), SWIPEDURATION));
						setJumpDirection(Direction.RIGHT);
					} else {
						addAction(Actions.moveTo(getLeftSwipePosition(),
								getY(), SWIPEDURATION));
						setJumpDirection(Direction.LEFT);
					}
				} else {
					setAnimationSpeed(0.3f);
					clearActions();
					addAction(new AnimationAction(event.getDirection()));
					setJumpDirection(event.getDirection());
				}
			}
		};

		this.addListener(listener);
	}

	private boolean isJumpDirectionRight() {
		return jumpDirectionRight;
	}

	@Override
	public void setHeight(float h) {
		r.height = h;
		this.playerHeight = h;
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

	public void setJumpHeight(float y) {
		this.player_jumpheight = y;
	}

	public void setJumpSpeed(float duration) {
		this.player_jumpspeed = duration;
	}

	public void setJumpWitdh(float x) {
		this.player_jumpwidth = x;
	}

	@Override
	public void setPosition(float x, float y) {
		checkIfRectangleIsInitialized();
		r.x = x;
		r.y = y;
		this.x = x;
		this.y = y;
		
	}
	
	/**
	 * Berechnung der Sprungfunktion in abh�ngigkeit des aktuellen x und y.
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
		} else {

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

	@Override
	public void setWidth(float w) {
		r.width = w;
		this.playerWidth = w;
	}
}
