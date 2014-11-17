package com.haw.projecthorse.level.game.memoryspiel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.game.memoryspiel.Karte.State;

public class KartenManager {

	private ArrayList<Karte> karten  = new ArrayList<Karte>();
	private TextureRegion atlant;
	
	
	public KartenManager(){
		setUpKarten();
	}
	
	
	public void setUpKarten(){	
		Karte karte1 = new Karte(new Vector2(40,75));
		Karte karte2 = new Karte(new Vector2(265,75));
		Karte karte3 = new Karte(new Vector2(490,75));
		Karte karte4 = new Karte(new Vector2(40,300));
		Karte karte5 = new Karte(new Vector2(265,300));
		Karte karte6 = new Karte(new Vector2(490,300));
		Karte karte7 = new Karte(new Vector2(40,525));
		Karte karte8 = new Karte(new Vector2(265,525));
		Karte karte9 = new Karte(new Vector2(490,525));
		Karte karte10 = new Karte(new Vector2(40,750));
		Karte karte11 = new Karte(new Vector2(265,750));
		Karte karte12 = new Karte(new Vector2(490,750));
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
		
		Drawable picture1 = new TextureRegionDrawable(AssetManager.getTextureRegion("memorySpiel","Foto" + 1));
		Drawable picture2 = new TextureRegionDrawable(AssetManager.getTextureRegion("memorySpiel","Foto" + 2));
		Drawable picture3 = new TextureRegionDrawable(AssetManager.getTextureRegion("memorySpiel","Foto" + 3));
		((Karte) karten.get(0)).setPicture(picture1);
		((Karte) karten.get(1)).setPicture(picture1);
		((Karte) karten.get(2)).setPicture(picture2);
		((Karte) karten.get(3)).setPicture(picture2);
		((Karte) karten.get(4)).setPicture(picture3);
		((Karte) karten.get(5)).setPicture(picture3);
			
	}
	
	public ArrayList<Karte> getKarten(){
		return karten;
	}
	
	
	public void checkChanged(){
//		for (Karte karte1 : karten) {
//			if (karte1.getState() == State.TEMPORARILY_OPENED) {
//				for (Karte karte2 : karten) {
//					if (!(karte1.equals(karte2))
//							&& karte2.getState() == State.TEMPORARILY_OPENED) {
//						if (karte1.getPicture().equals(karte2.getPicture())) {
//							karte1.setState(State.OPEN);
//							karte2.setState(State.OPEN);
//						}
//						else { 
//							karte1.setState(State.CLOSED);
//							karte2.setState(State.CLOSED);
//							karte1.setDrawable(Karte.karte);
//							karte2.setDrawable(Karte.karte);
//						}
//					}
//				}
//			}
//		}
	}
}
