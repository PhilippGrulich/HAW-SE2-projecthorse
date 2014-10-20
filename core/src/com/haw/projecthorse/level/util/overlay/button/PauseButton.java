package com.haw.projecthorse.level.util.overlay.button;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.util.overlay.NavigationBar;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

public class PauseButton extends Button {

	private Popup p;
	
	public PauseButton() {
		ImageButton imageButton = new ImageButton(getDrawable());
		
		
		this.addActor(imageButton);
		this.addListener(new InputListener() {
			
			@Override
			public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("PAUSE Button", "PAUSE BUTTON CLick");
				NavigationBar navBar = PauseButton.this.getNavigationBar();
		
				Popup p = new Popup();
				navBar.getOverlay().showPopup(p);
				
				return true;				
			};
			
		});
	}
	
	private Drawable getDrawable() {
		TextureAtlas atlant = AssetManager.load("ui", false, false, true);
		Drawable drawable = new TextureRegionDrawable(atlant.findRegion("pause_icon"));
		return drawable;
	}	

}
