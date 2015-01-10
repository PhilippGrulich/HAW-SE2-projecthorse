package com.haw.projecthorse.level.game.memoryspiel;

import java.util.ArrayList;
import java.util.Collections;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.game.memoryspiel.Karte.CardState;

/**
 * Karten erzeugen, konfigurieren und deren Zustände aktualisieren.
 * 
 * @author Ngoc Huyen Nguyen
 * @version 1.0
 */

public class KartenManager {

	// alle Karten
	private ArrayList<Karte> karten = new ArrayList<Karte>();

	// Helfen dabei, das falsche Paar für wenige Zeit aufgedeckt zu halten,
	// bevor
	// sie wieder zugedeckt werden.
	private float delta = 0;
	private boolean timeDelay = false;

	private Karte karteA;
	private Karte karteB;

	// Sorgt dafür, dass jedes Mal maximal 2 Karten aufgedeckt werden können.
	public boolean canOpen = true;

	// alle Bilder
	private Drawable[] pictures = new Drawable[6];

	private int score = 0;

	/**
	 * Konstruktor.
	 * 
	 */
	public KartenManager() {
		setUpKarten();
	}

	/**
	 * Hier werden die Karten mit Koordinaten erzeugt und Bilder zugewiesen.
	 */
	public void setUpKarten() {

		for (int i = 0; i < 12; i++) {
			karten.add(null);
		}

		int y = 75;
		karten.set(0, new Karte(40, 75 + y));
		karten.set(1, new Karte(265, 75 + y));
		karten.set(2, new Karte(490, 75 + y));
		karten.set(3, new Karte(40, 300 + y));
		karten.set(4, new Karte(265, 300 + y));
		karten.set(5, new Karte(490, 300 + y));
		karten.set(6, new Karte(40, 525 + y));
		karten.set(7, new Karte(265, 525 + y));
		karten.set(8, new Karte(490, 525 + y));
		karten.set(9, new Karte(40, 750 + y));
		karten.set(10, new Karte(265, 750 + y));
		karten.set(11, new Karte(490, 750 + y));

		Collections.shuffle(karten);

		pictures[0] = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 1));
		pictures[1] = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 2));
		pictures[2] = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 3));
		pictures[3] = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 4));
		pictures[4] = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 5));
		pictures[5] = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 6));

		int j = 0;
		for (int i = 0; i < pictures.length; i++) {
			((Karte) karten.get(j)).setPicture(pictures[i]);
			((Karte) karten.get(j + 1)).setPicture(pictures[i]);
			j += 2;
		}

	}

	/**
	 * Karten Getter.
	 * 
	 * @return alle Karten
	 */
	public ArrayList<Karte> getKarten() {
		return karten;
	}

	/**
	 * Zustände von Karten aktualisieren.
	 * 
	 * @param delta
	 *            Delta von Methode doRender beim MemorySpiel
	 */
	public void checkChanged(final float delta) {
		if (!timeDelay) {
			for (Karte karte1 : karten) {
				if (karte1.getState() == CardState.TEMPORARILY_OPENED) {
					for (Karte karte2 : karten) {
						if (!(karte1.equals(karte2))
								&& karte2.getState() == CardState.TEMPORARILY_OPENED) {
							canOpen = false;
							if (karte1.getPicture().equals(karte2.getPicture())) {
								karte1.setState(CardState.OPEN);
								karte2.setState(CardState.OPEN);
								score += 10;
								canOpen = true;
							} else {
								karteA = karte1;
								karteB = karte2;
								if (!timeDelay) {
									score -= 5;
									if (score < 0) {
										score = 0;
									}
								}
								timeDelay = true;
							}
						}
					}
				}
			}
		} else {
			this.delta += delta;
			if (this.delta > 0.5) {
				karteA.setState(CardState.TEMPORARILY_CLOSED);
				karteB.setState(CardState.TEMPORARILY_CLOSED);
				this.delta = 0;
				timeDelay = false;
				canOpen = true;
			}
		}
	}

	/**
	 * Reihenfolge von Karten shuffeln. Karten bekommen neue Bilder und werden
	 * wieder zugedeckt.
	 */
	public void restart() {

		Collections.shuffle(karten);

		Karte k1;
		Karte k2;
		int j = 0;
		for (int i = 0; i < pictures.length; i++) {
			k1 = karten.get(j);
			k2 = karten.get(j + 1);
			k1.setPicture(pictures[i]);
			k2.setPicture(pictures[i]);
			k1.setState(CardState.TEMPORARILY_CLOSED);
			k2.setState(CardState.TEMPORARILY_CLOSED);
			j += 2;
		}

		setScore(0);
	}

	/**
	 * Score Getter.
	 * 
	 * @return Score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Score Setter.
	 * 
	 * @param score
	 *            zu setzende Score
	 */
	public void setScore(final int score) {
		this.score = score;
	}

	/**
	 * Score Setter.
	 * 
	 * @return Score
	 */
}
