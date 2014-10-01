package com.haw.projecthorse.gamemanager.navigationmanager.json;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelLoadException;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.level.Level;

/**
 * Der Level Manager das Laden eines Levels zuständig.
 * Außerdem liefert er Information anhand der LevelID
 */
public class LevelManager {

	private GameConfigtImpl config = null;
	

	public LevelManager() {
		loadJson();
		
	}

	public final GameConfig getGameConfig() {
		return config;
	}
	
	
	private void loadJson() {
		Json json = new Json();

		String jsonText;
		try {
			jsonText = readGameConfigFile();
			config = json.fromJson(GameConfigtImpl.class, jsonText);
		} catch (IOException e) {
			System.err.println("!!!Die GameConfig.json Datei konnte nicht geladen werden!!! \n Verzeichnis prüfen");
			e.printStackTrace();
		}
	}

	private String readGameConfigFile() throws IOException {
		FileHandle file = Gdx.files.internal("json/GameConfig.json");	
		return file.readString();
	}

	public final Screen getScreenByLevelID(final String levelID) throws LevelLoadException {
		String className = getClassByLevelID(levelID);
		
		try {
			Class<?> clazz = Class.forName(className);
			Level level = (Level) clazz.newInstance();
			level.setLevelID(levelID);
			return level;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new LevelLoadException(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
		
			throw new LevelLoadException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			throw new LevelLoadException(new LevelNotFoundException(className));
		}

		
	}

	

	private String getClassByLevelID(final String levelID) {
		try {
			return config.getClassNameByLevelID(levelID);
		} catch (LevelNotFoundException e) {
			return config.getDefaultClassName();
		}
	}

	public final CityObject getCityObject(final String levelID) throws LevelNotFoundException {
		return config.getCityByLevelID(levelID);
	}

	public final GameObject getGameObject(final String levelID) throws LevelNotFoundException {		
		return config.getGameByLevelID(levelID);
	}
	

}
