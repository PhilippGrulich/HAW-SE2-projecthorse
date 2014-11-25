package com.haw.projecthorse.gamemanager.navigationmanager;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelLoadException;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.LevelManager;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;
import com.haw.projecthorse.intputmanager.InputManager;

/**
 * Navigation Manager. über den NavigationManager wird von einem Level zum
 * N�chsten navigiert. Er Verwaltet den aktuellen Screen.
 */
public class NavigationManagerImpl implements NavigationManager {
	private Game game;
	private Stack<String> levelIDHistory = new Stack<String>();
	private LevelManager levelManager;

	public NavigationManagerImpl(final Game newGame) {
		this.game = newGame;
		levelManager = new LevelManager();

		// game.setScreen(new DefaultLevel());

	}

	@Override
	public final void navigateToLevel(final String levelID) {
		Gdx.app.log("NavigationManager", "NavigateToLevel");
		try {
			if (game.getScreen() != null) {
				InputManager.clear();
				game.getScreen().dispose();
				game.setScreen(null);
			}
			if (levelIDHistory.isEmpty() || !levelIDHistory.peek().equals(levelID))
				levelIDHistory.push(levelID);
			Screen screen = levelManager.getScreenByLevelID(levelID);
			game.setScreen(screen);

		} catch (LevelLoadException e) {
			e.printStackTrace();
			navigateToLevel("worldmap");
		}
	}

	@Override
	public final void navigateToWorldMap() {
		navigateToLevel("worldmap");
	}

	@Override
	public final CityObject getCityObject(final String levelID) throws LevelNotFoundException {
		return levelManager.getCityObject(levelID);

	}

	@Override
	public final GameObject getGameObject(final String levelID) throws LevelNotFoundException {
		return levelManager.getGameObject(levelID);
	}

	@Override
	public final MenuObject getMenuObject(final String levelID) throws LevelNotFoundException {
		return levelManager.getMenuObject(levelID);
	}

	@Override
	public final GameConfig getGameConfig() {
		return levelManager.getGameConfig();
	}

	@Override
	public void navigateToMainMenu() {
		navigateToLevel("mainMenu");

	}

	@Override
	public String getCurrentLevelID() {
		return levelIDHistory.peek();
	}

	@Override
	public void navigateBack() {
		if ((levelIDHistory.size() > 1))
			levelIDHistory.pop();
		if (!levelIDHistory.isEmpty())
			navigateToLevel(levelIDHistory.peek());

	}

}
