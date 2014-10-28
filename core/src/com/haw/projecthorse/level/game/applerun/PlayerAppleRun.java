package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.haw.projecthorse.player.PlayerImpl;

public class PlayerAppleRun extends PlayerImpl implements Collidable {
	private Rectangle hitbox;
	private final Gamestate gamestate;
	private final float HITBOX_HEIGTH_REDUCTION = 128; //Hardcoded: To reduce height of the player class
	private final float HITBOX_WIDTH_REDUCTION = 48;
	private final float HITBOX_LEFT_ADDITION = HITBOX_WIDTH_REDUCTION/3.0f; //Additional px from left
	private final float HITBOX_RIGHT_SUB = HITBOX_WIDTH_REDUCTION/3.0f*2.0f;
	
	public PlayerAppleRun(Gamestate gamestate) {
		this.gamestate = gamestate;
	}

	private void fireHitByEntity(Entity entity) {
		// If hit by apple
		// Add score
		


		// ??? //gamestate.addScore();
		
		// If hit by branch - -score
		if(entity instanceof Branch){
			gamestate.playerHitByBranch();
		}

		gamestate.removeFallingEntity(entity);

	}

	@Override
	public Rectangle getHitbox() {
		hitbox = new Rectangle(this.getX()+ HITBOX_LEFT_ADDITION, this.getY(), (this.getWidth()*this.getScaleX())-HITBOX_RIGHT_SUB, (this.getHeight()*this.getScaleY())-HITBOX_HEIGTH_REDUCTION);
		return hitbox;
	}

	@Override
	public void fireIsHit(Collidable otherObject) {
		if (otherObject instanceof Entity) {
			fireHitByEntity((Entity)otherObject);
			
		} else {
			Gdx.app.log("PlayerAppleRun", "Collision hit by non-Entity class. Doing nothing");
		}

	}

}
