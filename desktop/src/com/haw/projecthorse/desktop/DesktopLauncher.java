package com.haw.projecthorse.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.haw.projecthorse.gamemanager.CoreGameMain;


public class DesktopLauncher {
	
	public static void main (String[] arg) {		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720;
		config.height = 1280;
		new LwjglApplication(new CoreGameMain(), config);
		
	}
}


