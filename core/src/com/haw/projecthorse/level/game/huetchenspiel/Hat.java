package com.haw.projecthorse.level.game.huetchenspiel;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Definition eines Hutes, der "angewischt" und vom Zufallsgenerator
 * als Hut gewählt worden sein kann, unter der sich das Pferd befindet
 * @author Fabian Reiber
 *
 */
public class Hat extends Image{
	
	/**
	 * Merkmale eines Hutes
	 */
	private boolean flinged;
	private boolean choosed;

	/**
	 * Konstruktor
	 * @param tex jeweilige Texture fuer den Hut
	 */
	public Hat(TextureRegion tex){
		super(tex);
		this.flinged = false;
		this.choosed = false;
	}
	
	/**
	 * 
	 * @return wurde Hut "angewischt" true, sonst false
	 */
	public boolean isFlinged() {
		return flinged;
	}

	/**
	 * 
	 * @param flinged setzen, wenn Hut "angewischt" wurde
	 */
	public void setFlinged(boolean flinged) {
		this.flinged = flinged;
	}

	/**
	 * 
	 * @return wurde Pferd unter Hut versteckt true, sonst false
	 */
	public boolean isChoosed() {
		return choosed;
	}

	/**
	 * 
	 * @param choosed setzen, wenn Pferd unter Hut gesteckt wurde
	 */
	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}	
}
