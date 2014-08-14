package com.haw.projecthorse.gamemanager.navigationmanager.json;

import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;

/**
 * GameConfigJSON ist die Representation der GameConfig.json Datei.
 * @author Philipp
 *
 */
public class GameConfigJSON {
	private String title = "";
	private String worldmap = "";
	private CityObject[] cities = new CityObject[0];

	public final String getGameTitle() {
		return title;
	}

	public final String getWorldmapClassName() {
		return worldmap;
	}

	public final String[] getCityNames() {
		String[] cityNames = new String[cities.length];
		for (int i = 0; i < cities.length; i++) {
			cityNames[i] = cities[i].getCityName();
		}
		return cityNames;
	}

	public final String getClassNameByLevelID(final String levelID)
			throws LevelNotFoundException {
		for (CityObject city : cities) {
			String className = city.getClassByLevelID(levelID);
			if (className != null) {
				return className;
			}
		}
		throw new LevelNotFoundException();

	}

	public final CityObject getCityByLevelID(final String levelID)
			throws LevelNotFoundException {
		for (CityObject city : cities) {
			if (city.getLevelID().equals(levelID)) {
				return city;
			}
		}
		throw new LevelNotFoundException();
	}

	public final GameObject getGameByLevelID(final String levelID)
			throws LevelNotFoundException {
		for (CityObject city : cities) {
			GameObject game = city.getGameByLevelID(levelID);
			if (game != null) {
				return game;
			}
		}
		throw new LevelNotFoundException();

	}
}
