package com.haw.projecthorse.level.game.parcours;

import com.haw.projecthorse.lootmanager.LootImage;

public class ParcoursLoot extends com.haw.projecthorse.lootmanager.Loot {

	private LootImage image;
	private int availableAtScore;

	public int getAvailableAtScore() {
		return availableAtScore;
	}

	public ParcoursLoot() {

	}

	public ParcoursLoot(int availableAtScore, String name, String description) {
		super(name, description);
		image = new LootImage("parcours", ParcoursLoot.this.getName());
		this.availableAtScore = availableAtScore;
	}

	@Override
	public LootImage getLootImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public int doHashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + availableAtScore;
		result = prime * result
				+ (((LootImage) image == null) ? 0 : image.hashCode());
		return result;
	}

	/*
	 * @Override public boolean equals(Object obj) { if (this == obj) return
	 * true; if (obj == null) return false; if (getClass() != obj.getClass())
	 * return false; Loot other = (Loot) obj; if (description == null) { if
	 * (other.description != null) return false; } else if
	 * (!description.equals(other.description)) return false; return
	 * doEquals(obj); }
	 */

	@Override
	public boolean doEquals(Object obj) {
		ParcoursLoot other = (ParcoursLoot) obj;
		if (availableAtScore != other.getAvailableAtScore())
			return false;
		if (image == null) {
			if (other.getLootImage() != null)
				return false;
		} else if (!((LootImage) image)
				.equals((LootImage) other.getLootImage()))
			return false;
		return true;
	}
}
