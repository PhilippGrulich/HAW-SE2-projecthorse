package com.haw.projecthorse.gamemanager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.navigationmanager.NavigationManagerImpl;
import com.haw.projecthorse.intputmanager.InputManager;

public class CoreGameMain extends Game {

	@Override
	public final void create() {

		ExecutorService executor = Executors.newFixedThreadPool(1);
		  AssetManager.initialize();
		
		
		FutureTask<NavigationManagerImpl> navigationImpelTask = new FutureTask<NavigationManagerImpl>(new Callable<NavigationManagerImpl>() {
            @Override
            public NavigationManagerImpl call() {
                return new NavigationManagerImpl(CoreGameMain.this);		
            }
        });
		executor.execute(navigationImpelTask);
		
		
		NavigationManagerImpl navigationManager;		
		try {	InputManager.createInstance();
				GameManagerImpl gameManager = GameManagerImpl.getInstance();	
				AssetManager.finishLoading();
				navigationManager = navigationImpelTask.get();
			
	
					
				gameManager.setNavigationManager(navigationManager);
				navigationManager.navigateToMainMenu();	
				AssetManager.checkLicenses();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
