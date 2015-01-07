package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;

/**
 * Diese Klasse ist kapselt den LibGDX ImageTextButton und erweitert ihn
 * sinnvoll.
 * 
 * @author Viktor
 * @version 1
 */
public class ButtonOwnTextImage extends ImageTextButton {

	/**
	 * Ein Konstruktor.
	 * @param text Der Text für den Button
	 * @param skin Ein Skin Objekt, dass die Images für den Button enthält
	 */
	public ButtonOwnTextImage(final String text, final Skin skin) {
		super(text, skin);
		addFeedback();
	}

	/**
	 * Ein Konstruktor.
	 * @param text Der Text für den Button
	 * @param style Ein ImageTextButtonStyle Objekt, dass die Images für den Button enthält
	 */
	public ButtonOwnTextImage(final String text, final ImageTextButtonStyle style) {
		super(text, style);
		addFeedback();
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
