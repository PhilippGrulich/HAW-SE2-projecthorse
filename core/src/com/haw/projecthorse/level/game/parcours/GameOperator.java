package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.lootmanager.Chest;
import com.haw.projecthorse.level.game.parcours.ParcoursLoot;
import com.haw.projecthorse.savegame.SaveGameManager;

public class GameOperator implements IGameOperator, IGameOperatorFuerParcours {

	private IGameObjectLogicFuerGameOperator logic;
	private IGameFieldFuerGameOperator gameField;
	private Chest chest;
	private boolean saved = false;
	private boolean paused = false;
	private boolean gameEndReached;
	private GameState gameStatus = GameState.START;

	public GameOperator(Stage stage, Viewport viewport, int width, int height,
			Chest chest) {
		gameField = (IGameFieldFuerGameOperator) new GameField(stage, viewport,
				width, height);
		logic = new GameObjectLogic(width,
				(IGameFieldFuerGameObjectLogic) gameField);
		setInputProcessor();
		// getEarnedLoot();
		this.chest = chest;
		showEarnedLoot();
	}

	
	private void showEarnedLoot() {
		List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
		if(allreadyWon.size() > 0){
			chest.showAllLoot();
		}
	}

	@Override
	public void restart() {
		// TODO Alles auf 0 setzen

	}

	public void setInputProcessor() {
		GestureDetector listener = new GestureDetector(new GameInputListener(
				(IGameObjectLogicFuerGameInputListener) logic,
				(IGameFieldFuerGameInputListener) gameField));
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(Gdx.input.getInputProcessor());
		inputMultiplexer.addProcessor(new StageGestureDetector(gameField
				.getStage(), true, ControlMode.HORIZONTAL));
		inputMultiplexer.addProcessor(listener);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void update(float delta) {
		if (delta != 0 && !paused &&  gameStatus != GameState.END) {
			logic.update(delta);
			verifyGameState(delta);
		} else if (gameEndReached  &&  gameStatus != GameState.END) {
			if (gameField.isButtonYesPressed(gameStatus)) {
				gameEndReached = false;
				gameField.restart();
				gameField.removePopup();
				this.gameStatus = GameState.START;
				this.setPause(false);
			} else if (gameField.isButtonNoPressed(gameStatus)) {
				gameEndReached = false;
				this.setPause(false);
				gameField.clear();
				this.gameStatus = GameState.END;
				GameManagerFactory.getInstance().navigateBack();
				
			}
			gameField.drawGameField();
		} else if(gameStatus != GameState.END) {
			
		}

		// TODO logic.success -> popup, stats, loot, restart?
	}

	// Score von GameField erfragen u. bei Niederlage Spiel beenden.
	// Dann prüfen ob Loot gewonnen -> wenn ja, Erfolgsmeldung u. in Chest
	// legen.
	private void verifyGameState(float delta) {
		if (gameField.getScore() >= 10) {
			
			gameField.showPopup(GameState.WON);
			gameEndReached = true;
			gameStatus = GameState.WON;
			List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
			for(ParcoursLoot l : gameField.getLoot()){
				System.out.println("gewonnen: " + allreadyWon.size() + " " + allreadyWon.contains(l));
				
				if(l.getAvailableAtScore() <= gameField.getScore() && !allreadyWon.contains(l)){
					chest.addLoot(l);
					chest.saveAllLoot();
				}
			}
			
			this.pause();
		} else if (gameField.getScore() < 0) {
			gameField.showPopup(GameState.LOST);
			gameStatus = GameState.LOST;
			gameEndReached = true;
			this.pause();
		}
	}

	public void setPause(boolean p) {
		paused = p;
	}

	public void pause() {
		paused = true;
	}
	
	/**
	 * ermittelt alle bereits gewonnen Loots aus diesem Spiel die unter dem verwendeten
	 * Spielstand gesichert sind
	 * @return Liste aller Thimblerig-Loots
	 */
	private List<? extends ParcoursLoot> getLoots(){
		return SaveGameManager.getLoadedGame().getSpecifiedLoot(com.haw.projecthorse.level.game.parcours.ParcoursLoot.class);
	}

}
