package com.haw.projecthorse.level.util.overlay.navbar;

import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarBackButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarSettingsButton;

public class MenuNavBar extends NavBar {

	public MenuNavBar() {


		NavbarSettingsButton settingsButton = new NavbarSettingsButton();
		addButton(settingsButton);
		
		NavbarBackButton pauseButton = new NavbarBackButton();
		addButton(pauseButton);

	}

}
