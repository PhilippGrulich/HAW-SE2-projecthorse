package com.haw.projecthorse.savegame.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.Lootable;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.RaceLoot;

/**
 * Konkrete Implementierung des {@link SaveGame}SaveGame-Interfaces.
 * 
 * @author Oliver
 * @version 1.3
 */

public class SaveGameImpl implements SaveGame {
	private int id = -1, ep = 0;
	private String name;
	private Horse horse = new HorseImpl();
	private ArrayList<Lootable> lootCollection = new ArrayList<Lootable>();

	/**
	 * Default-Konstruktor zum Laden aus des JSON-Datei.
	 */
	public SaveGameImpl() {
	}
	
	/**
	 * Legt einen neuen Spielstand mit der Standard-Pferde-Rasse als erstes Loot an.
	 * 
	 * @param id id des neuen Spielstands
	 */
	public SaveGameImpl(final int id) {
		this.id = id;
		
		// Pferderasse intialisieren
		lootCollection.add(new RaceLoot(HorseRace.HAFLINGER));
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public int getEP() {
		return ep;
	}

	@Override
	public String getPlayerName() {
		if (name == null) {
			return "NoName";
		}
		return name;
	}
	
	@Override
	public void setPlayerName(final String name) {
		this.name = name;
	}

	@Override
	public void addEP(final int toAdd) {
		ep += toAdd;
	}

	@Override
	public HorseRace getHorseRace() {
		return horse.getRace();
	}

	@Override
	public void setHorseRace(final HorseRace race) {
		horse.setRace(race);
	}

	@Override
	public void addCollectedLoot(final Loot loot) {
		if (!lootCollection.contains(loot)) {
			lootCollection.add(loot);			
		}
	}
	
	@Override
	public void addCollectedLootList(final Collection<Lootable> loots) {
		lootCollection.addAll(loots);
		// Doppelte Elemente entfernen
		lootCollection = new ArrayList<Lootable>(new HashSet<Lootable>(lootCollection));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Lootable> List<T> getSpecifiedLoot(final Class<T> c) {
		ArrayList<T> loots = new ArrayList<T>();
		for (Lootable l : lootCollection) {
			if (c.isInstance(l)) {
				loots.add((T) l);
			}
		}
		return loots;
	}

	@Override
	public List<Lootable> getSpecifiedLoot(final String category) {
		ArrayList<Lootable> loots = new ArrayList<Lootable>();
		for (Lootable l : lootCollection) {
			if (l.getCategory().equals(category)) {
				loots.add(l);
			}
		}
		return loots;
	}

}
