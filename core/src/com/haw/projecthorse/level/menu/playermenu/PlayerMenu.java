package com.haw.projecthorse.level.menu.playermenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.savegame.SaveGameManager;

public class PlayerMenu extends Menu {
	private Stage stage;
	private Player active, player1, player2;
	private Label label;
	private float playerPositionX, playerPositionY = 120, invisiblePositionX;
	private String playerName;
//	private TextureAtlas atlas, buttonAtlas;

	private final int DURATION = 2;

	private void updateNameLabel() {
		label.setText(playerName);
		label.setWidth(label.getTextBounds().width);
		label.setPosition((width - label.getWidth()) / 2, height - 200);
	}

	private void incIndex() {
		updatePlayer(true);
	}

	private void decIndex() {
		updatePlayer(false);
	}

	private void updatePlayer(boolean right) {
		Player inActive;

		if (active == player1) {
			inActive = player2;
		} else {
			inActive = player1;
		}

		inActive.setPosition((right ? invisiblePositionX : width
				- invisiblePositionX), playerPositionY);
		inActive.setAnimationSpeed(0.3f);
		active.setAnimationSpeed(0.6f);

		if (right) {
			inActive.addAction(Actions.sequence(
					Actions.moveTo(playerPositionX, playerPositionY, DURATION),
					new AnimationAction(Direction.RIGHT)));
			active.addAction(Actions.sequence(
					Actions.moveBy(width * (right ? 1.5f : -1.5f), 0, DURATION),
					new AnimationAction(Direction.RIGHT)));
		} else {
			inActive.clearActions();
			active.clearActions();
			inActive.addAction(Actions.sequence(
					Actions.parallel(new AnimationAction(Direction.LEFT, DURATION),
					Actions.moveTo(playerPositionX, playerPositionY, DURATION)),
					new AnimationAction(Direction.RIGHT)));
			active.addAction(Actions.sequence(
					Actions.parallel(new AnimationAction(Direction.LEFT, DURATION),
					Actions.moveBy(width * (right ? 1.5f : -1.5f), 0, DURATION)),
					new AnimationAction(Direction.RIGHT)));
		}
		
		active = inActive;
	}

	private void createBackground() {
		stage.addActor(new EndlessBackground(width, AssetManager.getTextureRegion("menu", "sky"), 30));
		stage.addActor(new EndlessBackground(width, AssetManager.getTextureRegion("menu", "second_grass"), 11));
		stage.addActor(new EndlessBackground(width, AssetManager.getTextureRegion("menu", "first_grass"), 8));
		stage.addActor(new EndlessBackground(width, AssetManager.getTextureRegion("menu", "ground"),
				4));
	}

	private void createButtons() {
		Image next, prev;

		prev = new Image(AssetManager.getTextureRegion("selfmade", "button_prev"));
		prev.setPosition(10, 300);
		prev.setScale(2);
		prev.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				decIndex();
				return false;
			}
		});
		stage.addActor(prev);

		next = new Image(AssetManager.getTextureRegion("selfmade", "button_next"));
		next.setPosition(width - 70, 300);
		next.setScale(2);
		next.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				incIndex();
				return false;
			}
		});
		stage.addActor(next);
	}

	private void createPlayers() {
		player1 = new PlayerImpl();
		player1.setScale(3);

		playerPositionX = (width - 3 * player1.getWidth()) / 2;
		invisiblePositionX = player1.getWidth() * -5;

		player1.setPosition(playerPositionX, playerPositionY);
		player1.setAnimationSpeed(0.3f);
		player1.addAction(new AnimationAction(Direction.RIGHT));
		player1.addListener(new SwipeListener() {
			@Override
			public void swiped(SwipeEvent event, Actor actor) {
				if (event.getDirection() == Direction.RIGHT) {
					incIndex();
				} else {
					decIndex();
				}

			}
		});
		active = player1;
		stage.addActor(player1);

		player2 = new PlayerImpl();
		player2.setScale(3);
		player2.setPosition(invisiblePositionX, playerPositionY);
		player2.setAnimationSpeed(0.3f);
		player2.addAction(new AnimationAction(Direction.RIGHT));
		stage.addActor(player2);
	}

	private void createLabel() {
		BitmapFont textFont = new BitmapFont();
		textFont.setScale(3);

		label = new Label("", new LabelStyle(textFont, Color.BLACK));

		updateNameLabel();

		label.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.input.getTextInput(new TextInputListener() {
					@Override
					public void input(String text) {
						playerName = text;
						updateNameLabel();
					}

					@Override
					public void canceled() {
						// nichts zu tun
					}
				}, "Neuer Name deines Pferdes", playerName);
				return true;
			}
		});
		stage.addActor(label);
	}

	@Override
	protected void doRender(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	protected void doDispose() {
		// Alles speichern, bevor das Men� verlassen wird
		SaveGameManager.getLoadedGame().setHorseName(playerName);
		SaveGameManager.saveLoadedGame();
	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doShow() {
		SaveGameManager.loadSavedGame(1); // TODO: zum testen

		playerName = SaveGameManager.getLoadedGame().getHorseName();
		if (playerName.length() == 0) {
			playerName = "Name deines Pferdes";
		}

		stage = new Stage(getViewport(), getSpriteBatch());
		InputManager.addInputProcessor(new StageGestureDetector(stage, true,
				ControlMode.HORIZONTAL));

		createBackground();
		createButtons();
		createPlayers();
		createLabel();
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
