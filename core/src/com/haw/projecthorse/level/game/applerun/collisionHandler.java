package com.haw.projecthorse.level.game.applerun;

import java.util.ArrayList;

public class collisionHandler {

	private collisionHandler() {
		//Static only class
	}
	
	public static void collide(Collidable collidable1, Collidable collidable2) {
		
		if(collidable1.getHitbox().overlaps(collidable2.getHitbox())){
			collidable1.fireIsHit(collidable2);
			collidable2.fireIsHit(collidable1);
		}
	}
	
	/**
	 * Calls collide for each entity in group1, with each one in group2
	 * */
	public static void collide(EntityGroup group, EntityGroup group2) {
		ArrayList<Entity> entityList = group.getEntityList();
		ArrayList<Entity> entityList2 = group2.getEntityList();
		for (Entity entity : entityList) {
			for (Entity entity2 : entityList2) {
				collide(entity, entity2);
			}
		}

	}

	/**
	 * Calls collide for each entity in group1, with the entity
	 * */
	public static void collide(EntityGroup group, Collidable collidable) {
		ArrayList<Entity> entityList = group.getEntityList();
		for (Entity entity2 : entityList) {
			collide(entity2, collidable);
		}
	}


	/**
	 * Calls collide for each entity in group1, with the entity
	 * */
	public static void collide(Collidable collidable, EntityGroup group) {
		collide(group, collidable); //Forward for inverted arguments
	}
	
}
