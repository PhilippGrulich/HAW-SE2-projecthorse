package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author Lars Entity: A base game object that does something. Like a falling
 *         apple etc. (Image is an Actor)
 *         
 *         
 *         &&Actor.collide
 *         Group.collide(actor/group)
 *         actor.collision(source)
 * 
 *         Override "static Texture loadTexture()" to load init gfx
 */

public abstract class Entity extends Image {

	public Entity(Texture texture) {
		super(texture);
		
		
		
		this.addAction(generateActionSequenz());
		
	
		float pos_x = (((float) Math.random()) * 636f) + 10;

		float pos_y = (((float) Math.random()) * 200f) + 1000;
		this.setPosition(pos_x, pos_y);
		//updateBounds();
		

	}
	
	private SequenceAction generateActionSequenz(){
		Action grow = Actions.scaleTo(1.1f, 1.1f, 0.25f);
		Action shrink = Actions.scaleTo(0.9f, 0.9f, 0.25f);
		Action normalize = Actions.scaleTo(1, 1, 0.25f);
		
		//Action shrink = Actions.scaleTo(1f, 1f, 0.25f);
		
		float fallingtime = (((float) Math.random()) * 1.5f) + 2.5f;   
		Action move = Actions.moveBy(0.0f, -1280.0f, fallingtime);
		
		return Actions.sequence(grow, shrink, normalize, move);
		
	}
	
	

	// Bounding box for collision detection
	private void updateBounds() {
		this.setBounds(getX(), getY(), getImageWidth(),
				getImageHeight());
	}

	@Override
	public void act(float delta) {

		super.act(delta);

		//updateBounds(); //Update bounding box for collision detection

	}

	// protected abstract void handleCollision(Actor actor); //Handle if you
	// have hit something

}
