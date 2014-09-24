package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author Lars Entity: A base game object that does something. Like a falling
 *         apple etc. (Image is an Actor)
 * 
 *         Override "static Texture loadTexture()" to load init gfx
 */

public abstract class Entity extends Image {

	public Entity(Texture texture) {
		super(texture);
		
		
		float fallingtime = (((float) Math.random()) * 3f) + 5f;   
		
		Action move = Actions.moveBy(0.0f, -1280.0f, fallingtime);
		this.addAction(move);
		
	
		float pos_x = (((float) Math.random()) * 636f) + 10;

		float pos_y = (((float) Math.random()) * 200f) + 1000;
		this.setPosition(pos_x, pos_y);

	}

	// Bounding box for collision detection
	private void updateBounds() {
		this.setBounds(getImageX(), getImageY(), getImageWidth(),
				getImageHeight());
	}

	@Override
	public void act(float delta) {

		super.act(delta);

		// updateBounds(); //Update bounding box for collision detection

	}

	// protected abstract void handleCollision(Actor actor); //Handle if you
	// have hit something

}
