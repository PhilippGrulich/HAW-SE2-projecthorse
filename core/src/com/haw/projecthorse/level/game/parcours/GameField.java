package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.player.PlayerImpl;

public class GameField {
	
	private Player player;
	private List<GameObject> gameObjects;
	private List<BackgroundObject> backgroundObjects;
	private Stage stage;
	private int width;
	private int height;
	private float minDistanceX;
	private float maxDistanceX;
	private float minDistanceY;
	private float maxDistanceY;
	private float runUp;
	private float playersPointOfView;
	private float gameSpeed;
	
	public GameField(Stage s, Viewport p, int width, int height){
		gameObjects = new ArrayList<GameObject>();
		backgroundObjects = new ArrayList<BackgroundObject>();
		
		this.width = width;
		this.height = height;
		
		player = new Player();
		
		minDistanceX = 0;
		maxDistanceX = 0;
		minDistanceY = 0;
		maxDistanceY = 0;
		gameSpeed = 3;
		playersPointOfView =  20;
		
		stage = s;
		stage.setViewport(p);
		stage.addCaptureListener(new InputListener(){
			 public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
				 if(!(player.getY() > 0 )){
				 player.jump();
				 }
				 return true;
			 }
		 });
		
		InputManager.addInputProcessor(stage);
	}
	
	public void initializePlayer(){
		 //player.setScale(1.5F);
		 runUp = player.getWidth() * 1.3f;
		 player.setPosition(playersPointOfView, 0);
		 player.scaleBy(0.2F);
		 stage.addActor(player);
	}
	
	public void loadGameObjects(){
		TextureRegion huerde = AssetManager.getTextureRegion("Parcours_Gameobjects", "Level_1/HuerdeClean");
		
		GameObject huerdeObj = new GameObject(huerde);
		huerdeObj.setHeight(huerde.getRegionHeight());
		huerdeObj.setWidth(huerde.getRegionWidth());
		huerdeObj.scaleBy(-0.5f, -0.5f);
		setMinMaxDistancesX(player.getWidth()+(player.getWidth()/2), player.getWidth()+player.getWidth());
		
		gameObjects.add(huerdeObj);
		
		for(int i = 0; i < gameObjects.size(); i++){
			stage.addActor(gameObjects.get(i));
		}
	}
	
	public void loadBackgroundObjects(){
		TextureRegion ground = AssetManager.getTextureRegion("Parcours_BackgroundGraphics", "ground");
		TextureRegion landscape = AssetManager.getTextureRegion("Parcours_BackgroundGraphics", "landscape");
		EndlessBackground groundObj = new EndlessBackground((int)stage.getWidth(), ground, getGameSpeed());

		BackgroundObject landscapeObj = new BackgroundObject(landscape);

		landscapeObj.setWidth(this.width);
		landscapeObj.setHeight(this.height - 105);
		landscapeObj.setPosition(0, 104);
		
		backgroundObjects.add(landscapeObj);

		for(int i = 0; i < backgroundObjects.size(); i++){
			stage.addActor(backgroundObjects.get(i));
		}
		stage.addActor(groundObj);
	}
	
	public void actGameField(float delta){
		stage.act(delta);
	}
	
	public void drawGameField(){
		stage.draw();
	}
	
	/******************************************************************************/
	/************************Methoden fuer Spiellogik******************************/
	/******************************************************************************/
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
		float x =  this.width + rand ;
		return x;
	}
	
	public float getRandomY(GameObject o){
		float y = this.height + (float)Math.floor(Math.random() * (maxDistanceY - minDistanceY) + minDistanceY); 
		return y;
	}
	
	
	
	public GameObject getRandomObject(){
		return gameObjects.get((int)Math.floor(Math.random() * gameObjects.size()));
	}
	
	public List<GameObject> getObjects(){
		return gameObjects;
	}
	
	public float getGameSpeed(){
		return gameSpeed;
	}
	
	public float getPlayersPointOfView(){
		return playersPointOfView;
	}
	
	/******************************************************************************/
	/************************Getter / Setter **************************************/
	/******************************************************************************/

	public Player getPlayer(){
		return player;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public List<BackgroundObject> getBackgroundObjects(){
		return backgroundObjects;
	}
}
