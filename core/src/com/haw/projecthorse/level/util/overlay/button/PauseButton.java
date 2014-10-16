package com.haw.projecthorse.level.util.overlay.button;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

public class PauseButton extends Button {

	public PauseButton() {
		ImageButton imageButton = new ImageButton(getDrawable());
		imageButton.setHeight(64);
		imageButton.setWidth(64);				
		this.addActor(imageButton);
	}

	private Drawable getDrawable() {
		TextureAtlas atlant = AssetManager.load("ui", false, false, true);
		Drawable drawable = new TextureRegionDrawable(atlant.findRegion("pause_icon"));
		return drawable;
	}	

}
