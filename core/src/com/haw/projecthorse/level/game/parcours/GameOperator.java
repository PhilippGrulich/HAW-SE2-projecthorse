package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.player.Direction;

public class GameOperator {
	
	private GameField gameField;
	private Sanction sanction;
	
	public GameOperator(Stage stage, Viewport viewport, int width, int height){
		gameField = new GameField(stage, viewport, width, height);
		//Reihenfolge der Methodenaufrufe bestimmt die z-Order in der Stage
		gameField.loadBackgroundObjects();
		gameField.loadGameObjects();
		gameField.initializePlayer();
		
	}
	
	public void update(float delta){
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameField.getPlayer().setAnimation(Direction.RIGHT, 0.2f);
		
		//Bewege Gameobjects
		for(GameObject o : gameField.getObjects()){
			o.moveBy(0-gameField.getGameSpeed(), 0);	
			
			//Gameobject außerhalb rand? -> Neu setzen
			if(o.getX() + o.getWidth() < 0){
				float rand = gameField.getRandomX(o);
				o.setPosition(rand, 0);
				//textLabel.setText("" + ++challengedObstacles);
			}
			
		}
		
		if(gameField.getPlayer().getX() + gameField.getPlayer().getWidth() >= gameField.getWidth()){
			//System.out.println("Player out of screen view -- implement");
		}
		
	
		if(gameField.getPlayer().getX() > gameField.getPlayersPointOfView()){
			gameField.getPlayer().moveBy(0 - gameField.getGameSpeed(), 0);
		}
		
		gameField.actGameField(delta);
		gameField.drawGameField();

	}

}
