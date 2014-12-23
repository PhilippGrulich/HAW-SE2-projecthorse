package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.lootmanager.Chest;
import com.haw.projecthorse.level.game.parcours.ParcoursLoot;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.Race;
import com.haw.projecthorse.player.race.RaceLoot;
import com.haw.projecthorse.savegame.SaveGameManager;

public class GameOperator implements IGameOperator, IGameOperatorFuerParcours {

	private IGameObjectLogicFuerGameOperator logic;
	private IGameFieldFuerGameOperator gameField;
	private Chest chest;
	private Chest chestToShow;
	private boolean saved = false;
	private boolean paused = false;
	private boolean gameEndReached;
	private GameState gameStatus = GameState.GREETING;
	GestureDetector listener;
	private InputMultiplexer inputMultiplexer;
	private float breite;
	private float distance;
	private float moveToDuration;
	private final float MOVEMENT_PER_SECOND;
	private boolean lootShown = false;
	private boolean greeting = true;
	private boolean selectHorse = false;
	private boolean isHorseChosen = false;

	public GameOperator(Stage stage, Viewport viewport, int width, int height,
			Chest chest, AudioManager audioManager, Overlay overlay) {
		AssetManager.loadMusic("parcours");

		gameField = (IGameFieldFuerGameOperator) new GameField(stage, viewport,
				width, height, audioManager);
		logic = new GameObjectLogic(width,
				(IGameFieldFuerGameObjectLogic) gameField);
		setInputProcessor();
		// getEarnedLoot();
		this.chest = chest;
		chestToShow = new Chest(overlay);
		MOVEMENT_PER_SECOND = gameField.getStage().getWidth() / 1.25f;
		// ArrayList<ParcoursLoot> loots = (ArrayList<ParcoursLoot>) getLoots();
		// for(ParcoursLoot l : loots){
		// chest.addLoot(l);
		// }
		// showEarnedLoot(chest);

	}

	@Override
	public void restart() {
		// TODO Alles auf 0 setzen

	}

	public void setInputProcessor() {
		listener = new GestureDetector(new GameInputListener(
				(IGameObjectLogicFuerGameInputListener) logic,
				(IGameFieldFuerGameInputListener) gameField));
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(Gdx.input.getInputProcessor());
		inputMultiplexer.addProcessor(new StageGestureDetector(gameField
				.getStage(), true, ControlMode.HORIZONTAL));
		inputMultiplexer.addProcessor(listener);

		Gdx.input.setInputProcessor(inputMultiplexer);

	}

