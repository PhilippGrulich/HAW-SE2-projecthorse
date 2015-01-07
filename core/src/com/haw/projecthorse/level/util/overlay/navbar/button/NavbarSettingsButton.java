package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.haw.projecthorse.level.util.overlay.popup.SettingsPopup;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

/**
 * Dieser Button öffnet das Settings Menü.
 * 
 * @author Philipp
 * @version 1.0
 */
public class NavbarSettingsButton extends NavbarButton {
	/**
	 * Konstruktor.
	 */
	public NavbarSettingsButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.SETTINGS);

		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer,
					final int button) {
				getNavigationBar().getOverlay().showPopup(new SettingsPopup());
				Gdx.app.log("Settings Button", "Settings BUTTON CLick");

				return true;
			};

		});
		this.setWidth(imageButton.getHeight());
	}
}
