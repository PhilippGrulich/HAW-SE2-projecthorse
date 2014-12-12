package com.haw.projecthorse.level.util.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

public abstract class CompleteAction extends Action {

	public abstract void done();

	@Override
	public boolean act(float delta) {
		done();
		return true;
	}

}
