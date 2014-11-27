package com.haw.projecthorse.level.util.overlay.popup;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.settings.Settings;

/**
 * Dieses Popup ist speziell für die Verwendung in Mini Spielen gedacht. Es
 * bietet dem Nutzer zugriff auf die Funktionen: Musik : An/Aus Spiel Verlassen
 * Weiter Spielen
 * 
 * @author Philipp
 *
 */
public class GamePausePopup extends Popup implements Observer {
	
	private Settings setting = GameManagerFactory.getInstance().getSettings();
	private ImageTextButton musicButton;	
	private ImageTextButton soundButton;

	public GamePausePopup() {

		musicButton = createButton("", new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				setting.setMusicState(!setting.getMusicState());
				event.cancel();
			}

		});

		soundButton = createButton("", new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				setting.setSoundState(!setting.getSoundState());
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

		setTextes();
		setting.addObserver(this);

	}

	private void setTextes() {
		if (setting.getMusicState())
			musicButton.setText("Musik ist AN");
		else
			musicButton.setText("Musik ist AUS");

		if (setting.getSoundState())
			soundButton.setText("Sound ist AN");
		else
			soundButton.setText("Sound ist AUS");
	}

	private ImageTextButton createButton(String label, ChangeListener inputListener) {
		ImageTextButton btn = super.createButton(label);
		btn.addListener(inputListener);
		this.addActor(btn);
		return btn;
	}

	@Override
	public void update(Observable observable, Object data) {
		setTextes();
	}

}
