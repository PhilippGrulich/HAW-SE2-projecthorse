package com.haw.projecthorse.level.game.memoryspiel;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

/**
 * Die Klasse stellt Loot-Objekt dar, die im Spiel gewonnen werden können.
 * 
 * @author Ngoc Huyen Nguyen
 * @version 1.0
 */

public class MemorySpielLoot extends Loot {

	private LootImage image;

	/**
	 * Konstruktor der vorhanden sein muss, damit Json die bereits gewonnenen
	 * Loot-Objekte aus dem Spielstand laden kann.
	 */
	public MemorySpielLoot() {
	}

	/**
	 * Konstruktor.
	 * 
	 * @param name
	 *            des zu gewinnenden Loot
	 * @param description
	 *            Beschreibung für den Loot
	 * @param imageName
	 *            Dateiname für den Loot
	 */
	public MemorySpielLoot(final String name, final String description,
			final String imageName) {
		super(name, description);
		this.image = new LootImage("memorySpielLoot", imageName);
	}

	@Override
	public String getCategory() {
		return "Memory Spiel";
	}

	@Override
	protected LootImage getLootImage() {
		return this.image;
	}

	@Override
	protected int doHashCode() {
		return ((this.image == null) ? 0 : this.image.hashCode());
	}

	@Override
	protected boolean doEquals(final Object other) {
		if (this.image == null) {
			if (((MemorySpielLoot) other).image != null) {
				return false;
			}
		} else if (!this.image.equals(((MemorySpielLoot) other).image)) {
			return false;
		}
		return true;
	}
}
