package com.haw.projecthorse.lootmanager;

import java.util.Date;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class Loot {
	private String description = null, name = null;
	Date achievedDate = null;
	
	public Loot() {}
	
	public Loot(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gibt einen treffenden, kurzen Namen für den Loot an.
	 * @return Der Name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gibt das {@link LootImage} zur�ck, welches das
	 * Bild des Loots darstellt. Wird in der Methode {@link #getImage() getImage}
	 * verwendet.
	 * @return Das LootImage.
	 */
	protected abstract LootImage getLootImage();
	
	/**
	 * Ein kleines Bild, wie eine Medaille, ein Stern o.Ä.
	 * @return Das Bild des Loots.
	 */
	public Drawable getImage() {
		return getLootImage().getDrawable();
	}
	
	public final Date getAchievedDate() {
		return achievedDate;
	}

	/**
	 * Generiert einen HashCode anhand der Instanzvariablen der Implementierung.
	 * @return Den HashCode des Objekts.
	 */
	protected abstract int doHashCode();
	
	@Override
	public int hashCode() {
		return doHashCode()
				+ ((description == null) ? 0 : description
						.hashCode());
	}

	/**
	 * Vergleicht das aktuelle Objekt mit other.
	 * Die trivialen Prüfungen müssen nicht gemacht werden. Das Objekt
	 * other kann direkt auf die konkrete Klasse gecastet werden
	 * 
	 * @param other Das Objekt mit dem verglichen werden soll.
	 * @return Ob this und other inhaltsgleich sind.
	 */
	protected abstract boolean doEquals(Object other);
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loot other = (Loot) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return doEquals(obj);
	}
	
	
}
