package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.navbar.NavBar;
import com.haw.projecthorse.level.util.overlay.popup.GamePausePopup;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

public class NavbarPauseButton extends NavbarButton {

	public NavbarPauseButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.PAUSE);
		this.setWidth(imageButton.getHeight());
		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(
					com.badlogic.gdx.scenes.scene2d.InputEvent event, float x,
					float y, int pointer, int button) {
				Gdx.app.log("PAUSE Button", "PAUSE BUTTON CLick");
				NavBar navBar = NavbarPauseButton.this.getNavigationBar();

				Popup p = new GamePausePopup();
				Overlay overlay = navBar.getOverlay();
				overlay.getLevel().pause();
				overlay.showPopup(p);

				return true;
			};

		});
	}
}
