package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.game.Game;

/**
 * Die Klasse Parcours enthält die Methoden, die das Framework auf der von Screen
 * ableitenden Klasse aufruft. Level leitet die Aufrufe in unserem Fall an die Klassen weiter,
 * die von Game ableiten.
 * @author Francis
 * @version 1.0
 */
public class Parcours extends Game {

	private IGameOperatorFuerParcours gameOperator;

	/**
	 * Konstruktor.
	 */
	public Parcours() {
		super(Orientation.Landscape);
		gameOperator = new GameOperator(new Stage(this.getViewport(),
				 this.getSpriteBatch()), this.getViewport(), this.width,
				this.height, chest, this.audioManager, Parcours.overlay);
	}

	@Override
	protected void doDispose() {
		gameOperator.dispose();

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
	protected void doRender(final float delta) {
		if (gameOperator != null){
			gameOperator.update(delta);
		}

	}

	@Override
	protected void doResize(final int width, final int height) {
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
