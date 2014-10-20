package com.haw.projecthorse.level.util.overlay;

import com.haw.projecthorse.level.util.overlay.button.PauseButton;

public class GameNavigationBar extends NavigationBar {

	public GameNavigationBar() {
		
		PauseButton pauseButton = new PauseButton();
		addButton(pauseButton);
		
		pauseButton = new PauseButton();
		addButton(pauseButton);
	}

}
