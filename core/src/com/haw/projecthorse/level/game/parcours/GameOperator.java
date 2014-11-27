package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.lootmanager.Chest;

public class GameOperator implements IGameOperator, IGameOperatorFuerParcours {

	private IGameObjectLogicFuerGameOperator logic;
	private IGameFieldFuerGameOperator gameField;
	private Chest chest;
	private boolean saved = false;

	public GameOperator(Stage stage, Viewport viewport, int width, int height,
			Chest chest) {
		gameField = new GameField(stage, viewport, width, height);
		logic = new GameObjectLogic(width,
				(IGameFieldFuerGameObjectLogic) gameField);
		setInputProcessor();
		//getEarnedLoot();
		this.chest = chest;
	}

	// Bereits gewonnene Loot aus Chest holen und in Spieler packen.
	private void getEarnedLoot() {

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
		logic.update(delta);
		 verifyGameState();
		// TODO logic.success -> popup, stats, loot, restart?
	}

	// Score von GameField erfragen u. bei Niederlage Spiel beenden.
	// Dann prüfen ob Loot gewonnen -> wenn ja, Erfolgsmeldung u. in Chest
	// legen.
	private void verifyGameState() {
		if (!saved) {
			if (gameField.getScore() >= 1) {
				for (Loot l : gameField.getLoot()) {
					if (l.getAvailableAtScore() <= 10) {
						chest.addLoot(l.getLootInChest());
						chest.saveAllLoot();
						//chest.showAllLoot();
						saved = true;
					}
				}
			}
		}
	}

}
