package com.haw.projecthorse.level.memoryspiel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Karte {

	private Vector2 position;
	private State state;
	private Texture picture;

	public enum State {
		OPEN, CLOSED;
	}

	public Karte(){
		
	}
	public Karte(Vector2 position) {
		this.position = position;
		this.state = State.CLOSED;
	}

	public void onClick() {
		if (this.state == (State.CLOSED)) {
			setState(State.OPEN);
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
	 
	public Texture getPicture(){
		return this.picture;
	}
	
	public void setPicture(Texture picture){
		this.picture = picture;
	}
	 
	
}