	@Override
	public void update(float delta) {
		if (gameStatus == GameState.GREETING) {
			// Begin Test
			if (greeting) {
				//Begrüßungspopup
				String userName = SaveGameManager.getLoadedGame()
						.getPlayerName();
				gameField.showPopup(GameState.GREETING, userName);
				inputMultiplexer.removeProcessor(listener);
				//gameField.getPlayer().removeSwipeListener();
				Gdx.input.setInputProcessor(inputMultiplexer);
				gameField.pauseGallop();
				this.pause();
				greeting = false;
			}
			// Ende Test

			if (gameField.isGreetingButtonPressed() && !selectHorse) {
	
				gameField.removePopup();
			
				List<ParcoursLoot> loot = SaveGameManager.getLoadedGame().getSpecifiedLoot(ParcoursLoot.class);
				
				System.out.println("1");
				boolean set = false;
				for(ParcoursLoot l : loot){
					if(l.getName().equals("hannoveraner")){
						//gameField.initPlayer(HorseRace.HANNOVERANER);
						HorseRace[] races = new HorseRace[2];
						races[0] = HorseRace.HAFLINGER;
						races[1] = HorseRace.HANNOVERANER;
						gameField.showPopup(GameState.HORSESELECTION, races);
						set = true;
						selectHorse = true;
					}
				}
				if(!set){
					System.out.println("2");
					gameField.initPlayer(null);
				}
				
				if(!selectHorse){
					System.out.println("3");
				inputMultiplexer.addProcessor(listener);
				//gameField.getPlayer().addSwipeListener();
				Gdx.input.setInputProcessor(inputMultiplexer);
				gameField.playGallop();
				this.gameStatus = GameState.START;
				this.setPause(false);
				}
				
			} else {
				gameField.fadePopup(delta, gameStatus);
				gameField.drawGameField();
			}
			
			if(selectHorse){
				System.out.println("4");
				if(gameField.isHorseSelected()){
					System.out.println("5");
					gameField.initPlayer(gameField.getSelectedRace());
					isHorseChosen = true;
					selectHorse = false;
				}else {
					gameField.fadePopup(delta, GameState.HORSESELECTION);
					gameField.drawGameField();
				}
			}else if(isHorseChosen){
				System.out.println("7");
				gameField.removePopup();
				inputMultiplexer.addProcessor(listener);
				//gameField.getPlayer().addSwipeListener();
				Gdx.input.setInputProcessor(inputMultiplexer);
				gameField.playGallop();
				this.gameStatus = GameState.START;
				this.setPause(false);
			}
			
		} else {

			if (delta != 0 && !paused && gameStatus != GameState.END) {
				logic.update(delta);
				verifyGameState(delta);
			} else {
				if (gameField.isButtonYesPressed(gameStatus)) {
					inputMultiplexer.addProcessor(listener);
					Gdx.input.setInputProcessor(inputMultiplexer);
					gameEndReached = false;
					gameField.restart();
					gameField.removePopup();
					gameField.playGallop();
					this.gameStatus = GameState.START;
					this.setPause(false);
				} else if (gameField.isButtonNoPressed(gameStatus)) {
					List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
					List<ParcoursLoot> loot = gameField.getLoot();
					for (ParcoursLoot pl : loot) {
						if (!allreadyWon.contains(pl) && pl.getWonStatus()) {
							chest.addLoot(pl);
						}
					}
					chest.saveAllLoot();
					gameEndReached = false;
					this.setPause(false);
					gameField.clear();
					this.gameStatus = GameState.END;
					gameField.stopGallop();
					GameManagerFactory.getInstance().navigateBack();
				}
				gameField.fadePopup(delta, gameStatus);
				gameField.drawGameField();

			}
		}
		// TODO logic.success -> popup, stats, loot, restart?
	}

	// Score von GameField erfragen u. bei Niederlage Spiel beenden.
	// Dann prüfen ob Loot gewonnen -> wenn ja, Erfolgsmeldung u. in Chest
	// legen.
	private void verifyGameState(float delta) {
		if (gameField.getScore() >= 5) {
			inputMultiplexer.removeProcessor(listener);
			Gdx.input.setInputProcessor(inputMultiplexer);
			gameField.pauseGallop();
			gameEndReached = true;
			gameStatus = GameState.WON;
			List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
			for (ParcoursLoot l : gameField.getLoot()) {
				if (l.getAvailableAtScore() <= gameField.getScore()
						&& !l.getWonStatus()) {
					chestToShow.addLoot(l);
					l.setWonStatus(true);
				}
			}

			if (!lootShown) {
				chestToShow.showAllLoot();
				lootShown = true;
			} else {
				gameField.showPopup(GameState.WON);
				lootShown = false;
			}
			this.pause();
		} else if (gameField.getScore() < -10000) {
			gameField.pauseGallop();
			gameField.showPopup(GameState.LOST);
			gameStatus = GameState.LOST;
			gameEndReached = true;
			this.pause();
		}
	}

	public void setPause(boolean p) {
		if (!p)
			gameField.playGallop();
		paused = p;
	}

	public void pause() {
		gameField.pauseGallop();
		paused = true;
	}

	public void dispose() {
		gameField.dispose();
	}

	private void showEarnedLoot(Chest chest) {
		List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
		if (allreadyWon.size() > 0) {
			chest.showAllLoot();
		}
	}

	/**
	 * ermittelt alle bereits gewonnen Loots aus diesem Spiel die unter dem
	 * verwendeten Spielstand gesichert sind
	 * 
	 * @return Liste aller Thimblerig-Loots
	 */
	public List<? extends ParcoursLoot> getLoots() {
		return SaveGameManager.getLoadedGame().getSpecifiedLoot(
				com.haw.projecthorse.level.game.parcours.ParcoursLoot.class);
	}

}
