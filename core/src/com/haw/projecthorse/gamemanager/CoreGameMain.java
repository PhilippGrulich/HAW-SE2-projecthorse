package com.haw.projecthorse.gamemanager;

import com.badlogic.gdx.Game;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;
import com.haw.projecthorse.intputmanager.InputManager;

public class CoreGameMain extends Game {

	@Override
	public final void create() {

		InputManager.createInstance();
		GameManagerImpl gameManager = GameManagerImpl.getInstance();
		NavigationManagerImpl navigationManager = new NavigationManagerImpl(this);		
		
		gameManager.setNavigationManager(navigationManager);		
			
	}
	
}
