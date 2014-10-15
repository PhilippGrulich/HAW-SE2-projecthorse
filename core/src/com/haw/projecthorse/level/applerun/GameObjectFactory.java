package com.haw.projecthorse.level.applerun;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.haw.projecthorse.assetmanager.AssetManager;

public final class GameObjectFactory {

	private final static int PRE_GENERATED_OBJECTS = 7; //Generated objects per class at initialilze 
	private static int rand; //Temp var
	private static TextureAtlas spritemap;
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
		
		for(int i = 0; i<PRE_GENERATED_OBJECTS; i++){
			constructedApples.add(generateApple());
			constructedBranches.add(generateBranch());
		}
		isInitialized = true;
	}

	private GameObjectFactory() {
	}
	
	public static void dispose(){
		isInitialized = false;
		constructedApples = new LinkedList<Apple>();
		constructedBranches = new LinkedList<Branch>();
		spritemap.dispose();
	}
	
	public static void giveBackEntity(Entity entity){
		entity.initializeAsNewEntity(); //Re initialize position etc.
		if(entity instanceof Apple){
			constructedApples.add((Apple)entity);
		}
		else if(entity instanceof Branch){
			constructedBranches.add((Branch)entity);
		}
	}

	private static TextureRegion generateAppleTexture() {
		// TODO load a real apple texture
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
		}
		return tempTextureRegion;

	}

	private static Apple generateApple() {
		return new Apple(generateAppleTexture());
	}
	
	private static Branch generateBranch() {
		return new Branch(generateBranchTexture());
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


	public static Entity getBranch() {
		initialize();
		
		if(constructedBranches.size()>0){
			return constructedBranches.removeFirst();	
		}
		else{
			return generateBranch(); //No preconstructed object left. Generate a new one
		}
	}
	
	public static Entity getApple() {
		initialize();
		
		if(constructedApples.size()>0){
			return constructedApples.removeFirst();	
		}
		else{
			return generateApple(); //No preconstructed object left. Generate a new one
		}
	}
	

//	private static Texture generateDefaultTexture() {
//		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
//		pixel.setColor(Color.RED);
//		pixel.fill();
//		Texture defaultTexture = new Texture(pixel, Format.RGBA8888, true);
//		pixel.dispose(); // No longer needed
//		return defaultTexture;
//
//	}

}
