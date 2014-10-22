package com.haw.projecthorse.level.parcours;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.ObjectSet.SetIterator;

public class GameLogic {
	
	private float minDistanceX;
	private float maxDistanceX;
	private float minDistanceY;
	private float maxDistanceY;
	private ArrayList<GameObject> objects;
	
	public GameLogic(){
		objects = new ArrayList<GameObject>();
		minDistanceX = 0;
		maxDistanceX = 0;
		minDistanceY = 0;
		maxDistanceY = 0;
	}
	
	public void initGameObjects(TextureAtlas a, int gameWidth, int gameHeight){
		if(objects.size() > 0){ objects.clear();}
		
		ObjectSet<Texture> t = a.getTextures();
		SetIterator<Texture> iterator = t.iterator();
		
		while(iterator.hasNext){
			objects.add(new GameObject(iterator.next(), gameWidth, gameHeight));
			System.out.println("init");
		}
		
		
	}
	
	public void setMinMaxDistancesX(float minDistance, float maxDistance){
		this.minDistanceX = minDistance;
		this.maxDistanceX = maxDistance;
	}
	
	public void setMinMaxDistancesY(float minDistance, float maxDistance){
		this.minDistanceY = minDistance;
		this.maxDistanceY = maxDistance;
	}
	
	public float getMinDistanceX(){
		return minDistanceX;
	}
	
	public float getMinDistanceY(){
		return minDistanceY;
	}
	
	public float getMaxDistanceX(){
		return maxDistanceX;
	}
	
	public float getMaxDistanceY(){
		return maxDistanceY;
	}
	
	public float getRandomX(GameObject o){
		float rand = (float)Math.floor(Math.random() * (maxDistanceX - minDistanceX) + minDistanceX);
		float x =  o.getGameWidth() + rand ;
		return x;
	}
	
	public float getRandomY(GameObject o){
		float y = o.getGameHeight() + (float)Math.floor(Math.random() * (maxDistanceY - minDistanceY) + minDistanceY); 
		return y;
	}
	
	
	
	public GameObject getRandomObject(){
		return objects.get((int)Math.floor(Math.random() * objects.size()));
	}
	
	public ArrayList<GameObject> getObjects(){
		return objects;
	}
}
