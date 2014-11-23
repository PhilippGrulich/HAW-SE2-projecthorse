package com.haw.projecthorse.level.game.farm;

import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.haw.projecthorse.level.Level;

public class Farm extends Level {
	private PlayGround p;
	private UserModel rootUser;
	private FireBaseManager f;
	
	
	public Farm() {
		
		p = new PlayGround(getViewport(), getSpriteBatch());
		f = new FireBaseManager(p);
		rootUser = new UserModel();
		rootUser.id = getPlayerUUID();
		rootUser.name = "Philipp";		
		p.addRootUser(rootUser,f);	
		f.addUser(rootUser);
	}
	
	private String getPlayerUUID(){
		Preferences prefs = Gdx.app.getPreferences("Farm");
		String uuid = prefs.getString("uuid");
		if(uuid==null || uuid.isEmpty()){
			prefs.putString("uuid", UUID.randomUUID().toString());
			prefs.flush();
			uuid = prefs.getString("uuid");
		}
		return uuid;
	}

	@Override
	protected void doRender(float delta) {
		p.act();
		p.draw();

	}

	@Override
	protected void doDispose() {
		f.removeUser(rootUser);

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
