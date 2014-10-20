package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

public class Overlay extends Stage {

	private NavigationBar navBar;

	private Level level;

	public Overlay(Viewport viewport, SpriteBatch spriteBatch, Level level) {
		super(viewport, spriteBatch);
		InputManager.addInputProcessor(this);
		this.level = level;
	}

	public void setNavigationBar(NavigationBar nav) {
		//if (navBar != null)
			// this.re(navBar);
			this.addActor(nav);
		navBar = nav;
	}

	public Level getLevel(){
		return level;
	}
	
	public void showPopup(Popup p) {
		this.addActor(p);		
	}

	public void disposePopup() {

	}

}
