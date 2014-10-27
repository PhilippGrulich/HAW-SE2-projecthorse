package com.haw.projecthorse.savegame.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.player.color.PlayerColor;

public class SaveGameImpl implements SaveGame {
	private int ID = -1, ep = 0;
	private Horse horse = new HorseImpl();
	private ArrayList<Loot> lootCollection = new ArrayList<Loot>();

	public SaveGameImpl() {
		lootCollection.add(PlayerColor.BLACK);
		lootCollection.add(PlayerColor.WHITE);
	}
	
	public SaveGameImpl(int ID) {
		this();
		this.ID = ID;
	}
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public int getEP() {
		return ep;
	}

	@Override
	public String getHorseName() {
		return horse.getName();
	}
	
	@Override
	public void setHorseName(String name) {
		horse.setName(name);
	}

	@Override
	public void addEP(int toAdd) {
		ep += toAdd;
	}

	@Override
	public PlayerColor getHorseColor() {
		return horse.getColor();
	}

	@Override
	public void setHorseColor(PlayerColor color) {
		horse.setColor(color);
	}

	@Override
	public void addCollectedLoot(Loot loot) {
		if (!lootCollection.contains(loot)) {
			lootCollection.add(loot);			
		}
	}
	
	@Override
	public void addCollectedLootList(Collection<Loot> loots) {
		lootCollection.addAll(loots);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Loot> List<T> getSpecifiedLoot(Class<T> c) {
		ArrayList<T> loots = new ArrayList<T>();
		for (Loot l : lootCollection) {
			if (c.isInstance(l)) {
				loots.add((T) l);
			}
		}
		return loots;
	}

}
