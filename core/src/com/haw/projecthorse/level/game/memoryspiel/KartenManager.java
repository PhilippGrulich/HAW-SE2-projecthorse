package com.haw.projecthorse.level.game.memoryspiel;

import java.util.ArrayList;
import java.util.Collections;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.game.memoryspiel.Karte.CardState;

public class KartenManager extends Thread {

	private ArrayList<Karte> karten = new ArrayList<Karte>();
	private float delta = 0;
	private boolean flag = false;
	private Karte karteA;
	private Karte karteB;
	public boolean canOpen = true;
	private Drawable picture1;
	private Drawable picture2;
	private Drawable picture3;
	private Drawable picture4;
	private Drawable picture5;
	private Drawable picture6;
	private int score = 0;

	public KartenManager() {
		setUpKarten();
	}

	public void setUpKarten() {
		Karte karte1 = new Karte(40, 75);
		Karte karte2 = new Karte(265, 75);
		Karte karte3 = new Karte(490, 75);
		Karte karte4 = new Karte(40, 300);
		Karte karte5 = new Karte(265, 300);
		Karte karte6 = new Karte(490, 300);
		Karte karte7 = new Karte(40, 525);
		Karte karte8 = new Karte(265, 525);
		Karte karte9 = new Karte(490, 525);
		Karte karte10 = new Karte(40, 750);
		Karte karte11 = new Karte(265, 750);
		Karte karte12 = new Karte(490, 750);
		karten.add(karte1);
		karten.add(karte2);
		karten.add(karte3);
		karten.add(karte4);
		karten.add(karte5);
		karten.add(karte6);
		karten.add(karte7);
		karten.add(karte8);
		karten.add(karte9);
		karten.add(karte10);
		karten.add(karte11);
		karten.add(karte12);

		Collections.shuffle(karten);

		picture1 = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 1));
		picture2 = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 2));
		picture3 = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 3));
		picture4 = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 4));
		picture5 = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 10));
		picture6 = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Foto" + 9));
		((Karte) karten.get(0)).setPicture(picture1);
		((Karte) karten.get(1)).setPicture(picture1);
		((Karte) karten.get(2)).setPicture(picture2);
		((Karte) karten.get(3)).setPicture(picture2);
		((Karte) karten.get(4)).setPicture(picture3);
		((Karte) karten.get(5)).setPicture(picture3);
		((Karte) karten.get(6)).setPicture(picture4);
		((Karte) karten.get(7)).setPicture(picture4);
		((Karte) karten.get(8)).setPicture(picture5);
		((Karte) karten.get(9)).setPicture(picture5);
		((Karte) karten.get(10)).setPicture(picture6);
		((Karte) karten.get(11)).setPicture(picture6);

	}

	public ArrayList<Karte> getKarten() {
		return karten;
	}

	public void checkChanged(float delta) {
		if (flag == false) {
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
								if (flag == false) {
									score -= 5;
									if (score < 0)
										score = 0;
								}
								flag = true;
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
				flag = false;
				canOpen = true;
			}
		}
	}

	protected void restart() {

		Collections.shuffle(karten);

		karten.get(0).setPicture(picture1);
		karten.get(1).setPicture(picture1);
		karten.get(2).setPicture(picture2);
		karten.get(3).setPicture(picture2);
		karten.get(4).setPicture(picture3);
		karten.get(5).setPicture(picture3);
		karten.get(6).setPicture(picture4);
		karten.get(7).setPicture(picture4);
		karten.get(8).setPicture(picture5);
		karten.get(9).setPicture(picture5);
		karten.get(10).setPicture(picture6);
		karten.get(11).setPicture(picture6);

		karten.get(0).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(1).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(2).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(3).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(4).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(5).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(6).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(7).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(8).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(9).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(10).setState(CardState.TEMPORARILY_CLOSED);
		karten.get(11).setState(CardState.TEMPORARILY_CLOSED);

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
