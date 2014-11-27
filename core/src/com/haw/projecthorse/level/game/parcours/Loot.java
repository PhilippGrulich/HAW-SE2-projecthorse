package com.haw.projecthorse.level.game.parcours;

import com.haw.projecthorse.lootmanager.LootImage;

public class Loot extends GameObject implements ILoot, ILootFuerGameOperator {

	private int availableAtScore;
	private LootInChest lootInChest;
	String name;

	public Loot(int availableAtScore, String name){
		super();
		this.availableAtScore = availableAtScore;
		this.name = name;
		lootInChest = new LootInChest();
	}

	@Override
	public int getAvailableAtScore() {
		return availableAtScore;
	}
	
	@Override
	public LootInChest getLootInChest() {
		// TODO Auto-generated method stub
		return lootInChest;
	}
	
	@Override
	public void setName(String name){
		System.out.println("SetName: " + name);
		this.name = name;
	}
	
	@Override
	public String getName(){
		System.out.println("getName: " + name);
		return name;
	}
	
	
	 class LootInChest extends com.haw.projecthorse.lootmanager.Loot {

		private LootImage image;
		 
		@Override
		public int doHashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((image == null) ? 0 : image.hashCode());
			return result;
		}

		@Override
		public boolean doEquals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			LootInChest other = (LootInChest) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (image == null) {
				if (other.image != null)
					return false;
			} else if (!image.equals(other.image))
				return false;
			return true;
		}

		public LootInChest() {
			System.out.println("LootInChest: " + Loot.this.getName());
			image = new LootImage("parcours",  Loot.this.getName());
		}

		@Override
		protected LootImage getLootImage() {
			// TODO Auto-generated method stub
			return image;
		}

		private Loot getOuterType() {
			return Loot.this;
		}
	}	
}
