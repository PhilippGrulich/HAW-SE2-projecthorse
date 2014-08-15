package com.haw.projecthorse.gamemanager;

import com.badlogic.gdx.Game;

import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;

public class CoreGameMain extends Game {

	@Override
	public final void create() {

		
		GameManagerImpl gameManager = new GameManagerImpl();
		NavigationManagerImpl navigationManager = new NavigationManagerImpl(this);		
		
		gameManager.setNavigationManager(navigationManager);		
		GameManagerFactory.setInstance(gameManager);
		

			
	}
	
	
	
	

}
