package com.haw.projecthorse.score;

import com.haw.projecthorse.score.json.Score;


/*
 * Singleton class to handle with the levels
 */

public interface ScoreManager {
	/**
	 * @return the loaded score or null.
	 */
	public Score getLoadedScore();
	
	/**
	 * Load a score and sets it as loaded
	 * @param scoreID ID of the score to load
	 * @return the loaded score
	 */
	public Score loadScore(int scoreID);
	
	/**
	 * saves the loaded score
	 */
	public void saveLoadedScore();
	
	/**
	 * @param scoreID ID of the score
	 * @return true if there is a saved score with the given scoreID, else returns false.
	 */
	public boolean scoreExists(int scoreID);
}
