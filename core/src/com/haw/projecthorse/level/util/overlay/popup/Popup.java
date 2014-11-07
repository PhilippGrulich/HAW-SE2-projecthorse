package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;

/**
 * @author Philipp
 * 
 *         Dies ist ein grundlegendes Popup und alle weiteren Popops können ein
 *         spezielles Verhalten in Supklassen implementieren. Außerdem bittet es
 *         Implementierungen für Standart-Komponenten.
 *         (createButton,createLabel) Diese sollten von dem Erbenen Popup
 *         verwendet werden um ein Einheitliches Look and Feel zu erhalten.
 *         Damit alle Popups immer ähnlich aussehen werden in dieser Klasse
 *         mehrere gennerelle Settings gesetzt. Popups werden immer zental auf
 *         dem Bildschirm dargestellt und haben ein {@link VerticalGroup}
 *         Layout. Des weiteren ist die addActor Methode so überschrieben das
 *         alle {@link Actor} zur {@link VerticalGroup} hinzugefügt werden. Das
 *         {@link VerticalGroup} Layout ordnet alle Elemente Vertikal
 *         untereinander an. Weitere Informationen zum Layout unter
 *         https://github.com/libgdx/libgdx/wiki/Scene2d.ui#verticalgroup
 */
public class Popup extends OverlayWidgetGroup {

	protected int popupHeigh = (int) (height / 2.5), popupWidth = width - 100;;
	protected VerticalGroup contentGroup;
	private OverlayWidgetGroup content = new OverlayWidgetGroup();

	public Popup() {

		content.setHeight(popupHeigh);
		content.setWidth(popupWidth);
		content.setX(50);
		this.setHeight(height);
		this.setWidth(width);
		// Setzen eines neuen KeyDown Listener um Back Keys abzufangen. So wird
		// nur das Popups disposed.
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

	/**
	 * Inizialisierung der VerticalGroup.
	 */
	private void createContentGroup() {
		contentGroup = new VerticalGroup();
		contentGroup.setY((height / 2) - popupHeigh / 2 - 60);
		contentGroup.space(10);
		contentGroup.setHeight(popupHeigh);
		contentGroup.setWidth(popupWidth);
		content.addActor(contentGroup);
	}

	/**
	 * Initalisierung des standart backgrounds.
	 */
	private void createBackgroundImage() {

		Image backgroundImage = new Image(new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "panel_beige")));
		backgroundImage.setHeight(popupHeigh);
		backgroundImage.setWidth(popupWidth);

		backgroundImage.setY((height / 2) - popupHeigh / 2);
		backgroundImage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.cancel();
				return true;
			}
		});
		content.addActor(backgroundImage);
	}

	/**
	 * Die addActor Methode wird überschrieben damit alle Kind Elemente immer
	 * auf das Vertical Layout gelegt werden.
	 */
	@Override
	public void addActor(Actor actor) {
		contentGroup.addActor(actor);
	}

	/**
	 * Methode um auf das Parent Overlay zuzugreifen.
	 * 
	 * @return
	 */
	protected Overlay getOverlay() {
		if (this.getParent() == null)
			return null;
		if (!(this.getStage() instanceof Overlay))
			return null;
		return (Overlay) this.getStage();
	}

	/**
	 * Diese Methode ermöglicht das erstellen eines Standart Labels. Dieses
	 * Label wird zurückgegeben und kann weiter Verändert werden. Außerdem muss
	 * es noch dem Popup mit AddActor hinzugefügt werden.
	 * 
	 * @param message
	 * @return {@link Label}
	 */
	protected Label createLabel(String message) {

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(
				Gdx.files.internal("pictures/fontButton/font.fnt"));
		style.font.scale(-0.5f);
		Label label = new Label(message, style);
		label.setWrap(true);
		label.setAlignment(Align.center);

		label.setWidth(popupWidth);

		return label;
	}

	/**
	 * Diese Methode ermöglicht das erstellen ein Standart Button. Dieses Button
	 * wird zurückgegeben und kann weiter Verändert werden. Es wird z.b. ein
	 * Standart Image gesetzt.
	 * 
	 * @param text
	 * @return {@link ImageTextButton}
	 */
	protected ImageTextButton createButton(String text) {
		ImageTextButtonStyle style = getImageButtonStyle();
		ImageTextButton button = new ImageTextButton(text, style);
		return button;
	}

	/**
	 * Erstellt den {@link ImageTextButtonStyle} für die Methode createButton.
	 * 
	 * @return {@link ImageTextButtonStyle}
	 */
	private ImageTextButtonStyle getImageButtonStyle() {
		Drawable drawable = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "popup_button"));

		ImageTextButtonStyle imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;

		imageButtonStyle.font = new BitmapFont(
				Gdx.files.internal("pictures/fontButton/font.fnt"));

		imageButtonStyle.font.scale(-0.5f);
		new Color();
		imageButtonStyle.fontColor = Color.valueOf("838796");

		return imageButtonStyle;
	}

	/**
	 * Diese Methode erstellt eine Standard Checkbox
	 * 
	 * @param text
	 * @return {@link CheckBox}
	 */
	protected CheckBox createCheckbox(String text) {
		CheckBoxStyle style = createCheckboxStyle();
		CheckBox box = new CheckBox(text, style);

		return box;
	}

	/**
	 * Erstellt den {@link CheckBoxStyle} für die MEthode createCheckbox.
	 * 
	 * @return {@link CheckBoxStyle}
	 */
	private CheckBoxStyle createCheckboxStyle() {
		Drawable on = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"ui", "on"));

		Drawable off = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"ui", "off"));
		CheckBoxStyle style = new CheckBoxStyle();
		style.checkboxOn = on;
		style.checkboxOff = off;
		style.font = new BitmapFont(
				Gdx.files.internal("pictures/fontButton/font.fnt"));
		return style;
	}

}
