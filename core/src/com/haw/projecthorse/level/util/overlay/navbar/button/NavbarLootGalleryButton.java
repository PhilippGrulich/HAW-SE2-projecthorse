package com.haw.projecthorse.level.util.overlay.navbar.button;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.haw.projecthorse.gamemanager.GameManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;

/**
 * Dieser Button öffnet die LootGallery.
 * 
 * @author Philipp
 * @version 1.0
 */
public class NavbarLootGalleryButton extends NavbarButton {
	/**
	 * Konstruktor.
	 */
	public NavbarLootGalleryButton() {
		ImageButton imageButton = new ButtonSmall(ButtonSmall.ButtonType.LOOT);

		this.addActor(imageButton);
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer,
					final int button) {

				GameManager gm = GameManagerFactory.getInstance();
				if (gm.getCurrentLevelID() != "lootGallery") {
					gm.navigateToLevel("lootGallery");
				}

				return true;
			};

		});
		this.setWidth(imageButton.getHeight());
	}
}
