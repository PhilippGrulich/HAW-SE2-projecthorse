package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Dialog extends Popup {

	public Dialog(final String message) {
		addActor(createLabel(message));
	}

	public final void addButton(final String text, final ChangeListener listener) {
		Button btn = super.createButton(text);
		btn.addListener(listener);
		addActor(btn);
	}
}
