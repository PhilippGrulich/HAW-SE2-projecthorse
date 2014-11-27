package com.haw.projecthorse.level.game.parcours;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.Level;

/**
 * 
 * @author Francis
 *
 */
public class Parcours extends Level{
	
	private IGameOperatorFuerParcours gameOperator;
	//private static GameOperator gameOperator;
	
	public Parcours(){
		gameOperator = new GameOperator(new Stage(), this.getViewport(), this.width, this.height, this.chest);
		
	}

	@Override
	protected void doDispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doHide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doRender(float delta) {
		gameOperator.update(delta);
		
	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doResume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void doShow() {
		// TODO Auto-generated method stub
		
	}
	
	/*public static GameOperator getGameOperator(){
		return gameOperator;
	}*/

}
