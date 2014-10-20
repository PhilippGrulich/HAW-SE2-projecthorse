package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

public  class Overlay extends WidgetGroup {

	private NavigationBar navBar;

	private Level level;

	public Overlay(Level level) {
		this.level = level;
	}

	public void setNavigationBar(NavigationBar nav) {
		if (navBar != null)
			this.removeActor(navBar);
		this.addActor(nav);
		navBar = nav;
	}

	public void showPopup(Popup p) {
		this.addActor(p);
	}

	public void disposePopup() {

	}

}
