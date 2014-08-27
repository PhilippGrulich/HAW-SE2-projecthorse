package com.haw.projecthorse.gamemanager;

import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManager;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.gamemanager.settings.Settings;
import com.haw.projecthorse.gamemanager.settings.SettingsImpl;

final class GameManagerImpl implements GameManager {
	private NavigationManager navigationManager;

	/**
	 * Navigiert zu einer Stadt oder einem Spiel welches anhand der LevelID
	 * (String) identifiziert wird.
	 * 
	 * @param String
	 *            leveLID
	 */
	@Override
	public void navigateToLevel(final String levelID) {
		navigationManager.navigateToLevel(levelID);
	}

	/**
	 * Navigiert zur Weltkarte
	 */
	@Override
	public void navigateToWorldMap() {
		navigationManager.navigateToWorldMap();
	}

	@Override
	public GameConfig getGameConfig() {
		return navigationManager.getGameConfig();
	}

	/**
	 * Liefert das CityObject zur�ck falls die LevelID existiert. Wenn nicht
	 * wird eine LevelNotFoundException geworfen.
	 * 
	 * @param levelID
	 * @return {@link CityObjectImpl}
	 * @throws LevelNotFoundException
	 */
	@Override
	public CityObject getCityObject(final String levelID)
			throws LevelNotFoundException {
		return navigationManager.getCityObject(levelID);
	}

	/**
	 * Liefert das GameObject zur�ck falls die LevelID existiert. Wenn nicht
	 * wird eine LevelNotFoundException geworfen.
	 * 
	 * @param levelID
	 * @return {@link GameObjecttImpl}
	 * @throws LevelNotFoundException
	 */
	@Override
	public GameObject getGameObject(final String levelID)
			throws LevelNotFoundException {
		return navigationManager.getGameObject(levelID);
	}

	/**
	 * Liefert eine Referenz auf das Spielstand Modul. *
	 * 
	 * @return Spielstand
	 */
	@Override
	public Object getScoreManager() {
		// TODO Spielstand muss zur�ck gegeben werden.
		return null;
	}

	public void setNavigationManager(
			final NavigationManagerImpl newNaviationManager) {
		this.navigationManager = newNaviationManager;
	}

	public void setScoreManager() {
		// TODO
	}

	@Override
	public Settings getSettings() {
		return SettingsImpl.getInstance();
	}

}
