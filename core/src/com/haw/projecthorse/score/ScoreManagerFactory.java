package com.haw.projecthorse.score;

public abstract class ScoreManagerFactory {
	private static ScoreManager instance = null;
	/**
	 * Returns and creates the singleton instance of ScoreManager
	 * @return the singleton instance of ScoreManager
	 */
	public static ScoreManager getInstance() {
		if (instance == null) {
			instance = new ScoreManagerImpl();
		}
		
		return instance;
	}
}
