package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;

public interface IGameFieldFuerGameOperator {
	
	Player getPlayer();
	
	List<ParcoursLoot> getLoot();
	
	Stage getStage();
	
	int getScore();
	
	void showPopup(GameState g);

	void drawGameField();

	boolean isButtonYesPressed(GameState g);

	boolean isButtonNoPressed(GameState g);

	void restart();
	
	void clear();

	void removePopup();
}
