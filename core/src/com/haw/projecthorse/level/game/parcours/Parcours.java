package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.game.Game;

/**
 * 
 * @author Francis
 *
 */
public class Parcours extends Game{
	
	private IGameOperatorFuerParcours gameOperator;
	
	public Parcours(){
		super(Orientation.Landscape);
		gameOperator = new GameOperator(new Stage(this.getViewport(),this.getSpriteBatch()), this.getViewport(), this.width, this.height, chest);

		
	}

	@Override
	protected void doDispose() {
		//gameOperator = null;
		
	}

	@Override
	protected void doHide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doPause() {
		gameOperator.pause();
		
	}

	@Override
	protected void doRender(float delta) {
		if(gameOperator != null)
			gameOperator.update(delta);
		
	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doResume() {
		gameOperator.setPause(false);
		
	}
	
	@Override
	protected void doShow() {
		// TODO Auto-generated method stub
		
	}

}
