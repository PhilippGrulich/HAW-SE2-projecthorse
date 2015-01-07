package com.haw.projecthorse.level.util.overlay.navbar;

import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarPauseButton;

/**
 * Spezielle MenuNavBar für Spiele.
 * 
 * @author Philipp
 * @version 1.0
 */
public class GameNavBar extends NavBar {

	/**
	 * Konstruktor.
	 */
	public GameNavBar() {

		NavbarPauseButton pauseButton = new NavbarPauseButton();
		addButton(pauseButton);

	}

}
