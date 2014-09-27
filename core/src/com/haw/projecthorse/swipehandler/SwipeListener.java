package com.haw.projecthorse.swipehandler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.haw.projecthorse.player.Direction;

public abstract class SwipeListener implements EventListener {

	@Override
	public boolean handle(Event event) {
		if (!(event instanceof SwipeEvent)) return false;
		swiped((SwipeEvent)event, event.getTarget());
		return false;
	}
	
	/**
	 * Verarbeitet das SwipeEvent
	 * @param event Das SwipeEvent, welches auch die Richtung beinhaltet.
	 * @param actor Das Ziel des SwipeEvent. Das ist der Actor, dem dieser Listener hinzugefügt wurde.
	 */
	public abstract void swiped(SwipeEvent event, Actor actor);

	/**
	 * Event wird geworfen, wenn eine Swipe-Bewegung erkannt wurde.
	 * @author Oliver
	 */
	public static class SwipeEvent extends Event {
		private Direction direction;
		public SwipeEvent(Direction direction) {
			this.direction = direction;
		}
		
		public Direction getDirection() {
			return direction;
		}
	}

}
