package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author Lars Entity: A base game object that does something. Like a falling apple etc. (Image is an Actor)
 */

public abstract class Entity extends Image implements Collidable {

	private Rectangle hitbox = new Rectangle();
	float pos_x;
	float pos_y;
	float fallingtime;
	
	public Entity(TextureRegion texture) {
		super(texture);
		initializeAsNewEntity();
	}

	protected SequenceAction generateActionSequenz() {
		Action minimize = Actions.scaleTo(0.01f, 0.01f, 0.0f);
		Action grow = Actions.scaleTo(0.6f, 0.6f, 0.25f);
		Action shrink = Actions.scaleTo(0.4f, 0.4f, 0.25f);
		Action normalize = Actions.scaleTo(0.5f, 0.5f, 0.25f);

		fallingtime = (((float) Math.random()) * 1.5f) + 2.5f;
		Action move = Actions.moveBy(0.0f, -1360.0f, fallingtime); //Hard coded 1360 - should just fall threw the bottom. Hardcoded to save time here

		return Actions.sequence(minimize, grow, shrink, normalize, move);

	}

	public void initializeAsNewEntity(){
		this.clearActions();
		this.addAction(generateActionSequenz());
		pos_x = (((float) Math.random()) * 636f) + 10;
		pos_y = (((float) Math.random()) * 200f) + 1000;
		this.setPosition(pos_x, pos_y);
	}
	// Bounding box for collision detection
	private void updateBounds() {
		this.setBounds(getX(), getY(), getImageWidth(), getImageHeight());
	}
	
	public Rectangle getHitbox() {
		// TODO check if coords are absolute. Wenn coords relativ könnten evtl. Gegenstände collidieren die sich gar nicht überschneiden
		// weil die Gegenstände beide relativ an pos 10,10 liegen
		// Evtl. hitbox nur updaten wenn sich was bewegt hat? Schneller?
		hitbox.x = this.getX();
		hitbox.y = this.getY();
		hitbox.width = this.getWidth() * this.getScaleX();
		hitbox.height = this.getHeight() * this.getScaleY();
		return hitbox;

	}

	@Override
	public void fireIsHit(Collidable otherObject) {
		// CURRENTLY NOTHING TO DO HERE, Collision is handled by PlayerAppleRun (removing this item from the group)

		// This is hit by "byActor";
		// If other is pferd -> deal with it / else bla
		// System.out.println("actor collided");
		// if(otherObject instanceof PlayerAppleRun){
		// ((PlayerAppleRun) otherObject).fireHitByEntity(this);
		// System.out.println("Collided with player object");
		// }

	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

}
