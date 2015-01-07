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

public class LootItem extends WidgetGroup {
	private Table group;
	private Table nameAndDescription;
	private LabelStyle labelStyle;
	private Label description;
	
	private float contentHeight;
	
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
				
		labelStyle = new LabelStyle(AssetManager.getHeadlineFont(FontSize.FORTY), Color.GRAY);
		name = new Label(loot.getName(), labelStyle);
		
		labelStyle.font = AssetManager.getTextFont(FontSize.FORTY);
		description = new Label(loot.getDescription(), labelStyle);
		description.setWrap(true);
		
		nameAndDescription = new Table();
		nameAndDescription.align(Align.left + Align.top);
		nameAndDescription.add(name).expandX().align(Align.left).spaceLeft(10);
		nameAndDescription.row();
		nameAndDescription.add(description).align(Align.left).spaceLeft(10).width(maxWidth-260);
		
		group.add(img).size(200).pad(10, 0, 10, 10);
		group.add(nameAndDescription);
		
		contentHeight = group.getPrefHeight() + 10;
		
		group.setSize(maxWidth, contentHeight);
		group.setBackground(new TextureRegionDrawable(AssetManager.getTextureRegion("ui", "panel_beige")));
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
		return contentHeight;
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
