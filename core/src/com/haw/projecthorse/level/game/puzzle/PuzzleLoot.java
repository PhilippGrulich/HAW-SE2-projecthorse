package com.haw.projecthorse.level.game.puzzle;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

public class PuzzleLoot extends Loot {
	private String imageName;
	
	public PuzzleLoot(String name, String description, String imageName) {
		super(name, description);
		this.imageName = imageName;
	}

	@Override
	protected LootImage getLootImage() {
		return new LootImage("puzzle", imageName);
	}

	@Override
	protected int doHashCode() {
		return imageName.hashCode();
	}

	@Override
	protected boolean doEquals(Object other) {
		PuzzleLoot o = (PuzzleLoot)other;
		return imageName.equals(o.imageName);
	}

	@Override
	public String getCategory() {
		return "Puzzle";
	}

}
