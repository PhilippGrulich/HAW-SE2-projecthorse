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
import com.badlogic.gdx.utils.Scaling;
import com.esotericsoftware.tablelayout.Cell;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.lootmanager.Lootable;

public class LootItem extends WidgetGroup {
	private Table group;
	private Table nameAndDescription;
	private LabelStyle labelStyle;
	private Cell<Label> descriptionCell;
	private Label description;
//	private boolean descriptionIsVisible = false;
	
	public LootItem(Lootable loot, float width) {
		createTable(loot, width);
		
		/*addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				toogleDescription();
			}
		});*/
		
		addActor(group);
		toFront();
	}
	
	private void createTable(Lootable loot, float maxWidth) {
		group = new Table();
		
		Drawable drawable = loot.getImage();
		Image img = new Image(drawable, Scaling.fit, Align.center);
		Label name;
		
		// Größe und Position vom Bild normalisieren
		float width = drawable.getMinWidth(), height = drawable.getMinHeight(), faktor;
		if (width > height) {
			faktor = 200 / width;
		} else {
			faktor = 200 / height;
		}
				
		labelStyle = new LabelStyle(AssetManager.getTextFont(FontSize.FORTY), Color.GRAY);
		name = new Label(loot.getName(), labelStyle);
		
		description = new Label(loot.getDescription(), labelStyle);
		description.setWrap(true);
		
		nameAndDescription = new Table();
		nameAndDescription.align(Align.left + Align.top);
		nameAndDescription.add(name).expandX().align(Align.left).spaceLeft(10);
		nameAndDescription.row();
		descriptionCell = nameAndDescription.add(description).align(Align.left).spaceLeft(10).width(maxWidth-200);
		
		group.add(img).size(200);
		group.add(nameAndDescription);
		group.left();
	}
	
	/*private void toogleDescription() {
		if (descriptionIsVisible) {
//			nameAndDescription.removeActor(description);
			descriptionCell.setWidget(null);
		} else {
//			nameAndDescription.addActor(description);
			descriptionCell.setWidget(description);
		}
		
		nameAndDescription.layout();
		descriptionIsVisible = !descriptionIsVisible;
	}*/
	
	@Override
	public float getPrefHeight() {
		return group.getPrefHeight();
	}
	
	@Override
	public float getPrefWidth() {
		return group.getPrefWidth();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
