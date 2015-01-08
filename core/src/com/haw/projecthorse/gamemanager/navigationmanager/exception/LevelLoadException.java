package com.haw.projecthorse.gamemanager.navigationmanager.exception;

import com.badlogic.gdx.Gdx;

/**
 * Diese Exception wird aufgerufen wenn das Level nicht geladen werden konnte.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public class LevelLoadException extends Exception {

	Throwable e;

	/**
	 * Konstruktor für die Exception. Sie Wrappt die ursprüngliche Fehler
	 * Exception.
	 * 
	 * @param e
	 *            Exception die eigentlich geworfen wurde.
	 */
	public LevelLoadException(final Throwable e) {
		this.e = e;

	}

	@Override
	public void printStackTrace() {
		Gdx.app.error("LevelLoadException", "The Level can't be loaded");
		e.printStackTrace();
	};

	/**
	 * SerialVerionUID.
	 */
	private static final long serialVersionUID = 1L;

}
