package com.haw.projecthorse.level.game.memoryspiel;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

public class MemorySpielLoot extends Loot{

	private LootImage image;

	public MemorySpielLoot(final String name, final String description,final String imageName) {
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
	protected boolean doEquals(Object other) {
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

