package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

/**
 * Dieser Button ermöglicht es zurück zu navigieren.
 * 
 * @author Philipp
 * @version 1.0
 */
public class NavbarBackButton extends NavbarButton {

	/**
	 * Konstruktor.
	 */
	public NavbarBackButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.BACK);
		this.setWidth(imageButton.getHeight());
		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer,
					final int button) {
				Gdx.app.log("NavbarBackButton Button", "NavbarBackButton BUTTON CLick");
				if (GameManagerFactory.getInstance().getCurrentLevelID().equals("mainMenu")) {

					final Overlay overlay = NavbarBackButton.this.getNavigationBar().getOverlay();

					Dialog beendenDialog = new Dialog("Möchtest du deine\nReise wirklich beenden?");
					beendenDialog.addButton("jaa, ich komme ja wieder", new ChangeListener() {

						@Override
						public void changed(final ChangeEvent event, final Actor actor) {
							Gdx.app.exit();
						}

					});

					beendenDialog.addButton("nein, bloß nicht", new ChangeListener() {

						@Override
						public void changed(final ChangeEvent event, final Actor actor) {
							overlay.disposePopup();
							event.cancel();
						}

					});

					overlay.getLevel().pause();
					overlay.showPopup(beendenDialog);

				} else {
					GameManagerFactory.getInstance().navigateBack();
				}

				return true;
			};

		});
	}
}
