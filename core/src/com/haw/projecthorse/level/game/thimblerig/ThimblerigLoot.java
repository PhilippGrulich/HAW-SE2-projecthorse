package com.haw.projecthorse.level.game.thimblerig;


import com.haw.projecthorse.lootmanager.Loot;
import com.haw.projecthorse.lootmanager.LootImage;

/**
 * Repraesentation der Loots fuer das Huetchenspiel.
 * @author Fabian Reiber
 * @version 1.0
 *
 */
public class ThimblerigLoot extends Loot{
	
	private LootImage image;
	
	/**
	 * Konstruktor für das Laden per Reflection aus der JSON-Datei.
	 */
	public ThimblerigLoot() {}
	
	/**
	 * Konstruktor.
	 * @param name Bezeichnung des Loots
	 * @param description Beschreibung des Loots
	 * @param filename Dateiname des Loots zum Laden des Images
	 */
	public ThimblerigLoot(final String name, final String description, final String filename){
		super(name, description);
		this.image = new LootImage("thimblerig", filename);
	}
	
	@Override
	public LootImage getLootImage() {
		return this.image;
	}

	@Override
	protected int doHashCode() {
		return ((this.image == null) ? 0 : this.image.hashCode());
	}

	/**
	 * Prueft 2 Objekte auf Gleichheit.
	 * @param other 2. objekt mit dem verglichen wird
	 * @return true wenn gleich, sonst false
	 */
	protected boolean doEquals(final Object other) {
		if (this.image == null) {
			if (((ThimblerigLoot)other).image != null){
				return false;
			}
		} else if (!this.image.equals(((ThimblerigLoot)other).image)){
			return false;
		}
		return true;
	}

	@Override
	public String getCategory() {
		return "Hütchenspiel";
	}
}
