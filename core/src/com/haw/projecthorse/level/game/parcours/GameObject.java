package com.haw.projecthorse.level.game.parcours;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {
	
	private TextureRegion region;
	private float duration;
	private float x;
	private float y;
	private Rectangle r;
	private int points;

	public GameObject(TextureRegion a, float duration, int points){
		this.region = a;
		this.duration = duration;
		this.points = points;
		toBack();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		//float pos = getX();
		
		//batch.draw(region, pos, this.getY());
		batch.draw(region, getX(), getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
		//x = getX();
	}
	
	public float getDuration(){
		return duration;
	}
	
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		if (duration > 0){
			float x = this.getX()-duration*delta;
			this.setX(x);
		}
		
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
