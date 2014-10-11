package com.haw.projecthorse.level.util.overlay.button.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.button.OvelayButton;

public class PauseButton extends OvelayButton {

	
	

	private Drawable getDrawable() {
		TextureAtlas atlant = AssetManager.load("ui", false, false, true);
		Drawable drawable = new TextureRegionDrawable(
				atlant.findRegion("pause_icon"));
		return drawable;

	}

	private Button createButton() {

		ImageButton imageButton = new ImageButton(getDrawable());

		imageButton.setHeight(64);
		imageButton.setWidth(64);
		imageButton.setX(300);
		return imageButton;

	}

}
