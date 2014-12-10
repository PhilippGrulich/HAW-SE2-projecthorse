package com.haw.projecthorse.level.game.puzzle;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

public class PuzzleLoot extends Loot {
	private String color;
	
	public PuzzleLoot(String name, String description, String color) {
		super(name, description);
		this.color = color;
	}

	@Override
	protected LootImage getLootImage() {
		return new LootImage("puzzle", "carrot_" + color);
	}

	@Override
	protected int doHashCode() {
		return color.hashCode();
	}

	@Override
	protected boolean doEquals(Object other) {
		PuzzleLoot o = (PuzzleLoot)other;
		return color.equals(o.color);
	}

}
