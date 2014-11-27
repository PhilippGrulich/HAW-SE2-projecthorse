package com.haw.projecthorse.platform;

import com.badlogic.gdx.Input.Orientation;

public interface Platform {
	
	/**
	 * Setzt die Orientation. Portrai oder Landscape. (Vorallem für Android wichtig)
	 * @param orientation
	 */
	 public void SetOrientation(Orientation orientation);
	 
	 public Orientation getOrientation();
	
}
