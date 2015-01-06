package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * Fallender Ast.
 * 
 * @author Lars
 * @version 1.0
 */
public class Branch extends Entity {
	float fallingtime;

	/**
	 * Constructor.
	 * @param texture image
	 */
	public Branch(final TextureRegion texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SequenceAction generateActionSequenz() {
		Action minimize = Actions.scaleTo(0.01f, 0.01f, 0.0f);
		Action grow = Actions.scaleTo(1.1f, 1.1f, 0.25f);
		Action shrink = Actions.scaleTo(0.8f, 0.8f, 0.25f);
		Action normalize = Actions.scaleTo(1, 1, 0.25f);

		fallingtime = (((float) Math.random()) * 1.5f) + 2.5f;
		Action move = Actions.moveBy(0.0f, -1280.0f - (this.getHeight() * this.getScaleY()), fallingtime);

		return Actions.sequence(minimize, grow, shrink, normalize, move);

	}
}
