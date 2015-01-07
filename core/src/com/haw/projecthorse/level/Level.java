package com.haw.projecthorse.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;

/**
 * Level . Abstract baseclass for Level implementations.
 * 
 * @author Lars
 * 
 *         ACHTUNG: Um Sicherzustellen das hier alle Methoden wie z.B. dispose()
 *         auch aufgerufen werden sind alle Methoden final. Ableitende Klassen
 *         müssen stattdessen jeweils doDispose() usw. implementieren
 * 
 * 
 *         Jedes level hat ein Overlay. über das Overlay können Popups unter
 *         anderm Popups angezeigt werden.
 * @version 17.4
 */
public abstract class Level implements Screen {

	// ########### DEBUG ##################
	// private FPSLogger fpsLogger = new FPSLogger();
	// ####################################

	private Boolean paused = false;
	private boolean overlayPaused = false;
	private String levelID = null;
	private Viewport viewport;
	private OrthographicCamera cam;
	private SpriteBatch spriteBatch;
	protected Overlay overlay;

	protected int height;
	protected int width;

	protected AudioManager audioManager;
	private FitViewport overlayViewport;

	/**
	 * Constructor.
	 */
	public Level() {
		this(Orientation.Portrait);
	}

	/**
	 * Mittels diesem Konsturcktor kann eine {@link Orientation} übergeben
	 * werden.
	 * 
	 * @param orientation
	 *            orientation
	 */
	public Level(final Orientation orientation) {
		GameManagerFactory.getInstance().getPlatform().SetOrientation(orientation);
		height = GameManagerFactory.getInstance().getSettings().getVirtualScreenHeight();
		width = GameManagerFactory.getInstance().getSettings().getVirtualScreenWidth();

		createViewport();
		audioManager = AudioManagerImpl.getInstance();
		AssetManager.loadSounds("ui");
	}

	/**
	 * createViewport.
	 */
	public void createViewport() {
		cam = createCamera();
		viewport = new FitViewport(width, height, cam);
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);

		overlayViewport = new FitViewport(width, height, createCamera());
		overlay = new Overlay(overlayViewport, spriteBatch, this);
	}

	/**
	 * Erstellt eine OrthographicCamera diese wird für die jeweiliegen Viewports
	 * gebraucht.
	 * 
	 * @return {@link OrthographicCamera}
	 */
	private OrthographicCamera createCamera() {

		OrthographicCamera cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false); // Set to Y-Up - Coord system

		return cam;
	}

	/**
	 * setLevelID.
	 * 
	 * @param newID
	 *            neue LevelID
	 */
	public final void setLevelID(final String newID) {
		if (levelID != null) {
			Gdx.app.log("DEBUG", "ACHTUNG Level id: " + levelID + " umbenannt in: " + newID);
		}

		levelID = newID;

	}

	public final String getLevelID() {
		return levelID;
	}

	/**
	 * doRender.
	 * 
	 * @param delta
	 *            delta
	 */
	protected abstract void doRender(float delta); // Called by render() - to be
													// used in subclasses

	/**
	 * render.
	 * 
	 * @param delta
	 *            delta
	 */
	@Override
	public final void render(final float deltaIn) {
		float delta = deltaIn;
		// zu schnell Bug Fix
		delta = delta % 1;
		paintBackground();
		// Wenn das spiel pausiert wird bekommt das untere level ein Delta von 0
		// übergeben.
		// Hierdurch wird sichergestellt das die Interaktionen

		overlay.act(delta);
		if (paused || overlayPaused) {
			delta = 0;
		}
		doRender(delta);
		overlay.draw();

		// ########### DEBUG ##################
		// fpsLogger.log();
		// ####################################

	}

	/**
	 * Male den Hintergrund.
	 */
	private void paintBackground() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * doDispose.
	 */
	protected abstract void doDispose();

	@Override
	public final void dispose() {
		spriteBatch.dispose();
		overlay.dispose();
		audioManager = null;
		doDispose();

	}

	/**
	 * doResize.
	 * 
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	protected abstract void doResize(int width, int height);

	@Override
	public final void resize(final int width, final int height) {

		this.getViewport().update(width, height, true);
		this.overlayViewport.update(width, height, true);
		doResize(width, height);

	}

	/**
	 * doShow.
	 */
	protected abstract void doShow();

	@Override
	public final void show() {
		doShow();
	}

	/**
	 * doHide.
	 */
	protected abstract void doHide();

	@Override
	public final void hide() {
		doHide();
	}

	/**
	 * doPause.
	 */
	protected abstract void doPause();

	@Override
	public final void pause() {
		paused = true;
		doPause();
	}

	/**
	 * pause.
	 * 
	 * @param b
	 *            true == pause
	 */
	public final void pause(final boolean b) {
		overlayPaused = true;
		pause();
	}

	/**
	 * doDispose.
	 */
	protected abstract void doResume();

	@Override
	public final void resume() {
		if (!overlayPaused) {
			paused = false;
			doResume();
		}
	}

	/**
	 * resume.
	 * 
	 * @param b
	 *            b
	 */
	public final void resume(final boolean b) {
		overlayPaused = b;
		resume();
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
