package com.haw.projecthorse.gamemanager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManager;
import com.haw.projecthorse.level.DefaultLevel;

public class CoreGameMain extends Game {

	@Override
	public void create() {
		NavigationManager navigationManager = new NavigationManager(this);
		GameManager gm = GameManager.getInstance();
		gm.setNavigationManager(navigationManager);
			
	}
	
	

}
