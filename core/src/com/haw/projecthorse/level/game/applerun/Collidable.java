package com.haw.projecthorse.level.game.applerun;

import com.badlogic.gdx.math.Rectangle;

/**
 * Objekte die kollidieren können.
 * @author Lars
 * @version 1.0
 */
public interface Collidable {

	/**
	 * Hit box. Collidable fläche.
	* @return Rectangle (hitbox)
	*/
	public abstract Rectangle getHitbox();
	
	/**
	* Prueft 2 Objekte auf Gleichheit.
	* @param otherObject Object mitdem "Collidiert" wurde
	*/
	public abstract void fireIsHit(Collidable otherObject);
}
