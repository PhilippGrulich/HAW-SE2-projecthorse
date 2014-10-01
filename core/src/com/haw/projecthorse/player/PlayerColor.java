package com.haw.projecthorse.player;

import com.badlogic.gdx.graphics.Color;

/**
 * Klasse für die Färbung des Spielers.
 * Es sind bereits einige Farben als in statischen Variablen vorgegeben.
 * 
 * @author Oliver
 *
 */
public class PlayerColor {
	public static final PlayerColor BLACK = new PlayerColor(true, 255, 255, 255);
	public static final PlayerColor WHITE = new PlayerColor(false, 255, 255, 255);
	public static final PlayerColor BROWN = new PlayerColor(true, 255, 214, 86);
	public static final PlayerColor DARK_BRONW = new PlayerColor(true, 255, 163, 44);
	public static final PlayerColor LIGHT_BROWN = new PlayerColor(false, 255, 158, 72);
	public static final PlayerColor PINK = new PlayerColor(false, 255, 126, 223);
	public static final PlayerColor LIGHT_PINK = new PlayerColor(false, 255, 202, 214);
	public static final PlayerColor DARK_PINK = new PlayerColor(false, 255, 58, 146);
	public static final PlayerColor LILA = new PlayerColor(false, 213, 110, 255);
	public static final PlayerColor ICE_BLUE = new PlayerColor(false, 192, 255, 250);
	public static final PlayerColor DARK_BLUE = new PlayerColor(true, 50, 214, 255);
	public static final PlayerColor YELLOW = new PlayerColor(false, 255, 254, 92);
	public static final PlayerColor RED = new PlayerColor(false, 255, 72, 72);
	public static final PlayerColor DARK_GREEN = new PlayerColor(true, 104, 255, 84);
	public static final PlayerColor LIGHT_GREEN = new PlayerColor(false, 132, 255, 108);

	private Color color;
	private boolean black;

	/**
	 * Konstruktor für eine Spielerfarbe
	 * 
	 * @param blackBase
	 *            Wahr, wenn das schwarze Spritesheet als Grundlage benutzt
	 *            werden soll
	 * @param red
	 *            Rotanteil der Farbe (Wert zwischen 0 und 1)
	 * @param green
	 *            Grünanteil der Farbe (Wert zwischen 0 und 1)
	 * @param blue
	 *            Blauanteil der Farbe (Wert zwischen 0 und 1)
	 */
	public PlayerColor(boolean blackBase, float red, float green, float blue) {
		black  = blackBase;
		color = new Color(red, green, blue, 1);
	}
	
	/**
	 * Konstruktor für eine Spielerfarbe
	 * 
	 * @param red
	 *            Rotanteil der Farbe (Wert zwischen 0 und 255)
	 * @param green
	 *            Grünanteil der Farbe (Wert zwischen 0 und 255)
	 * @param blue
	 *            Blauanteil der Farbe (Wert zwischen 0 und 255)
	 */
	public PlayerColor(int red, int green, int blue) {
		this(false, red, green, blue);
	}
	
	/**
	 * Konstruktor für eine Spielerfarbe
	 * 
	 * @param blackBase
	 *            Wahr, wenn das schwarze Spritesheet als Grundlage benutzt
	 *            werden soll
	 * @param red
	 *            Rotanteil der Farbe (Wert zwischen 0 und 255)
	 * @param green
	 *            Grünanteil der Farbe (Wert zwischen 0 und 255)
	 * @param blue
	 *            Blauanteil der Farbe (Wert zwischen 0 und 255)
	 */
	public PlayerColor(boolean blackBase, int red, int green, int blue) {
		float r = red / 255f;
		float g = green / 255f;
		float b = blue / 255f;
		
		black = blackBase;
		color = new Color(r, g, b, 1);
	}

	/**
	 * @return Wahr, wenn das schwarze Spritesheet als Grundlage benutzt wird
	 */
	public boolean hasBlackBase() {
		return black;
	}
	
	public Color getColor() {
		return new Color(color);
	}
}
