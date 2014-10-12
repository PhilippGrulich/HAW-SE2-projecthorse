package com.haw.projecthorse.level.memoryspiel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.Level;

public class MemorySpiel extends Level {

	private Stage main;
	private Stage card;
	private SpriteBatch batcher = this.getSpriteBatch();
	private KartenManager manager;
	@Override
	protected void doRender(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doDispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doShow() {
		main = new MainScreen(this.getViewport(), batcher);
		card = new KartenScreen(this.getViewport(),this.getSpriteBatch(), null);
		
	}
	@Override
	protected void doHide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doResume() {
		// TODO Auto-generated method stub
		
	}
	
	

}
