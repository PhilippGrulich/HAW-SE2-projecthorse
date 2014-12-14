package com.haw.projecthorse.level.game.memoryspiel;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.game.Game;
import com.haw.projecthorse.level.game.memoryspiel.Karte.CardState;

public class MemorySpiel extends Game {

	public enum GameState {
		READY, RESTART, END;
	}

	private SpriteBatch batcher;
	private KartenManager manager;
	private Stage stage;
	private Drawable drawable;
	private Image backgroundImage;
	private GameState state;
	private Label replay;
	private Karte playButton;
	private Music music;

	private Label scoreLabel;
	
	public MemorySpiel() {
		manager = new KartenManager();
		batcher = this.getSpriteBatch();
		state = GameState.READY;
		stage = new Stage(this.getViewport(), batcher);
		AssetManager.loadMusic("memorySpiel");
		this.music = this.audioManager.getMusic("memorySpiel", "Happy Ukulele(edited).mp3");
		this.music.setLooping(true);
		InputManager.addInputProcessor(stage);
		replay = createReplayLabel();
		replay.setPosition(this.width / 3.7f, this.height * 0.85f);
		drawable = new TextureRegionDrawable(
				AssetManager.getTextureRegion("memorySpiel", "Background" + getBackground()));
		backgroundImage = new Image(drawable);
		backgroundImage.toFront();
		playButton = createPlayButton();
		playButton.setVisible(false);
		enableReplayButton(false);
		stage.addActor(backgroundImage);
		stage.addActor(replay);
		stage.addActor(playButton);
		initKarten();
		playMusic();
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
	
	protected void playMusic(){	
		this.music.play();
		this.music.setVolume(0.4f);
	}

	protected Karte createPlayButton() {
		Karte button = new Karte(this.width / 2.2f , 900);
		button.setDrawable(new TextureRegionDrawable(AssetManager
				.getTextureRegion("memorySpiel", "PlayButton")));
		button.setWidth(250);
		return button;
	}

	protected Label createReplayLabel() {
		BitmapFont font = AssetManager.getTextFont(FontSize.FORTY);
		font.setScale(1f, 1f);
		font.setColor(Color.WHITE);
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		return new Label("Neue Runde?", labelStyle);
	}

	protected void enableReplayButton(boolean b) {
		replay.setVisible(b);
		playButton.setVisible(b);
	}

	protected void updateKarten(float delta) {
		int i = 0;
		List<Karte> karten = manager.getKarten();
		for (Karte k : karten) {
			if (k.getState() == CardState.TEMPORARILY_OPENED) {
				if (manager.canOpen) {
					k.setDrawable(k.getPicture());
				} else {
					k.setState(CardState.CLOSED);
				}
			}
			if (k.getState() == CardState.TEMPORARILY_CLOSED) {
				k.setState(CardState.CLOSED);
				k.setDrawable(Karte.karte);
			}
			if (k.getState() == CardState.OPEN) {
				i++;
			}
		}
		if (i == karten.size()) {
			state = GameState.END;
			music.stop();
			enableReplayButton(true);
			return;
		}
		manager.checkChanged(delta);

	}

	@Override
	protected void doRender(float delta) {
		stage.draw();
		if (state == GameState.READY) {
			updateKarten(delta);
		} else if (state == GameState.END) {
			if (playButton.getState() == CardState.TEMPORARILY_OPENED) {
				restart();
			}
		}
	}

	protected void restart() {
		drawable = new TextureRegionDrawable(
				AssetManager.getTextureRegion("memorySpiel", "Background" + getBackground()));
		backgroundImage.setDrawable(drawable);
		manager.restart();
		playButton.setState(CardState.CLOSED);
		enableReplayButton(false);
		state = GameState.READY;
		playMusic();
	}

	@Override
	protected void doDispose(){
			this.stage.dispose();
			this.music.stop();
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
