package com.haw.projecthorse.parcours;

import com.badlogic.gdx.math.Intersector;

public class GameObjectNavigatorImpl implements GameObjectNavigator {

	@Override
	public boolean collides(GameObjectCollidable o1, GameObjectCollidable o2) {
		// TODO Auto-generated method stub
		return Intersector.overlapConvexPolygons(o1.getPolygon(), o2.getPolygon());
	}

	@Override
	public void moveLeft(GameObjectCollidable o, float delta) {
		if(o.getAcceleration() * delta + o.getVelocity() <= o.getMaxVelocity()){
			o.setVelocity(o.getAcceleration() * delta + o.getVelocity());
		}
		/*
		 * Weiterer Code...
		 */
	}

	@Override
	public void moveLeft(GameObject o, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight(GameObjectCollidable o, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight(GameObject o, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp(GameObjectCollidable o, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp(GameObject o, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown(GameObjectCollidable o, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown(GameObject o, float delta) {
		// TODO Auto-generated method stub
		
	}

}
