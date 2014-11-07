package com.haw.projecthorse.level.game.parcours;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundObject extends Actor{
	
	private TextureRegion region;
	private float duration;
	
	public BackgroundObject(TextureRegion a, float duration){
		this.region = a;
		this.duration = duration;
		toBack();
	}
	
	@Override
    public void draw (Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		if (duration > 0){
			float x = this.getX()-duration*delta;
			BigDecimal x_new = new BigDecimal(x).setScale(4, RoundingMode.HALF_UP);
				this.setX(x_new.floatValue());
			}
	}
	
	public float getDuration(){
		return duration;
	}
	
	public void setDuration(float duration){
		this.duration = duration;
	}
	
	

}
