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
	 * F�gt ein gesammelten Gegenstand dem Spieler hinzu
	 * 
	 * @param loot
	 *            Der gesammelte Gegenstand
	 */
	void addCollectedLoot(Loot loot);
	
	/**
	 * F�gt eine Liste von gesammelten Gegenst�nden dem Spieler hinzu
	 * 
	 * @param loots
	 *            Liste der gesammelte Gegenst�nde
	 */
	void addCollectedLootList(Collection<Lootable> loots);

	/**
	 * Erstellt eine Liste von gesammelten Gegest�nden eines bestimmten Typs.
	 * 
	 * @param c
	 *            Die Klasse der Lootobjekte, die zur�ck kommen sollen.
	 *            
	 * @return Die Liste von Gegenst�nden.
	 */
	<T extends Loot> List<T> getSpecifiedLoot(Class<T> c);
}
