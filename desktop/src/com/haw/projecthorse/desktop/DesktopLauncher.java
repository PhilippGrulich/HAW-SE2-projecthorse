package com.haw.projecthorse.desktop;

import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.haw.projecthorse.gamemanager.CoreGameMain;
import com.haw.projecthorse.platform.Platform;


public class DesktopLauncher implements Platform {
	
	private  LwjglApplication app;
	private LwjglApplicationConfiguration config;
	private Orientation active = Orientation.Portrait;
	public DesktopLauncher() {
		
	    config = new LwjglApplicationConfiguration();
		config.height = 640;
		config.width = 360;
		config.title = "Mein Pferdeabenteuer";
		
		app = new LwjglApplication(new CoreGameMain(this), config);
	}

	public static void main (String[] arg) {		
		new DesktopLauncher();
	}

	@Override
	public void SetOrientation(Orientation orientation) {
		active=orientation;
		if(orientation == Orientation.Landscape)
			app.getGraphics().setDisplayMode(640, 360, false);
		else
			app.getGraphics().setDisplayMode(360,640, false);
		
	}

	@Override
	public Orientation getOrientation() {
		return active;
	}
}


