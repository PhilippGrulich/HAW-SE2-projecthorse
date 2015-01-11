package com.haw.projecthorse.level.util.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Endloser Hintergrund.
 * @author Oliver
 * @version 1.0
 *
 */
public class EndlessBackground extends Actor {
	private TextureRegion img;
	private int stageWidth;
	private float startPosition = 0, duration;

	/**
	 * Ein Hintergrund, der sich endlos bewegt.
	 * 
	 * @param stageWidth
	 *            Die Breite der Stage
	 * @param backgroundImage
	 *            Das Bild für den Hintergrund.
	 * @param duration
	 *            Die Dauer (in Sekunden), wie schnell das Bild von links nach
	 *            rechts laufen soll.
	 */
	public EndlessBackground(final int stageWidth, 
			final TextureRegion backgroundImage, final float duration) {
		img = backgroundImage;
		this.stageWidth = stageWidth;
		this.duration = duration;

		toBack();
	}

	@Override
	public void act(final float delta) {
		super.act(delta);
		if (duration > 0){
			startPosition -= img.getRegionWidth() * (delta / duration);
		}
	}

	@Override
	public void draw(final Batch batch, final float parentAlpha) {
		if (startPosition < -img.getRegionWidth()) {
			// das erste gemalte Bild waere gar nicht sichtbar
			// also koennen wir die startPosition verringern
			startPosition += img.getRegionWidth();
		}
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, 1f);
		float pos = startPosition;

		while (pos < stageWidth) {
			batch.draw(img, pos, 0);
			pos += img.getRegionWidth() - 10;
		}
	}
}
