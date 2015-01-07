package com.haw.projecthorse.level.menu.playermenu;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;
import com.haw.projecthorse.level.util.uielements.ButtonSmall.ButtonType;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.RaceLoot;
import com.haw.projecthorse.savegame.SaveGameManager;

/**
 * Menü zum Wählen der Pferderasse.
 * 
 * @author Oliver
 * @version 1.2
 */

public class PlayerMenu extends Menu {
	private static final float SCALEBYFACTOR = 1f;
	private static final int DURATION = 2;

	private Stage stage;
	private Player active;
	private Label label;
	private float playerPositionX, playerPositionY = 120, invisiblePositionX;
	private ArrayList<RaceLoot> races;
	private int curRaceIndex;

	/**
	 * Aktualisiert den Rassennamen.
	 */
	private void updateNameLabel() {
		label.setText(races.get(curRaceIndex).getName());
		label.setWidth(label.getTextBounds().width);
		label.setPosition((width - label.getWidth()) / 2, height - 200);
	}

	/**
	 * Wechselt zur nächsten Rasse.
	 */
	private void incIndex() {
		curRaceIndex = (curRaceIndex == 0 ? races.size() : curRaceIndex) - 1;
		updatePlayer(true);
	}

	/**
	 * Wechselt zur vorherigen Rasse.
	 */
	private void decIndex() {
		curRaceIndex = (curRaceIndex + 1) % races.size();
		updatePlayer(false);
	}

	/**
	 * Aktualisiert die Pferde.
	 * 
	 * @param right
	 *            true, wenn sich das alte Pferd nach Rechts wegbewegen soll
	 */
	private void updatePlayer(final boolean right) {
		Player inActive = new PlayerImpl(races.get(curRaceIndex).getRace());
		inActive.scaleBy(SCALEBYFACTOR);
		stage.addActor(inActive);

		inActive.setPosition((right ? invisiblePositionX : width
				- invisiblePositionX), playerPositionY);
		inActive.setAnimationSpeed(0.3f);
		active.setAnimationSpeed(0.45f);

		if (right) {
			inActive.addAction(Actions.parallel(
					Actions.moveTo(playerPositionX, playerPositionY, DURATION),
					new AnimationAction(Direction.RIGHT)));
			active.addAction(Actions.sequence(
					Actions.moveBy(width * (right ? 1.5f : -1.5f), 0, DURATION),
					Actions.removeActor()));
		} else {
			inActive.clearActions();
			active.clearActions();
			inActive.addAction(Actions.sequence(
					Actions.parallel(new AnimationAction(Direction.LEFT,
							DURATION), Actions.moveTo(playerPositionX,
							playerPositionY, DURATION)), new AnimationAction(
							Direction.RIGHT)));
			active.addAction(Actions.sequence(
					Actions.parallel(new AnimationAction(Direction.LEFT,
							DURATION), Actions.moveBy(width
							* (right ? 1.5f : -1.5f), 0, DURATION)), Actions
							.removeActor()));
		}

		active = inActive;
		updateNameLabel();
	}

	/**
	 * Erzeugt den Hintergrund.
	 */
	private void createBackground() {
		stage.addActor(new EndlessBackground(width, AssetManager
				.getTextureRegion("menu", "sky"), 30));
		stage.addActor(new EndlessBackground(width, AssetManager
				.getTextureRegion("menu", "second_grass"), 11));
		stage.addActor(new EndlessBackground(width, AssetManager
				.getTextureRegion("menu", "first_grass"), 8));
		stage.addActor(new EndlessBackground(width, AssetManager
				.getTextureRegion("menu", "ground"), 4));
	}

	/**
	 * Erzeugt die Pfeile zum Wechseln.
	 */
	private void createButtons() {
		ButtonSmall next, prev;

		prev = new ButtonSmall(ButtonType.LEFT);
		prev.setPosition(10, 300);
		prev.addListener(new InputListener() {
			@Override
			public boolean touchDown(final InputEvent event, final float x,
					final float y, final int pointer, final int button) {
				decIndex();
				return false;
			}
		});
		stage.addActor(prev);

		next = new ButtonSmall(ButtonType.RIGHT);
		next.setPosition(width - 10 - next.getWidth(), 300);
		next.addListener(new InputListener() {
			@Override
			public boolean touchDown(final InputEvent event, final float x,
					final float y, final int pointer, final int button) {
				incIndex();
				return false;
			}
		});
		stage.addActor(next);
	}

	/**
	 * Erzeugt das erste Pferd.
	 */
	private void createPlayer() {
		active = new PlayerImpl();
		active.scaleBy(SCALEBYFACTOR);

		playerPositionX = (width - active.getWidth() * active.getScaleX()) / 2;
		invisiblePositionX = active.getWidth() * -5;

		active.setPosition(playerPositionX, playerPositionY);
		active.setAnimationSpeed(0.3f);
		active.addAction(new AnimationAction(Direction.RIGHT));
		active.addListener(new SwipeListener() {
			@Override
			public void swiped(final SwipeEvent event, final Actor actor) {
				if (event.getDirection() == Direction.RIGHT) {
					incIndex();
				} else {
					decIndex();
				}

			}
		});
		stage.addActor(active);
	}

	/**
	 * Erzeugt das Label für den Namen der gewählten Rasse.
	 */
	private void createLabel() {
		label = new Label("", new LabelStyle(
				AssetManager.getTextFont(FontSize.SIXTY), Color.BLACK));

		updateNameLabel();
		stage.addActor(label);
	}

	/**
	 * Initialisiert den Index für die Liste der verfügbaren Rassen.
	 */
	private void initRaceListIndex() {
		HorseRace curRace = SaveGameManager.getLoadedGame().getHorseRace();
		for (int i = 0; i < races.size(); i++) {
			if (races.get(i).getRace() == curRace) {
				curRaceIndex = i;
				break;
			}
		}
	}

	@Override
	protected void doRender(final float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	protected void doDispose() {
		// Alles speichern, bevor das Menü verlassen wird
		SaveGameManager.getLoadedGame().setHorseRace(
				races.get(curRaceIndex).getRace());
		SaveGameManager.saveLoadedGame();
	}

	@Override
	protected void doResize(final int width, final int height) {
	}

	@Override
	protected void doShow() {
		stage = new Stage(getViewport(), getSpriteBatch());

		createBackground();
		races = new ArrayList<RaceLoot>(SaveGameManager.getLoadedGame()
				.getSpecifiedLoot(RaceLoot.class));
		if (races.size() > 1) {
			InputManager.addInputProcessor(new StageGestureDetector(stage,
					true, ControlMode.HORIZONTAL));

			createButtons();

			initRaceListIndex();
		} else {
			curRaceIndex = 0;
		}

		createPlayer();
		createLabel();
	}

	@Override
	protected void doHide() {

	}

	@Override
	protected void doPause() {
	}

	@Override
	protected void doResume() {
	}

}
