package com.haw.projecthorse.level.game.farm;

import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener.SwipeEvent;

public class Farm extends Level {
	private PlayGround p;

	public Farm() {
		
		Preferences prefs = Gdx.app.getPreferences("Farm");
		String uuid = prefs.getString("uuid");
		if(uuid==null || uuid.isEmpty()){
			prefs.putString("uuid", UUID.randomUUID().toString());
			prefs.flush();
			uuid = prefs.getString("uuid");
		}
		 p = new PlayGround(getViewport(), getSpriteBatch());
		final FireBaseManager f =	new FireBaseManager(p);
		final UserModel u = new UserModel();
		u.id = uuid;
		u.name = "Philipp";
		
		p.addRootUser(u,f);
		
		
		f.addUser(u);
	}

	@Override
	protected void doRender(float delta) {
		p.act();
		p.draw();

	}

	@Override
	protected void doDispose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doShow() {
	
		

	}

	@Override
	protected void doHide() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doPause() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResume() {
		// TODO Auto-generated method stub

	}

}
