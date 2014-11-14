package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class LootObject extends GameObject {
	
	private Rectangle r;
	private float x;
	private float y;
	private int points;
	
	public LootObject(TextureRegion a, float duration, int points) {
		super(a, duration);
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		this.points = points;
	}
	
	public void applyRactangle(){
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void setX(float x){
		r.setX(x);
		this.x = x;
	}
	
	@Override 
	public void setY(float y){
		r.setY(y);
		this.y = y;
	}
	
	@Override
	public float getY(){
		return y;
	}
	
	@Override
	public float getX(){
		return x;
	}
	
	public Rectangle getRectangle(){
		return r;
	}
	
	public int getPoints(){
		return points;
	}

}
