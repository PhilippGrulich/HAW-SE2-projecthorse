package com.haw.projecthorse.level.playermenu;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.haw.projecthorse.level.EndlessBackground;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.color.ColorManager;
import com.haw.projecthorse.player.color.PlayerColor;
import com.haw.projecthorse.savegame.SaveGameManager;
import com.haw.projecthorse.swipehandler.ControlMode;
import com.haw.projecthorse.swipehandler.StageGestureDetector;
import com.haw.projecthorse.swipehandler.SwipeListener;

public class PlayerMenu extends Level {
	private Stage stage;
	private Actor background;
	private Image next, prev;
	private Player player1, player2, active;
	private int index = 0;
	private float playerPositionX, playerPositionY = 120, invisiblePositionX;
	private Label label;
	private String playerName;

	private static List<PlayerColor> colors = ColorManager.getColorManager()
			.getPossibleColors();
	private static final int MAX = colors.size(), DURATION = 2;

	private void updateNameLabel() {
		SaveGameManager.getLoadedGame().setHorseName(playerName);
		SaveGameManager.saveLoadedGame(); // TODO: sollte in eine "am Ende"
											// Methdode ausgelagert werden

		label.setText(playerName);
		label.setWidth(label.getTextBounds().width);
		label.setPosition((width - label.getWidth()) / 2, height - 200);
	}

	private void incIndex() {
		index = ++index % MAX;
		updatePlayer(true);
	}

	private void decIndex() {
		if (--index < 0)
			index = MAX - 1;
		updatePlayer(false);
	}

	private void updatePlayer(boolean right) {
		SaveGameManager.getLoadedGame().setHorseColor(colors.get(index));
		SaveGameManager.saveLoadedGame(); // TODO: sollte in eine "am Ende"
											// Methdode ausgelagert werden

		Player inActive;

		if (active == player1) {
			inActive = player2;
		} else {
			inActive = player1;
		}

		if (!right) {
			inActive.addAction(new ChangeDirectionAction(Direction.LEFT));
			active.addAction(new ChangeDirectionAction(Direction.LEFT));
		}

		inActive.setPosition((right ? invisiblePositionX : width
				- invisiblePositionX), playerPositionY);
		inActive.setPlayerColor(colors.get(index));
		inActive.setAnimationSpeed(0.3f);
		inActive.addAction(Actions.sequence(
				Actions.moveTo(playerPositionX, playerPositionY, DURATION),
				new ChangeDirectionAction(Direction.RIGHT)));

		active.setAnimationSpeed(0.6f);
		active.addAction(Actions.sequence(
				Actions.moveBy(width * (right ? 1.5f : -1.5f), 0, DURATION),
				new ChangeDirectionAction(Direction.RIGHT)));

		active = inActive;
	}

	@Override
	protected void doRender(float delta) {
		stage.act(delta);
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
		SaveGameManager.loadSavedGame(1); // TODO: zum testen

		playerName = SaveGameManager.getLoadedGame().getHorseName();
		if (playerName.length() == 0) {
			playerName = "Name deines Pferdes";
		}

		PlayerColor c = SaveGameManager.getLoadedGame().getHorseColor();
		for (int i = colors.size() - 1; i >= 0; i--) {
			if (colors.get(i).equals(c)) {
				index = i;
				break;
			}
		}

		stage = new Stage(getViewport(), getSpriteBatch());
		InputManager.addInputProcessor(new StageGestureDetector(stage, true,
				ControlMode.HORIZONTAL));

		// background = new Image(AssetManager.load("menu", false, false, true)
		// .findRegion("Background"));
		TextureAtlas atlas = AssetManager.load("menu", false, false, true);

		background = new EndlessBackground(width, atlas.findRegion("sky"), 30);
		background.toBack();
		stage.addActor(background);

		background = new EndlessBackground(width,
				atlas.findRegion("second_grass"), 11);
		background.toBack();
		stage.addActor(background);

		background = new EndlessBackground(width,
				atlas.findRegion("first_grass"), 8);
		background.toBack();
		stage.addActor(background);

		background = new EndlessBackground(width, atlas.findRegion("ground"), 4);
		background.toBack();
		stage.addActor(background);

		atlas = AssetManager.load("selfmade", false, false, true);
		prev = new Image(atlas.findRegion("button_prev"));
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

		next = new Image(atlas.findRegion("button_next"));
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

		player1 = new PlayerImpl(colors.get(index));
		player1.setScale(3);

		playerPositionX = (width - 3 * player1.getWidth()) / 2;
		invisiblePositionX = player1.getWidth() * -5;

		player1.setPosition(playerPositionX, playerPositionY);
		player1.setAnimationSpeed(0.3f);
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
		stage.addActor(player2);

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
