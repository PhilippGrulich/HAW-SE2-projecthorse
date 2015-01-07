package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

/**
 * Dieser Button zeigt ein InfoSymbol an.
 * 
 * @author Philipp
 * @version 1.0
 */
public abstract class NavbarCityInfoButton extends NavbarButton {
	/**
	 * Konstruktor.
	 */
	public NavbarCityInfoButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.INFO);

		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer,
					final int button) {
				clicked();

				return true;
			};

		});
		this.setWidth(imageButton.getHeight());
	}

	/**
	 * Diese Methode wird aufgeruffen wenn der Button geklickt wird.
	 */
	public abstract void clicked();
}
