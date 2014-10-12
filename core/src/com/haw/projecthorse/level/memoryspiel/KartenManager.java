package com.haw.projecthorse.level.memoryspiel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haw.projecthorse.assetmanager.AssetManager;

public class KartenManager {

	private Texture[] pictures;
	private ArrayList<Karte> karten;
	private TextureAtlas atlant;
	
	
	public KartenManager(){
		karten = new ArrayList();	
		atlant = AssetManager.load("hamburg", false, false, true);
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
			
	}
	
	public ArrayList getKarten(){
		return karten;
	}
}
