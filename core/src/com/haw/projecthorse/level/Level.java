package com.haw.projecthorse.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

/**
 * @author Lars Level . Abstract baseclass for Level implementations.
 * 
 *         ACHTUNG: Aus der ableitenden Klasse muss beim .dispose auch
 *         super.dispose() aufgerufen werden um die Ressourcen wieder
 *         freizugeben
 * 
 */

public abstract class Level implements HorseScreen {

	private Viewport viewport;
	private OrthographicCamera cam;
	private SpriteBatch spriteBatch;

	protected final int height = GameManagerFactory.getInstance().getSettings()
			.getScreenHeight();
	protected final int width = GameManagerFactory.getInstance().getSettings()
			.getScreenWidth();

	public Level() {
		cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false); // Set to Y-Up - Coord system
		viewport = new FitViewport(width, height, cam);
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	protected Viewport getViewport() {
		return viewport;
	}

	protected OrthographicCamera getCam() {
		return cam;
	}

	protected SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

}
