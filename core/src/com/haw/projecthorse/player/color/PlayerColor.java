package com.haw.projecthorse.player.color;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.haw.projecthorse.lootmanager.Loot;

/**
 * Klasse f�r die F�rbung des Spielers. Es sind bereits einige Farben als in
 * statischen Variablen vorgegeben.
 * 
 * @author Oliver
 * 
 */
public class PlayerColor extends Loot {
	static final PlayerColor[] DEFINED_COLORS = new PlayerColor[] {
			new PlayerColor(),
			new PlayerColor(false, 255, 255, 255, "Weiss"),
			new PlayerColor(true, 255, 214, 86, "Braun"),
			new PlayerColor(true, 255, 163, 44, "Dunkelbraun"),
			new PlayerColor(false, 255, 158, 72, "Hellbraun"),
			new PlayerColor(false, 255, 126, 223, "Pink"),
			new PlayerColor(false, 255, 202, 214, "Helles Pink"),
			new PlayerColor(false, 255, 58, 146, "Dunkles Pink"),
			new PlayerColor(false, 213, 110, 255, "Lila"),
			new PlayerColor(false, 192, 255, 250, "Eisblau"),
			new PlayerColor(true, 50, 214, 255, "Dunkelblau"),
			new PlayerColor(false, 255, 254, 92, "Gelb"),
			new PlayerColor(false, 255, 72, 72, "Rot"),
			new PlayerColor(true, 104, 255, 84, "Dunkelgr�n"),
			new PlayerColor(false, 132, 255, 108, "Hellgr�n") };

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
		this(true, 1f, 1f, 1f, "Schwarz");
	}

	/**
	 * Konstruktor f�r eine Spielerfarbe
	 * 
	 * @param red
	 *            Rotanteil der Farbe (Wert zwischen 0 und 255)
	 * @param green
	 *            Gr�nanteil der Farbe (Wert zwischen 0 und 255)
	 * @param blue
	 *            Blauanteil der Farbe (Wert zwischen 0 und 255)
	 * @param name
	 *            Bezeichnung der Farbe
	 */
	public PlayerColor(int red, int green, int blue, String name) {
		this(false, red, green, blue, name);
	}

	/**
	 * Konstruktor f�r eine Spielerfarbe
	 * 
	 * @param blackBase
	 *            Wahr, wenn das schwarze Spritesheet als Grundlage benutzt
	 *            werden soll
	 * @param red
	 *            Rotanteil der Farbe (Wert zwischen 0 und 255)
	 * @param green
	 *            Gr�nanteil der Farbe (Wert zwischen 0 und 255)
	 * @param blue
	 *            Blauanteil der Farbe (Wert zwischen 0 und 255)
	 * @param name
	 *            Bezeichnung der Farbe
	 */
	public PlayerColor(boolean blackBase, int red, int green, int blue,
			String name) {
		this(blackBase, red / 255f, green / 255f, blue / 255f, name);	
	}
	
	/**
	 * Konstruktor f�r eine Spielerfarbe
	 * 
	 * @param blackBase
	 *            Wahr, wenn das schwarze Spritesheet als Grundlage benutzt
	 *            werden soll
	 * @param red
	 *            Rotanteil der Farbe (Wert zwischen 0 und 1)
	 * @param green
	 *            Gr�nanteil der Farbe (Wert zwischen 0 und 1)
	 * @param blue
	 *            Blauanteil der Farbe (Wert zwischen 0 und 1)
	 * @param name
	 *            Bezeichnung der Farbe
	 */
	public PlayerColor(boolean blackBase, float red, float green, float blue,
			String name) {
		super(name, "Die Farbe '" + name + "' f�r dein Pferd");
		
		black = blackBase;
		color = new Color(red, green, blue, 1);
	}

	/**
	 * @return True, wenn das schwarze Spritesheet als Grundlage benutzt wird
	 */
	public boolean hasBlackBase() {
		return black;
	}

	public Color getColor() {
		return new Color(color);
	}

	@Override
	public int doHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (black ? 1231 : 1237);
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean doEquals(Object obj) {
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

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
