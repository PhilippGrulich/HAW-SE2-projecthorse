package com.haw.projecthorse.gamemanager.navigationmanager.json;

import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;

/**
 * GameConfigJSON ist die Representation der GameConfig.json Datei.
 * 
 * @author Philipp
 *
 */
class GameConfigtImpl implements GameConfig {
	private String title = "";

	private CityObjectImpl[] cities = new CityObjectImpl[0];
	private MenuObjectImpl[] menus = new MenuObjectImpl[0];
	@Override
	public final String getGameTitle() {
		return title;
	}

	

	public final String[] getCityNames() {
		String[] cityNames = new String[cities.length];
		for (int i = 0; i < cities.length; i++) {
			cityNames[i] = cities[i].getCityName();
		}
		return cityNames;
	}

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
		throw new LevelNotFoundException();

	}

	public final CityObjectImpl getCityByLevelID(final String levelID) throws LevelNotFoundException {
		for (CityObjectImpl city : cities) {
			if (city.getLevelID().equals(levelID)) {
				return city;
			}
		}
		throw new LevelNotFoundException();
	}

	public final GameObjecttImpl getGameByLevelID(final String levelID) throws LevelNotFoundException {
		for (CityObjectImpl city : cities) {
			GameObjecttImpl game = city.getGameByLevelID(levelID);
			if (game != null) {
				return game;
			}
		}
		throw new LevelNotFoundException();

	}



	public String getDefaultClassName() {
		return menus[0].getClassName();
	}



	
}