package com.haw.projecthorse.level.game.memoryspiel;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

public class Karte extends Image{

	private Vector2 position;
	private State state;
	private Drawable picture;
	public static Drawable karte = new TextureRegionDrawable(AssetManager.getTextureRegion("memorySpiel","Karte"));
	
	public enum State {
		OPEN, CLOSED, TEMPORARILY_OPENED;
	}

	public Karte(){
		super(karte);
		this.state = State.CLOSED;
	}
	public Karte(Vector2 position) {
		this();
		this.position = position;
		this.setX(getPosition().x);
		this.setWidth(187);
		this.setY(getPosition().y);
		this.setHeight(190);
		this.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		onClick();
		 	}

		});
	}

	public void onClick() {
		if (this.state == (State.CLOSED)) {
			setState(State.TEMPORARILY_OPENED);
		}
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
	
	public void SetPosition(Vector2 position){
		this.position = position;
	}
	
	public Vector2 getPosition(){
		return position;
	}
	 
	public Drawable getPicture(){
		return picture;
	}
	
	public void setPicture(Drawable picture1){
		this.picture = picture1;
	}
	
	
}
