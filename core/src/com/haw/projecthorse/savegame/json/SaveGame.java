package com.haw.projecthorse.savegame.json;

import java.util.Collection;
import java.util.List;

import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.Lootable;
import com.haw.projecthorse.player.race.HorseRace;

/**
 * Der Spielstand, der im JSON-Format gespeichert und wieder geladen werden kann
 * und alle relevanten Informationen des Spielers beinhaltet.
 * 
 * @author Oliver
 * @version 1.3
 */

public interface SaveGame {
	/**
	 * Liefert die ID des Spielstandes.
	 * 
	 * @return ID als Integer
	 */
	int getID();

	/**
	 * Liefert die bereits gesammelten EP in diesem Spielstand.
	 * 
	 * @return bisher gesammelte Erfahrungspunkte
	 */
	int getEP();

	/**
	 * Erhöht die Erfahrungspunkte um den angegebenen Wert.
	 * 
	 * @param toAdd
	 *            Anzahl der EP, die der Spieler hinzugewonnen hat
	 */
	void addEP(int toAdd);

	/**
	 * Liefert den Namen des Kindes für diesen Spielstand.
	 * 
	 * @return Der Namen des Kindes
	 */
	String getPlayerName();

	/**
	 * Ändert den Namen des Kindes für diesen Spielstand.
	 * 
	 * @param name
	 *            Der neue Name
	 */
	void setPlayerName(String name);

	/**
	 * Gibt die Rasse des Pferdes zurück.
	 * 
	 * @return Die Rasse des Pferdes
	 */
	HorseRace getHorseRace();

	/**
	 * Ändert die Rasse des Pferdes.
	 * 
	 * @param race
	 *            Die neue Rasse
	 */
	void setHorseRace(HorseRace race);

	/**
	 * Fügt ein gesammelten Gegenstand dem Spieler hinzu.
	 * 
	 * @param loot
	 *            Der gesammelte Gegenstand
	 */
	void addCollectedLoot(Loot loot);

	/**
	 * Fügt eine Liste von gesammelten Gegenständen dem Spieler hinzu.
	 * 
	 * @param loots
	 *            Liste der gesammelte Gegenstände
	 */
	void addCollectedLootList(Collection<Lootable> loots);

	/**
	 * Erstellt eine Liste von gesammelten Gegeständen eines bestimmten Typs.
	 * 
	 * @param <T>
	 *            Implementierung des Lootable-Interfaces.
	 * @param c
	 *            Die Klasse der Lootobjekte, die zurück kommen sollen.
	 * 
	 * @return Die Liste von Gegenständen.
	 */
	<T extends Lootable> List<T> getSpecifiedLoot(Class<T> c);

	/**
	 * Erstellt eine Liste von gesammelten Gegeständen der angegebenen
	 * Kategorie.
	 * 
	 * @param category
	 *            Die Kategorie der Lootobjekte, die zurück kommen sollen.
	 * 
	 * @return Die Liste von Gegenständen.
	 */
	List<Lootable> getSpecifiedLoot(String category);
}
