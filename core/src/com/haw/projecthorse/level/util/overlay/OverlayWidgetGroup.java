package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.haw.projecthorse.gamemanager.GameManagerFactory;

/**
 * Dies ist eine standart WidgetGroup die mittels der height und with property
 * erweitert wurde.
 * 
 * @author Philipp
 * @version 1.0
 */
public class OverlayWidgetGroup extends WidgetGroup {
	protected final int height = GameManagerFactory.getInstance().getSettings().getVirtualScreenHeight();
	protected final int width = GameManagerFactory.getInstance().getSettings().getVirtualScreenWidth();

	public float getPrefWidth() {
		return this.getWidth();
	}

	public float getPrefHeight() {
		return this.getHeight();
	}

	public float getMaxWidth() {
		return this.getWidth();
	}

	public float getMaxHeight() {
		return this.getHeight();
	}
}
