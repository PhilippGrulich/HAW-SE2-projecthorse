package com.haw.projecthorse.level.game.memoryspiel;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.game.memoryspiel.Karte.State;

public class MemorySpiel extends Level {

	private SpriteBatch batcher;
	private KartenManager manager;
	private Stage stage;
	private int background;
	private TextureRegion region;
	private Drawable karte;
	private Image backgroundImage;

	public MemorySpiel() {
		manager = new KartenManager();
		manager.setUpKarten();
		batcher = this.getSpriteBatch();
		background = getBackground();
		stage = new Stage(this.getViewport(), batcher);
		region = AssetManager.getTextureRegion("memorySpiel", "Background"
				+ background);
		backgroundImage = new Image(region);
		backgroundImage.toBack();
		stage.addActor(backgroundImage);
		initKarten();
	}

	protected void initKarten() {
		List<Karte> karten = manager.getKarten();
		for (Karte k : karten) {
			k.toFront();
			stage.addActor(k);
			
		}
	}


	protected int getBackground() {
		Random rand = new Random();
		int i = rand.nextInt(5);
		return i;
	}
	
	protected void updateKarten(){
		List<Karte> karten = manager.getKarten();
		for(Karte k : karten){
			if(k.getState() == State.TEMPORARILY_OPENED){
				k.setDrawable(k.getPicture());
			}
		}
		manager.checkChanged();
	}

	@Override
	protected void doRender(float delta) {	
		updateKarten();
		stage.draw();

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
