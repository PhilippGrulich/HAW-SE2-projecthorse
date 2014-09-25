package com.haw.projecthorse.gamemanager.settings;

/**
 * Die Klasse Settings stellt gerätespezifische Informationen bereit und speichert zudem Einstellungen
 * des Users. Die Klasse selbst nutzt das Singleton Pattern.
 */


public interface Settings {
					
	/**
	 * Gibt Auskunft über den aktuellen Ton Status.
	 * @return boolean Ton an (true) oder aus (false).
	 */
	public boolean getSoundState();
	
	/**
	 * Verändert den aktuellen Ton Status.
	 * @param state Ton an (true) oder aus (false).
	 */
	public void setSoundState(boolean state);
	
	/**
	 * Gibt die reale Breite des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Breite in Pixeln.
	 */
	public int getScreenWidth();
	
	/**
	 * Gibt die reale Höhe des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Höhe in Pixeln.
	 */
	public int getScreenHeight();
	
	/**
	 * Gibt die virtuelle Breite des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Breite in Pixeln.
	 */
	public int getVirtualScreenWidth();
	
	/**
	 * Gibt die virtuelle Höhe des Spielbildschirms bzw. des Fensters zurück.
	 * @return int Höhe in Pixeln.
	 */
	public int getVirtualScreenHeight();
	
}
