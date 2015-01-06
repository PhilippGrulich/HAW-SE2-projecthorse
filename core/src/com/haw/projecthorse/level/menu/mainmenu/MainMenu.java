package com.haw.projecthorse.level.menu.mainmenu;

import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.savegame.SaveGameManager;

public class MainMenu extends Menu {

	private VerticalGroup table;

	private Stage stage;

	private ImageTextButton[] buttonsSpiel;
	private ImageTextButton buttonCredits;

	private Music music;

	private Player player;

	public MainMenu() {
		float moveToDuration = width / 5 / 30;

		initStage(this.getViewport(), this.getSpriteBatch());
		initTable(); // Table = Gridlayout

		addBackground();
		initButtons(); // To be called after initTable (adds itself to table)
		setupEventListeners();

		stage.addActor(table);

		player = new PlayerImpl();
		player.setPosition(0, 0.13f * height);
		// player.scaleBy(0.5F);

		player.setAnimationSpeed(0.4f);
		stage.addActor(player);

		ParallelAction toRight = Actions.parallel(Actions.moveTo(width + 50, player.getY(), moveToDuration),
				new AnimationAction(Direction.RIGHT, moveToDuration));
		ParallelAction toLeft = Actions.parallel(
				Actions.moveTo(-100 - player.getWidth(), player.getY(), moveToDuration), new AnimationAction(
						Direction.LEFT, moveToDuration));

		player.addAction(Actions.forever(Actions.sequence(toRight, toLeft)));

		AssetManager.loadMusic("mainMenu");
		AssetManager.loadSounds("worldmap");

		music = audioManager.getMusic("mainMenu", "belotti.mp3");

		if (!music.isPlaying()) {
			music.setLooping(true);
			music.play();
		}

	}

	private void addBackground() {

		EndlessBackground background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "sky"), 30);
		stage.addActor(background);

		background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "second_grass"), 0);
		stage.addActor(background);

		background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "first_grass"), 0);
		stage.addActor(background);

		background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "ground"), 0);
		stage.addActor(background);

	}

	private void initButtons() {
		buttonsSpiel = new ImageTextButton[3];
		Map<Integer, String> games = SaveGameManager.getSaveGameList();
		Iterator<Integer> gamesIterator = games.keySet().iterator();
		ImageTextButton buttonSpiel;
		String buttonText;
		int gameID = 0;

		for (int i = 0; i < 3; i++) {
			if (gamesIterator.hasNext()) {
				gameID = gamesIterator.next();
				buttonText = games.get(gameID);
			} else {
				gameID = (games.containsKey(i)) ? 2 * i : i;
				buttonText = "Spielstand " + (i + 1);
			}

			buttonSpiel = new ButtonLarge(buttonText, ButtonLarge.ButtonColor.LIGHT_BROWN);
			table.addActor(buttonSpiel);
			buttonSpiel.toFront();
			addButtonSpielListener(buttonSpiel, gameID);
			buttonsSpiel[i] = buttonSpiel;
		}

		// der Credits-Button
		buttonCredits = new ButtonLarge("Credits", ButtonLarge.ButtonColor.LIGHT_BROWN);
		table.addActor(buttonCredits);
		buttonCredits.toFront();

	}

	private void loadCredits() {
		// TODO: Implement Creditscreen

		Gdx.app.log("DEBUG", "CreditScreen not yet implemented - Todo");
	}

	private void addButtonSpielListener(ImageTextButton button, int saveGameID) {
		button.addListener(new SavegameButtonListener(saveGameID, overlay));
	}

	private void setupEventListeners() {
		buttonCredits.addListener(new ChangeListener() {
			public void changed(final ChangeEvent event, final Actor actor) {
				System.out.println("buttonCredits pressed");
				loadCredits();
			}

		});
	}

	private void initStage(final Viewport viewport, final Batch batch) {
		stage = new Stage(viewport, batch);
		InputManager.addInputProcessor(stage); // Now Stage is processing inputs
	}

	private void initTable() {

		table = new VerticalGroup();
		table.setHeight((float) (height * 0.5));
		table.setY((height / 2) - table.getHeight() / 2);
		table.setWidth(width);
		System.out.println(table.getHeight());
		System.out.println(table.getY());
	}

	@Override
	public void doRender(final float delta) {

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void doDispose() {
		stage.dispose();
	}

	@Override
	public void doShow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doHide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doResume() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}
}
