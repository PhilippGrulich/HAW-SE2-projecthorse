package com.haw.projecthorse.level.menu;

import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.navbar.MenuNavBar;


/**
 * Abstraktion für alle Menüs.
 * Es setzt die MenuNavBar als navbar für das Overlay
 * So haben alle Menüs automatisch eine einheitliche Navigationsleiste.
 * @author Philipp
 */
public abstract class Menu extends Level {
	public Menu() {
		MenuNavBar nav = new MenuNavBar();
		this.overlay.setNavigationBar(nav);
	}
}
