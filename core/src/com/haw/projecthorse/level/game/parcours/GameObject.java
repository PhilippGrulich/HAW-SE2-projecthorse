package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor{
	
	private TextureRegion tr;
	private float duration;
	private int points;
	private boolean collidable;
	
	@Override
	public void act(float delta){
		super.act(delta);
		if (duration > 0){
			setX(this.getX()-duration*delta);
		}else if(duration < 0){
			setX(this.getX()+duration*delta);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		/*batch.draw(tr, getX(), getY(), this.getScaleX() * this.getWidth(), 
				this.getScaleY() * this.getHeight());*/
		batch.draw(tr, getX(), getY(), getWidth(), getHeight());
	}
	
	public float getDuration(){
		return duration;
	}
	
	public int getPoints(){
		return points;
	}
	
	public boolean isCollidable(){
		return collidable;
	}
	
	public void setCollidable(boolean b){
		collidable = b;
	}

	public void setDuration(float d){
		duration = d;
	}
	
	public void setPoints(int p){
		points = p;
	}
	
	public void setTextureRegion(TextureRegion t){
		tr = t;
	}

}
