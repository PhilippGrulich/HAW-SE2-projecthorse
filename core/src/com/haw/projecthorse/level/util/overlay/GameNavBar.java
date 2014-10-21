package com.haw.projecthorse.level.util.overlay;

import com.haw.projecthorse.level.util.overlay.button.NavbarPauseButton;

public class GameNavBar extends NavBar {

	public GameNavBar() {
		
		NavbarPauseButton pauseButton = new NavbarPauseButton();
		addButton(pauseButton);
		
		pauseButton = new NavbarPauseButton();
		addButton(pauseButton);
	}

}
