package com.haw.projecthorse.level.game.parcours;

import com.haw.projecthorse.level.game.parcours.Loot.LootInChest;

public interface ILootFuerGameOperator {

	public int getAvailableAtScore();
	
	public LootInChest getLootInChest();
}
