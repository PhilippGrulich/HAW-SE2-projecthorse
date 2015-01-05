package com.haw.projecthorse.level.game.parcours;

import com.haw.projecthorse.lootmanager.LootImage;
import com.haw.projecthorse.player.race.RaceLoot;

public class ParcoursLoot extends com.haw.projecthorse.lootmanager.Loot {

	private LootImage image;
	private int availableAtScore;
	private boolean allreadyWon;

	/**
	 * Konstruktor der vorhanden sein muss, damit Json die bereits gewonnenen Loot-Objekte
	 * aus dem Spielstand laden kann.
	 */
	public ParcoursLoot(){
		allreadyWon = false;
	}
	/**
	 * Liefert die Punktzahl, ab der das Objekt gewonnen werden kann.
	 * @return p Die Punktzahl.
	 */
	public int getAvailableAtScore() {
		return availableAtScore;
	}

	/**
	 * Legt fest, ob das Objekt im aktuellen Spiel bereits gewonnen wurde.
	 * @param b true, wenn das Objekt mind. 1 Mal im aktuellen Spiel gewonnen wurde.
	 */
	public void setWonStatus(boolean b){
		this.allreadyWon = b;
	}
	
	/**
	 * 
	 * @return b true, wenn das Objekt im aktuellen Spiel bereits gewonnen wurde.
	 */
	public boolean getWonStatus(){
		return allreadyWon;
	}

	/**
	 * Konstruktor für Loot-Objekte, welche keine Pferderassen sind.
	 * @param availableAtScore Punktzahl ab der das Loot-Objekt gewonnen werden kann.
	 * @param name Name des Bildes, dass dieses Loot-Objekt darstellt.
	 * @param description Eine kurze beschreibung des Bildes.
	 */
	public ParcoursLoot(int availableAtScore, String name, String description) {
		super(name, description);
		image = new LootImage("parcours", ParcoursLoot.this.getName());
		this.availableAtScore = availableAtScore;
	}

	@Override
	public LootImage getLootImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public int doHashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + availableAtScore;
		result = prime * result
				+ (((LootImage) image == null) ? 0 : image.hashCode());
		return result;
	}

	@Override
	public boolean doEquals(Object obj) {
		ParcoursLoot other = (ParcoursLoot) obj;
		if (availableAtScore != other.getAvailableAtScore())
			return false;
		if (image == null) {
			if (other.getLootImage() != null)
				return false;
		} else if (!((LootImage) image)
				.equals((LootImage) other.getLootImage()))
			return false;
		return true;
	}
	@Override
	public String getCategory() {
		return "Parcours";
	}
}
