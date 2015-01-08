package com.haw.projecthorse.gamemanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Screen;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;
import com.haw.projecthorse.gamemanager.splashscreen.SplashScreen;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.platform.DefaultPlatform;
import com.haw.projecthorse.platform.Platform;

/**
 * Dies ist die Startklasse des ganzen Spiels. Hier werden alle anderen Elemente
 * geladen und währenddessen der {@link SplashScreen} angezeigt.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public class CoreGameMain extends Game {

	private Screen splash;

	private String[] preloadAssets = new String[] { "ui", "menu", "notChecked" };

	private GameManagerImpl gameManager;

	/**
	 * Default Konstruktor.
	 */
	public CoreGameMain() {
		this(new DefaultPlatform());
	}

	/**
	 * Konstruktor.
	 * 
	 * @param nativ
	 *            Die Platform wird jeweils in den Plattformspezifischen
	 *            Projekten implementiert um ein besonderes Verhalten zu
	 *            erhalten.
	 */
	public CoreGameMain(final Platform nativ) {
		gameManager = GameManagerImpl.getInstance();
		gameManager.setPlatform(nativ);
	}

	/**
	 * Wenn alle Preloadassets geladen sind kann auch das Spiel gestartet
	 * werden.
	 * 
	 * @param nav
	 *            {@link NavigationManagerImpl}
	 */
	private void startGame(final NavigationManagerImpl nav) {

		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				if (AssetManager.isLoadingFinished()) {
					nav.navigateToMainMenu();
				} else {
					startGame(nav);
				}
			}
		});
	}

	/**
	 * Lädt alle für das Spiel wichtigen Assets in einem separaten Thread.
	 */
	private void loadGame() {
		Thread loadThread = new Thread() {
			public void run() {

				AssetManager.initialize();
				final NavigationManagerImpl nav = new NavigationManagerImpl(CoreGameMain.this);
				InputManager.createInstance();

				gameManager.setNavigationManager(nav);
				// Call startGame in the Render Thread

				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < preloadAssets.length; i++) {
							AssetManager.loadTexturRegionsAsync(preloadAssets[i]);
						}

						startGame(nav);
					}
				});
				startGame(nav);

				AssetManager.checkLicenses();
			}
		};
		loadThread.start();

	}

	@Override
	public final void create() {
		splash = new SplashScreen();
		this.setScreen(splash);
		loadGame();

		Gdx.app.addLifecycleListener(new LifecycleListener() {

			@Override
			public void resume() {
				Gdx.app.log("LifecycleListener", "resume");

			}

			@Override
			public void pause() {
				Gdx.app.log("LifecycleListener", "pause");

			}

			@Override
			public void dispose() {
				Gdx.app.log("LifecycleListener", "dispose");
				GameManagerFactory.getInstance().getSettings().dispose();
				AudioManagerImpl.getInstance().dispose();
				AssetManager.disposeAll();
			}
		});
	}

}
