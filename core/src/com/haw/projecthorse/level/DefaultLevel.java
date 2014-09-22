package com.haw.projecthorse.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

public class DefaultLevel extends Level {
	
	private Stage stage;

	@Override
	public void doRender(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AssetManager.showTexture("pictures/cc-0/worldmap.png", 0, 0);
		AssetManager.showTexture("pictures/selfmade/logo.png", 0, 0);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void doResize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doShow() {
		// TODO Auto-generated method stub
		AssetManager.load();
		AssetManager.playSound("sounds/flap.wav");
		AssetManager.playMusic("music/life.mp3");
		
		stage = new Stage(getViewport());
		Player player = new PlayerImpl();
		player.setPosition(100, 100);
		player.setAnimation(Direction.UPRIGHT, 0.3f);
		ScaleToAction scale = new ScaleToAction();
		scale.setScale(5f);
		scale.setDuration(20f);
		player.addAction(scale);
		stage.addActor(player);

	}

	@Override
	public void doHide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDispose() {
		// TODO Auto-generated method stub

	}

}
