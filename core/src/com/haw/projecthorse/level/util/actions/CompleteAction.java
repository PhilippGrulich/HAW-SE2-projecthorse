package com.haw.projecthorse.level.util.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Diese Action ermöglicht es auf das ende einer SequenceAction zu reagieren.
 * 
 * @author Philipp
 * @version 1.0
 */
public abstract class CompleteAction extends Action {

	/**
	 * Wird aufgerufen wenn die Action bearbeitet wurde.
	 */
	public abstract void done();

	@Override
	public final boolean act(final float delta) {
		done();
		return true;
	}

}
