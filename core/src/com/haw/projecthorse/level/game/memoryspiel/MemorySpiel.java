package com.haw.projecthorse.level.game.memoryspiel;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.game.memoryspiel.Karte.CardState;


public class MemorySpiel extends Level {

	public enum GameState {
		READY, RESTART, END;
	}
	
	private SpriteBatch batcher;
	private KartenManager manager;
	private Stage stage;
	private int background;
	private TextureRegion region;
	private Image backgroundImage;
	private GameState state;
	private Label replay;
	
	public MemorySpiel() {
		manager = new KartenManager();
		batcher = this.getSpriteBatch();
		state = GameState.READY;
		stage = new Stage(this.getViewport(), batcher);
		InputManager.addInputProcessor(stage);
		replay = createReplayLabel();
		replay.setPosition(this.width / 3.7f, this.height * 0.85f);
		backgroundImage = getBackground();
		backgroundImage.toFront();
		enableReplayButton(false);
		stage.addActor(backgroundImage);
		stage.addActor(replay);
		initKarten();
	}

	protected void initKarten() {
		List<Karte> karten = manager.getKarten();
		for (Karte k : karten) {
			k.toFront();
			stage.addActor(k);
		}
	}
	
	protected Image getBackground() {
		Random rand = new Random();
		int i = rand.nextInt(5);
		region = AssetManager.getTextureRegion("memorySpiel", "Background"
				+ i);
		return new Image(region);
	}
	
	protected Label createReplayLabel(){
		BitmapFont font = new BitmapFont(Gdx.files.internal("pictures/city/font.txt"));
		font.setScale(1f, 1f);
		font.setColor(Color.WHITE);
		LabelStyle labelStyle = new LabelStyle(font,Color.WHITE);	
		return new Label("Neue Runde?", labelStyle);
	}
	
	protected void enableReplayButton(boolean b){
		replay.setVisible(b);
	}

	protected void updateKarten(float delta) {
		int i = 0;
		List<Karte> karten = manager.getKarten();
		for (Karte k : karten) {
			if (k.getState() == CardState.TEMPORARILY_OPENED && manager.canOpen) {
				k.setDrawable(k.getPicture());
			}
			if (k.getState() == CardState.TEMPORARILY_CLOSED) {
				k.setState(CardState.CLOSED);
				k.setDrawable(Karte.karte);
			}
			if(k.getState() == CardState.OPEN){
				i++;
			}
		}
		if (i == karten.size()){
			state = GameState.END;
			enableReplayButton(true);
			return;
		}
		manager.checkChanged(delta);

	}

	@Override
	protected void doRender(float delta) {
		stage.draw();
		if(state == GameState.READY){
		updateKarten(delta);
		}
//		else if(state == GameState.END){
//			drawFont();
//		}
	}

	protected Karte drawFont() {
		Karte replayButton = new Karte(this.width/2 - 67, 900);
		return replayButton;
	}
	
	protected void restart() {
		backgroundImage = getBackground();
		manager.restart();
		enableReplayButton(false);
		state = GameState.READY;
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
