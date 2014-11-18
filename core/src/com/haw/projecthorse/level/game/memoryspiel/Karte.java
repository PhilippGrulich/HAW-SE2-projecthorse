package com.haw.projecthorse.level.game.memoryspiel;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

public class Karte extends Image {

	private CardState state;
	private Drawable picture;
	public static Drawable karte = new TextureRegionDrawable(
			AssetManager.getTextureRegion("memorySpiel", "Karte"));

	public enum CardState {
		OPEN, CLOSED, TEMPORARILY_OPENED, TEMPORARILY_CLOSED;
	}

	public Karte() {
		super();

	}

	public Karte(float x, float y) {
		this();
		this.state = CardState.CLOSED;
		this.setDrawable(Karte.karte);
		this.setX(x);
		this.setWidth(187);
		this.setY(y);
		this.setHeight(190);
		this.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				changed();
				return true; // or false
			}
		});
	}

	public void changed() {
		if (this.state == (CardState.CLOSED)) {
			setState(CardState.TEMPORARILY_OPENED);
		}
	}

	public void setState(CardState state) {
		this.state = state;
	}

	public CardState getState() {
		return state;
	}

	public Drawable getPicture() {
		return picture;
	}

	public void setPicture(Drawable picture1) {
		this.picture = picture1;
	}

}
