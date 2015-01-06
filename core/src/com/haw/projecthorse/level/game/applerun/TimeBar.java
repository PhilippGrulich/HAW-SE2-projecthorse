package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Zeit anzeigender Balken der langsam schrumpft, bis die Zeit abgelaufen ist.
 * @author Lars
 * @version 1.0
 */
public class TimeBar extends Image {

	/**
	* Constructor.
	*/
	public TimeBar() {
		super(generateDefaultTexture());
	}

	/**
	* generateDefaultTexture.
	* @return Texture
	*/
	private static Texture generateDefaultTexture() {
		Pixmap pixel = new Pixmap(600, 16, Format.RGBA8888);
		pixel.setColor(Color.BLUE);
		pixel.fill();
		Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		return defaultTexture;

	}

}
