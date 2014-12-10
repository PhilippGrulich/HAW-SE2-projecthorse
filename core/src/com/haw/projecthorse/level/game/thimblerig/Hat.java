package com.haw.projecthorse.level.game.thimblerig;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Definition eines Hutes, der "angewischt" und vom Zufallsgenerator
 * als Hut gewählt worden sein kann, unter der sich das Pferd befindet.
 * @author Fabian Reiber
 * @version 1.0
 *
 */
public class Hat extends Image{
	
	/**
	 * Merkmale eines Hutes.
	 */
	private boolean flinged;
	private boolean choosed;

	/**
	 * Konstruktor.
	 * @param tex jeweilige Texture fuer den Hut
	 */
	public Hat(final TextureRegion tex){
		super(tex);
		this.flinged = false;
		this.choosed = false;
	}
	
	public boolean isFlinged() {
		return flinged;
	}

	public void setFlinged(final boolean flinged) {
		this.flinged = flinged;
	}

	public boolean isChoosed() {
		return choosed;
	}

	public void setChoosed(final boolean choosed) {
		this.choosed = choosed;
	}	
}
