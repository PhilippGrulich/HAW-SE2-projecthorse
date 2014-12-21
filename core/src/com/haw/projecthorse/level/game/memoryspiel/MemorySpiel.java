package com.haw.projecthorse.level.game.memoryspiel;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.game.Game;
import com.haw.projecthorse.level.game.memoryspiel.Karte.CardState;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;

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
	private Label scoreLabel;
	private Label score;
	private Music music;
	private ButtonLarge newGame;
	private ButtonLarge exitGame;

	public MemorySpiel() {
		manager = new KartenManager();
		batcher = this.getSpriteBatch();
		state = GameState.READY;
		stage = new Stage(this.getViewport(), batcher);
		AssetManager.loadMusic("memorySpiel");
		this.music = this.audioManager.getMusic("memorySpiel",
				"Happy Ukulele(edited).mp3");
		this.music.setLooping(true);
		InputManager.addInputProcessor(stage);

		// Hintergrund
		drawable = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Background" + getBackground()));
		backgroundImage = new Image(drawable);
		backgroundImage.toFront();
		stage.addActor(backgroundImage);

		// Score Anzeige
		scoreLabel = createScoreLabel();
		scoreLabel.toFront();
		score = initScore();
		score.toFront();
		stage.addActor(scoreLabel);
		stage.addActor(score);
		initKarten();
		initButtons();
		playMusic();
	}

	protected void initKarten() {
		List<Karte> karten = manager.getKarten();
		for (Karte k : karten) {
			k.toFront();
			stage.addActor(k);
		}
	}

	protected void initButtons() {

		this.newGame = new ButtonLarge("Neue Runde?", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				newGame.setVisible(false);
				exitGame.setVisible(false);
				state = GameState.END;
			}
		});

		this.exitGame = new ButtonLarge("Nein, danke", new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
			}

		});

		this.newGame.setPosition(this.width / 6.5f, this.height / 2f);
		this.exitGame.setPosition(this.width / 6.5f, this.height / 3f);
//		this.newGame.setColor(Color.PINK);
//		this.exitGame.setColor(Color.PINK);
//		this.newGame.getStyle().fontColor = Color.WHITE;
//		this.exitGame.getStyle().fontColor = Color.WHITE;

		this.newGame.toFront();
		this.exitGame.toFront();
		stage.addActor(newGame);
		stage.addActor(exitGame);
		this.newGame.setVisible(false);
		this.exitGame.setVisible(false);
	}

	protected int getBackground() {
		Random rand = new Random();
		int i = rand.nextInt(5);
		return i;
	}

	protected void playMusic() {
		this.music.play();
		this.music.setVolume(0.4f);
	}

	protected Label createScoreLabel() {
		BitmapFont font = AssetManager.getTextFont(FontSize.THIRTY);
		font.setScale(3f, 3f);
		font.setColor(Color.WHITE);
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label label = new Label("Score", labelStyle);
		label.setPosition(this.width / 2.5f, this.height * 0.9f);
		return label;
	}

	protected void setScore(int score) {
		if (score > 9) {
			this.score.setPosition(this.width / 2.8f, this.height * 0.75f);
		} else {
			this.score.setPosition(this.width / 2.2f, this.height * 0.75f);
		}
		this.score.setText(score + "");
	}

	protected Label initScore() {
		BitmapFont font = AssetManager.getTextFont(FontSize.SIXTY);
		font.setScale(2f, 2f);
		font.setColor(Color.WHITE);
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label label = new Label("0", labelStyle);
		label.setPosition(this.width / 2.2f, this.height * 0.75f);
		return label;
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
			this.newGame.setVisible(true);
			this.newGame.getStyle().font.setScale(1f, 1f);
			this.exitGame.setVisible(true);
			this.exitGame.getStyle().font.setScale(1f, 1f);
			music.stop();
			return;
		}
		manager.checkChanged(delta);
		setScore(manager.getScore());
	}

	@Override
	protected void doRender(float delta) {
		stage.draw();
		if (state == GameState.READY) {
			updateKarten(delta);
		} else if (state == GameState.END) {
			restart();
		}
	}

	protected void restart() {
		drawable = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Background" + getBackground()));
		backgroundImage.setDrawable(drawable);
		manager.restart();
		state = GameState.READY;
		playMusic();
	}

	@Override
	protected void doDispose() {
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
