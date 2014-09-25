package com.haw.projecthorse.level.mainmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.score.ScoreManagerFactory;

public class SavegameButtonListener extends ChangeListener {
	
	private int saveGameID;
	
	public SavegameButtonListener(int saveGameID) {
		super();
		this.saveGameID = saveGameID;
	}
	
	private void loadSavegame(){
		ScoreManagerFactory.getInstance().loadScore(saveGameID);
	}

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		System.out.println("Savegame Button für Spiel :"+saveGameID+" wurde gedrückt");		
		loadSavegame();		
		GameManagerFactory.getInstance().navigateToLevel("worldmap");
	
		
	}

	

}
