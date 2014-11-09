package com.haw.projecthorse.lootmanager;

import java.util.Date;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class Loot {
	private String description = null, name = null;
	Date achievedDate = null;
	
	public Loot() {}
	
	public Loot(String name, String description) {
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
	 * Ein kleines Bild, wie eine Medaille, ein Stern o.Ä.
	 * @return Das Bild des Loots.
	 */
	public abstract Drawable getImage();
	
	public final Date getAchievedDate() {
		return achievedDate;
	}

	/*
	 * Generiert einen HashCode anhand der Instanzvariablen der Implementierung.
	 */
	protected abstract int doHashCode();
	
	@Override
	public int hashCode() {
		return doHashCode()
				+ ((description == null) ? 0 : description
						.hashCode());
	}

	/*
	 * Vergleicht das aktuelle Objekt mit other.
	 * Die trivialen Prüfungen müssen nicht gemacht werden.
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
