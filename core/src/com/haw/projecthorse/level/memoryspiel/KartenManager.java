package com.haw.projecthorse.level.memoryspiel;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class KartenManager {

	private Texture[] pictures;
	private Karte[] karten = new Karte[6];
	
	
	
	public KartenManager(){
		karten = new Karte[6];	

	}
	
	public void setUpKarten(){	
		Random rand = new Random();
		Karte karte1 = new Karte();
		Karte karte2 = new Karte();
		Texture picture;
		int j = 0;
		int k = 0;
		for(int i = 0; i<3; i++){
			int in = rand.nextInt(3);
			picture =  new Texture(Gdx.files.internal("pictures/memorySpiel/" + in));
			karte1.setPicture(picture);
			karte2.setPicture(picture);
			
		}
		
	}
	
	public Karte[] getKarten(){
		return karten;
	}
}
