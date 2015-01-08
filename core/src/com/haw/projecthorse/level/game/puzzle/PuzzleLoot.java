package com.haw.projecthorse.level.game.puzzle;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

/**
 * Die Klasse PuzzleLoot präsentiert, was der Spieler gewinnen kann.
 * @author Masha
 * @version 1.0
 */
public final class PuzzleLoot extends Loot {

	private LootImage image;
/**
 * defaultkonstruktor.
 */
	protected PuzzleLoot() {
	};

	/**
	 * Konstruktor.
	 * 
	 * @param name ein Name für den zu gewinnene Teil
	 * @param description Beschreibung für den Teil
	 * @param imageName Bildname für den Teil
	 */
	protected PuzzleLoot(final String name, final String description,
			final String imageName) {
		super(name, description);
		this.image = new LootImage("puzzle", imageName);
	}

	@Override
	protected LootImage getLootImage() {
		return image;
	}

	@Override
	protected int doHashCode() {
		return ((this.image == null) ? 0 : this.image.hashCode());
	}

	@Override
	protected boolean doEquals(final Object other) {
		if (this.image == null) {
			if (((PuzzleLoot) other).image != null) {
				return false;
			}
		} else if (!this.image.equals(((PuzzleLoot) other).image)) {
			return false;
		}
		return true;

	}

	@Override
	public String getCategory() {
		return "Puzzle";
	}

}
