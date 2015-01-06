package com.haw.projecthorse.lootmanager;

import java.util.Date;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Interface für alle Klasse, die eine Art von Beute realisieren.
 * 
 * @author Oliver
 */

public interface Lootable {
	/**
	 * Gibt die Kategorie des Loots an. Die einzelnen Kategorien werden in der
	 * LootGallery unterschieden.
	 * 
	 * @return Die Kategorie des Loots.
	 */
	public String getCategory();

	/**
	 * Gibt einen treffenden, kurzen Namen für den Loot an.
	 * 
	 * @return Der Name.
	 */
	public String getName();

	/**
	 * Ein kleines Bild, wie eine Medaille, ein Stern o.Ä.
	 * 
	 * @return Das Bild des Loots.
	 */
	public Drawable getImage();

	/**
	 * Gibt den Zeitpunkt an, an dem diese Beute erhalten wurde.
	 * 
	 * @return Zeitpunkt des Erhalts.
	 */
	public Date getAchievedDate();

	/**
	 * Setzt den Zeitpunkt des Erhalts der Beute auf das übergebene Datum.
	 */
	public void setAchievedDate(Date date);

	/**
	 * Gibt eine kurze Beschreibung der Beute an.
	 * 
	 * @return Beschreibung der Beute.
	 */
	public String getDescription();
}
