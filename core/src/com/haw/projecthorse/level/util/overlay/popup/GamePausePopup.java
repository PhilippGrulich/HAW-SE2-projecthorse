package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

public class GamePausePopup extends Popup {

	private TextButtonStyle buttonStyle;

	public GamePausePopup() {

		Pixmap pixel = new Pixmap(popupWith, popupHeigh, Format.RGBA8888); // Create
																			// a
		// temp-pixmap
		// to use as a
		// background
		// texture
		pixel.setColor(Color.BLUE);
		pixel.fill();
		Texture t = new Texture(pixel, Format.RGBA8888, true);
		TextureRegion text = new TextureRegion(t, 350, 60);
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(text);
		buttonStyle.font = new BitmapFont();
		buttonStyle.font.scale(3);
		
		String soundState = "Musik An";
		final boolean sound = GameManagerFactory.getInstance().getSettings()
				.getSoundState();
		if (sound)
			soundState = "Musik Aus";
		createButton(soundState, new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				GamePausePopup.this.getOverlay().disposePopup();
				GameManagerFactory.getInstance().getSettings()
						.setSoundState(!sound);
				event.cancel();
				return true;
			}

		});

		createButton("Spiel verlassen", new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				GamePausePopup.this.getOverlay().disposePopup();
				GameManagerFactory.getInstance().navigateBack();
				event.cancel();
				return true;
			}

		});

	}

	private void createButton(String label, InputListener inputListener) {
		TextButton tbutton = new TextButton(label, buttonStyle);
		
		tbutton.addListener(inputListener);
	
		this.addActor(tbutton);
	}

}
