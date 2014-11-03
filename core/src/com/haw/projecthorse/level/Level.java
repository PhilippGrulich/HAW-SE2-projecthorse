package com.haw.projecthorse.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.navbar.GameNavBar;
import com.haw.projecthorse.lootmanager.Chest;

/**
 * @author Lars Level . Abstract baseclass for Level implementations.
 * 
 *         ACHTUNG: Um Sicherzustellen das hier alle Methoden wie z.B. dispose()
 *         auch aufgerufen werden sind alle Methoden final. Ableitende Klassen
 *         mï¿½ssen stattdessen jeweils doDispose() usw. implementieren
 * 
 *         Alle Implementierungen Mï¿½SSEN im Konstruktor super() aufrufen!
 * 
 *         Jedes level hat ein Overlay. ï¿½ber das Overlay kï¿½nnen Popups unter
 *         anderm Popups angezeigt werden. *
 * 
 */

public abstract class Level implements Screen {

	// ########### DEBUG ##################
	// TODO für Release entfernen
	private FPSLogger fpsLogger = new FPSLogger();
	// ####################################

	private Boolean paused = false;
	private String levelID = null;
	private Viewport viewport;
	private OrthographicCamera cam;
	private SpriteBatch spriteBatch;
	protected Overlay overlay;
	protected Chest chest;

	protected final int height = GameManagerFactory.getInstance().getSettings()
			.getVirtualScreenHeight();
	protected final int width = GameManagerFactory.getInstance().getSettings()
			.getVirtualScreenWidth();

	public Level() {
		cam = createCamera();
		viewport = new FitViewport(width, height, cam);
		System.out.println(viewport.getTopGutterHeight());
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);		

		FitViewport overlayViewport = new FitViewport(width, height, createCamera());
		overlay = new Overlay(overlayViewport, spriteBatch, this);

		GameNavBar nav = new GameNavBar();
		this.overlay.setNavigationBar(nav);
		
		chest = new Chest();

	}
	
	
	
	/**
	 * Erstellt eine OrthographicCamera diese wird für die jeweiliegen Viewports gebraucht.
	 * @return {@link OrthographicCamera}
	 */
	private OrthographicCamera createCamera() {

		OrthographicCamera cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false); // Set to Y-Up - Coord system
		
		return cam;
	}

	public final void setLevelID(String newID) {
		if (levelID != null) {
			System.out.println("ACHTUNG Level id: " + levelID
					+ " umbenannt in: " + newID);
		}

		levelID = newID;

	}

	public final String getLevelID() {
		return levelID;
	}

	protected abstract void doRender(float delta); // Called by render() - to be
													// used in subclasses

	@Override
	public final void render(float delta) {
		paintBackground();
		// Wenn das spiel pausiert wird bekommt das untere level ein Delta von 0
		// übergeben.
		// Hierdurch wird sichergestellt das die Interaktionen
		if (paused) {
			delta = 0;
		}				
		doRender(delta);
		overlay.draw();

		// ########### DEBUG ##################
		// TODO für Release entfernen
		fpsLogger.log();
		// ####################################

	}
	
	/**
	 * Male den Hintergrund
	 */
	private void paintBackground(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	protected abstract void doDispose();

	@Override
	public final void dispose() {
		spriteBatch.dispose();
		overlay.dispose();
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
		paused = true;
		doPause();
	}

	protected abstract void doResume();

	@Override
	public final void resume() {
		paused = false;
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
