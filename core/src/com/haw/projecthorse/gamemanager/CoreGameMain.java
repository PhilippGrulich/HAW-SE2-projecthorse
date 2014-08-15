package com.haw.projecthorse.gamemanager;

import com.badlogic.gdx.Game;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManager;

public class CoreGameMain extends Game {

	@Override
	public final void create() {
		NavigationManager navigationManager = new NavigationManager(this);
		
		GameManager gm = GameManager.getInstance();
		gm.setNavigationManager(navigationManager);
			
	}
	
	

}
