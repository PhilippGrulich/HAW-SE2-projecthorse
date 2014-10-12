package com.haw.projecthorse.level.util.overlay.button;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.haw.projecthorse.level.util.overlay.Overlay;

public abstract class OverlayButton extends Overlay {
	
	Button button;
	public OverlayButton(){
		button = createButton();		
		addActor(button);
		
	}
	
	@Override
	public boolean addListener(EventListener listener) {
		return button.addListener(listener);
	};
	
	@Override
	public boolean addCaptureListener(EventListener listener) {
		return button.addCaptureListener(listener);
	}
	
	
	protected abstract Button createButton();

}
