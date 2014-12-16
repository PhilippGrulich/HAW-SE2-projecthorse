package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

public class DefaultScrollPane extends ScrollPane {
	private final float height, width;

	public DefaultScrollPane(Actor widget, float height, float width) {
		super(widget);
		ScrollPaneStyle paneStyle = new ScrollPaneStyle();
		paneStyle.vScroll = new TextureRegionDrawable(AssetManager.getTextureRegion("ui", "vScroll"));
		paneStyle.vScrollKnob = new TextureRegionDrawable(AssetManager.getTextureRegion("ui", "vScrollKnob"));
		paneStyle.corner = new TextureRegionDrawable(AssetManager.getTextureRegion("ui", "vScrollKnob"));
		setStyle(paneStyle);

		setScrollingDisabled(true, false);
		setFadeScrollBars(false);
		this.height = height;
		this.width = width;
	}

	@Override
	public float getPrefHeight() {
		return height;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getPrefWidth() {
		return width;
	}

}
