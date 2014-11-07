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
	 * Kameramove wenn Vorwärtssprung (todo)
	 */
	public void jump(){
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
	
	/**
	 * player erzeugen, x,y, breite, höhe setzen,
	 * skalieren
	 */
	public void initialize(){
		this.setScale(1.5F);
		 runUp = this.getWidth() * 1.3f;
		 this.setPosition(playersPointOfView, 1);
		 this.scaleBy(0.2F);
		 
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
