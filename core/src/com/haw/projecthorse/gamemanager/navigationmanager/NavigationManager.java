package com.haw.projecthorse.gamemanager.navigationmanager;

import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameConfig;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;

public interface NavigationManager {

	void navigateToLevel(String levelID);

	void navigateToWorldMap();
	
	void navigateToMainMenu();

	CityObject getCityObject(String levelID) throws LevelNotFoundException;

	GameObject getGameObject(String levelID) throws LevelNotFoundException;

	GameConfig getGameConfig();



}
