package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;

/**
 * @author Viktor Diese Klasse ist stellt das Standarddesign eines kleinen
 *         Buttons dar wie er zum Beispiel für die Navigationsmenüs genutzt
 *         wird.
 */

public class ButtonSmall extends ImageButton {

	public enum ButtonType {
		BACK, APPLY, CANCEL, LEFT, RIGHT, START, PAUSE, SETTINGS, LOOT, HOME;

		String getFileName() {
			switch (this) {
			case BACK:
				return "backIcon";
			case APPLY:
				return "buttonApply";
			case CANCEL:
				return "buttonCancel";
			case LEFT:
				return "buttonLeft";
			case RIGHT:
				return "buttonRight";
			case START:
				return "buttonStart";
			case PAUSE:
				return "pauseIcon";
			case LOOT:
				return "buttonLoot";
			case HOME:
				return "buttonHome";
			case SETTINGS:
				return "settingsIcon";
			default:
				Gdx.app.log("ERROR", "Missing Filename string for " + this.name() + " in ButtonType enum!");
				return this.name();
			}
		}
	}

	public ButtonSmall(ButtonType type) {
		super(getDrawable(type));
		addFeedback();
	}

	private static Drawable getDrawable(ButtonType type) {
		return new TextureRegionDrawable(AssetManager.getTextureRegion("ui", type.getFileName()));
	}

	private void addFeedback() {
		this.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AudioManagerImpl.getInstance().getSound("ui", "click.ogg").play();
				Gdx.input.vibrate(50);
			}
		});
	}
}
