package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

/**
 * Dieser Button öffnet das PlayerMenu.
 * 
 * @author Philipp
 * @version 1.0
 */
public class NavbarStableButton extends NavbarButton {
	/**
	 * Konstruktor.
	 */
	public NavbarStableButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.HOME);

		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer,
					final int button) {
				Gdx.app.log("Settings Button", "Settings BUTTON CLick");

				GameManagerFactory.getInstance().navigateToLevel("playerMenu");

				return true;
			};

		});
		this.setWidth(imageButton.getHeight());
	}
}
