package com.haw.projecthorse.gamemanager.navigationmanager.exception;

import com.badlogic.gdx.Gdx;

public class LevelNotFoundException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String levelID;
	public LevelNotFoundException(String levelID) {
		this.levelID = levelID;
	}

	

	@Override
	public void printStackTrace() {
		
		Gdx.app.error("LevelNotFoundException", "The Level "+levelID+" can´t Found. Please check the gameConfig.json",this);
	}
	
	

}
