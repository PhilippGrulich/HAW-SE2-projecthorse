package com.haw.projecthorse.lootmanager;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;

/**
 * Beinhaltet Infos für das Bild eines Loot-Objekts.
 * 
 * @author Oliver
 * @version 1.0
 */
public class LootImage {
	private String level, fileName;

	/**
	 * Konstruktor NUR fürs Laden per Reflection aus der JSON-Datei.
	 */
	public LootImage() {
		level = "";
		fileName = "";
	}

	/**
	 * Erstellt eine LootImage und holt das dazugehörige Bild aus dem
	 * AssetManager mit den angegeben Daten.
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param imageFileName
	 *            Dateiname
	 */
	public LootImage(final String levelID, final String imageFileName) {
		level = levelID;
		fileName = imageFileName;
	}

	protected Drawable getDrawable() {
		return new TextureRegionDrawable(AssetManager.getTextureRegion(level,
				fileName));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LootImage other = (LootImage) obj;
		if (fileName == null) {
			if (other.fileName != null) {
				return false;
			}
		} else if (!fileName.equals(other.fileName)) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
			return false;
		}
		return true;
	}

}
