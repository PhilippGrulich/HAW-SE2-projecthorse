package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.util.HashMap;

/**
 * Implementierung des {@link MenuObject} Interfaces.
 * 
 * @author Philipp
 * @version 1.0
 */
public class MenuObjectImpl implements MenuObject {
	private String className = "";
	private String levelID = "";
	private HashMap<String, String> parameter = new HashMap<String, String>();

	@Override
	public String getLevelID() {
		return levelID;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public final HashMap<String, String> getParameter() {
		return parameter;
	}

}
