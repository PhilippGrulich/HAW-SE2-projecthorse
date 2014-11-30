package com.haw.projecthorse.level.menu.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.savegame.json.SaveGame;

public class SavegameButtonListener extends ChangeListener {
	
	private int saveGameID;
	
	public SavegameButtonListener(int saveGameID) {
		super();
		this.saveGameID = saveGameID;
	}
	
	private SaveGame loadSavegame(){
		return SaveGameManager.loadSavedGame(saveGameID);
	}

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		System.out.println("Savegame Button für Spiel :"+saveGameID+" wurde gedrückt");
		
		if (SaveGameManager.gameExists(saveGameID)) {
			loadSavegame();		
			GameManagerFactory.getInstance().navigateToWorldMap();
		} else {
//			overlay.showPopup(new InitNamePopup());
			Gdx.input.getTextInput(new TextInputListener() {
				
				@Override
				public void input(String text) {
					SaveGame game = loadSavegame();
					game.setPlayerName(text);
					GameManagerFactory.getInstance().navigateToWorldMap();
				}
				
				@Override
				public void canceled() {
					// nothing to do here
				}
			}, "Hallo Reisende. Wie lautet dein Name?", "Dein Name");
		}
	}

	

}
