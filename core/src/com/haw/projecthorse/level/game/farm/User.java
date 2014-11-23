package com.haw.projecthorse.level.game.farm;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.haw.projecthorse.level.game.parcours.Player;
import com.haw.projecthorse.player.PlayerImpl;

public class User extends PlayerImpl {

	UserModel um;

	public User(UserModel u) {
		um = u;
		
	}

	public void update(UserModel u) {
		um = u;
		
		
		
		MoveToAction action = new MoveToAction();
		action.setPosition(Float.parseFloat(um.x), Float.parseFloat(um.y));
		action.setDuration((float) 0.5);
		this.addAction(action);

	}
}
