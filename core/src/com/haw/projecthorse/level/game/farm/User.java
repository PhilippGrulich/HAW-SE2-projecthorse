package com.haw.projecthorse.level.game.farm;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.haw.projecthorse.level.game.parcours.Player;
import com.haw.projecthorse.player.PlayerImpl;

public class User extends PlayerImpl {

	UserModel um;

	public User(UserModel u) {
		um = u;
		
	}

	public void update(UserModel u) {
		um = u;
		
		
		this.setPosition(Float.parseFloat(um.x), Float.parseFloat(um.y));

	}
}
