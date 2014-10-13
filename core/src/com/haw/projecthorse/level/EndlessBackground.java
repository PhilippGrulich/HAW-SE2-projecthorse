package com.haw.projecthorse.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EndlessBackground extends Actor {
	private TextureRegion img;
	private int stageWidth;
	private float startPosition = 0, duration;
	
	/**
	 * Ein Hintergrund, der sich endlos bewegt
	 * 
	 * @param stageWidth
	 *            Die Breite der Stage
	 * @param backgroundImage
	 *            Das Bild für den Hintergrund.
	 * @param duration
	 *            Die Dauer (in Sekunden), wie schnell das Bild von links nach
	 *            rechts laufen soll.
	 */
	public EndlessBackground(int stageWidth, TextureRegion backgroundImage,
			float duration) {
		img = backgroundImage;
		this.stageWidth = stageWidth;
		this.duration = duration;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (duration > 0)
			startPosition -= img.getRegionWidth() * (delta / duration);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (startPosition < -img.getRegionWidth()) {
			// das erste gemalte Bild waere gar nicht sichtbar
			// also koennen wir die startPosition verringern
			startPosition += img.getRegionWidth();
		}

		float pos = startPosition;

		while (pos < stageWidth) {
			batch.draw(img, pos, 0);
			pos += img.getRegionWidth();
		}
	}
}
