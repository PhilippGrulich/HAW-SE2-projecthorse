package com.haw.projecthorse.level.game.parcours;

import com.haw.projecthorse.lootmanager.LootImage;

/**
 * Die Klasse stellt Loot-Objekt dar, die im Spiel gewonnen werden können.
 * @author Francis
 * @version 1.0
 */
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
	public void setWonStatus(final boolean b){
		this.allreadyWon = b;
	}
	
	/**
	 * Liefert ein boolean in Abhängigkeit davon, ob das Spiel gewonnen wurde oder nicht.
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
	public ParcoursLoot(final int availableAtScore, final String name, final String description) {
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
		int result = 1;
		result = prime * result + availableAtScore;
		result = prime * result
				+ (((LootImage) image == null) ? 0 : image.hashCode());
		return result;
	}

	@Override
	public boolean doEquals(final Object obj) {
		ParcoursLoot other = (ParcoursLoot) obj;
		if (availableAtScore != other.getAvailableAtScore()){
			return false;
		}
		if (image == null) {
			if (other.getLootImage() != null){
				return false;
			}
		} else if (!((LootImage) image)
				.equals((LootImage) other.getLootImage())){
			return false;
		}
		return true;
	}
	@Override
	public String getCategory() {
		return "Parcours";
	}
}
