package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Apple Entity.
 * @author Lars
 * @version 1.0
 */
public class Apple extends Entity {

	/**
	* Fallender Apfel.
	* @param texture .
	*/
	public Apple(final TextureRegion texture) {
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
