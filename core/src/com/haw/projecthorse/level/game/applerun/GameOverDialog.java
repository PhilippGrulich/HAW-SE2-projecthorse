package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;

/**
 * Erstellt einen Dialog um das Spiel nochmal neu zu starten.
 * 
 * @author Philipp
 * @version 1.0
 */
public class GameOverDialog extends Dialog {

	/**
	 * Konstruktor.
	 * 
	 * @param score 
	 */
	public GameOverDialog(final int score) {

		super(String.format("Du hast %s Punke. \nSuper:) \nWillst du noch eine Runde \nspielen?", score));
		super.addButton("Los gehts!:)", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				GameManagerFactory.getInstance().navigateToLevel(GameManagerFactory.getInstance().getCurrentLevelID());

			}

		});

		super.addButton("Nein gerade nicht.", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				GameManagerFactory.getInstance().navigateBack();

			}

		});
	}
}
