package com.haw.projecthorse.gamemanager;

import com.badlogic.gdx.Game;

import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;

public class CoreGameMain extends Game {

	@Override
	public final void create() {

		
		GameManagerImpl gameManager = GameManagerImpl.getInstance();
		NavigationManagerImpl navigationManager = new NavigationManagerImpl(this);		
		
		gameManager.setNavigationManager(navigationManager);		
			
	}
	
}
