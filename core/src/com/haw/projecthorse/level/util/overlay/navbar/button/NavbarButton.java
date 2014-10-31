package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;
import com.haw.projecthorse.level.util.overlay.navbar.NavBar;

public abstract class NavbarButton extends OverlayWidgetGroup {

	public NavbarButton() {
		
	}
	
	public NavBar getNavigationBar(){		
		Group parent = this.getParent().getParent();
		return (NavBar) parent ;
	}
}
