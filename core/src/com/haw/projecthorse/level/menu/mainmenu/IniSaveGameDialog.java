package com.haw.projecthorse.level.menu.mainmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;
import com.haw.projecthorse.level.util.uielements.TextInputField;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.savegame.json.SaveGame;

public class IniSaveGameDialog extends Dialog {

	private TextInputField textInput;

	/**
	 * Dialog für um ein Neuen Spieler anzulegen. 
	 * @param  saveGameID Id des SaveGames
	 */
	public IniSaveGameDialog(final int saveGameID) {
		super("Hallo Reisende. \n Wie lautet dein Name?");

		textInput = new TextInputField("Name");
		textInput.setBlinkTime(1);
		textInput.setCursorPosition(0);
		textInput.setFillParent(true);
		textInput.setRightAligned(false);
		textInput.setWidth(popupWidth - 100);
		textInput.setMaxLength(20);

		addActor(textInput);

		addButton("Los", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				SaveGame game = SaveGameManager.loadSavedGame(saveGameID);
				game.setPlayerName(textInput.getText());
				SaveGameManager.saveLoadedGame();
				GameManagerFactory.getInstance().navigateToWorldMap();

			}
		});

		addButton("Abbrechen", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				getOverlay().disposePopup();

			}
		});
	}

}
