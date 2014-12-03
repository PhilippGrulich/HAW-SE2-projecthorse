package com.haw.projecthorse.level.util.overlay.navbar;

import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarBackButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarLootGalleryButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarSettingsButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarStableButton;

public class MenuNavBar extends NavBar {

	public MenuNavBar() {

		NavbarSettingsButton settingsButton = new NavbarSettingsButton();
		addButton(settingsButton);

		NavbarLootGalleryButton lootButton = new NavbarLootGalleryButton();
		addButton(lootButton);
		NavbarStableButton homeButton = new NavbarStableButton();
		addButton(homeButton);

		NavbarBackButton pauseButton = new NavbarBackButton();
		addButton(pauseButton);

	}

}
