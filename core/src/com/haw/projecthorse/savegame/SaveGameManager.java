package com.haw.projecthorse.savegame;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.haw.projecthorse.savegame.json.SaveGame;
import com.haw.projecthorse.savegame.json.SaveGameImpl;

/**
 * Utility Klasse zum Verwalten des Spielstands.
 * 
 * @author Oliver
 * @version 1.1
 */

public abstract class SaveGameManager {
	private static final String GAME_DIRECTORY = "saved/";
	private static final FilenameFilter SAVED_GAME_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			return name.endsWith(".game");
		}
	};
	private static Map<Integer, SaveGame> savedGames = new HashMap<Integer, SaveGame>();
	private static Integer loadedGameID = null;

	/**
	 * Liefert den aktuell geladenen Spielstand.
	 * 
	 * @return Der geladene Spielstand oder null.
	 */
	public static SaveGame getLoadedGame() {
		if (loadedGameID == null) {
			return null;
		}
		return savedGames.get(loadedGameID);
	}

	/**
	 * Lädt einen gespeicherten Spielstand und setzt ihn als "geladen".
	 * 
	 * @param gameID
	 *            ID des zu ladenen Spielstands
	 * @return Der geladene Spielstand
	 */
	public static SaveGame loadSavedGame(final int gameID) {
		loadedGameID = gameID;

		if (!gameExists(gameID)) {
			savedGames.put(gameID, new SaveGameImpl(gameID));
			saveLoadedGame();
		}
		return getLoadedGame();
	}

	/**
	 * Speichert den aktuell geladene Spielstand.
	 */
	public static void saveLoadedGame() {
		if (loadedGameID != null) {
			Json json = new Json();
			FileHandle scoreFile = Gdx.files.local(GAME_DIRECTORY).child(
					loadedGameID + ".game");
			scoreFile.writeString(json.prettyPrint(getLoadedGame()), false);
		}
	}

	/**
	 * Prüft, ob ein Spiel mit der angegebenen ID gibt.
	 * 
	 * @param gameID
	 *            ID des Spielstand
	 * @return true wenn es einen Spielstand mit der angegebenen gameID gibt,
	 *         ansonsten false
	 */
	public static boolean gameExists(final int gameID) {
		if (savedGames.size() == 0) {
			loadScores();
		}

		return savedGames.containsKey(gameID);
	}

	/**
	 * Lädt alle vorhandenen Spielstände.
	 */
	private static void loadScores() {
		Json json = new Json();
		SaveGame score = null;
		FileHandle dir = Gdx.files.local(GAME_DIRECTORY);

		for (FileHandle file : dir.list(SAVED_GAME_FILTER)) {
			score = json.fromJson(SaveGameImpl.class, file);
			savedGames.put(score.getID(), score);
		}
	}

	/**
	 * Liefert eine Liste mit (ID, Name)-Paaren, wobei ID die SaveGameID ist und
	 * Name f�r den Namen des Pferdes in diesem Spiel steht.
	 * 
	 * @return Eine Map der Spielstände als (ID, Name des Kindes)-Paare .
	 */
	public static Map<Integer, String> getSaveGameList() {
		if (savedGames.size() == 0) {
			loadScores();
		}

		HashMap<Integer, String> games = new HashMap<Integer, String>();

		for (Integer id : savedGames.keySet()) {
			games.put(id, savedGames.get(id).getPlayerName());
		}

		return games;
	}
}
