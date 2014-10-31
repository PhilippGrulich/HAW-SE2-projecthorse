package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.Level;

/**
 * 
 * @author Francis
 *
 */
public class Parcours extends Level{

	private GameOperator gameOperator;
	
	public Parcours(){
		gameOperator = new GameOperator(new Stage(), this.getViewport(), this.width, this.height);
		
	}
	
	@Override
	protected void doRender(float delta) {
		gameOperator.update(delta);
		
	}

	@Override
	protected void doDispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doShow() {
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
	protected void doResume() {
		// TODO Auto-generated method stub
		
	}

}
