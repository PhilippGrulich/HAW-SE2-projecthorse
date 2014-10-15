package com.haw.projecthorse.level.applerun;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

public class EntityGroup extends Group{

	public EntityGroup() {
		// TODO Auto-generated constructor stub
	}
	
	/** Adds an actor as a child of this group. The actor is first removed from its parent group, if any.
	 * @see #remove() */
	@Override
	public void addActor (Actor actor) {
		if(actor instanceof Entity){
			super.addActor(actor);
		}
		else{
//			System.out.println("Only entities allowed. Ignored adding of Actor-Element inside this Entity group");
			//throw new Exception("Only Entities allowed");
			throw new IllegalArgumentException("Only Entities allowed");
		}
		
	}
	
	public void addActor(Entity entity){
		super.addActor(entity);
	}
	
	/** Adds an actor as a child of this group, at a specific index. The actor is first removed from its parent group, if any.
	 * @param index May be greater than the number of children. */
	@Override
	public void addActorAt (int index, Actor actor) {
		if(actor instanceof Entity){
			super.addActorAt(index, actor);
		}
		else{
//			System.out.println("Only entities allowed. Ignored adding of Actor-Element inside this Entity group");

			throw new IllegalArgumentException("Only Entities allowed");
		}
		
	}
	
	/** Adds an actor as a child of this group, immediately before another child actor. The actor is first removed from its parent
	 * group, if any. */
	@Override
	public void addActorBefore (Actor actorBefore, Actor actor) {
		if(actor instanceof Entity){
			super.addActorBefore(actorBefore, actor);
		}
		else{
			System.out.println("Only entities allowed. Ignored adding of Actor-Element inside this Entity group");
			//throw new Exception("Only Entities allowed");
		}
		
	}

	/** Adds an actor as a child of this group, immediately after another child actor. The actor is first removed from its parent
	 * group, if any. */
	@Override
	public void addActorAfter (Actor actorAfter, Actor actor) {
		if(actor instanceof Entity){
			super.addActorAfter(actorAfter, actor);
		}
		else{
//			System.out.println("Only entities allowed. Ignored adding of Actor-Element inside this Entity group");
			throw new IllegalArgumentException("Only Entities allowed");
		}
		
	}
	
	/** Returns an ordered list of child actors in this group. */
	public ArrayList<Entity> getEntityList () {
		SnapshotArray<Actor> childList = super.getChildren();
		ArrayList<Entity> entityList = new ArrayList<Entity>();
		for(Actor elem : childList){
			if(elem instanceof Entity){
				entityList.add((Entity)elem);
			}
			else{
				System.out.println("Non-Entity inside EntityGroup !!! Shouldn't have happened o.O");
			}
				
		}
		return entityList;
	}
	
}
