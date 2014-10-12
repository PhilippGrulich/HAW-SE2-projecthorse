package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

public class Overlay extends Stage  {
	protected final int height = GameManagerFactory.getInstance().getSettings()
			.getVirtualScreenHeight();
	protected final int width = GameManagerFactory.getInstance().getSettings()
			.getVirtualScreenWidth();
	
	
}
