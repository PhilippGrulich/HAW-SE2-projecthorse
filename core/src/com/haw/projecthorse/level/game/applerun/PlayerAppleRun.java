package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * AppleRun implementierung des Players.
 * @author Lars
 * @version 1.0
 */
public class PlayerAppleRun extends PlayerImpl implements Collidable {
	private Rectangle hitbox;
	private final Gamestate gamestate;
	private static final float HITBOX_HEIGTH_REDUCTION = 128; // Hardcoded: To reduce height of the player class
	private static final float HITBOX_WIDTH_REDUCTION = 48;
	private static final float HITBOX_LEFT_ADDITION = HITBOX_WIDTH_REDUCTION / 3.0f; // Additional px from left
	private static final float HITBOX_RIGHT_SUB = HITBOX_WIDTH_REDUCTION / 3.0f * 2.0f;
	private Sound chewSound, hitSound;

	/**
	* Constructor.
	* @param gamestate gamestate
	*/
	public PlayerAppleRun(final Gamestate gamestate) {
		this.gamestate = gamestate;
		AudioManager audioManager = AudioManagerImpl.getInstance();
		chewSound = audioManager.getSound("AppleRun", "chew.mp3");
		hitSound = audioManager.getSound("AppleRun", "hit.mp3");
	}

	/**
	* Wenn der player von einer Entity getroffen wurde.
	* @param entity Collidierte Entity
	*/
	private void fireHitByEntity(final Entity entity) {
		// If hit by apple
		// Add score
		if (entity instanceof Apple) {
			// ??? //gamestate.addScore();
			gamestate.playerHitByApple();
			chewSound.play();
		}

		// If hit by branch - -score
		if (entity instanceof Branch) {
			hitSound.play();
			gamestate.playerHitByBranch();
		}

		gamestate.removeFallingEntity(entity);
	}

	@Override
	public Rectangle getHitbox() {
		hitbox = new Rectangle(this.getX() + HITBOX_LEFT_ADDITION, this.getY(), (this.getWidth() * this.getScaleX()) - HITBOX_RIGHT_SUB,
				(this.getHeight() * this.getScaleY()) - HITBOX_HEIGTH_REDUCTION);
		return hitbox;
	}

	/**
	* Wenn der player von einer Entity getroffen wurde.
	* @param otherObject Collidierte Entity
	*/
	@Override
	public void fireIsHit(final Collidable otherObject) {
		if (otherObject instanceof Entity) {
			fireHitByEntity((Entity) otherObject);

		} else {
			Gdx.app.log("PlayerAppleRun", "Collision hit by non-Entity class. Doing nothing");
		}

	}

}
