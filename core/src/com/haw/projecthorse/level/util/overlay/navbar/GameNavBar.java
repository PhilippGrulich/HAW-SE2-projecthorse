package com.haw.projecthorse.level.util.overlay.navbar;

import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarPauseButton;

public class GameNavBar extends NavBar {

	public GameNavBar() {

		NavbarPauseButton pauseButton = new NavbarPauseButton();
		addButton(pauseButton);

		pauseButton = new NavbarPauseButton();
		addButton(pauseButton);
	}

}
