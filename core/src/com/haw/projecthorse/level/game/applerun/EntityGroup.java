package com.haw.projecthorse.level.game.applerun;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;


/**
 * Gruppe von Spiel Entities.
 * @author Lars
 * @version 1.0
 */
public class EntityGroup extends Group{

	/**
	 * Constructor.
	 */	
	public EntityGroup() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Actor als child hinzufügen. Actor wird vorher aus parent group entfernt, falls vorhanden.
	 * @param actor actor
	 */
	@Override
	public void addActor(final Actor actor) {
		if(actor instanceof Entity){
			super.addActor(actor);
		}
		else{
			throw new IllegalArgumentException("Only Entities allowed");
		}
		
	}
	
	/**
	 * entity als child hinzufügen. Actor wird vorher aus parent group entfernt, falls vorhanden.
	 * @param entity eine Entity
	 */
	public void addActor(final Entity entity){
		super.addActor(entity);
	}
	
	/** Adds an actor as a child of this group, at a specific index. The actor is first removed from its parent group, if any.
	 * @param index May be greater than the number of children. 
	 * @param actor actor 
	 * */
	@Override
	public void addActorAt(final int index, final Actor actor) {
		if(actor instanceof Entity){
			super.addActorAt(index, actor);
		}
		else{
			throw new IllegalArgumentException("Only Entities allowed");
		}
		
	}
	
	/** Adds an actor as a child of this group, immediately before another child actor. The actor is first removed from its parent
	 * group, if any. 
	 * @param actorBefore actor
	 * @param actor actor
	 * */
	@Override
	public void addActorBefore(final Actor actorBefore, final Actor actor) {
		if(actor instanceof Entity){
			super.addActorBefore(actorBefore, actor);
		}
		else{
			Gdx.app.log("WARNING", "Only entities allowed. Ignored adding of Actor-Element inside this Entity group");
		}
		
	}

	/** Adds an actor as a child of this group, immediately after another child actor. The actor is first removed from its parent
	 * group, if any. 
	 * @param actorAfter actor
	 * @param actor actor
	 * */
	@Override
	public void addActorAfter(final Actor actorAfter, final Actor actor) {
		if(actor instanceof Entity){
			super.addActorAfter(actorAfter, actor);
		}
		else{
			throw new IllegalArgumentException("Only Entities allowed");
		}
		
	}
	
	/** 
	 * Returns an ordered list of child actors in this group.
	 * @return ArrayList<Entity>
	 * */
	public ArrayList<Entity> getEntityList() {
		SnapshotArray<Actor> childList = super.getChildren();
		ArrayList<Entity> entityList = new ArrayList<Entity>();
		for(Actor elem : childList){
			if(elem instanceof Entity){
				entityList.add((Entity)elem);
			}
			else{
				Gdx.app.log("WARNING", "Non-Entity inside EntityGroup !!! Shouldn't have happened o.O");
			}
				
		}
		return entityList;
	}
	
}
