package com.haw.projecthorse.score.json;

public interface Score {
	/**
	 * @return ID of the score as integer
	 */
	int getID();
	
	/**
	 * @return experience points of the score as integer
	 */
	int getEP();
	
	/**
	 * @param toAdd EP to add to the score
	 */
	void addEP(int toAdd);
	
	/**
	 * @return name of the horse in this score
	 */
	String getHorseName();
}
