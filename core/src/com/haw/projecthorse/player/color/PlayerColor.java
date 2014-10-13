package com.haw.projecthorse.player.color;

import com.badlogic.gdx.graphics.Color;

/**
 * Klasse für die Färbung des Spielers.
 * Es sind bereits einige Farben als in statischen Variablen vorgegeben.
 * 
 * @author Oliver
 *
 */
public class PlayerColor {
	static final PlayerColor[] DEFINED_COLORS = new PlayerColor[] {
		new PlayerColor(true, 255, 255, 255),
		new PlayerColor(false, 255, 255, 255),
		new PlayerColor(true, 255, 214, 86),
		new PlayerColor(true, 255, 163, 44),
		new PlayerColor(false, 255, 158, 72),
		new PlayerColor(false, 255, 126, 223),
		new PlayerColor(false, 255, 202, 214),
		new PlayerColor(false, 255, 58, 146),
		new PlayerColor(false, 213, 110, 255),
		new PlayerColor(false, 192, 255, 250),
		new PlayerColor(true, 50, 214, 255),
		new PlayerColor(false, 255, 254, 92),
		new PlayerColor(false, 255, 72, 72),
		new PlayerColor(true, 104, 255, 84),
		new PlayerColor(false, 132, 255, 108)
	};
	
	public static final PlayerColor BLACK = DEFINED_COLORS[0];
	public static final PlayerColor WHITE = DEFINED_COLORS[1];
	public static final PlayerColor BROWN = DEFINED_COLORS[2];
	public static final PlayerColor DARK_BRONW = DEFINED_COLORS[3];
	public static final PlayerColor LIGHT_BROWN = DEFINED_COLORS[4];
	public static final PlayerColor PINK = DEFINED_COLORS[5];
	public static final PlayerColor LIGHT_PINK = DEFINED_COLORS[6];
	public static final PlayerColor DARK_PINK = DEFINED_COLORS[7];
	public static final PlayerColor LILA = DEFINED_COLORS[8];
	public static final PlayerColor ICE_BLUE = DEFINED_COLORS[9];
	public static final PlayerColor DARK_BLUE = DEFINED_COLORS[10];
	public static final PlayerColor YELLOW = DEFINED_COLORS[11];
	public static final PlayerColor RED = DEFINED_COLORS[12];
	public static final PlayerColor DARK_GREEN = DEFINED_COLORS[13];
	public static final PlayerColor LIGHT_GREEN = DEFINED_COLORS[14];

	private Color color;
	private boolean black;
	boolean enabled = false;

	public PlayerColor() {
		this(true, 1f, 1f, 1f);
	}
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (black ? 1231 : 1237);
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerColor other = (PlayerColor) obj;
		if (black != other.black)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
}
