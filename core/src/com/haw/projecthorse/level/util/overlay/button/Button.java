package com.haw.projecthorse.level.util.overlay.button;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.haw.projecthorse.level.util.overlay.NavigationBar;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;

public abstract class Button extends OverlayWidgetGroup {

	public Button() {
		
	}
	
	public NavigationBar getNavigationBar(){
		
		Group parent = this.getParent().getParent();
		return (NavigationBar) parent ;
	}
}
