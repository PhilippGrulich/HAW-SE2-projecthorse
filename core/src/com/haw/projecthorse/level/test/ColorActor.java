package com.haw.projecthorse.level.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.haw.projecthorse.assetmanager.AssetManager;

public class ColorActor extends Actor {
	private Pixmap colors;
	private TextureRegion c = AssetManager.getTextureRegion("notChecked", "colors");
	
	public ColorActor() {
		TextureData td = c.getTexture().getTextureData();
		td.prepare();
		Pixmap colorsPixmap = td.consumePixmap();

		colors = new Pixmap(c.getRegionWidth(), c.getRegionHeight(), colorsPixmap.getFormat());
		colors.drawPixmap(colorsPixmap, 0, 0, c.getRegionX(),
				c.getRegionY(), c.getRegionWidth(), c.getRegionHeight());

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(c, super.getX(), super.getY());
		batch.draw(c.getTexture(), super.getX(), super.getY(), 256, 256, c.getRegionX(), c.getRegionY(), 256, 256, false, true);
	}
	
	public Color getColorForPosition(int x, int y) {
		return new Color(colors.getPixel(x, y));
	}
}
