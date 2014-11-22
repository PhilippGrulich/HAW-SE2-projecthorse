package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;

public class GameOperator implements IGameOperator, IGameOperatorFuerParcours{

	IGameObjectLogicFuerGameOperator logic;
	IGameFieldFuerGameObjectLogic gameField;
	
	public GameOperator(Stage stage, Viewport viewport, int width, int height){
		gameField = new GameField(stage, viewport, width, height);
		logic = new GameObjectLogic(width, gameField);
		setInputProcessor();
	}
	
	@Override
	public void restart() {
		//TODO Alles auf 0 setzen
		
	}

	public void setInputProcessor(){
		GestureDetector listener = new GestureDetector(new GameInputListener((IGameObjectLogicFuerGameInputListener)logic, (IGameFieldFuerGameInputListener)gameField));
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(Gdx.input.getInputProcessor());
		inputMultiplexer.addProcessor(new StageGestureDetector(gameField.getStage(), true,
				ControlMode.HORIZONTAL));
		inputMultiplexer.addProcessor(listener);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void update(float delta) {
		logic.update(delta);
		//TODO logic.success -> popup, stats, loot, restart?
	}


}
