package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;

public final class GameObjectFactory {

	private GameObjectFactory() {
	}
	
	private static Texture generateAppleTexture(){
		//TODO load a real apple texture
		return generateDefaultTexture();
	}
	
	public static Apple getApple(){
//		return generateDefaultTexture();
		return new Apple(generateAppleTexture());
		
	}
	
	public static Branch getBranch(){
		return new Branch(generateBrownTexture());
	}
	
	private static Texture generateDefaultTexture() {
		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
		pixel.setColor(Color.RED);
		pixel.fill();
		Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		return defaultTexture;
		
	}

	private static Texture generateBrownTexture() {
		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
		pixel.setColor(Color.rgba8888(205f/255f, 130f/255f, 63f/255f, 1f));
		pixel.fill();
		Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		return defaultTexture;
		
	}
	
}
