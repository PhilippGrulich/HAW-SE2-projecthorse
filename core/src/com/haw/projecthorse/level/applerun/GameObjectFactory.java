package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.haw.projecthorse.assetmanager.AssetManager;

public final class GameObjectFactory {

	private static TextureAtlas spritemap;
	private static TextureRegion apple1;
	private static TextureRegion apple2;
	private static TextureRegion apple3;
	private static TextureRegion apple4;
	private static TextureRegion branch1;
	private static TextureRegion branch2;

	private static TextureRegion tempTextureRegion; // temp Region, used in switch case

	private static boolean isInitialized = false;

	private static void initialize() {
		if (isInitialized) {
			return;
		}

		spritemap = AssetManager.load("appleRun", false, false, true);
		apple1 = spritemap.findRegion("apple1");
		apple2 = spritemap.findRegion("apple2");
		apple3 = spritemap.findRegion("apple3");
		apple4 = spritemap.findRegion("apple4");
		branch1 = spritemap.findRegion("branch1");
		branch2 = spritemap.findRegion("branch2");
		isInitialized = true;
	}

	private GameObjectFactory() {
	}

	private static TextureRegion generateAppleTexture() {
		// TODO load a real apple texture
		int rand = MathUtils.random(1, 4);

		switch (rand) {
		case 1:
			tempTextureRegion = apple1;
			break;
		case 2:
			tempTextureRegion = apple2;
			break;
		case 3:
			tempTextureRegion = apple3;
			break;
		case 4:
			tempTextureRegion = apple4;
			break;
		}

		return tempTextureRegion;

	}

	public static Apple getApple() {
		initialize();
		// return generateDefaultTexture();
		return new Apple(generateAppleTexture());

	}

	private static TextureRegion generateBranchTexture() {
		// TODO load a real apple texture
		int rand = MathUtils.random(1, 2);

		switch (rand) {
		case 1:
			tempTextureRegion = branch1;
			break;
		case 2:
			tempTextureRegion = branch2;
			break;
		}

		return tempTextureRegion;

	}
	
	public static Branch getBranch() {
		initialize();
		return new Branch(generateBranchTexture());
	}

	private static Texture generateDefaultTexture() {
		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
		pixel.setColor(Color.RED);
		pixel.fill();
		Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		return defaultTexture;

	}

	private static Texture generateBrownTexture() {
		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
		pixel.setColor(Color.rgba8888(205f / 255f, 130f / 255f, 63f / 255f, 1f));
		pixel.fill();
		Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		return defaultTexture;

	}

}
