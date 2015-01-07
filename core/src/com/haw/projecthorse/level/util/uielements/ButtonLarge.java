package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;

/**
 * Diese Klasse ist stellt das Standarddesign eines breiten Buttons dar.
 * 
 * @author Viktor
 * @version 1
 */

public class ButtonLarge extends ImageTextButton {

	/**
	 * Enum für Button Stile.
	 * 
	 * @author Viktor
	 *
	 */
	public enum ButtonColor {
		LIGHT_GRAY, LIGHT_BROWN;

		protected static ButtonColor getDefaultButtonColor() {
			return LIGHT_GRAY;
		}

		protected static FontSize getDefaultFontSize() {
			return FontSize.FORTY;
		}

		protected static Color getDefaultFontColor() {
			return Color.GRAY;
		}

		/**
		 * Liefert den zum Stil passenden Dateinamen der zugehörigen Button
		 * Grafik.
		 * 
		 * @return Der Dateiname
		 */
		protected String getFileName() {
			switch (this) {
			case LIGHT_GRAY:
				return "popup_button";
			case LIGHT_BROWN:
				return "buttonBackground";
			default:
				Gdx.app.log("ERROR",
						"Missing Filename string for " + this.name()
								+ " in ButtonColor enum!");
				return this.name();
			}
		}

		/**
		 * Liefert die zum Stil passende Textfarbe.
		 * 
		 * @return Die Textfarbe
		 */
		protected Color getFontColor() {
			switch (this) {
			case LIGHT_GRAY:
				return Color.GRAY;
			case LIGHT_BROWN:
				return Color.GRAY;
			default:
				Gdx.app.log("ERROR", "Missing FontColor for " + this.name()
						+ " in ButtonColor enum!");
				return getDefaultFontColor();
			}
		}

		/**
		 * Liefert die zum Stil passende Textgröße.
		 * 
		 * @return Die Textgröße
		 */
		protected FontSize getFontSize() {
			switch (this) {
			case LIGHT_GRAY:
				return FontSize.FORTY;
			case LIGHT_BROWN:
				return FontSize.FORTY;
			default:
				Gdx.app.log("ERROR", "Missing FontSize for " + this.name()
						+ " in ButtonColor enum!");
				return getDefaultFontSize();
			}
		}
	}

	/**
	 * Default Konstruktor. Liefert einen Button im Default Stil (Hellgrau).
	 * 
	 * @param label
	 *            Der Text, der auf dem Button stehen soll
	 */

	public ButtonLarge(final String label) {
		this(label, ButtonColor.getDefaultButtonColor());
	}

	/**
	 * Erweiterter Konstruktor.
	 * 
	 * @param label
	 *            Der Text, der auf dem Button stehen soll
	 * @param btnColor
	 *            Der Button Stil
	 */
	public ButtonLarge(final String label, final ButtonColor btnColor) {
		super(label, getImageButtonStyle(btnColor));
		addFeedback();
	}

	/**
	 * Erweiterter Konstruktor.
	 * 
	 * @param label
	 *            Der Text, der auf dem Button stehen soll
	 * @param inputListener
	 *            Der Listener, der dem Button hinzugefügt werden soll
	 */
	public ButtonLarge(final String label, final ChangeListener inputListener) {
		this(label, ButtonColor.getDefaultButtonColor());
		addListener(inputListener);
	}

	/**
	 * Erstellt einen ImageTextButtonStyle anhand eines Button Stils.
	 * 
	 * @param color
	 *            Der Button Stil
	 * @return Das ImageTextButtonStyle Objekt
	 */
	private static ImageTextButtonStyle getImageButtonStyle(
			final ButtonColor color) {

		Drawable drawable = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", color.getFileName()));

		ImageTextButtonStyle imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;

		imageButtonStyle.font = AssetManager.getTextFont(color.getFontSize());
		imageButtonStyle.fontColor = color.getFontColor();

		return imageButtonStyle;
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
