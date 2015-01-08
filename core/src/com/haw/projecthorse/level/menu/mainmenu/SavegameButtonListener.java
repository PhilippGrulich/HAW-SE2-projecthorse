package com.haw.projecthorse.level.menu.mainmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.savegame.json.SaveGame;

/**
 * Buttonlisteer um ein SaveGame zu selektieren.
 * 
 * @author Philipp
 * @version 1.0
 *
 */
public class SavegameButtonListener extends ChangeListener {

	private int saveGameID;
	private Overlay overlay;

	/**
	 * Konstruktor.
	 * 
	 * @param saveGameID
	 *            Savegame
	 * @param overlay
	 *            Overlay
	 */
	public SavegameButtonListener(final int saveGameID, final Overlay overlay) {
		super();
		this.saveGameID = saveGameID;
		this.overlay = overlay;
	}

	/**
	 * Lädt das Savegame.
	 * 
	 * @return Savegame.
	 */
	private SaveGame loadSavegame() {
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
