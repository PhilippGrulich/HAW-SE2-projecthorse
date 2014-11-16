package com.haw.projecthorse.savegame;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.haw.projecthorse.savegame.json.SaveGame;
import com.haw.projecthorse.savegame.json.SaveGameImpl;


/*
 * Utility Klasse zum Verwalten des Spielstands
 */

public abstract class SaveGameManager {
	private static final String gameDirectory = "assets/json/saved/";
	private static final FilenameFilter savedGamesFilter = new FilenameFilter() {
		
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".game");
		}
	};
	private static Map<Integer, SaveGame> savedGames = new HashMap<Integer, SaveGame>();
	private static Integer loadedGameID = null;
	
	/**
	 * @return Der geladene Spielstand oder null.
	 */
	public static SaveGame getLoadedGame() {
		if (loadedGameID == null) {
			return null;
		}
		return savedGames.get(loadedGameID);
	}
	
	/**
	 * Lädt einen gespeicherten Spielstand und setzt ihn als "geladen"
	 * @param gameID ID des zu ladenen Spielstands
	 * @return Der geladene Spielstand
	 */
	public static SaveGame loadSavedGame(int gameID) {
		if (!gameExists(gameID)) {
			savedGames.put(gameID, new SaveGameImpl(gameID));
		}
		
		loadedGameID = gameID;
		return getLoadedGame();
	}
	
	/**
	 * Speichert den aktuell geladene Spielstand
	 */
	public static void saveLoadedGame() {
		if (loadedGameID != null) {
			Json json = new Json();
			FileHandle scoreFile = Gdx.files.local(gameDirectory).child(loadedGameID + ".game");
			scoreFile.writeString(json.prettyPrint(getLoadedGame()), false);
		}
	}
	
	/**
	 * @param gameID ID des Spielstand
	 * @return true wenn es einen Spielstand mit der angegebenen gameID gibt, ansonsten false
	 */
	public static boolean gameExists(int gameID) {
		if (savedGames.size() == 0) {
			loadScores();
		}
		
		return savedGames.containsKey(gameID);
	}
	
	private static void loadScores() {
		Json json = new Json();
		SaveGame score = null;
		FileHandle dir = Gdx.files.local(gameDirectory);
		
		for (FileHandle file : dir.list(savedGamesFilter)) {
			score = json.fromJson(SaveGameImpl.class, file);
			savedGames.put(score.getID(), score);
		}
	}
	
	/**
	 * Liefert eine Liste mit (ID, Name)-Paaren, wobei ID die SaveGameID
	 * ist und Name für den Namen des Pferdes in diesem Spiel steht.
	 * @return Eine ArrayList mit (ID, Name)-Pairs.
	 */
	public static ArrayList<Pair<Integer, String>> getSaveGameList() {
		ArrayList<Pair<Integer, String>> games = new ArrayList<Pair<Integer, String>>();
		
		if (savedGames.size() == 0) {
			loadScores();
		}
		
		for (Integer id : savedGames.keySet()) {
			games.add(new Pair<Integer, String>(id, savedGames.get(id).getHorseName()));
		}
		
		return games;
	}
	
	public static class Pair<K, V> {
		private K key;
		private V value;
		
		public Pair(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
	}
}
