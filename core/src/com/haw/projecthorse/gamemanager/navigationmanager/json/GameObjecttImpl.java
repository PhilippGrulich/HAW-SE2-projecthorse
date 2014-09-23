package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

class GameObjecttImpl implements GameObject {
	private String title = "";
	private String className = "";
	private String levelID = "";
	private HashMap<String, String> parameter = new HashMap<String, String>();
	
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

	@Override
	public HashMap<String, String> getParameter() {
		return parameter;
	}
}
//