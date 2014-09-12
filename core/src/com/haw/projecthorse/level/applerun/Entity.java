package com.haw.projecthorse.level.applerun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * @author Lars 
 * Entity: A base game object that does something. Like a falling apple etc. 
 * (Image is an Actor)
 */

public abstract class Entity extends Image {

	protected Texture thisTexture = null;

	public Entity() {
		thisTexture = loadTexture(); //Load a texture, set by extending class 
		if(thisTexture == null){ //No Texture supplied
			loadDefaultTexture();	
		}
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(thisTexture))); //Convert the texture to use as a drawable Image
		
	}

	private void loadDefaultTexture() {
		Pixmap pixel = new Pixmap(64, 64, Format.RGBA8888);
		pixel.setColor(Color.PINK);
		pixel.fill();
		thisTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed
		
	}
	
	//Bounding box for collision detection
	private void updateBounds(){
		this.setBounds(getImageX(), getImageY(), getImageWidth(), getImageHeight());
		
	}

	//This method should return a Texture and will be set as the image.
	protected abstract Texture loadTexture();
	
	@Override
	public void act(float delta){
		
		super.act(delta);
		updateBounds(); //Update bounding box for collision detection
	
	}

	//protected abstract void handleCollision(Actor actor); //Handle if you have hit something
	
}
