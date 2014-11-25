package com.haw.projecthorse.gamemanager.settings;

import java.util.Observable;

/**
 * Die Klasse Settings stellt gerätespezifische Informationen bereit und speichert zudem Einstellungen
 * des Users. Die Klasse selbst nutzt das Singleton Pattern. Sie ist darüberhinaus Observable, um andere
 * Klassen über Veränderungen der Einstellungen benachrichtigen zu können.
 */
public abstract class Settings extends Observable{
	
	// Umsetzung Singleton
	protected Settings(){
	}	
	
	/**
	 * Gibt Auskunft über den aktuellen Sound Ton Status.
	 * @return boolean Ton an (true) oder aus (false).
	 */
	public abstract boolean getSoundState();
	
	/**
	 * Verändert den aktuellen Sound Ton Status.
	 * @param state Ton an (true) oder aus (false).
	 */
	public abstract void setSoundState(boolean state);
	
	/**
	 * Gibt Auskunft über den aktuellen Musik Ton Status.
	 * @return boolean Ton an (true) oder aus (false).
	 */
	public abstract boolean getMusicState();
	
	/**
	 * Verändert den aktuellen Musik Ton Status.
	 * @param state Ton an (true) oder aus (false).
	 */
	public abstract void setMusicState(boolean state);
	
	/**
	 * Gibt die reale Breite des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Breite in Pixeln.
	 */
	public abstract int getScreenWidth();
	
	/**
	 * Gibt die reale Höhe des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Höhe in Pixeln.
	 */
	public abstract int getScreenHeight();
	
	/**
	 * Gibt die virtuelle Breite des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Breite in Pixeln.
	 */
	public abstract int getVirtualScreenWidth();
	
	/**
	 * Gibt die virtuelle Höhe des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Höhe in Pixeln.
	 */
	public abstract int getVirtualScreenHeight();
	
}
