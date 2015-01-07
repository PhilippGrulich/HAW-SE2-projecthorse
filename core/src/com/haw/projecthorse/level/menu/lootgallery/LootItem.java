package com.haw.projecthorse.level.menu.lootgallery;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.lootmanager.Lootable;

/**
 * Klasse für die einzelnen Loots in der LootGallery.
 * 
 * @author Oliver
 * @version 1.0
 */

public class LootItem extends WidgetGroup {
	private Table group;
	private Table nameAndDescription;
	private LabelStyle labelStyle;
	private Label description;

	private float contentHeight;

	/**
	 * Konstruktor.
	 * 
	 * @param loot
	 *            das zugrunde liegende Loot
	 * @param width
	 *            die Breite
	 */
	public LootItem(final Lootable loot, final float width) {
		createTable(loot, width);
		addActor(group);
		toFront();
	}

	/**
	 * Erstellt die Tabelle.
	 * 
	 * @param loot
	 *            das Loot
	 * @param maxWidth
	 *            die Breite
	 */
	private void createTable(final Lootable loot, final float maxWidth) {
		group = new Table();

		Drawable drawable = loot.getImage();
		Image img = new Image(drawable, Scaling.fit, Align.center);
		Label name;

		labelStyle = new LabelStyle(
				AssetManager.getHeadlineFont(FontSize.FORTY), Color.GRAY);
		name = new Label(loot.getName(), labelStyle);

		labelStyle.font = AssetManager.getTextFont(FontSize.FORTY);
		description = new Label(loot.getDescription(), labelStyle);
		description.setWidth(maxWidth - 260);
		description.setWrap(true);

		nameAndDescription = new Table();
		nameAndDescription.align(Align.left + Align.top);
		nameAndDescription.add(name).expandX().align(Align.left).spaceLeft(10);
		nameAndDescription.row();
		nameAndDescription.add(description).align(Align.left).spaceLeft(10)
				.width(maxWidth - 260);
		nameAndDescription.setSize(nameAndDescription.getPrefWidth(),
				nameAndDescription.getPrefHeight());

		group.add(img).size(200).pad(10, 0, 10, 10);
		group.add(nameAndDescription).pad(10, 0, 10, 0);
		group.pad(0);

		contentHeight = group.getPrefHeight() + 10;

		group.setSize(maxWidth, contentHeight);
		group.setBackground(new TextureRegionDrawable(AssetManager
				.getTextureRegion("ui", "panel_beige")));
	}

	@Override
	public float getPrefHeight() {
		return contentHeight;
	}

	@Override
	public float getPrefWidth() {
		return group.getPrefWidth();
	}

	@Override
	public void draw(final Batch batch, final float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
