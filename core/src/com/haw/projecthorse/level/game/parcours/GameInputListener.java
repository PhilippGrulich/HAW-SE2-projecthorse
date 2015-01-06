package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

/**
 * Klasse zur Reaktion von "Tip"-Berührungen auf dem Bildschirm um
 * die Sprungfunktion des Pferdes zu initialisieren.
 * @author Francis
 * @version 1.0
 *
 */
public class GameInputListener implements GestureListener {

	IGameObjectLogicFuerGameInputListener logic;
	IGameFieldFuerGameInputListener gameField;
	
	/**
	 * Konstruktor.
	 * @param logic Die GameLogic von Parcours.
	 * @param gameField Eine Instanz vom GameField.
	 */
	public GameInputListener(final IGameObjectLogicFuerGameInputListener logic,
			final IGameFieldFuerGameInputListener gameField) {
		this.logic = logic;
		this.gameField = gameField;
	}

	@Override
	public boolean fling(final float velocityX, final float velocityY, final int button) {
		return false;
	}

	@Override
	public boolean longPress(final float x, final float y) {
		return false;
	}

	@Override
	public boolean pan(final float x, final float y, final float deltaX, final float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(final float x, final float y, final int pointer, final int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(final Vector2 initialPointer1, final Vector2 initialPointer2,
			final Vector2 pointer1, final Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(final float x, final float y, final int count, final int button) {
		// TODO Auto-generated method stub
		if (!logic.isPlayerJumping()) {
			logic.setPlayerJump(true);
			// Ausgehend von aktueller x-Position die Parabel berechnen.
			gameField.getPlayer().setupJumpFunction();
		}
		return true;
	}

	@Override
	public boolean touchDown(final float x, final float y, final int pointer, final int button) {
		return false;
	}

	@Override
	public boolean zoom(final float initialDistance, final float distance) {
		// TODO Auto-generated method stub
		return false;
	}

}
