package com.haw.projecthorse.level.game;

import com.badlogic.gdx.Input.Orientation;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.navbar.GameNavBar;
import com.haw.projecthorse.lootmanager.Chest;

/**
 * Abstraktion für alle Spiele. Erbt von Level und fügt spezielle Funktionen wie
 * die Chest hinzu. Außerdem wird die GameNavbar als navbar dem Oberlay
 * Hinzugefügt. So haben alle Spiele automatisch eine einheitliche
 * Navigationsleiste.
 * 
 * @author Philipp
 * @version 1.0
 */
public abstract class Game extends Level {
	protected Chest chest;

	/**
	 * Default Konstruktor.
	 */
	public Game() {
		GameNavBar nav = new GameNavBar();
		this.overlay.setNavigationBar(nav);
		chest = new Chest(overlay);
	}

	/**
	 * Über diesen Konstruktor kann eine {@link Orientation} übergeben werden.
	 * 
	 * @param orientation
	 *            {@link Orientation}
	 */
	public Game(final Orientation orientation) {
		super(orientation);
		GameNavBar nav = new GameNavBar();
		this.overlay.setNavigationBar(nav);
		chest = new Chest(overlay);
	}
}
