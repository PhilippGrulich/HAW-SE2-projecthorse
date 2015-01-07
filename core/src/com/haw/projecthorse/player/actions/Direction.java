package com.haw.projecthorse.player.actions;

/**
 * Enum für die Richtungen der Player-Animationen.
 * 
 * @author Oliver und Viktor
 * @version 1.0
 */

public enum Direction {
	RIGHT, LEFT, UP, DOWN, DOWNLEFT, DOWNRIGHT, UPLEFT, UPRIGHT, IDLELEFT, IDLERIGHT, JUMPLEFT, JUMPRIGHT;

	/**
	 * Getter für den Datei-Präfix der Richtung für die Animation-Sprites.
	 * 
	 * @return der Datei-Präfix
	 */
	public String getImagePrefix() {

		switch (this) {
		case LEFT:
		case RIGHT:
			return "side-";
		case UP:
			return "up-";
		case DOWN:
			return "down-";
		case DOWNLEFT:
		case DOWNRIGHT:
			return "downleft-";
		case UPLEFT:
		case UPRIGHT:
			return "upleft-";
		case IDLELEFT:
		case IDLERIGHT:
			return "idle-";
		case JUMPLEFT:
		case JUMPRIGHT:
			return "jump-";
		default:
			return "getImagePrefix()_WRONG_DIRECTION_ENUM_SUPPLIED";
		}
	}

	/**
	 * Anzahl der Frames. (Bis dort zaehlt die Animationsschleife (inklusive))
	 * Beispiel: Idle hat z.B. zwei Frames: "Idle-1.png" und "Idle-2.png". In
	 * diesem Fall wird dann 2 zurückgegeben.
	 * 
	 * @return Anzahl der Frames der Bewegung dieser Richtung
	 */
	public int getLastFrameId() {
		switch (this) {
		case LEFT:
		case UP:
		case DOWN:
		case DOWNLEFT:
		case UPLEFT:
		case RIGHT:
		case DOWNRIGHT:
		case UPRIGHT:
			return 6;

		case IDLELEFT:
		case IDLERIGHT:
			return 2;

		case JUMPLEFT:
		case JUMPRIGHT:
			return 7;

		default:
			return -1;
		}
	}

	/**
	 * Gibt an ob das Bild an der y-Achse gespiegelt werden soll. Mit true wird
	 * z.B. ein nach links laufendes Pferd nach rechts laufend.
	 * 
	 * @return True, wenn das Bild für die Animation an der y-Achse gespiegelt
	 *         werden soll.
	 */
	public boolean getFlipX() {
		switch (this) {
		case LEFT:
		case UP:
		case DOWN:
		case DOWNLEFT:
		case UPLEFT:
		case JUMPLEFT:
		case IDLELEFT:
			return false;

		case RIGHT:
		case DOWNRIGHT:
		case UPRIGHT:
		case JUMPRIGHT:
		case IDLERIGHT:
			return true;

		default:
			return false;
		}
	}

}
