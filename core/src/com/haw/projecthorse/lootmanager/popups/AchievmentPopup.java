package com.haw.projecthorse.lootmanager.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;

/**
 * Kleines Popup für die Methode addLootAndShowAchievment der Klasse
 * {@link Chest}Chest.
 * 
 * @author Oliver
 * @version 1.0
 */

public class AchievmentPopup extends WidgetGroup {
	private float width, height;

	/**
	 * Konsturktor.
	 * 
	 * @param name
	 *            Name des Loots
	 * @param img
	 *            Bild des Loots
	 * @param parentWidth
	 *            maximale Breite des Popups
	 * @param parentHeight
	 *            Y-Position des Popups
	 */
	public AchievmentPopup(final String name, final Drawable img,
			final float parentWidth, final float parentHeight) {
		height = 100;
		width = parentWidth * 0.7f;

		createLabelAndImg(name, img);
		setBounds((parentWidth - width) / 2, parentHeight + 10, width, height);

		addAction(Actions.sequence(Actions.moveBy(0, -height, 0.8f),
				Actions.delay(0.5f), Actions.fadeOut(1.6f),
				Actions.removeActor()));
	}

	/**
	 * Erzeugt die Elemente für den Namen und das Bild des Loots.
	 * 
	 * @param name
	 *            Name des Loots
	 * @param img
	 *            Bild des Loots
	 */
	private void createLabelAndImg(final String name, final Drawable img) {
		Table table = new Table();
		table.setFillParent(true);
		table.left().top();
		table.setBackground(new TextureRegionDrawable(AssetManager
				.getTextureRegion("ui", "buttonBackground")));

		Image icon = new Image(img, Scaling.fit);
		table.add(icon).height(70).maxWidth(120).pad(15, 0, 15, 0);

		Label desc = new Label(name, new LabelStyle(
				AssetManager.getTextFont(FontSize.FORTY), Color.DARK_GRAY));
		desc.setEllipse(true);
		table.add(desc).expandX().left();

		addActor(table);
	}
}
