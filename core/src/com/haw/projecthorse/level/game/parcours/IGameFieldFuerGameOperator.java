package com.haw.projecthorse.level.game.parcours;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface IGameFieldFuerGameOperator {
	
	Player getPlayer();
	
	List<Loot> getLoot();
	
	Stage getStage();
	
	int getScore();

}
