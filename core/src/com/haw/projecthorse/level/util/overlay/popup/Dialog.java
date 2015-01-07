package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Hierbei handelt es sich um einen Dialog mit einem Text und mehreren Button.
 * 
 * @author Philipp
 * @version 1.0
 */
public class Dialog extends Popup {

	/**
	 * Konstruktor für den Dialog. Die übergabe eines Textes zum Anzeigen.
	 * 
	 * @param message
	 *            Text der angezeigt werden soll.
	 */
	public Dialog(final String message) {
		addActor(createLabel(message));
	}

	/**
	 * Fügt einen neuen Button hinzu.
	 * 
	 * @param text
	 *            Text auf dem Button
	 *
	 * @param listener
	 *            Listener um auf den Button Klick zu reagieren
	 */
	public final void addButton(final String text, final ChangeListener listener) {
		Button btn = super.createButton(text);
		btn.addListener(listener);
		addActor(btn);
	}
}
