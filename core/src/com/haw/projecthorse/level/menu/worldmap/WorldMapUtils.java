package com.haw.projecthorse.level.menu.worldmap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.actions.Direction;

/**
 * Eine reine Utility Klasse für Methoden und Objekte, die alleine in der WorldMap genutzt werden
 */
class WorldMapUtils {
	
	// Verhindere Instanziierung der Utility Klasse
	private WorldMapUtils(){	
	}
	
	
	/**
	 * Liefert die Richtung für die Animation des Pferdes, die bei Bewegung von
	 * einem Punkt zum anderen benötogt wird.
	 * @param source Startpunkt der Bewegung
	 * @param target Zielpunkt der Bewegung
	 * @return Richtung für die Animation
	 */
	protected static Direction getDirection(Vector2 source, Vector2 target){
		Vector2 directionVector = new Vector2(target).sub(source);
		
		float angle = directionVector.angle();
		
		/* Die Richtungen sind 8 Kreisteile, die je eine Weite von 45 Grad haben. Rechts liegt zum Beispiel
		 * zwischen -22,5 (in LibGDX als 360 - 22,5 abgebildet) und +22,5 Grad. Um von den negativen Werten 
		 * wegzukommen, verschieben wir den Winkel um 22,5 Grad. Nun kann man durch einfache Einteilung 
		 * in 45 Grad Stücke ablesen, um welchen Kreisteil und somit um welche Richtung es geht.
		*/
		
		int angletype = ((int)(angle + 22.5f) % 360) / 45;
		
		switch (angletype) {
		case 0:
			return Direction.RIGHT;
		case 1:
			return Direction.UPRIGHT;
		case 2:
			return Direction.UP;
		case 3:
			return Direction.UPLEFT;
		case 4:
			return Direction.LEFT;
		case 5:
			return Direction.DOWNLEFT;
		case 6:
			return Direction.DOWN;
		case 7:
			return Direction.DOWNRIGHT;
		default:
			return null;	
		}
		
	}
	
	/**
	 * Gibt die zur letzten Bewegungsrichtung passende IdleDirection zurück
	 * @param lastDirection Letze Bewegungsrichtung
	 * @return
	 */
	protected static Direction getIdleDirection(Direction lastDirection){
		
		switch (lastDirection){
		case LEFT:
		case UPLEFT:
		case DOWNLEFT:
		case DOWN:
			return Direction.IDLELEFT;
		default:
			return Direction.IDLERIGHT;
		}
	}
	
	
	/**
	 * Prüft, ob der übergebene Player sich bewegt anhand des Vorhandenseins einer MoveByAction
	 * 
	 * @param player
	 * @return true wenn der Player sich bewegt, sonst false
	 */
	
	protected static boolean isPlayerMoving(Player player){
		Array<Action> actions = player.getActions();
		
		for (Action action : actions){
			if (action instanceof MoveByAction)
				return true;
		}
		
		return false;
	}
	
	
	
	
	
}
