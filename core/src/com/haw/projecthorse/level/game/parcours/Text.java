package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Die Klasse stellt Text dar, der direkt auf der Stage angezeigt wird.
 * @author Francis
 * @version 1.0
 */
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
	public Text(final BitmapFont f, final String message, final float x, final float y) {
		font = f;
		this.message = message;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(final Batch batch, final float parentAlpha) {
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

	/**
	 * Setzt die Farbe des Texts.
	 * @param c Die Farbe des Texts.
	 */
	public void setColor(final Color c) {
		font.setColor(c);
	}

	/**
	 * Setzt die Farbe des Texts nach dem rgb-Schema mit Transparenz a.
	 * @param r Rot-Anteil
	 * @param g Grün-Anteil
	 * @param b Blau-Anteil
	 * @param a Alpha-Anteil.
	 */
	public void setColor(final float r, final float g, final float b, final float a) {
		font.setColor(r, g, b, a);
	}

	/**
	 * Setzt den Text der dargestellt werden soll.
	 * @param text Der drazustellende Text.
	 */
	public void setText(final String text) {
		this.message = text;
	}

	@Override
	public void setX(final float x) {
		this.x = x;
	}

	@Override
	public void setY(final float y) {
		this.y = y;
	}
	
	@Override
	public void setPosition(final float x, final float y){
		this.x = x;
		this.y = y;
	}
}
