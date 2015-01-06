package com.haw.projecthorse.level.util.overlay.popup;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.settings.Settings;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;

/**
 * Dieses Popup ist speziell für die Verwendung in Mini Spielen gedacht. Es
 * bietet dem Nutzer zugriff auf die Funktionen: Musik : An/Aus Spiel Verlassen
 * Weiter Spielen
 * 
 * @author Philipp
 *
 */
public class SettingsPopup extends Popup implements Observer {

	private Settings setting = GameManagerFactory.getInstance().getSettings();
	private ImageTextButton musicButton;
	private ImageTextButton soundButton;
	private ImageTextButton accButton;

	public SettingsPopup() {

		musicButton = createButton("", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {

				setting.setMusicState(!setting.getMusicState());
				event.cancel();
			}

		});

		soundButton = createButton("", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				setting.setSoundState(!setting.getSoundState());
				event.cancel();
			}

		});
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
			accButton = createButton("", new ChangeListener() {

				@Override
				public void changed(final ChangeEvent event, final Actor actor) {
					setting.setAccelerometerState(!setting.getAccelerometerState());
					event.cancel();
				}

			});

		}
		createButton("Weiter", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				SettingsPopup.this.getOverlay().disposePopup();
				event.cancel();
			}

		});

		setTextes();
		setting.addObserver(this);

	}

	private void setTextes() {
		if (accButton != null) {
			if (setting.getAccelerometerState()) {
				accButton.setText("Neigungssensor ist AN");
			} else {
				accButton.setText("Neigungssensor ist AUS");
			}
		}
		if (setting.getMusicState()) {
			musicButton.setText("Musik ist AN");
		} else {
			musicButton.setText("Musik ist AUS");
		}

		if (setting.getSoundState()) {
			soundButton.setText("Sound ist AN");
		} else {
			soundButton.setText("Sound ist AUS");
		}
	}

	private ImageTextButton createButton(final String label, final ChangeListener inputListener) {
		ImageTextButton btn = new ButtonLarge(label, inputListener);
		btn.addListener(inputListener);
		this.addActor(btn);
		return btn;
	}

	@Override
	public final void update(final Observable observable, final Object data) {
		setTextes();
	}

}
