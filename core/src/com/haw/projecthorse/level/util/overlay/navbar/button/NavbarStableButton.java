package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

public class NavbarStableButton extends NavbarButton {

	public NavbarStableButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.HOME);

		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("Settings Button", "Settings BUTTON CLick");

				return true;
			};

		});
		this.setWidth(imageButton.getHeight());
	}
}
