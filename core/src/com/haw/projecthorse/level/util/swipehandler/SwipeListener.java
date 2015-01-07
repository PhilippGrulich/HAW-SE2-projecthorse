package com.haw.projecthorse.level.util.swipehandler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.haw.projecthorse.player.actions.Direction;

/**
 * Abstrakte Oberklasse für Listerner, die Swipe-Bewegungen verarbeiten.
 * 
 * @author Oliver
 * @version 1.0
 */

public abstract class SwipeListener implements EventListener {

	@Override
	public boolean handle(final Event event) {
		if (!(event instanceof SwipeEvent)) {
			return false;
		}
		return handleSwiped((SwipeEvent) event, event.getTarget());
	}

	/**
	 * Verarbeitung des Swipe-Events auf höherer Ebene.
	 * 
	 * @param event
	 *            Das SwipeEvent, welches auch die Richtung beinhaltet.
	 * @param actor
	 *            Das Ziel des SwipeEvent. Das ist der Actor, dem dieser
	 *            Listener hinzugefügt wurde.
	 * @return false
	 */
	protected boolean handleSwiped(final SwipeEvent event, final Actor actor) {
		swiped(event, actor);
		return false;
	}

	/**
	 * Verarbeitet das SwipeEvent.
	 * 
	 * @param event
	 *            Das SwipeEvent, welches auch die Richtung beinhaltet.
	 * @param actor
	 *            Das Ziel des SwipeEvent. Das ist der Actor, dem dieser
	 *            Listener hinzugefügt wurde.
	 */
	public abstract void swiped(SwipeEvent event, Actor actor);

	/**
	 * Event wird geworfen, wenn eine Swipe-Bewegung erkannt wurde.
	 * 
	 * @author Oliver
	 */
	public static class SwipeEvent extends Event {
		private Direction direction;

		/**
		 * Konstruktor.
		 * 
		 * @param direction Richtung des Swipe-Events.
		 */
		public SwipeEvent(final Direction direction) {
			this.direction = direction;
		}

		public Direction getDirection() {
			return direction;
		}
	}

}
