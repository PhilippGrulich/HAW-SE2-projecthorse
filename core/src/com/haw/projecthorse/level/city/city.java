package com.haw.projecthorse.level.city;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.level.Level;

public class city extends Level {
	
	private TextureAtlas assetAtlas;
	private SpriteBatch batcher = new SpriteBatch();
    
	@Override
	protected void doRender(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.batcher.begin();
		batcher.draw(this.assetAtlas.findRegion("karte"),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.batcher.end();
		
		try {
		CityObject city =	GameManagerFactory.getInstance().getCityObject(getLevelID());
		
		} catch (LevelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		// TODO Auto-generated method stub
		this.assetAtlas = AssetManager.load("hamburg", false, false, true);

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
