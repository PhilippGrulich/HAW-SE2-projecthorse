package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;

/**
 * Ein Input Feld im Einheitsdesign.
 * @author Philipp
 * @version 1
 */
public class TextInputField extends TextField {

	/**
	 * Der Default Konstruktor.
	 * @param text Der initiale Text
	 */
	public TextInputField(final String text) {
		super(text, getTextFieldStyle());

	}

	@Override
	public float getPrefWidth() {
		return super.getWidth() - 50;
	}

	/**
	 * Erzeugt ein TextFieldStyle Objekt im Einheitsdesign.
	 * @return Das TextFieldStyle Objekt
	 */
	private static TextFieldStyle getTextFieldStyle() {
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = AssetManager.getTextFont(FontSize.FORTY);
		textFieldStyle.fontColor = Color.LIGHT_GRAY;
		textFieldStyle.focusedFontColor = Color.GRAY;
		textFieldStyle.cursor = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "cursor"));
		return textFieldStyle;
	}

}
