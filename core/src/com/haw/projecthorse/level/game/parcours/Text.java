package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {

	private BitmapFont font;
	private String message;
	private float x;
	private float y;

	public Text(BitmapFont f, String message, float x, float y) {
		font = f;
		this.message = message;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		font.draw(batch, message, x, y);
	}

	public String getText() {
		return message;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	public void setColor(Color c) {
		font.setColor(c);
	}

	public void setColor(float r, float g, float b, float a) {
		font.setColor(r, g, b, a);
	}

	public void setText(String text) {
		this.message = text;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
}
