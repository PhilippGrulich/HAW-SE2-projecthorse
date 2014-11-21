package com.haw.projecthorse.gamemanager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;
import com.haw.projecthorse.gamemanager.splashscreen.SplashScreen;
import com.haw.projecthorse.intputmanager.InputManager;

public class CoreGameMain extends Game {

	private Screen splash;

	private void startGame(NavigationManagerImpl nav) {
		GameManagerImpl gameManager = GameManagerImpl.getInstance();
		gameManager.setNavigationManager(nav);
		nav.navigateToMainMenu();
	}

	// Läd alle für das Spiel wichtiegen Assets in einem seperaten Thread
	private void loadGame() {
		Thread loadThread = new Thread() {
			public void run() {
				
				AssetManager.initialize();
				final NavigationManagerImpl nav = new NavigationManagerImpl(CoreGameMain.this);
				InputManager.createInstance();
				AssetManager.finishLoading();
				// Call startGame in the Render Thread
				
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						startGame(nav);
					}
				});
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
	}

}
