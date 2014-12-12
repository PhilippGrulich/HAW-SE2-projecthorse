package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.navbar.NavBar;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

/**
 * Die Overlay Stage liegt immer über jedem Level. So wird der Inhalt dieser
 * Stage immer vor dem eigentlichen Level Content angezeigt. Ein Overlay kann
 * eine Navigationsleiste und ein Popup enthalten. Außerdem muss dem Overlay
 * eine Referenz auf das Level übergeben werden damit z.B. ein Popup auch mit
 * diesem Interagieren kann.
 * 
 * @author Philipp
 * 
 */
public class Overlay extends Stage {

	private NavBar navBar;
	private Popup popup;
	private Level level;

	public Overlay(Viewport viewport, SpriteBatch spriteBatch, Level level) {
		super(viewport, spriteBatch);
		InputManager.addInputProcessor(this, Integer.MAX_VALUE);
		this.level = level;
	}

	/**
	 * Setzt eine neue Navigationsbar welche durch {@link NavBar} bzw. durch
	 * deren Unterklassen Implementiert wurde.
	 * 
	 * @param nav
	 */
	public void setNavigationBar(NavBar nav) {
		this.addActor(nav);
		navBar = nav;
	}

	/**
	 * Liefert die Referenz auf das Level über welchem das Overlay liegt
	 * 
	 * @return
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Zeigt ein neues Popup und lässt das Lavel Pausieren.
	 * 
	 * @param Popup
	 */
	public void showPopup(Popup p) {
		if (popup != null) {
			disposePopup();
		}
		popup = p;

		this.addActor(p);
		this.level.pause();
	}

	/**
	 * Lässt alle Popups verschwinden.
	 */
	public void disposePopup() {
		this.popup.fadeOut();
		this.level.resume();

	}

}
