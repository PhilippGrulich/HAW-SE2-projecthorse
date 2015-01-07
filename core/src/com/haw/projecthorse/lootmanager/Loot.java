package com.haw.projecthorse.lootmanager;

import java.util.Date;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Standardimplementierung des {@link Lootable}Lootable-Interfaces.
 * 
 * @author Oliver
 * @version 1.1
 */

public abstract class Loot implements Lootable {
	private String description = null, name = null;
	private Date achievedDate = null;

	/**
	 * Konsturktor zum Laden aus der JSON-Datei.
	 */
	public Loot() {
	}

	/**
	 * Konsturktor.
	 * 
	 * @param name
	 *            Name des Loots
	 * @param description
	 *            Beschreibung des Loots
	 */
	public Loot(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Gibt das {@link LootImage} zurück, welches das Bild des Loots darstellt.
	 * Wird in der Methode {@link #getImage() getImage} verwendet.
	 * 
	 * @return Das LootImage.
	 */
	protected abstract LootImage getLootImage();

	@Override
	public Drawable getImage() {
		return getLootImage().getDrawable();
	}

	@Override
	public Date getAchievedDate() {
		return achievedDate;
	}

	@Override
	public void setAchievedDate(final Date date) {
		achievedDate = date;
	}

	/**
	 * Generiert einen HashCode anhand der Instanzvariablen der Implementierung.
	 * 
	 * @return Den HashCode des Objekts.
	 */
	protected abstract int doHashCode();

	@Override
	public int hashCode() {
		return doHashCode()
				+ ((description == null) ? 0 : description.hashCode());
	}

	/**
	 * Vergleicht das aktuelle Objekt mit other. Die trivialen Prüfungen müssen
	 * nicht gemacht werden. Das Objekt other kann direkt auf die konkrete
	 * Klasse gecastet werden
	 * 
	 * @param other
	 *            Das Objekt mit dem verglichen werden soll.
	 * @return Ob this und other inhaltsgleich sind.
	 */
	protected abstract boolean doEquals(Object other);

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Loot other = (Loot) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		return doEquals(obj);
	}

}
