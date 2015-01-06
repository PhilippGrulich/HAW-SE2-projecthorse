package com.haw.projecthorse.level.menu.mainmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.savegame.json.SaveGame;

public class SavegameButtonListener extends ChangeListener {
	
	private int saveGameID;
	private Overlay overlay;
	
	public SavegameButtonListener(int saveGameID, Overlay overlay) {
		super();
		this.saveGameID = saveGameID;
		this.overlay = overlay;
	}
	
	private SaveGame loadSavegame(){
		return SaveGameManager.loadSavedGame(saveGameID);
	}

	@Override
	public void changed(final ChangeEvent event, final Actor actor) {
		if (SaveGameManager.gameExists(saveGameID)) {
			loadSavegame();		
			GameManagerFactory.getInstance().navigateToWorldMap();
		} else {
			overlay.showPopup(new IniSaveGameDialog(saveGameID));
		}
	}

	

}
