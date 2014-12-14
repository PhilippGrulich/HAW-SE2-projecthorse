package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {

	private BitmapFont font;
	private String message; //Das, was angezeigt werden soll.
	private float x;
	private float y;

	/**
	 * Erzeugt ein neues Text-Objekt.
	 * @param f BitmapFont vom AssetManager.
	 * @param message Die Information, die angezeigt werden soll.
	 * @param x Die x-Koordinate, an der der Text angezeigt werden soll.
	 * @param y Die y-Koordinate, an der der Text angezeigt werden soll.
	 */
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
