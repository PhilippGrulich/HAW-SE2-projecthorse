package com.haw.projecthorse.gamemanager.navigationmanager.json;

class GameObjecttImpl implements GameObject {
	private String title = "";
	private String className = "";
	private String levelID = "";
	
	@Override
	public final String getGameTitle() {
		return title;
	}
	
	@Override
	public final String getLevelID() {
		return levelID;
	}
	
	public final String getClassName() {
		return className;
	}
}
//