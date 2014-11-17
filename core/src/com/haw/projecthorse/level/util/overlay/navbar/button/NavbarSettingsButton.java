package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.navbar.NavBar;
import com.haw.projecthorse.level.util.overlay.popup.GamePausePopup;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

public class NavbarSettingsButton extends NavbarButton {

	public NavbarSettingsButton() {
		ImageButton imageButton = new ImageButton(getDrawable());

		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(
					com.badlogic.gdx.scenes.scene2d.InputEvent event, float x,
					float y, int pointer, int button) {
				Gdx.app.log("Settings Button", "Settings BUTTON CLick");
				

				return true;
			};

		});
	}

	private Drawable getDrawable() {

		Drawable drawable = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "settingsIcon"));
		return drawable;
	}

}
