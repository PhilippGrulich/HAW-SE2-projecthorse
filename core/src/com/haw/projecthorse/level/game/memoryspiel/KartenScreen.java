package com.haw.projecthorse.level.game.memoryspiel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class KartenScreen extends Stage {

	private Texture picture;
	
	public KartenScreen(Viewport viewport, Batch batcher, Object picture){
		super(viewport, batcher);
		this.picture = (Texture) picture;
	}

	public void setPicture(Texture picture){
		this.picture = picture;
	}
	
	public Texture getPicture(){
		return picture;
	}
}
