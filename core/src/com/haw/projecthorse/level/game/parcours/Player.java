package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.haw.projecthorse.player.PlayerImpl;

public class Player extends PlayerImpl{
	
	
	private Action vorwaerts;
	private float vorwaertsPos;
	private Action springen;
	private Action fallen;
	private float runUp;
	private float playersPointOfView;
	private float fallenPos;
	private float jumpHeight;
	private float jumpUpTime;
	private float jumpDownTime;

	public Player(){
		super();
		initialize();
		toFront();
		jumpHeight = 50;
		jumpUpTime = 0.4f;
		jumpDownTime = 0.4f;
	}
	

	/**
	 * Sprung von player,
	 * smoothing (todo),
	 * nicht aus Spielfeld raus (todo),
	 */
	public void jump(){
		
		
		if(this.getX() + this.getWidth() + 2*vorwaertsPos + fallenPos  > GameField.width){
			cutPlayerActions();
			//Vorwaerts bewegen
			this.addAction(vorwaerts);
			this.setX(this.getX());
			
			//Springen
			this.addAction(springen);
			setX(getX());
			
			//Fallen
			this.addAction(fallen);
			setX(getX());
		}else{
			initPlayerActions();
			//Vorwaerts bewegen
			this.addAction(vorwaerts);
			this.setX(vorwaertsPos);
			
			//Springen
			this.addAction(springen);
			System.out.println("player jump");
			
			//Fallen
			this.addAction(fallen);
		}
		
		
				
		
		
	}
	
	private void cutPlayerActions() {
		  //vorwaertsPos = GameField.width - this.getWidth()*2;
	     //fallenPos = GameField.width - this.getWidth()*2;
		vorwaertsPos = GameField.width;
		fallenPos = GameField.width;
		
	    //Vorwaerts Action
	 	vorwaerts = Actions.moveTo(GameField.width - (getWidth()), 0,0.5f, new Interpolation() {
			
			@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
			}
		});
	 	
	 	//Springen Action
	 	springen = Actions.moveTo(GameField.width - (getWidth()), this.getY() + this.getHeight() * jumpHeight, 
				jumpUpTime, new Interpolation() {
			
			@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
			}
		});
	 	
	 	//Fallen Action
	 	//Fallen
	 	fallen = Actions.moveTo(GameField.width - ( getWidth()), 0, jumpDownTime, new Interpolation() {
	 				
	 				@Override
	 				public float apply(float a) {
	 					// TODO Auto-generated method stub
	 					return a;
	 				}
	 			});
		
	}


	/**
	 * player erzeugen, x,y, breite, höhe setzen,
	 * skalieren
	 */
	public void initialize(){
		//this.setScale(1.5F);
		 runUp = this.getWidth() * 1.3f;
		 this.setPosition(playersPointOfView, 1);
		 
		initPlayerActions();
	}
	
	private void initPlayerActions(){
		 vorwaertsPos = this.getX() + runUp;
	     fallenPos = vorwaertsPos + runUp / 1.5f;
	     
	    //Vorwaerts Action
	 	vorwaerts = Actions.moveBy(vorwaertsPos, 0, 0.5f, new Interpolation() {
	 		
	 		@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
				}
			});
	 	
	 	//Springen Action
	 	springen = Actions.moveTo(vorwaertsPos, this.getY() + this.getHeight() * jumpHeight, 
				jumpUpTime, new Interpolation() {
			
			@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
			}
		});
	 	
	 	//Fallen Action
	 	//Fallen
	 	fallen = Actions.moveTo(fallenPos, 0, jumpDownTime, new Interpolation() {
	 				
	 				@Override
	 				public float apply(float a) {
	 					// TODO Auto-generated method stub
	 					return a;
	 				}
	 			});
	}

}
