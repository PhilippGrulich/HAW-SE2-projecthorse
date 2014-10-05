package com.haw.projecthorse.level.applerun;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class collisionHandler {

	public collisionHandler() {
		// TODO Auto-generated constructor stub
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
	public static void collide(EntityGroup group, Entity entity) {
		ArrayList<Entity> entityList = group.getEntityList();
		for (Entity entity2 : entityList) {
			collide(entity2, entity);
		}
	}

	/**
	 * Creates a Rectangle as a hitbox for the actor and collides it with the entity.
	 * */
	public static void collide(Actor actor, Entity entity) {
		Rectangle hitboxActor = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		collide(actor, hitboxActor, entity, entity.getHitbox());
	}

	/**
	 * Calls collide for the actor with each entity in the group.
	 * */
	public static void collide(Actor actor, EntityGroup group) {
		ArrayList<Entity> entityList = group.getEntityList();
		for (Entity entity : entityList) {
			collide(actor, entity);
		}
	}

	/**
	 * Real collision detection. Other overloaded methods call this one to check for the real collision Only Entity-Class Objects get the
	 * "fireIsHitEvent", but with the other actor as a source. They need to forward it, if needed
	 * */
	private static void collide(Actor actor1, Rectangle hitbox1, Actor actor2, Rectangle hitbox2) {

		// TODO Class Intersector?! for collision
		// test if both collide
		// collide -> fireEvent to Entity#
		if (!actor1.isVisible() || !actor2.isVisible()) { // No collision without visibility
			System.out.println("Invisible object inside collision detection group");
			return;
		}

		boolean isHit = hitbox1.overlaps(hitbox2);

		if (isHit) {
			if (actor1 instanceof Entity) {
				fireIsHitEvent((Entity) actor1, actor2);

			}
			if (actor2 instanceof Entity) {
				fireIsHitEvent((Entity) actor2, actor1);
			}
		}

	}

	/**
	 * Collide two entities, specific Entity-Version, because it is faster to use the Entity-Rectangle; instead of creating a new one (Like
	 * for other Actor-Types)
	 * 
	 * Forwards to the generic collide(actor,hitbox,actor,hitbox)-Method
	 * */
	public static void collide(Entity entity, Entity entity2) {
		Rectangle box1 = entity.getHitbox();
		Rectangle box2 = entity2.getHitbox();

		collide(entity, box1, entity2, box2);
	}

	/**
	 * Message to a hit-Entity with the other Object as the source. If the other Actor is not an Entity, it will never be notified of the
	 * hit. Thus the Entity has to handle it for the Actor too.
	 * */
	private static void fireIsHitEvent(Entity target, Actor source) {
		target.fireIsHit(source);
	}

}
