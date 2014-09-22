package com.haw.projecthorse.level.city;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.level.Level;

public class city extends Level {

    
	private Texture texture;
	private Stage stage;
	private Actor actor;
	
	@Override
	protected void doShow() { 
		// TODO Auto-generated method stub
		stage = new Stage(this.getViewport(), this.getSpriteBatch());
		
		ImageButton button = new ImageButton(texture);
		
		actor = new Actor();
		
		stage.addActor(actor);
		
		
		createCityButton();
		AssetManager.load();
		try {
			CityObject city =	GameManagerFactory.getInstance().getCityObject(getLevelID());
			
			} catch (LevelNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Gdx.input.setInputProcessor(stage); 

	}
	@Override
	protected void doRender(float delta) {
	
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AssetManager.showTexture("pictures/cc-0/hamburg.png", 0, 0);
		
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		batch.setColor(Color.BLUE);
		batch.draw(texture, 100, 300,350, 128);				
		batch.end();
		SpriteBatch batch1 = new SpriteBatch();
		batch1.begin();
		batch1.setColor(Color.GREEN);
		batch1.draw(texture, 100, 100,350, 128);				
		batch1.end();
		actor.draw(batch,1);		
		actor.draw(batch1,1);		
		
		
	}
	
	private void createCityButton(){
		com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();
		assetManager.load("pictures/selfmade/raw_game_teaser_icon.png", Texture.class);
		assetManager.finishLoading();
		 texture = assetManager.get("pictures/selfmade/raw_game_teaser_icon.png", Texture.class);
		//texture.getTextureData().consumePixmap().setColor(Color.BLUE);
	
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
