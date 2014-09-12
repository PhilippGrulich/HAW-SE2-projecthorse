package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class Apple extends Entity {

	private static Texture appleTexture;
	
	public Apple() {
		
		
		//Add Wackel action
		//Add falling action
		
		
	}	

	
	private void initTexture(){
		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
		pixel.setColor(Color.RED);
		pixel.fill();
		appleTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
	}


	@Override
	protected Texture loadTexture() {
		initTexture(); //Load the texture to the local var
		return appleTexture;
	}
}
