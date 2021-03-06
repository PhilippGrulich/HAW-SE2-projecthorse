package com.haw.projecthorse.level.util.overlay.navbar;

import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarBackButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarLootGalleryButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarSettingsButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarStableButton;

/**
 * Spezielle MenuNavBar für Menüs.
 * 
 * @author Philipp
 * @version 1.0
 */
public class MenuNavBar extends NavBar {

	/**
	 * Konstruktor.
	 */
	public MenuNavBar() {

		NavbarSettingsButton settingsButton = new NavbarSettingsButton();
		addButton(settingsButton);

		if (!GameManagerFactory.getInstance().getCurrentLevelID().equals("mainMenu")) {
			NavbarStableButton homeButton = new NavbarStableButton();
			addButton(homeButton);
			NavbarLootGalleryButton lootButton = new NavbarLootGalleryButton();
			addButton(lootButton);

		}
		NavbarBackButton pauseButton = new NavbarBackButton();
		addButton(pauseButton);

	}

}
