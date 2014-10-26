package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class TimeBar extends Image {

	public TimeBar() {
		super(generateDefaultTexture());
	}


	private static Texture generateDefaultTexture() {
		Pixmap pixel = new Pixmap(600, 16, Format.RGBA8888);
		pixel.setColor(Color.BLUE);
		pixel.fill();
		Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		return defaultTexture;

	}

}
