package com.haw.projecthorse.gamemanager.navigationmanager.json;

public class CityObject {
	private String name = "";
	private String className = "";
	private String levelID = "";
	private GameObject[] games = new GameObject[0];

	public final String getCityName() {
		return name;
	}

	public final String getLevelID() {
		return levelID;
	}

	final String getClassName() {
		return className;
	}

	public final String[] getGameTitles() {

		String[] gameTitles = new String[games.length];
		for (int i = 0; i < games.length; i++) {
			gameTitles[i] = games[i].getGameTitle();
		}
		return gameTitles;

	}

	public final String getClassByLevelID(final String otherLevelID) {
		if (this.levelID.equals(otherLevelID)) {
			return this.className;
		} else {
			GameObject game = getGameByLevelID(otherLevelID);
			if (game != null) {
				return game.getClassName();
			}

		}
		return null;

	}

	public final GameObject getGameByLevelID(final String otherLevelID) {
		for (GameObject game : games) {
			if (game.getLevelID().equals(otherLevelID)) {
				return game;
			}
		}
		return null;
	}

}
