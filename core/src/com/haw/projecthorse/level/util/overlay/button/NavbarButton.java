package com.haw.projecthorse.level.util.overlay.button;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.haw.projecthorse.level.util.overlay.NavBar;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;

public abstract class NavbarButton extends OverlayWidgetGroup {

	public NavbarButton() {
		
	}
	
	public NavBar getNavigationBar(){		
		Group parent = this.getParent().getParent();
		return (NavBar) parent ;
	}
}
