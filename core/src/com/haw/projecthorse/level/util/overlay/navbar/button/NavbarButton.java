package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;
import com.haw.projecthorse.level.util.overlay.navbar.NavBar;

/**
 * Dies ist ein Generischer NavbarButton der in Subklassen ausimplementiert
 * werden muss.
 * 
 * @author Philipp
 * @version 1.0
 */
public abstract class NavbarButton extends OverlayWidgetGroup {

	/**
	 * Konstruktor.
	 */
	public NavbarButton() {

	}

	/**
	 * Liefert die parent Navbar.
	 * 
	 * @return {@link NavBar}
	 */
	public final NavBar getNavigationBar() {
		Group parent = this.getParent().getParent();
		return (NavBar) parent;
	}
}
