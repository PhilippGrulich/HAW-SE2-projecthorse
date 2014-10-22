package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;

public class Popup extends OverlayWidgetGroup {

	int popupHeigh = (int) (height / 2.5);
	int popupWith = width - 100;
	protected VerticalGroup contentGroup;
	private OverlayWidgetGroup content = new OverlayWidgetGroup();
	private TextureAtlas atlas = AssetManager.load("ui", false, false, true);

	public Popup() {
		
		
		
		
		content.setHeight(popupHeigh);
		content.setWidth(popupWith);		
		content.setX(50);
		this.setHeight(height);
		this.setWidth(width);
		this.addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
					Gdx.app.log("Popup", "Back Key Detected");
					getOverlay().disposePopup();
					return true;
				}
				return false;
			}
			
			
		});
		
		createBackgroundImage();
		createContentGroup();
		super.addActor(content);
		
	}

	private void createContentGroup() {
		contentGroup = new VerticalGroup();
		contentGroup.setY((height / 2) - popupHeigh / 2 - 60);		
		contentGroup.space(10);
		contentGroup.setHeight(popupHeigh);
		contentGroup.setWidth(popupWith);
		content.addActor(contentGroup);
	}

	private void createBackgroundImage() {

		Image backgroundImage = new Image(new TextureRegionDrawable(atlas.findRegion("panel_beige")));
		backgroundImage.setHeight(popupHeigh);
		backgroundImage.setWidth(popupWith);

		backgroundImage.setY((height / 2) - popupHeigh / 2);
		backgroundImage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				event.cancel();
				return true;
			}
		});
		content.addActor(backgroundImage);
	}

	@Override
	public void addActor(Actor actor) {
		contentGroup.addActor(actor);
	}

	protected Overlay getOverlay() {
		if (this.getParent() == null)
			return null;
		if (!(this.getStage() instanceof Overlay))
			return null;
		return (Overlay) this.getStage();
	}

	private ImageTextButtonStyle getImageButtonStyle() {
		Drawable drawable = new TextureRegionDrawable(atlas.findRegion("popup_button"));
		
		ImageTextButtonStyle imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;

		imageButtonStyle.font = new BitmapFont(Gdx.files.internal("pictures/fontButton/font.fnt"));
		
		imageButtonStyle.font.scale(-0.5f);
		new Color();
		imageButtonStyle.fontColor = Color.valueOf("838796");
		
		return imageButtonStyle;
	}

	protected ImageTextButton createButton(String text) {
		ImageTextButtonStyle style = getImageButtonStyle();
		ImageTextButton button = new ImageTextButton(text, style);		
		return button;
	}
	
	private CheckBoxStyle createCheckboxStyle(){
		Drawable on = new TextureRegionDrawable(atlas.findRegion("on"));
		
		Drawable off = new TextureRegionDrawable(atlas.findRegion("off"));
		CheckBoxStyle style = new CheckBoxStyle();
		style.checkboxOn = on;
		style.checkboxOff = off;
		style.font = new BitmapFont(Gdx.files.internal("pictures/fontButton/font.fnt"));
		return style;		
	}
	
	protected CheckBox createCheckbox(String text) {
		CheckBoxStyle style = createCheckboxStyle();
		CheckBox box = new CheckBox(text, style);	
		
		return box;
	}

}
