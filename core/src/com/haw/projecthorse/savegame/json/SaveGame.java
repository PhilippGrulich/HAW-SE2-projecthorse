package com.haw.projecthorse.savegame.json;

import java.util.Collection;
import java.util.List;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.Lootable;
import com.haw.projecthorse.player.race.HorseRace;

public interface SaveGame {
	/**
	 * @return ID of the score as integer
	 */
	int getID();

	/**
	 * @return experience points of the score as integer
	 */
	int getEP();

	/**
	 * @param toAdd
	 *            EP to add to the score
	 */
	void addEP(int toAdd);

	/**
	 * @return Den Namen des Kindes für diesen Spielstand
	 */
	String getPlayerName();

	/**
	 * Ändert den Namen des Kindes für diesen Spielstand
	 * 
	 * @param names
	 *            Der neue Name.
	 */
	void setPlayerName(String name);

	/**
	 * @return Die Rasse des Pferdes
	 */
	HorseRace getHorseRace();

	/**
	 * Ändert die Rasse des Pferdes.
	 * 
	 * @param race
	 *            Die neue Rasse.
	 */
	void setHorseRace(HorseRace race);

	/**
	 * Fügt ein gesammelten Gegenstand dem Spieler hinzu
	 * 
	 * @param loot
	 *            Der gesammelte Gegenstand
	 */
	void addCollectedLoot(Loot loot);
	
	/**
	 * Fügt eine Liste von gesammelten Gegenständen dem Spieler hinzu
	 * 
	 * @param loots
	 *            Liste der gesammelte Gegenstände
	 */
	void addCollectedLootList(Collection<Lootable> loots);

	/**
	 * Erstellt eine Liste von gesammelten Gegeständen eines bestimmten Typs.
	 * 
	 * @param c
	 *            Die Klasse der Lootobjekte, die zurück kommen sollen.
	 *            
	 * @return Die Liste von Gegenständen.
	 */
	<T extends Lootable> List<T> getSpecifiedLoot(Class<T> c);
	
	/**
	 * Erstellt eine Liste von gesammelten Gegeständen der angegebenen Kategorie.
	 * 
	 * @param category
	 *            Die Kategorie der Lootobjekte, die zurück kommen sollen.
	 *            
	 * @return Die Liste von Gegenständen.
	 */
	List<Lootable> getSpecifiedLoot(String category);
}
