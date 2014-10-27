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
	 * �ndert den Namen des Pferdes
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
	 * �ndert die Farbe des Pferdes.
	 * 
	 * @param color
	 *            Die neue Farbe.
	 */
	void setHorseColor(PlayerColor color);

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
	void addCollectedLootList(Collection<Loot> loots);

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
