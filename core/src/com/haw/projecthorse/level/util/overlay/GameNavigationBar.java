package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.level.util.overlay.button.PauseButton;

public class GameNavigationBar extends NavigationBar {

	public GameNavigationBar(Viewport viewport, Batch batch) {
		super(viewport, batch);
		PauseButton pauseButton = new PauseButton();
		addButton(pauseButton);
	}

}
