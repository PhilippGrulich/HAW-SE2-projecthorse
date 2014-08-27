package com.haw.projecthorse.gamemanager.settings;

/**
 * Die Klasse Settings stellt ger�tespezifische Informationen bereit und speichert zudem Einstellungen
 * des Users. Die Klasse selbst nutzt das Singleton Pattern.
 */


public interface Settings {
				
	/**
	 * Gibt Auskunft �ber den aktuellen Ton Status.
	 * @return boolean Ton an (true) oder aus (false).
	 */
	public boolean getSoundState();
	
	/**
	 * Ver�ndert den aktuellen Ton Status.
	 * @param state Ton an (true) oder aus (false).
	 */
	public void setSoundState(boolean state);
	
	/**
	 * Gibt die Breite des Spielbildschirms bzw. des Fensters zur�ck.
	 * @return int Breite in Pixeln.
	 */
	public int getScreenWidth();
	
	/**
	 * Gibt die H�he des Spielbildschirms bzw. des Fensters zur�ck.
	 * @return int H�he in Pixeln.
	 */
	public int getScreenHeight();
	
}
