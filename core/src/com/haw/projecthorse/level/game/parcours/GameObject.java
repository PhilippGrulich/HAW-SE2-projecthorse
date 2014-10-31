package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {
	
	private TextureRegion region;
	
	public GameObject(TextureRegion a){
		this.region = a;
		toBack();
	}
	
	@Override
    public void draw (Batch batch, float parentAlpha) {
	  batch.draw(region, getX(), getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
	}
}
