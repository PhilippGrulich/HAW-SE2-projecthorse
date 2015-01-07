package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

/**
 * Ein Scrollpane im Einheitsdesign.
 * 
 * @author Philipp
 * @version 1
 *
 */
public class DefaultScrollPane extends ScrollPane {
	private final float height, width;

	/**
	 * Der Default Konstruktor.
	 * 
	 * @param widget
	 *            Das Container Objekt, dass gescrollt werden soll
	 * @param height
	 *            Die gewünschte Höhe
	 * @param width
	 *            Die gewünschte Breite
	 */
	public DefaultScrollPane(final Actor widget, final float height,
			final float width) {
		super(widget);
		ScrollPaneStyle paneStyle = new ScrollPaneStyle();
		paneStyle.vScroll = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "vScroll"));
		paneStyle.vScrollKnob = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "vScrollKnob"));
		paneStyle.corner = new TextureRegionDrawable(
				AssetManager.getTextureRegion("ui", "vScrollKnob"));
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
