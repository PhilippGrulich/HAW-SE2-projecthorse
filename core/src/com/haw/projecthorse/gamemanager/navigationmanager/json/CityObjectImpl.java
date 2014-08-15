package com.haw.projecthorse.gamemanager.navigationmanager.json;

class CityObjectImpl implements CityObject {
	private String name = "";
	private String className = "";
	private String levelID = "";
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
	public final String[] getGameLevelIDs() {

		String[] gameTitles = new String[games.length];
		for (int i = 0; i < games.length; i++) {
			gameTitles[i] = games[i].getGameTitle();
		}
		return gameTitles;

	}

	
	public final GameObjecttImpl getGameByLevelID(final String otherLevelID) {
		for (GameObjecttImpl game : games) {
			if (game.getLevelID().equals(otherLevelID)) {
				return game;
			}
		}
		return null;
	}

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

}
