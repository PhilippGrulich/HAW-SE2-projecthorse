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
 * Der Level Manager das Laden eines Levels zuständig. Außerdem liefert er
 * Information anhand der LevelID.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public class LevelManager {

	private GameConfigtImpl config = null;

	/**
	 * Konstruktor für den LevelManager.
	 */
	public LevelManager() {
		loadJson();

	}

	public final GameConfig getGameConfig() {
		return config;
	}

	/**
	 * Mit dieser Methode wird die GameConfig.json geladen. Diese muss sich im
	 * Android Ordner "asset/json" befinden.
	 */
	private void loadJson() {
		Json json = new Json();

		String jsonText;
		try {
			Gdx.app.log("LevelManager", "LadeJSON");
			jsonText = readGameConfigFile();
			Gdx.app.log("LevelManager", "JSON TO Object");
			config = json.fromJson(GameConfigtImpl.class, jsonText);
			Gdx.app.log("LevelManager", "Fertig");
		} catch (IOException e) {
			System.err.println("!!!Die GameConfig.json Datei konnte nicht geladen werden!!! \n Verzeichnis prüfen");
			e.printStackTrace();
		}
	}

	/**
	 * Liest den GameConfig.json ein.
	 * 
	 * @return String
	 * @throws IOException
	 *             falls die Datei nicht gefundne wird.
	 */
	private String readGameConfigFile() throws IOException {
		FileHandle file = Gdx.files.internal("json/GameConfig.json");
		return file.readString();
	}

	/**
	 * Mit dieser Methode wird ein {@link Screen} anhand einer LevelID geladen.
	 * Erst wird der Klassennamen ermittelt und dann wird eine neue Instance des
	 * Levels erstellt. Da in diesem Spiel alle Level von der Klasse
	 * {@link Level} erben können wir auf diese Carsten.
	 * 
	 * @param levelID
	 *            Gesuchte level ID
	 * @return {@link Screen} des geladenen Levels
	 * @throws LevelLoadException
	 *             Falls die LevelID nicht in der {@link GameConfig} ist.
	 */
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

	/**
	 * Versucht den Java Klassennamen zu finden. Falls dieser nicht vorhanden
	 * ist wird der Default Klassen namen geladen. == MainMenu
	 * 
	 * @param levelID
	 *            gesuchte LevelID
	 * @return Klassennamen
	 */
	private String getClassByLevelID(final String levelID) {
		try {
			return config.getClassNameByLevelID(levelID);
		} catch (LevelNotFoundException e) {
			return config.getDefaultClassName();
		}
	}

	/**
	 * Delegiert den Aufruf an die {@link GameConfig}.
	 * 
	 * @param levelID
	 *            LevelID
	 * @return {@link CityObject}
	 * @throws LevelNotFoundException
	 *             falls die LevelID nicht vergeben ist.
	 */
	public final CityObject getCityObject(final String levelID) throws LevelNotFoundException {
		return config.getCityByLevelID(levelID);
	}

	/**
	 * Delegiert den Aufruf an die {@link GameConfig}.
	 * 
	 * @param levelID
	 *            LevelID
	 * @return {@link CityObject}
	 * @throws LevelNotFoundException
	 *             falls die LevelID nicht vergeben ist.
	 */
	public final GameObject getGameObject(final String levelID) throws LevelNotFoundException {
		return config.getGameByLevelID(levelID);
	}

	/**
	 * Delegiert den Aufruf an die {@link GameConfig}.
	 * 
	 * @param levelID
	 *            LevelID
	 * @return {@link CityObject}
	 * @throws LevelNotFoundException
	 *             falls die LevelID nicht vergeben ist.
	 */
	public final MenuObject getMenuObject(final String levelID) throws LevelNotFoundException {
		return config.getMenuByLevelID(levelID);
	}

}
