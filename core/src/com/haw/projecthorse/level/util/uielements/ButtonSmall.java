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
 * Diese Klasse ist stellt das Standarddesign eines kleinen Buttons dar wie er
 * zum Beispiel für die Navigationsmenüs genutzt wird.
 * 
 * @author Viktor
 * @version 1
 */
public class ButtonSmall extends ImageButton {

	/**
	 * Ein Enum für die Art des Buttons.
	 * 
	 * @author Viktor
	 *
	 */
	public enum ButtonType {
		BACK, APPLY, CANCEL, LEFT, RIGHT, START, PAUSE, SETTINGS, LOOT, HOME, INFO;

		/**
		 * Liefert den zur Button Art passenden Dateinamen der zugehörigen
		 * Grafik.
		 * 
		 * @return Der Dateiname
		 */
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
			case INFO:
				return "buttonCityInfo";
			default:
				Gdx.app.log("ERROR",
						"Missing Filename string for " + this.name()
								+ " in ButtonType enum!");
				return this.name();
			}
		}
	}

	/**
	 * Der Default Konstruktor.
	 * 
	 * @param type
	 *            Die Art des Buttons
	 */
	public ButtonSmall(final ButtonType type) {
		super(getDrawable(type));
		addFeedback();
	}

	/**
	 * Gibt das zur Button Art gehörige Drawable zurück.
	 * 
	 * @param type
	 *            Die Button Art
	 * @return Das Drawable
	 */
	private static Drawable getDrawable(final ButtonType type) {
		return new TextureRegionDrawable(AssetManager.getTextureRegion("ui",
				type.getFileName()));
	}

	/**
	 * Fügt dem Button ein akustisches und haptisches Feedback hinzu.
	 */
	private void addFeedback() {
		this.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				AudioManagerImpl.getInstance().getSound("ui", "click.ogg")
						.play();
				Gdx.input.vibrate(50);
			}
		});
	}
}
