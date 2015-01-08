package com.haw.projecthorse.level.game.parcours;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.game.parcours.GameOverPopup.GameState;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.lootmanager.Chest;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.RaceLoot;
import com.haw.projecthorse.savegame.SaveGameManager;

/**
 * Die Klasse ermittelt den Zustand des Spiels (start, ende, gewonnen, verloren),
 * blendet je nach Zustand die entsprechenden Popups ein und startet das Spiel neu,
 * wenn der Spieler das wünscht.
 * @author Francis
 * @version 1.0
 */
public class GameOperator implements IGameOperator, IGameOperatorFuerParcours {

	private IGameObjectLogicFuerGameOperator logic;
	private IGameFieldFuerGameOperator gameField;
	private Chest chest;
	private Chest chestToShow;
	private boolean paused = false;
	private GameState gameStatus = GameState.GREETING;
	GestureDetector listener;
	private InputMultiplexer inputMultiplexer;
	private boolean greeting = true;
	private Random randomGenerator;

	/**
	 * Konstruktor.
	 * @param stage Die Stage auf der alle Actor sind (inkl. GameObjects).
	 * @param viewport Der Viewport.
	 * @param width Die Breite des Spiels.
	 * @param height Die Höhe des Spiels.
	 * @param chest Die Chest in der die Beute landet.
	 * @param audioManager Der AudioManager zum abspielen der Sounds / Musik.
	 * @param overlay Das Overlaymodul der gesamten App.
	 */
	public GameOperator(final Stage stage, final Viewport viewport, final int width, final int height,
			final Chest chest, final AudioManager audioManager, final Overlay overlay) {
		AssetManager.loadMusic("parcours");

		gameField = (IGameFieldFuerGameOperator) new GameField(stage, viewport,
				width, height, audioManager);
		logic = new GameObjectLogic(width,
				(IGameFieldFuerGameObjectLogic) gameField);
		setInputProcessor();
		this.chest = chest;
		chestToShow = new Chest(overlay);
		randomGenerator = new Random();
	}

	@Override
	public void restart() {
		// TODO Alles auf 0 setzen

	}

	/**
	 * Setzt den Input-Prozessor der auf Berührungen des Screens reagiert.
	 */
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
	public void update(final float delta) {
		if (gameStatus == GameState.GREETING) {
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
		

			if (gameField.isGreetingButtonPressed() /*&& !selectHorse*/) {
	
				gameField.removePopup();
				gameField.initPlayer(SaveGameManager.getLoadedGame().getHorseRace());
				gameStatus = GameState.START;
				inputMultiplexer.addProcessor(listener);
				//gameField.getPlayer().addSwipeListener();
				Gdx.input.setInputProcessor(inputMultiplexer);
				this.setPause(false);
				gameField.playGallop();
				
			} else {
				gameField.fadePopup(delta, gameStatus);
				gameField.drawGameField();
			}
		} else {
			if(!paused && gameField.getGameOverState()){
				verifyGameState(delta);
			}
			
			if (delta != 0 && !paused && !gameField.getGameOverState()) {
				logic.update(delta);
				//verifyGameState(delta);
			} else {
				if (gameField.isButtonYesPressed(gameStatus)) {
					inputMultiplexer.addProcessor(listener);
					Gdx.input.setInputProcessor(inputMultiplexer);
					logic.setPlayerJump(false);
					gameField.restart();
					
					gameField.removePopup();
					gameField.playGallop();
					this.gameStatus = GameState.START;
					this.setPause(false);
				} else if (gameField.isButtonNoPressed(gameStatus)) {
					@SuppressWarnings("unchecked")
					List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
					List<ParcoursLoot> loot = gameField.getLoot();
					for (ParcoursLoot pl : loot) {
						if (!allreadyWon.contains(pl) && pl.getWonStatus()) {
							if(pl.getName().equals("Hannoveraner")){
								chest.addLoot(new RaceLoot(HorseRace.HANNOVERANER));
								//chest.addLoot(pl);
							}else{
							chest.addLoot(pl);
							}
						}
					}
					chest.saveAllLoot();
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
	}

	/**
	 * Prüft bei Spielende (Berührung mit Hindernis), ob Loot gewonnen wurde oder nicht u.
	 * zeigt das entsprechende Popup an.
	 * @param delta Die Zeit, die seit dem letzten Frame vergangen ist.
	 */
	private void verifyGameState(final float delta){
		if(gameField.getGameOverState()){
			inputMultiplexer.removeProcessor(listener);
			Gdx.input.setInputProcessor(inputMultiplexer);
			gameField.pauseGallop();
			gameStatus = GameState.WON;
			@SuppressWarnings("unchecked")
			List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
			List<ParcoursLoot> loot = gameField.getLoot();
			
			for(ParcoursLoot p : allreadyWon){
				if(loot.contains(p)){
					loot.remove(p);
				}
			}
			boolean isChestEmpty = true;
			for (ParcoursLoot l : loot) {
				if (l.getAvailableAtScore() <= gameField.getScore()
						&& !l.getWonStatus()) {
					if(l.getName().equals("Hannoveraner")){
						if(randomGenerator.nextInt(100) <= 101){
							chestToShow.addLoot(new RaceLoot(HorseRace.HANNOVERANER));
							l.setWonStatus(true);
							isChestEmpty = false;
						}
					}else{
						chestToShow.addLoot(l);
						l.setWonStatus(true);	
						isChestEmpty = false;
					}
				}
			}

			if (!isChestEmpty) {
				chestToShow.showAllLoot();
				chest.saveAllLoot();
			} else {
				gameField.showPopup(GameState.WON);
			}
			this.pause();
		} 
	}
	
	/**
	 * Setzt das Spiel auf Pause wenn p = true.
	 * @param p wenn p = true -> Spiel pausiert. Sonst nicht.
	 */
	public void setPause(final boolean p) {
		if (!p){
			gameField.playGallop();
		}
		paused = p;
	}

	/**
	 * Setzt das Spiel auf Pause & pausiert Gallopp.
	 */
	public void pause() {
		gameField.pauseGallop();
		paused = true;
	}

	/**
	 * Disposed die disposables.
	 */
	public void dispose() {
		@SuppressWarnings("unchecked")
		List<ParcoursLoot> allreadyWon = (List<ParcoursLoot>) getLoots();
		List<ParcoursLoot> loot = gameField.getLoot();
		for (ParcoursLoot pl : loot) {
			if (!allreadyWon.contains(pl) && pl.getWonStatus()) {
				if(pl.getName().equals("Hannoveraner")){
					chest.addLoot(new RaceLoot(HorseRace.HANNOVERANER));
					//chest.addLoot(pl);
				}else{
				chest.addLoot(pl);
				}
			}
		}
		chest.saveAllLoot();
		gameField.dispose();
	}

	/**
	 * Ermittelt alle bereits gewonnen Loots aus diesem Spiel die unter dem
	 * verwendeten Spielstand gesichert sind.
	 * @return Liste aller Parcours-Loots
	 */
	public List<? extends ParcoursLoot> getLoots() {
		return SaveGameManager.getLoadedGame().getSpecifiedLoot(
				com.haw.projecthorse.level.game.parcours.ParcoursLoot.class);
	}

}
