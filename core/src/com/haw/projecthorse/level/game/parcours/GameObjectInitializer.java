package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObjectInitializer implements IGameObjectInitializerFuerGameObjectLogic{

	@Override
	public float calcRelativeHeight(float regionHeight, float regionWidth,
			float desiredWidth) {
		
		return desiredWidth * (regionHeight / regionWidth);
	}

	@Override
	public float calcRelativeWidth(float regionHeight, float regionWidth,
			float desiredHeight) {
		
		return desiredHeight * (regionWidth / regionHeight);
	}

	@Override
	public GameObject initGameObject(TextureRegion r, String name, int points,
			float height, float width, float duration, float x, float y,
			boolean collidable) {
		
		if(collidable){
			CollidableGameObject o = new CollidableGameObject();
			o.setTextureRegion(r);
			o.setName(name);
			o.setPoints(points);
			o.setCollidable(collidable);
			o.setRectangle(new Rectangle());
			o.setX(x);
			o.setY(y);
			o.setDuration(duration);
			o.setHeight(height);
			o.setWidth(width);
			return o;
		} else {
			GameObject o = new CollidableGameObject();
			o.setTextureRegion(r);
			o.setName(name);
			o.setPoints(points);
			o.setCollidable(collidable);
			o.setX(x);
			o.setY(y);
			o.setDuration(duration);
			o.setHeight(height);
			o.setWidth(width);
			return o;
		}
	}

	
	
}
