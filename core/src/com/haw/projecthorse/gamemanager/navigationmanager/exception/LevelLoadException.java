package com.haw.projecthorse.gamemanager.navigationmanager.exception;

import com.badlogic.gdx.Gdx;

public class LevelLoadException extends Exception {

	Throwable e;
	public LevelLoadException(Throwable e) {
		this.e= e;
	
	}
	
	@Override
	public void printStackTrace() {
		Gdx.app.error("LevelLoadException", "The Level can't be loaded");
		e.printStackTrace();
	};	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
