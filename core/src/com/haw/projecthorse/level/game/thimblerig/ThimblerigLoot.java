package com.haw.projecthorse.level.game.thimblerig;


import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

public class ThimblerigLoot extends Loot{
	
	private LootImage image;
	
	// für das Laden per Reflection aus der JSON-Datei
	public ThimblerigLoot() {}
	
	public ThimblerigLoot(String name, String description, String filename){
		super(name, description);
		this.image = new LootImage("thimblerig", filename);
	}
	
	@Override
	public LootImage getLootImage() {
		return this.image;
	}

	@Override
	protected int doHashCode() {
		return ((this.image == null) ? 0 : this.image.hashCode());
	}

	@Override
	protected boolean doEquals(Object other) {
		if (this.image == null) {
			if (((ThimblerigLoot)other).image != null)
				return false;
		} else if (!this.image.equals(((ThimblerigLoot)other).image))
			return false;
		return true;
	}
}
