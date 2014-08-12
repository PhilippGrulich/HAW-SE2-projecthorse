package com.haw.projecthorse.gamemanager.navigationmanager;

import com.badlogic.gdx.Game;
import com.haw.projecthorse.level.DefaultLevel;

/**
 * Navigation Manager.
 */
public class NavigationManager {
	private Game game = null;

	public NavigationManager(Game game) {
		this.game = game;
		game.setScreen(new DefaultLevel());
	}
	
	
	
}
