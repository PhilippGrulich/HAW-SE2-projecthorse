package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

/**
 * Implementierung des CityObject Interfaces.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
class CityObjectImpl implements CityObject {
	private String name = "";
	private String className = "";
	private String levelID = "";
	private String info = "";
	private HashMap<String, String> parameter = new HashMap<String, String>();
	private GameObjecttImpl[] games = new GameObjecttImpl[0];

	@Override
	public final String getCityName() {
		return name;
	}

	@Override
	public final String getLevelID() {
		return levelID;
	}

	@Override
	public final GameObject[] getGames() {
		return games;

	}

	/**
	 * Liefert die {@link GameObjecttImpl} zu einer LevelID.
	 * 
	 * @param otherLevelID
	 *            gesuchte LevelID
	 * @return {@link GameObjecttImpl}
	 */
	public final GameObjecttImpl getGameByLevelID(final String otherLevelID) {
		for (GameObjecttImpl game : games) {
			if (game.getLevelID().equals(otherLevelID)) {
				return game;
			}
		}
		return null;
	}

	/**
	 * Liefert den Klassennamen zu einer LevelID.
	 * 
	 * @param otherLevelID
	 *            gesuchte LevelID
	 * @return String java Klassennamen
	 */
	public final String getClassByLevelID(final String otherLevelID) {
		if (this.levelID.equals(otherLevelID)) {
			return this.className;
		} else {
			GameObjecttImpl game = getGameByLevelID(otherLevelID);
			if (game != null) {
				return game.getClassName();
			}

		}
		return null;

	}

	public final String getClassName() {
		return className;
	}

	@Override
	public final HashMap<String, String> getParameter() {
		return parameter;
	}

	@Override
	public String getCityInfo() {
		return info;
	}

}
