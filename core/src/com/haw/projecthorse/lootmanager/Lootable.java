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
	 * Gibt eine kurze Beschreibung der Beute an.
	 * 
	 * @return Beschreibung der Beute.
	 */
	public String getDescription();
}
