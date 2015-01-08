package com.haw.projecthorse.gamemanager.navigationmanager.exception;

import com.badlogic.gdx.Gdx;

/**
 * Diese Exception wird aufgerufen wenn eine LevelID nicht gefunden werden
 * konnte.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public class LevelNotFoundException extends Throwable {

	/**
	 * SerialVerionUID.
	 */
	private static final long serialVersionUID = 1L;

	private String levelID;

	/**
	 * Konstruktor mit der LevelID als Parameter.
	 * 
	 * @param levelID
	 *            Die LevelID welche nicht gefunden wurde.
	 */
	public LevelNotFoundException(final String levelID) {
		this.levelID = levelID;
	}

	@Override
	public void printStackTrace() {

		Gdx.app.error("LevelNotFoundException", "The Level " + levelID
				+ " can't Found. Please check the gameConfig.json", this);
	}

}
