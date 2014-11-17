package com.haw.projecthorse.lootmanager;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

/**
 * Beinhaltet Infos für das Bild eines Loot-Objekts.
 */
public class LootImage {
	private String level, fileName;
	
	/**
	 * Nur fürs Laden per Reflection aus der JSON-Datei.
	 */
	public LootImage() {
		level = "";
		fileName = "";
	}
	
	public LootImage(String levelID, String imageFileName) {
		level = levelID;
		fileName = imageFileName;
	}
	
	protected Drawable getDrawable() {
		return new TextureRegionDrawable(AssetManager.getTextureRegion(level, fileName));
	}
}
