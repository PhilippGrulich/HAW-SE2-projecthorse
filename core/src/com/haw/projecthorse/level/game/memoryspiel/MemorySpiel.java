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
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.RaceLoot;
import com.haw.projecthorse.savegame.SaveGameManager;

/**
 * identische Paare von Karten finden.
 * 
 * @author Ngoc Huyen Nguyen
 * @version 1.0
 */

public class MemorySpiel extends Game {

	/**
	 * Spielzustände
	 */
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

	/**
	 * Konstruktor.
	 */
	public MemorySpiel() {
		super();
		batcher = this.getSpriteBatch();
		stage = new Stage(this.getViewport(), batcher);
		InputManager.addInputProcessor(stage);

		// Karten-Objekte erzeugen und konfigurieren
		manager = new KartenManager();

		// Default Spielzustand setzen
		state = GameState.READY;

		// Hintergrund setzen
		drawable = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Background" + getBackground()));
		backgroundImage = new Image(drawable);
		backgroundImage.toFront();
		stage.addActor(backgroundImage);

		// Score Anzeige initialisieren
		score = initScore();
		score.toFront();
		stage.addActor(score);

		// Karten ins Stage einfügen
		initKarten();

		// Replay Pop Up initialisieren
		initReplayPopUp();

		// Musik konfigurieren
		initMusic();

	}

	/**
	 * Musik laden und konfigurieren.
	 */
	private void initMusic() {
		AssetManager.loadMusic("memorySpiel");
		this.music = this.audioManager.getMusic("memorySpiel",
				"Happy Ukulele(edited).mp3");
		this.music.setLooping(true);
		this.music.setVolume(0.4f);
		this.music.play();
	}

	/**
	 * Karten ins Stage einfügen.
	 */
	private void initKarten() {
		List<Karte> karten = manager.getKarten();
		for (Karte k : karten) {
			k.toFront();
			stage.addActor(k);
		}
	}

	/**
	 * Replay Pop Up erzeugen und die Aktionen von Buttons konfigurieren.
	 */
	private void initReplayPopUp() {

		replay = new Dialog("Neue Runde?");

		// Beim Klicken auf diesem Button wird der Pop Up gelöscht und das Spiel
		// wird neugestartet
		replay.addButton("Ja, gerne", new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				getOverlay().disposePopup();
				restart();
			}
		});

		// Beim Klicken auf diesem Button verlässt der Spieler dem Minispiel und
		// kehrt zur City Menu zurück
		replay.addButton("Nein, danke", new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				GameManagerFactory.getInstance().navigateBack();
			}

		});

	}

	/**
	 * einen beliebigen Hintergrund wählen.
	 * 
	 * @return randomisierte Nummer für Hintergrundauswahl
	 */
	private int getBackground() {
		Random rand = new Random();
		int i = rand.nextInt(5);
		return i;
	}

	/**
	 * Score Anzeige Setter.
	 * 
	 * @param score
	 *            bei ScoreAnzeige zu setzende Score
	 */
	private void setScore(final int score) {
		this.score.setText("Score: " + score);
	}

	/**
	 * Score Anzeige erzeugen und konfigurieren.
	 * 
	 * @return konfigurierte Score Anzeige
	 */
	private Label initScore() {
		BitmapFont font = AssetManager.getHeadlineFont(FontSize.SIXTY);
		font.setColor(Color.WHITE);
		font.setScale(1f, 1f);
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label label = new Label("Score: 0", labelStyle);
		label.setPosition(2f, this.height * 0.93f);
		return label;
	}

	/**
	 * die Zustände bzw. die Bilder der Karten aktualisieren.
	 * 
	 * * @param delta Delta von Methode doRender
	 */
	private void updateKarten(final float delta) {
		int i = 0;

		// die Bilder der Karten nach entspechenden Zuständen anpassen
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

		// Wenn die Anzahl der geöffneten Karten gleich der gesamten Karten,
		// geht das Spiel zum Ende
		if (i == karten.size()) {
			endGame();
			return;
		}

		// die Kartenzustände aktualisieren
		manager.checkChanged(delta);

		// Score aktualisieren
		setScore(manager.getScore());
	}

	/**
	 * Nachdem alle Karten aufgedeckt werden, pausiert die Musik, SpielZustand
	 * wird auf End Zustand gesetzt, alle bisher gewonnene Loots werden
	 * angezeigt.
	 */
	private void endGame() {
		music.stop();
		state = GameState.END;
		showLoot();
	}

	/**
	 * Game Rendering.
	 * 
	 * @param delta
	 *            die Zeitdifferenz vom letzten Render
	 */
	@Override
	protected void doRender(final float delta) {
		// Stage zeichnen
		stage.draw();

		// Wähnrend dem Spiel werden Karten aktualisiert
		if (state == GameState.READY) {
			updateKarten(delta);
		}

		// Beim Anzeigen des LootPopUp wird Spielzutand auf ReplayZustand
		// gesetzt
		if (delta == 0) {
			state = GameState.REPLAY;
		}

		// Wenn das LootPopUp geschlossen wird, wird ReplayPopUp angezeigt
		if (state == GameState.REPLAY && delta != 0) {
			getOverlay().showPopup(replay);
		}
	}

	/**
	 * das Spiel wird gesettet mit neuem Hintergrund, neue Reihenfolge von
	 * Karten. Das Spielzustand wird wieder auf Readyzustand gesetzt.
	 * ReplayPopUp wird initialisiert. Musik spielt weiter.
	 */
	private void restart() {
		drawable = new TextureRegionDrawable(AssetManager.getTextureRegion(
				"memorySpiel", "Background" + getBackground()));
		backgroundImage.setDrawable(drawable);
		manager.restart();
		state = GameState.READY;
		initReplayPopUp();
		this.music.play();
	}

	/**
	 * Es wird erst durch randomisierte Wahrscheinlichkeit herausgefunden, ob
	 * der Spieler ein Pferd gewinnen kann. Wenn nicht, wird der Loot nach
	 * erreichten Punkte bestimmt. Loot wird danach in den Truhe eingefügt. Der
	 * Truhe wird im PopUp angezeigt. Nach dem der Truhe gespeischert wird, wird
	 * er geleert.
	 */
	public void showLoot() {
		double roll = Math.random();
		if (roll <= 0.2f
				&& !SaveGameManager.getLoadedGame()
						.getSpecifiedLoot(RaceLoot.class)
						.contains(HorseRace.SHETTI)) {
			chest.addLootAndShowAchievment(new RaceLoot(HorseRace.SHETTI));
		}

		int score = manager.getScore();
		if (20 <= score && score < 30) {
			Random r = new Random();
			int rand = r.nextInt(2) + 1;
			if (rand == 1) {
				chest.addLootAndShowAchievment(new MemorySpielLoot("Eichel",
						"Süße kleine Eichel", "Loot1"));
			} else if (rand == 2) {
				chest.addLootAndShowAchievment(new MemorySpielLoot("Kleeblatt",
						"Bringt dir Glück", "Loot2"));
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

	@Override
	protected void doDispose() {
		this.stage.dispose();
		overlay.dispose();
		this.music.dispose();
	}

	@Override
	protected void doResize(final int width, final int height) {
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

	/**
	 * Overlay Getter.
	 * 
	 * @return Overlay
	 */
	public Overlay getOverlay() {
		return overlay;
	}
}
