package com.haw.projecthorse.level.game.thimblerig;


import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.lootmanager.Loot;

public class ThimblerigLoot extends Loot{
	
	private Drawable image;
	
	public ThimblerigLoot(String name, String description, String filename){
		super(name, description);
		this.image = new TextureRegionDrawable(AssetManager
				.getTextureRegion("thimblerig", filename));
	}
	
	@Override
	public Drawable getImage() {
		return this.image;
	}

	@Override
	protected int doHashCode() {
		return ((this.image == null) ? 0 : this.image.hashCode());
	}

	@Override
	protected boolean doEquals(Object other) {
		if (this.image == null) {
			if (((ThimblerigLoot)other).image != null)
				return false;
		} else if (!this.image.equals(((ThimblerigLoot)other).image))
			return false;
		return true;
	}
}
