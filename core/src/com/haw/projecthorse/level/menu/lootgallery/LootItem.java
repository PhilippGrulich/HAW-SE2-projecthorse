package com.haw.projecthorse.level.menu.lootgallery;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.lootmanager.Lootable;

public class LootItem extends WidgetGroup {
	private HorizontalGroup group;
	private VerticalGroup nameAndDescription;
	private LabelStyle labelStyle;
	private Label description;
	private boolean descriptionIsVisible = false;
	
	public LootItem(Lootable loot, float width) {
		createTable(loot);
		
		description = new Label(loot.getDescription(), labelStyle);
		
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				toogleDescription();
			}
		});
		
		addActor(group);
		toFront();
	}
	
	private void createTable(Lootable loot) {
		group = new HorizontalGroup();
		group.align(Align.left + Align.top);
		Drawable drawable = loot.getImage();
		ScaleableImage img = new ScaleableImage(drawable);
		Label name;
		
		// Größe und Position vom Bild normalisieren
		float width = drawable.getMinWidth(), height = drawable.getMinHeight(), faktor;
		if (width > height) {
			faktor = 200 / width;
		} else {
			faktor = 200 / height;
		}
		
		img.setScale(faktor);
		
		labelStyle = new LabelStyle(AssetManager.getTextFont(FontSize.FORTY), Color.GRAY);
		name = new Label(loot.getName(), labelStyle);
		
		nameAndDescription = new VerticalGroup();
		nameAndDescription.align(Align.left + Align.top);
		nameAndDescription.addActor(name);
		nameAndDescription.pack();
		
		group.addActor(img);
		group.addActor(nameAndDescription);
		group.pack();
	}
	
	private void toogleDescription() {
		if (descriptionIsVisible) {
			nameAndDescription.removeActor(description);
		} else {
			nameAndDescription.addActor(description);
		}
		
		descriptionIsVisible = !descriptionIsVisible;
	}
	
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
		Actor a = group.getChildren().first();
		super.draw(batch, parentAlpha);
	}
	
	private class ScaleableImage extends Image {
		public ScaleableImage(Drawable drawable) {
			super(drawable);
		}
		
		@Override
		public float getPrefHeight() {
			return super.getPrefHeight() * super.getScaleY();
		}
		
		@Override
		public float getPrefWidth() {
			return super.getPrefWidth() * super.getScaleX();
		}
	}
}
