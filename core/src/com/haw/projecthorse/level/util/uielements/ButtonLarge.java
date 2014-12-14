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

/**
 * @author Viktor
 *	Diese Klasse ist stellt das Standarddesign eines breiten Buttons dar.
 */

public class ButtonLarge extends ImageTextButton{
	
	public enum ButtonColor {
		LIGHT_GRAY, LIGHT_BROWN;
		
		
		protected static ButtonColor getDefaultButtonColor(){
			return LIGHT_GRAY;
		}
		
		protected static FontSize getDefaultFontSize(){
			return FontSize.FORTY;
		}
		
		protected static Color getDefaultFontColor(){
			return Color.GRAY;
		}
		
		protected String getFileName(){
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
		
		protected Color getFontColor(){
			switch (this) {
			case LIGHT_GRAY:
				return Color.GRAY;
			case LIGHT_BROWN:
				return Color.GRAY;
			default:
				Gdx.app.log("ERROR",
						"Missing FontColor for " + this.name()
								+ " in ButtonColor enum!");
				return getDefaultFontColor();
			}
		}
			
		protected FontSize getFontSize(){
			switch (this) {
			case LIGHT_GRAY:
				return FontSize.FORTY;
			case LIGHT_BROWN:
				return FontSize.FORTY;
			default:
				Gdx.app.log("ERROR",
						"Missing FontSize for " + this.name()
								+ " in ButtonColor enum!");
				return getDefaultFontSize();
			}
		}
	}
	
	public ButtonLarge(String label, ButtonColor btnColor){
		super(label, getImageButtonStyle(btnColor));
		addVibrationFeedback();
	}
	
	public ButtonLarge(String label){
		this(label, ButtonColor.getDefaultButtonColor());		
	}
	
	public ButtonLarge(String label, ChangeListener inputListener){
		this(label, ButtonColor.getDefaultButtonColor());
		addListener(inputListener);
	}
	
	
	
	private static ImageTextButtonStyle getImageButtonStyle(ButtonColor color) {
		
		Drawable drawable = new TextureRegionDrawable(AssetManager.getTextureRegion("ui", color.getFileName()));

		ImageTextButtonStyle imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;

		imageButtonStyle.font = AssetManager.getTextFont(color.getFontSize());
		imageButtonStyle.fontColor = color.getFontColor();

		return imageButtonStyle;
	}
	
	private void addVibrationFeedback(){
		this.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			Gdx.input.vibrate(50);	
			}
		});
	}
}
