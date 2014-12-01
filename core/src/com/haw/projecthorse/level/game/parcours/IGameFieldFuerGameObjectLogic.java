package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface IGameFieldFuerGameObjectLogic {

	List<GameObject> getGameObjects();

	Player getPlayer();

	void addToScore(int points);

	float getWidth();

	float getTopOfGroundPosition();

	void actGameField(float delta);

	void drawGameField();

	Stage getStage();
	
	List<ParcoursLoot> getLoot();
	
	public void playGallop();
	
	public void pauseGallop();
	
	public void stopGallop();
	
	public void eat();


}
