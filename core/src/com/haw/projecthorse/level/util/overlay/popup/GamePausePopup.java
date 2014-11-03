package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

/**
 * Dieses Popup ist speziell für die Verwendung in Mini Spielen gedacht. Es
 * bietet dem Nutzer zugriff auf die Funktionen: Musik : An/Aus Spiel Verlassen
 * Weiter Spielen
 * 
 * @author Philipp
 *
 */
public class GamePausePopup extends Popup {

	public GamePausePopup() {

		String soundState = "Musik An";
		final boolean sound = GameManagerFactory.getInstance().getSettings()
				.getSoundState();
		if (sound)
			soundState = "Musik Aus";
		createButton(soundState, new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePausePopup.this.getOverlay().disposePopup();
				GameManagerFactory.getInstance().getSettings()
						.setSoundState(!sound);
				event.cancel();

			}

		});

		createButton("Spiel verlassen", new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePausePopup.this.getOverlay().disposePopup();
				GameManagerFactory.getInstance().navigateBack();
				event.cancel();

			}

		});

		createButton("Weiter Spielen", new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePausePopup.this.getOverlay().disposePopup();
				event.cancel();

			}

		});

	}

	private void createButton(String label, ChangeListener inputListener) {
		Button btn = super.createButton(label);
		btn.addListener(inputListener);
		this.addActor(btn);
	}

}
