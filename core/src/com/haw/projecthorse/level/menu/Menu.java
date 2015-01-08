package com.haw.projecthorse.level.menu;

import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.navbar.MenuNavBar;

/**
 * Abstraktion für alle Menüs. Es setzt die MenuNavBar als navbar für das
 * Overlay So haben alle Menüs automatisch eine einheitliche Navigationsleiste.
 * 
 * @author Philipp
 * @version 1.0
 */
public abstract class Menu extends Level {
	/**
	 * Konstruktor.
	 */
	public Menu() {
		MenuNavBar nav = new MenuNavBar();
		this.overlay.setNavigationBar(nav);
	}
}
