package com.haw.projecthorse.level.game.memoryspiel;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

public class MemorySpielLoot extends Loot{
		private String imageName;

	public MemorySpielLoot(String name, String description, String imageName) {
		super(name, description);
		this.imageName = imageName;
	}
	
	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LootImage getLootImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int doHashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean doEquals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

}
