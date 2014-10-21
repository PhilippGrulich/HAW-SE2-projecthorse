package com.haw.projecthorse.level.huetchenspiel;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Hat extends Image{
	
	private int id;

	/**
	 * Konstruktor
	 * @param tex jeweilige Texture fuer den Hut
	 * @param id Identifikation, um beim Speil den gewaehlten
	 * 			 Hut zu erkennen
	 */
	public Hat(TextureRegion tex, int id){
		super(tex);
		this.id = id;
	}
	
	/**
	 * Hut-Id zurueckgeben
	 * @return int Hut-Id
	 */
	public int getID(){
		return this.id;
	}
	

	
}
