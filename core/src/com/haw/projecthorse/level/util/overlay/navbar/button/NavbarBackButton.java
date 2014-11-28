package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

public class NavbarBackButton extends NavbarButton {

	
	private Overlay overlay;
	public NavbarBackButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.BACK);
		this.setWidth(imageButton.getHeight());
		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(
					com.badlogic.gdx.scenes.scene2d.InputEvent event, float x,
					float y, int pointer, int button) {
				Gdx.app.log("NavbarBackButton Button", "NavbarBackButton BUTTON CLick");
				if (GameManagerFactory.getInstance().getCurrentLevelID().equals("mainMenu")){
					
					
					NavbarBackButton.this.init();
										
					Dialog beendenDialog = new Dialog("Möchtest du deine\nReise wirklich beenden?");
					beendenDialog.addButton("jaa, ich komme ja wieder", new ChangeListener() {
						
						@Override
						public void changed(ChangeEvent event, Actor actor) {
								 Gdx.app.exit();	
						}
						
					});
					
					beendenDialog.addButton("nein, bloß nicht", new ChangeListener() {
						
						@Override
						public void changed(ChangeEvent event, Actor actor) {
								NavbarBackButton.this.overlay.disposePopup();
								event.cancel();
						}
						
					});
					
					overlay.getLevel().pause();
					overlay.showPopup(beendenDialog);	
					
				} else 
					GameManagerFactory.getInstance().navigateBack();

				return true;
			};

		});
	}

	private void init(){
		this.overlay = this.getNavigationBar().getOverlay();
	}
}
