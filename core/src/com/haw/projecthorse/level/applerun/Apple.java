package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Texture;

public class Apple extends Entity {

	public Apple(Texture texture) {
		super(texture);
		
	}	

	/*@Override
	protected static Texture loadTexture(){
		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
		pixel.setColor(Color.RED);
		pixel.fill();
		Texture appleTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		return appleTexture;
	}*/

	

}
