package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.haw.projecthorse.player.PlayerImpl;

public class PlayerAppleRun extends PlayerImpl implements Collidable {
	private Rectangle hitbox;
	private final Gamestate gamestate;

	public PlayerAppleRun(Gamestate gamestate) {
		this.gamestate = gamestate;
	}

	public void fireHitByEntity(Entity entity) {
		System.out.println("HIT");
		// If hit by apple
		// Add score

		// If hit by branch - -score

		// ??? //gamestate.addScore();

		gamestate.removeFallingEntity(entity);

	}

	@Override
	public Rectangle getHitbox() {
		hitbox = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		return hitbox;
	}

	@Override
	public void fireIsHit(Collidable otherObject) {
		if (otherObject instanceof Entity) {
			gamestate.removeFallingEntity((Entity) otherObject);
		} else {
			Gdx.app.log("PlayerAppleRun", "Collision hit by non-Entity class. Doing nothing");
		}

	}

}
