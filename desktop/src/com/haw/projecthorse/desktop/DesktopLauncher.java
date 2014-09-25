package com.haw.projecthorse.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.haw.projecthorse.gamemanager.CoreGameMain;


public class DesktopLauncher {
	
	public static void main (String[] arg) {		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 360;
		config.height = 640;
		new LwjglApplication(new CoreGameMain(), config);
		
	}
}


