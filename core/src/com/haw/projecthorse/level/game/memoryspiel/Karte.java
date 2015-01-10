package com.haw.projecthorse.level.game.memoryspiel;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

/**
 * Karten-Objekt darstellen.
 * 
 * @author Ngoc Huyen Nguyen
 * @version 1.0
 */

public class Karte extends Image {

	private CardState state;
	private Drawable picture;

	// Image für zugedeckte Karten
	public static Drawable karte = new TextureRegionDrawable(
			AssetManager.getTextureRegion("memorySpiel", "Karte"));

	/**
	 * alle mögliche Zustände einer Karte.
	 */
	public enum CardState {
		OPEN, CLOSED, TEMPORARILY_OPENED, TEMPORARILY_CLOSED;
	}

	/**
	 * Default-Konstruktor.
	 */
	public Karte() {
		super();

	}

	/**
	 * Konstruktor. Hier werden Koordinaten, die Größe, Default-Image einer
	 * Karte zugewiesen.
	 * 
	 * @param x
	 *            Koordinate einer Karte
	 * @param y
	 *            Koordinate einer Karte
	 */
	public Karte(final float x, final float y) {
		this();
		this.state = CardState.CLOSED;
		this.setDrawable(Karte.karte);
		this.setX(x);
		this.setWidth(187);
		this.setY(y);
		this.setHeight(190);
		this.addListener(new InputListener() {
			public boolean touchDown(final InputEvent event, final float x,
					final float y, final int pointer, final int button) {
				opened();
				return true; // or false
			}
		});
	}

	/**
	 * Beim Öffnen einer Karte wird der Zustand der entspechenden Karte
	 * geändert.
	 */
	public void opened() {
		if (this.state == (CardState.CLOSED)) {
			setState(CardState.TEMPORARILY_OPENED);
		}
	}

	/**
	 * Kartenzustand Setter.
	 * 
	 * @param state
	 *            zu setzender Kartenzustand
	 */
	public void setState(final CardState state) {
		this.state = state;
	}

	/**
	 * Kartenzustand Getter.
	 * 
	 * @return Kartenzustand
	 */
	public CardState getState() {
		return state;
	}

	/**
	 * Bild Getter.
	 * 
	 * @return das Bild der Karte
	 */
	public Drawable getPicture() {
		return picture;
	}

	/**
	 * Bild Setter.
	 * 
	 * @param picture1
	 *            zu setzendes Bild
	 */
	public void setPicture(final Drawable picture1) {
		this.picture = picture1;
	}

}
