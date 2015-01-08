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
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.popup.Dialog;


public class MemorySpiel extends Game {

	public enum GameState {
		READY, END, REPLAY;
	}

	private SpriteBatch batcher;
	private KartenManager manager;
	private Stage stage;
	private Drawable drawable;
	private Image backgroundImage;
	private GameState state;
	private Label score;
	private Music music;
	private Dialog replay;

	public MemorySpiel() {
		manager = new KartenManager();
		batcher = this.getSpriteBatch();
		state = GameState.READY;

		stage = new Stage(this.getViewport(), batcher);
		InputManager.addInputProcessor(stage);

		// Hintergrund
		drawable = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Background" + getBackground()));
		backgroundImage = new Image(drawable);
		backgroundImage.toFront();
		stage.addActor(backgroundImage);

		// Score Anzeige
		score = initScore();
		score.toFront();
		stage.addActor(score);

		initKarten();

		initReplayPopUp();

		initMusic();

	}

	private void initMusic() {
		AssetManager.loadMusic("memorySpiel");
		this.music = this.audioManager.getMusic("memorySpiel",
				"Happy Ukulele(edited).mp3");
		this.music.setLooping(true);
		playMusic();
	}

	protected void initKarten() {
		List<Karte> karten = manager.getKarten();
		for (Karte k : karten) {
			k.toFront();
			stage.addActor(k);
		}
	}

	protected void initReplayPopUp() {

		replay = new Dialog("Neue Runde?");

		replay.addButton("Ja, gerne", new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				getOverlay().disposePopup();
				restart();
			}
		});

		replay.addButton("Nein, danke", new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
			}

		});

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

	protected void setScore(int score) {
		this.score.setText("Score: " + score);
	}

	protected Label initScore() {
		BitmapFont font = AssetManager.getHeadlineFont(FontSize.SIXTY);
		font.setColor(Color.WHITE);
		font.setScale(1f, 1f);
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label label = new Label("Score: 0", labelStyle);
		label.setPosition(2f, this.height * 0.93f);
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
			endGame();
			return;
		}
		manager.checkChanged(delta);
		setScore(manager.getScore());
	}

	protected void endGame() {
		music.stop();
		state = GameState.END;
		showLoot();
	}

	@Override
	protected void doRender(float delta) {
		stage.draw();
		if (state == GameState.READY) {
			updateKarten(delta);
		}
		if(delta==0){
			state = GameState.REPLAY;
		}
		if (state == GameState.REPLAY && delta!= 0) {
			getOverlay().showPopup(replay);
		}	
	}

	protected void restart() {
		drawable = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Background" + getBackground()));
		backgroundImage.setDrawable(drawable);
		manager.restart();
		state = GameState.READY;
		initReplayPopUp();
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

	public Overlay getOverlay() {
		return overlay;
	}

	public void showLoot() {
		int score = manager.getScore();
		if (20 <= score && score < 30) {
			Random r = new Random();
			int rand = r.nextInt(2) + 1;
			if (rand == 1) {
				chest.addLootAndShowAchievment(new MemorySpielLoot("Eichel",
						"Süße kleine Eichel", "Loot1"));
			} else if (rand == 2) {
				chest.addLootAndShowAchievment(new MemorySpielLoot(
						"Kleeblatt", "Bringt dir Glück", "Loot2"));
			}
		} else if (score == 30) {
			chest.addLootAndShowAchievment(new MemorySpielLoot("Trauben",
					"Süße Trauben", "Loot3"));
		} else if (score == 35) {
			chest.addLootAndShowAchievment(new MemorySpielLoot("Pfirsiche",
					"Leckere Pfirsiche", "Loot4"));
		} else if (score == 40) {
			chest.addLootAndShowAchievment(new MemorySpielLoot("Kirschen",
					"Rote Kirschen", "Loot5"));
		} else if (score == 45) {
			chest.addLootAndShowAchievment(new MemorySpielLoot("Erdbeere",
					"Eine süße Erdbeere", "Loot6"));
		} else if (score == 50) {
			Random r = new Random();
			int rand = r.nextInt(2) + 7;
			if (rand == 7) {
				chest.addLootAndShowAchievment(new MemorySpielLoot("Rose",
						"Eine schöne Rose", "Loot7"));
			} else if (rand == 8) {
				chest.addLootAndShowAchievment(new MemorySpielLoot("Stern",
						"Brillanter Stern", "Loot8"));
			}
		} else if (score > 50) {
			chest.addLootAndShowAchievment(new MemorySpielLoot("Diamant",
					"Toller Blauer Diamant", "Loot9"));
		}	
		chest.showAllLoot();
		chest.saveAllLoot();
	}
}
