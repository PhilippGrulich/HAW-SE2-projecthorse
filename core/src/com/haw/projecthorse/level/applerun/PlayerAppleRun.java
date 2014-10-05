package com.haw.projecthorse.level.applerun;

import com.haw.projecthorse.player.PlayerColor;
import com.haw.projecthorse.player.PlayerImpl;

public class PlayerAppleRun extends PlayerImpl {

	private final Gamestate gamestate; 
//	public PlayerAppleRun() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public PlayerAppleRun(PlayerColor color) {
//		super(color);
//		// TODO Auto-generated constructor stub
//	}
//	
	public PlayerAppleRun(Gamestate gamestate){
		this.gamestate = gamestate;
	}
	
	public void fireHitByEntity(Entity entity){
		System.out.println("HIT");
		//If hit by apple
		//Add score
		
		//If hit by branch - -score
		
//???   //gamestate.addScore();
		
		
		gamestate.removeFallingEntity(entity);
		
	}

}
