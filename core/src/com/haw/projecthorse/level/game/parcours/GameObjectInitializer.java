package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObjectInitializer implements IGameObjectInitializerFuerGameObjectLogic{

	@Override
	public float calcRelativeWidth(float regionHeight, float regionWidth,
			float desiredWidth) {
		
		return desiredWidth * (regionWidth / regionHeight);
	}

	@Override
	public float calcRelativeHeight(float regionHeight, float regionWidth,
			float desiredHeight) {
		
		return desiredHeight * (regionHeight / regionWidth);
	}

	@Override
	public GameObject initGameObject(TextureRegion r, String name, int points,
			float height, float width, float duration, float x, float y,
			boolean collidable, boolean isLoot, boolean isMoveable) {
		
		GameObject o;
		if(collidable){
			o = new CollidableGameObject();
			o.setTextureRegion(r);
			o.setName(name);
			o.setPoints(points);
			o.setCollidable(collidable);
			((CollidableGameObject)o).setRectangle(new Rectangle());
			o.setX(x);
			o.setY(y);
			o.setDuration(duration);
			o.setHeight(height);
			o.setWidth(width);
			o.setLoot(isLoot);
			o.setMoveable(isMoveable);
			return o;
		} else {
		    o = new GameObject();
			o.setTextureRegion(r);
			o.setName(name);
			o.setPoints(points);
			o.setCollidable(collidable);
			o.setX(x);
			o.setY(y);
			o.setDuration(duration);
			o.setHeight(height);
			o.setWidth(width);
			o.setLoot(isLoot);
			o.setMoveable(isMoveable);
			return o;
		}
	}

	
	
}
