package com.haw.projecthorse.gamemanager.navigationmanager.json;

import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;

/**
 * Dieses Object repräsentiert die GameConfig.json und Implementierung des
 * Interfaces {@link GameConfig}.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
class GameConfigtImpl implements GameConfig {
	private String title = "";

	private CityObjectImpl[] cities = new CityObjectImpl[0];
	private MenuObjectImpl[] menus = new MenuObjectImpl[0];

	@Override
	public final String getGameTitle() {
		return title;
	}

	@Override
	public final String[] getCityNames() {
		String[] cityNames = new String[cities.length];
		for (int i = 0; i < cities.length; i++) {
			cityNames[i] = cities[i].getCityName();
		}
		return cityNames;
	}

	/**
	 * Liefert den java Klassennamen zu einer LevelID.
	 * 
	 * @param levelID
	 *            String
	 * @return Java Klassennamen
	 * @throws LevelNotFoundException
	 *             falls das Level nicht in der GameConfig.json steht
	 */
	public final String getClassNameByLevelID(final String levelID) throws LevelNotFoundException {

		for (MenuObjectImpl menu : menus) {
			if (menu.getLevelID().equals(levelID)) {
				return menu.getClassName();
			}
		}

		for (CityObjectImpl city : cities) {
			String className = city.getClassByLevelID(levelID);
			if (className != null) {
				return className;
			}
		}
		throw new LevelNotFoundException(levelID);

	}

	/**
	 * Liefert {@link CityObjectImpl} zu einer LevelID.
	 * 
	 * @param levelID
	 *            LevelID der Stadt
	 * @return CityObject Datei der Stadt
	 * @throws LevelNotFoundException
	 *             Wenn die Stadt nicht vorhanden ist. *
	 */
	public final CityObjectImpl getCityByLevelID(final String levelID) throws LevelNotFoundException {
		for (CityObjectImpl city : cities) {
			if (city.getLevelID().equals(levelID)) {
				return city;
			}
		}
		throw new LevelNotFoundException(levelID);
	}

	/**
	 * Liefert {@link GameObjecttImpl} zu einer LevelID.
	 * 
	 * @param levelID
	 *            LevelID des Spiels
	 * @return GameObject Datei des Spiels
	 * @throws LevelNotFoundException
	 *             Wenn das Spiel nicht vorhanden ist.
	 */
	public final GameObjecttImpl getGameByLevelID(final String levelID) throws LevelNotFoundException {
		for (CityObjectImpl city : cities) {
			GameObjecttImpl game = city.getGameByLevelID(levelID);
			if (game != null) {
				return game;
			}
		}
		throw new LevelNotFoundException(levelID);

	}

	/**
	 * Liefert {@link MenuObjectImpl} zu einer LevelID.
	 * 
	 * @param levelID
	 *            LevelID des Menüs
	 * @return GameObject Datei des Menüs
	 * @throws LevelNotFoundException
	 *             Wenn das Menü nicht vorhanden ist.
	 */
	public final MenuObjectImpl getMenuByLevelID(final String levelID) throws LevelNotFoundException {
		for (MenuObjectImpl menu : menus) {
			if (menu.getLevelID().equals(levelID)) {
				return menu;
			}
		}
		throw new LevelNotFoundException(levelID);

	}

	public String getDefaultClassName() {
		return menus[0].getClassName();
	}

}
