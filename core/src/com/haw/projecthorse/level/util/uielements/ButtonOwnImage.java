package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;

/**
 * Diese Klasse ist kapselt den LibGDX ImageButton und erweitert ihn sinnvoll.
 * 
 * @author Viktor
 * @version 1
 */
public class ButtonOwnImage extends ImageButton {

	/**
	 * Ein Konstruktor.
	 * @param imageUp Ein Drawable als Button Image.
	 */
	public ButtonOwnImage(final Drawable imageUp) {
		super(imageUp);
		addFeedback();
		setTransform(true); // Workaround für falsche Implementierung oder
							// Dokumentation in LibGDX
	}

	/**
	 * Ein Konstruktor.
	 * @param skin Ein Skin Object, dass die Button Images enthält.
	 */
	public ButtonOwnImage(final Skin skin) {
		super(skin);
		addFeedback();
		setTransform(true); // Workaround für falsche Implementierung oder
							// Dokumentation in LibGDX
	}

	/**
	 * Ein Konstruktor.
	 * @param style Ein ImageButtonStyle Objekt, dass die Button Images enthält.
	 */
	public ButtonOwnImage(final ImageButtonStyle style) {
		super(style);
		addFeedback();
		setTransform(true); // Workaround für falsche Implementierung oder
							// Dokumentation in LibGDX
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
