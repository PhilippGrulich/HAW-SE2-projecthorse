package com.haw.projecthorse.level.game.applerun;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.haw.projecthorse.assetmanager.AssetManager;

/**
 * Factory für GameObjects. Apple/Branch
 * 
 * @author Lars
 * @version 1.0
 */
public final class GameObjectFactory {

	private final static int PRE_GENERATED_OBJECTS = 7; // Generated objects per class at initialilze
	private static int rand; // Temp var
	// private static TextureAtlas spritemap;
	private static TextureRegion apple1;
	private static TextureRegion apple2;
	private static TextureRegion apple3;
	private static TextureRegion apple4;
	private static TextureRegion branch1;
	private static TextureRegion branch2;

	private static TextureRegion tempTextureRegion; // temp Region, used in switch case

	private static LinkedList<Apple> constructedApples = new LinkedList<Apple>();
	private static LinkedList<Branch> constructedBranches = new LinkedList<Branch>();
	private static boolean isInitialized = false;

	/**
	 * init.
	 */
	private static void initialize() {
		if (isInitialized) {
			return;
		}
		// spritemap = AssetManager.load("appleRun", false, false, true);
		apple1 = AssetManager.getTextureRegion("appleRun", "apple1");
		apple2 = AssetManager.getTextureRegion("appleRun", "apple2");
		apple3 = AssetManager.getTextureRegion("appleRun", "apple3");
		apple4 = AssetManager.getTextureRegion("appleRun", "apple4");
		branch1 = AssetManager.getTextureRegion("appleRun", "branch1");
		branch2 = AssetManager.getTextureRegion("appleRun", "branch2");

		for (int i = 0; i < PRE_GENERATED_OBJECTS; i++) {
			constructedApples.add(generateApple());
			constructedBranches.add(generateBranch());
		}
		isInitialized = true;
	}

	/**
	 * empty constructor.
	 */
	private GameObjectFactory() {
	}

	/**
	 * Destructor.
	 */
	public static void dispose() {
		isInitialized = false;
		constructedApples = new LinkedList<Apple>();
		constructedBranches = new LinkedList<Branch>();
		// spritemap.dispose(); //Removed due to AssetManager-management
	}

	/**
	 * Ein Object zurueck geben damit es wieder verwendet werden kann.
	 * 
	 * @param entity
	 *            Das Object das reinitialisiert werden kann.
	 */
	public static void giveBackEntity(final Entity entity) {
		entity.initializeAsNewEntity(); // Re initialize position etc.
		if (entity instanceof Apple) {
			constructedApples.add((Apple) entity);
		} else if (entity instanceof Branch) {
			constructedBranches.add((Branch) entity);
		}
	}

	/**
	 * generateAppleTexture.
	 * @return TextureRegion
	 */
	private static TextureRegion generateAppleTexture() {
		rand = MathUtils.random(1, 4);

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
		default:
			tempTextureRegion = apple4;
			break;

		}
		return tempTextureRegion;

	}

	/**
	 * generateApple.
	 * @return Apple
	 */
	private static Apple generateApple() {
		return new Apple(generateAppleTexture());
	}

	/**
	 * generateBranch.
	 * @return Branch
	 */
	private static Branch generateBranch() {
		return new Branch(generateBranchTexture());
	}

	/**
	 * generateBranchTexture.
	 * @return TextureRegion
	 */
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
		default:
			tempTextureRegion = branch1;
			break;
		}

		return tempTextureRegion;

	}

	/**
	 * getBranch.
	 * @return Entity
	 */
	public static Entity getBranch() {
		initialize();

		if (constructedBranches.size() > 0) {
			return constructedBranches.removeFirst();
		} else {
			return generateBranch(); // No preconstructed object left. Generate a new one
		}
	}

	/**
	 * getApple.
	 * @return Entity
	 */
	public static Entity getApple() {
		initialize();

		if (constructedApples.size() > 0) {
			return constructedApples.removeFirst();
		} else {
			return generateApple(); // No preconstructed object left. Generate a new one
		}
	}

	// private static Texture generateDefaultTexture() {
	// Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
	// pixel.setColor(Color.RED);
	// pixel.fill();
	// Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
	// pixel.dispose(); // No longer needed
	// return defaultTexture;
	//
	// }

}
