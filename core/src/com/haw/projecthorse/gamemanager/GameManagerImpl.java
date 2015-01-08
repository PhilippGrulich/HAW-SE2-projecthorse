package com.haw.projecthorse.gamemanager;

import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManager;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.MenuObject;
import com.haw.projecthorse.gamemanager.settings.Settings;
import com.haw.projecthorse.gamemanager.settings.SettingsImpl;
import com.haw.projecthorse.platform.Platform;

/**
 * Dies ist die Implementierung des {@link GameManager} Interfaces.
 * 
 * @author Philipp
 * @version 1.0
 */
final class GameManagerImpl implements GameManager {
	private NavigationManager navigationManager;
	private Platform platform;
	private static GameManagerImpl instance;

	/**
	 * Privater Konstruktor.
	 */
	private GameManagerImpl() {

	}

	/**
	 * Liefert eine Instanz des Singletons.
	 * 
	 * @return {@link GameManagerImpl}
	 */
	public static GameManagerImpl getInstance() {
		if (instance == null) {
			instance = new GameManagerImpl();
		}
		return instance;
	}

	@Override
	public void navigateToLevel(final String levelID) {
		navigationManager.navigateToLevel(levelID);
	}

	@Override
	public void navigateToWorldMap() {
		navigationManager.navigateToWorldMap();
	}

	@Override
	public void navigateToMainMenu() {
		navigationManager.navigateToMainMenu();

	}

	@Override
	public GameConfig getGameConfig() {
		return navigationManager.getGameConfig();
	}

	@Override
	public CityObject getCityObject(final String levelID) throws LevelNotFoundException {
		return navigationManager.getCityObject(levelID);
	}

	@Override
	public GameObject getGameObject(final String levelID) throws LevelNotFoundException {
		return navigationManager.getGameObject(levelID);
	}

	@Override
	public MenuObject getMenuObject(final String levelID) throws LevelNotFoundException {
		return navigationManager.getMenuObject(levelID);
	}

	@Override
	public Object getScoreManager() {
		// TODO Spielstand muss zur�ck gegeben werden.
		return null;
	}

	public void setNavigationManager(final NavigationManagerImpl newNaviationManager) {
		this.navigationManager = newNaviationManager;
	}

	@Override
	public Settings getSettings() {
		return SettingsImpl.getInstance();
	}

	@Override
	public String getCurrentLevelID() {
		return navigationManager.getCurrentLevelID();
	}

	@Override
	public void navigateBack() {
		navigationManager.navigateBack();

	}

	void setPlatform(final Platform platform) {
		this.platform = platform;
	}

	@Override
	public Platform getPlatform() {
		return platform;
	}

}
