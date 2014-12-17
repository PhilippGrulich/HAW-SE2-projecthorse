package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;

public class ButtonOwnImage extends ImageButton {

	/**
	 * @author Viktor Diese Klasse ist kapselt den LibGDX ImageButton und
	 *         erweitert ihn sinnvoll.
	 */

	public ButtonOwnImage(Drawable imageUp) {
		super(imageUp);
		addFeedback();
		setTransform(true); // Workaround für falsche Implementierung oder
							// Dokumentation in LibGDX
	}

	public ButtonOwnImage(Skin skin) {
		super(skin);
		addFeedback();
		setTransform(true); // Workaround für falsche Implementierung oder
							// Dokumentation in LibGDX
	}

	public ButtonOwnImage(ImageButtonStyle style) {
		super(style);
		addFeedback();
		setTransform(true); // Workaround für falsche Implementierung oder
							// Dokumentation in LibGDX
	}

	private void addFeedback() {
		this.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AudioManagerImpl.getInstance().getSound("ui", "click.ogg")
						.play();
				Gdx.input.vibrate(50);
			}
		});
	}
}
