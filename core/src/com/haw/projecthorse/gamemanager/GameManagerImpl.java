package com.haw.projecthorse.gamemanager;

import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManager;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;

final class GameManagerImpl implements GameManager {
	private NavigationManager naviationManager;

	/**
	 * Navigiert zu einer Stadt oder einem Spiel welches anhand der LevelID
	 * (String) identifiziert wird.
	 * 
	 * @param String
	 *            leveLID
	 */
	@Override
	public void navigateToLevel(final String levelID) {
		naviationManager.navigateToLevel(levelID);
	}

	/**
	 * Navigiert zur Weltkarte
	 */
	@Override
	public void navigateToWorldMap() {
		naviationManager.navigateToWorldMap();
	}

	@Override
	public GameConfig getGameConfig() {
		return naviationManager.getGameConfig();
	}

	/**
	 * Liefert das CityObject zurück falls die LevelID existiert. Wenn nicht
	 * wird eine LevelNotFoundException geworfen.
	 * 
	 * @param levelID
	 * @return {@link CityObjectImpl}
	 * @throws LevelNotFoundException
	 */
	@Override
	public CityObject getCityObject(final String levelID)
			throws LevelNotFoundException {
		return naviationManager.getCityObject(levelID);
	}

	/**
	 * Liefert das GameObject zurück falls die LevelID existiert. Wenn nicht
	 * wird eine LevelNotFoundException geworfen.
	 * 
	 * @param levelID
	 * @return {@link GameObjecttImpl}
	 * @throws LevelNotFoundException
	 */
	@Override
	public GameObject getGameObject(final String levelID)
			throws LevelNotFoundException {
		return naviationManager.getGameObject(levelID);
	}

	/**
	 * Liefert eine Referenz auf das Spielstand Modul. *
	 * 
	 * @return Spielstand
	 */
	@Override
	public Object getScoreManager() {
		// TODO Spielstand muss zurück gegeben werden.
		return null;
	}

	public void setNavigationManager(
			final NavigationManagerImpl newNaviationManager) {
		this.naviationManager = newNaviationManager;
	}

	public void setScoreManager() {
		// TODO
	}

}
