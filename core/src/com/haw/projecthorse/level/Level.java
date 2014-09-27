package com.haw.projecthorse.level;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

/**
 * @author Lars Level . Abstract baseclass for Level implementations.
 * 
 *         ACHTUNG: Um Sicherzustellen das hier alle Methoden wie z.B. dispose()
 *         auch aufgerufen werden sind alle Methoden final. Ableitende Klassen
 *         müssen stattdessen jeweils doDispose() usw. implementieren
 *         
 *         Alle Implementierungen MÜSSEN im Konstruktor super() aufrufen!
 * 
 * 
 */

public abstract class Level implements Screen {

	private String levelID = null;
	private Viewport viewport;
	private OrthographicCamera cam;
	private SpriteBatch spriteBatch;

	protected final int height = GameManagerFactory.getInstance().getSettings()
			.getVirtualScreenHeight();
	protected final int width = GameManagerFactory.getInstance().getSettings()
			.getVirtualScreenWidth();

	public Level() {
		cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false); // Set to Y-Up - Coord system
		viewport = new FitViewport(width, height, cam);
		System.out.println(viewport.getTopGutterHeight());
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);

	}

	
	public final void setLevelID(String newID){
		if(levelID != null){
			System.out.println("ACHTUNG Level id: " + levelID + " umbenannt in: " + newID);
		}
		
		levelID = newID;
		
	}
	
	public final String getLevelID(){
		return levelID;
	}
	
	
	protected abstract void doRender(float delta); // Called by render() - to be
													// used in subclasses

	
	
	@Override
	public final void render(float delta) {
		doRender(delta);
	}

	protected abstract void doDispose();

	@Override
	public final void dispose() {
		spriteBatch.dispose();
		doDispose();

	}

	protected abstract void doResize(int width, int height);

	@Override
	public final void resize(int width, int height) {
		this.getViewport().update(width, height, true);
		doResize(width, height);
	}

	protected abstract void doShow();

	@Override
	public final void show() {
		doShow();

	}

	protected abstract void doHide();

	@Override
	public final void hide() {
		doHide();
	}

	protected abstract void doPause();

	@Override
	public final void pause() {
		doPause();
	}

	protected abstract void doResume();

	@Override
	public final void resume() {
		doResume();
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
