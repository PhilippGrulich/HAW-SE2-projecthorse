package com.haw.projecthorse.platform;

import com.badlogic.gdx.Input.Orientation;

/**
 * Über diese Klasse können Platform spezifische Funktionen aufgerufen werden.
 * 
 * @author Philipp
 * @version 1.0
 */
public interface Platform {

	/**
	 * Setzt die Orientation. Portrait oder Landscape. (Vor allem für Android
	 * wichtig)
	 * 
	 * @param orientation
	 *            {@link Orientation}
	 */
	public void setOrientation(Orientation orientation);

	/**
	 * Liefert die {@link Orientation}.
	 * 
	 * @return {@link Orientation}
	 */
	public Orientation getOrientation();

}
