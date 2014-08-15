package com.haw.projecthorse.level;


import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.haw.projecthorse.assetmanager.AssetManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haw.projecthorse.gamemanager.GameManagerFactory;



public class DefaultLevel implements Screen{
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AssetManager.showTexture("pictures/cc-0/worldmap.png", 0, 0);
		AssetManager.showTexture("pictures/selfmade/logo.png", 0, 0);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		AssetManager.load();
		AssetManager.playSound("sounds/flap.wav");
		AssetManager.playMusic("music/life.mp3");
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
