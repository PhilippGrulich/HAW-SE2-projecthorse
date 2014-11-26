package com.haw.projecthorse.level.game;

import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.navbar.GameNavBar;
import com.haw.projecthorse.lootmanager.Chest;

/**
 * Abstraktion für alle Spiele.
 * Erbt von Level und fügt spezielle Funktionen wie die Chest hinzu.
 * Außerdem wird die GameNavbar als navbar dem Oberlay Hinzugefügt.
 * So haben alle Spiele automatisch eine einheitliche Navigationsleiste.
 * @author Philipp
 *
 */
public abstract class Game extends Level {
	protected Chest chest;

	public Game() {
		GameNavBar nav = new GameNavBar();
		this.overlay.setNavigationBar(nav);
		chest = new Chest(overlay);
	}
}
