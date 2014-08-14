package com.haw.projecthorse.gamemanager.navigationmanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelLoadException;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;

/**
 * Navigation Manager. Über den NavigationManager wird von einem Level zum
 * Nächsten navigiert. Er Verwaltet den aktuellen Screen.
 */
public class NavigationManager {
	private Game game;
	private LevelManager levelManager;

	public NavigationManager(final Game newGame) {
		this.game = newGame;
		levelManager = new LevelManager();

		navigateToWorldMap();
		// game.setScreen(new DefaultLevel());

	}

	public final void navigateToLevel(final String levelID) {
		try {
			if (game.getScreen() != null) {
				game.getScreen().dispose();
			}
			Screen screen = levelManager.getScreenByLevelID(levelID);
			game.setScreen(screen);

		} catch (LevelLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final void navigateToWorldMap() {
		navigateToLevel(levelManager.getDefaultClassName());
	}

	public CityObject getCityObject(String levelID) throws LevelNotFoundException {		
		return levelManager.getCityObject(levelID);
		
	}

	public GameObject getGameObject(String levelID) throws LevelNotFoundException {
		return levelManager.getGameObject(levelID);
	}

}
