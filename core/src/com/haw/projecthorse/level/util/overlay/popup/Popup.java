package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;

public class Popup extends OverlayWidgetGroup {
	
	int popupHeigh  = height/4;
	int popupWith = width;
	public Popup() {

		// 1. Load & Set gfx
		Pixmap pixel = new Pixmap(popupWith, popupHeigh, Format.RGBA8888); // Create a
																// temp-pixmap
		// to use as a
		// background
		// texture
		pixel.setColor(Color.GRAY);
		pixel.fill();
		Image image = new Image(new Texture(pixel, Format.RGBA8888, true));
		image.setX(0);
		image.setY((height/2)-popupHeigh/2);
		this.addActor(image);

	}

}
