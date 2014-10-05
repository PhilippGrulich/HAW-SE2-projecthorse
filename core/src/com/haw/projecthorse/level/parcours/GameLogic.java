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
	private float predecessorWidth;
	private float predecessorHeight;
	private float predecessorX;
	private float predecessorY;
	private ArrayList<GameObject> objects;
	
	public GameLogic(){
		objects = new ArrayList<GameObject>();
		minDistanceX = 0;
		maxDistanceX = 0;
		minDistanceY = 0;
		maxDistanceY = 0;
		predecessorHeight = 0;
		predecessorWidth = 0;
		predecessorX = 0;
		predecessorY = 0;
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
		setPredecessorsX(x);
		setPredecessorVars(o.getHeight(), o.getWidth());
		return x;
	}
	
	public float getRandomY(GameObject o){
		float y = o.getGameHeight() + (float)Math.floor(Math.random() * (maxDistanceY - minDistanceY) + minDistanceY); 
		setPredecessorsY(y);
		setPredecessorVars(o.getHeight(), o.getWidth());
		return y;
	}
	/*
	public void setPredecessorsWidth(float w){
		predecessorWidth = w;
	}
	*/
	public void setPredecessorVars(float height, float width){
		predecessorHeight = height;
		predecessorWidth = width;
	}
	
	/*
	public void setPredecessorHeight(float h){
		predecessorHeight = h;
	}
	*/
	public void setPredecessorsX(float x){
		predecessorX = x;
	}
	
	public void setPredecessorsY(float y){
		predecessorY = y;
	}
	
	public GameObject getRandomObject(){
		return objects.get((int)Math.floor(Math.random() * objects.size()));
	}
	
	public ArrayList<GameObject> getObjects(){
		return objects;
	}
}
