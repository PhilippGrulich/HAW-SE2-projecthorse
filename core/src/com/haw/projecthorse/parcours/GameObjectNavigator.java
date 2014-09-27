package com.haw.projecthorse.parcours;

public interface GameObjectNavigator {

	public boolean collides(GameObjectCollidable o1, GameObjectCollidable o2);
	public void moveLeft(GameObjectCollidable o, float delta);
	public void moveLeft(GameObject o, float delta);
	public void moveRight(GameObjectCollidable o, float delta);
	public void moveRight(GameObject o, float delta);
	public void moveUp(GameObjectCollidable o, float delta);
	public void moveUp(GameObject o, float delta);
	public void moveDown(GameObjectCollidable o, float delta);
	public void moveDown(GameObject o, float delta);
	
	
}
