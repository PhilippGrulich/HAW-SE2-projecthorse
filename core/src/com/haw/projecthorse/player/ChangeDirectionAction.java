package com.haw.projecthorse.player;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ChangeDirectionAction extends Action {
	private Direction changeTo;
	
	public ChangeDirectionAction(Direction changeTo) {
		this.changeTo = changeTo;
	}
	
	@Override
	public boolean act(float delta) {
		Actor a = getActor();
		Player p;
		
		if (!(a instanceof Player)) {
			return true;
		}
		p = (Player) a;
		
		p.setAnimation(changeTo, p.getAnimationSpeed());
		
		return true;
	}

}
