package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;

/**
 * 
 * @author Philipp
 *
 */
public class TextInputField extends TextField {

	public TextInputField(String text) {
		super(text,getTextFieldStyle());
		
	}
	
	@Override
	public float getPrefWidth() {
		return super.getWidth()-50;
	}
	
	private static TextFieldStyle getTextFieldStyle(){
		TextFieldStyle textFieldStyle = new TextFieldStyle();	
		textFieldStyle.font = AssetManager.getTextFont(FontSize.VIERZIG);
		textFieldStyle.fontColor = Color.LIGHT_GRAY;
		textFieldStyle.focusedFontColor = Color.GRAY;
		
		return textFieldStyle;
	}

}
