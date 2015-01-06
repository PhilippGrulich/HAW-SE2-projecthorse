package com.haw.projecthorse.level.game.applerun;

import java.util.ArrayList;

/**
 * Helper Class. Für Collisionsprüfung der AppleRun Entities
 * @author Lars
 * @version 1.0
 */
public final class CollisionHandler {
	
	/**
	 * empty constructor.
	 */
	private CollisionHandler(){
		
	}
	/**
	* Prueft 2 Objekte auf Gleichheit.
	* @param collidable1 Object mit dem collision geprüft werden soll
	* @param collidable2 Object mit dem collision geprüft werden soll
	*/
	public static void collide(final Collidable collidable1, final Collidable collidable2) {
		
		if(collidable1.getHitbox().overlaps(collidable2.getHitbox())){
			collidable1.fireIsHit(collidable2);
			collidable2.fireIsHit(collidable1);
		}
	}
	
	/**
	 * Calls collide for each entity in group1, with each one in group2.
	 * @param group Group of collidibles
	 * @param group2 Group of collidibles
	 * */
	public static void collide(final EntityGroup group, final EntityGroup group2) {
		ArrayList<Entity> entityList = group.getEntityList();
		ArrayList<Entity> entityList2 = group2.getEntityList();
		for (Entity entity : entityList) {
			for (Entity entity2 : entityList2) {
				collide(entity, entity2);
			}
		}

	}

	/**
	 * Calls collide for each entity in group1, with the entity.
	 * @param group Group of collidibles
	 * @param collidable einzelner collidible
	 * */
	public static void collide(final EntityGroup group, final Collidable collidable) {
		ArrayList<Entity> entityList = group.getEntityList();
		for (Entity entity2 : entityList) {
			collide(entity2, collidable);
		}
	}


	/**
	 * Calls collide for each entity in group1, with the entity.
	 * @param group Group of collidibles
	 * @param collidable einzelner collidible
	 * */
	public static void collide(final Collidable collidable, final EntityGroup group) {
		collide(group, collidable); //Forward for inverted arguments
	}
	
}
