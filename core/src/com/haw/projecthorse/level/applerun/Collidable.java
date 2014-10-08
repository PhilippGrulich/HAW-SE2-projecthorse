package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {

	public abstract Rectangle getHitbox();
	public abstract void fireIsHit(Collidable otherObject);
}
