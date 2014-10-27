package com.haw.projecthorse.savegame.json;

import java.util.Collection;
import java.util.List;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.player.color.PlayerColor;

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
	 * @return name of the horse in this score
	 */
	String getHorseName();

	/**
	 * Ändert den Namen des Pferdes
	 * 
	 * @param names
	 *            Der neue Name.
	 */
	void setHorseName(String name);

	/**
	 * @return Die Farbe des Pferdes
	 */
	PlayerColor getHorseColor();

	/**
	 * Ändert die Farbe des Pferdes.
	 * 
	 * @param color
	 *            Die neue Farbe.
	 */
	void setHorseColor(PlayerColor color);

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
	void addCollectedLootList(Collection<Loot> loots);

	/**
	 * Erstellt eine Liste von gesammelten Gegeständen eines bestimmten Typs.
	 * 
	 * @param c
	 *            Die Klasse der Lootobjekte, die zurück kommen sollen.
	 *            
	 * @return Die Liste von Gegenständen.
	 */
	<T extends Loot> List<T> getSpecifiedLoot(Class<T> c);
}
