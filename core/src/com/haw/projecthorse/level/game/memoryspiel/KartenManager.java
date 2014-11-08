package com.haw.projecthorse.level.game.memoryspiel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.game.memoryspiel.Karte.State;

public class KartenManager {

	private ArrayList<Karte> karten  = new ArrayList<Karte>();
	private TextureRegion atlant;
	private Texture newlyOpened;
	private ArrayList<Karte> lastChanged;
	
	
	public KartenManager(){
		atlant = AssetManager.getTextureRegion("city","Sankt-Michaelis-Kirche_Hamburg");
	}
	
	public void setUpKarten(){	
		Texture picture1 = new Texture(Gdx.files.internal("pictures/memorySpiel/" + 1));;
		Texture picture2 = new Texture(Gdx.files.internal("pictures/memorySpiel/" + 2));
		Texture picture3 = new Texture(Gdx.files.internal("pictures/memorySpiel/" + 3));
		((Karte) karten.get(1)).setPicture(picture1);
		((Karte) karten.get(2)).setPicture(picture1);
		((Karte) karten.get(3)).setPicture(picture2);
		((Karte) karten.get(4)).setPicture(picture2);
		((Karte) karten.get(5)).setPicture(picture3);
		((Karte) karten.get(6)).setPicture(picture3);
	
		
		Collections.shuffle(karten);
		lastChanged = karten;	
	}
	
	public ArrayList<Karte> getKarten(){
		return karten;
	}
	
	
	public boolean checkChanged(){
		for (Karte karte1 : karten) {
			if (karte1.getState() == State.TEMPORARILY_OPENED) {
				for (Karte karte2 : karten) {
					if (!(karte1.equals(karte2))
							&& karte2.getState() == State.TEMPORARILY_OPENED) {
						if (karte1.getPicture().equals(karte2.getPicture())) {
							karte1.setState(State.OPEN);
							karte2.setState(State.OPEN);
							newlyOpened = karte1.getPicture();
							return true;
						}
						else { 
							karte1.setState(State.CLOSED);
							karte2.setState(State.CLOSED);
							return false;
						}
					}
				}
			}
			return false;
		}
		return false;
	}
}
