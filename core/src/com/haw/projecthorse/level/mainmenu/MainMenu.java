package com.haw.projecthorse.level.mainmenu;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * @author Lars MainMenu. Shown when game starts Using a libgdx.stage and tables
 *         within to create an ui-layout
 * 
 */

public class MainMenu extends Level {

	private VerticalGroup table;// = new Table();

	private Stage stage;

	private TextButtonStyle buttonStyle; // Defines style how buttons appear

	private Texture upTexture; // Loaded into upRegion
	private Texture downTexture;
	private TextureRegion upRegion; // Aussehen des buttons wenn nicht gedrückt;
	private TextureRegion downRegion; // Aussehen des buttons wenn nicht
										// gedrückt;

	private AtlasRegion backgroundTexture;
	private Image background;

	private BitmapFont buttonFont = new BitmapFont(); // Standard 15pt Arial
														// Font. (inside
														// libgdx.jar file)

	private TextButton buttonCredits;
	private TextButton buttonSpiel1;
	private TextButton buttonSpiel2;
	private TextButton buttonSpiel3;
	private boolean playerMoveRight = true;

	private Player player;

	public MainMenu() {
		float moveToDuration = Gdx.graphics.getWidth() / 5 / 30;

		initStage(this.getViewport(), this.getSpriteBatch());
		initTable(); // Table = Gridlayout

		addBackground();
		initButtons(); // To be called after initTable (adds itself to table)
		setupEventListeners();

		stage.addActor(table);

		player = new PlayerImpl();
		player.setPosition(0, 180);
		player.scaleBy(0.5F);

		player.setAnimation(Direction.RIGHT, 0.4f);
		stage.addActor(player);

		player.addAction(Actions.forever(Actions.sequence(Actions.moveTo(
				Gdx.graphics.getWidth() + 50, player.getY(), moveToDuration),
				new ChangeDirectionAction(Direction.LEFT), Actions.moveTo(-100
						- player.getWidth(), player.getY(), moveToDuration),
				new ChangeDirectionAction(Direction.RIGHT))));
	}

	private void addBackground() {
		// Pixmap pixel = new Pixmap(this.width, this.height, Format.RGBA8888);
		// // Create
		// // a
		// pixel.setColor(Color.LIGHT_GRAY);
		// pixel.fill();
		// backgroundTexture = new Texture(pixel, Format.RGBA8888, true);
		// pixel.dispose(); // No longer needed

		TextureAtlas atlas = AssetManager.load("menu", false, false, true);
		backgroundTexture = atlas.findRegion("Background");
		background = new Image(backgroundTexture);
		background.toBack();
		stage.addActor(background);

	}

	private void initButtons() {
		// Setup Style:

		// 1. Load & Set gfx
		Pixmap pixel = new Pixmap(350, 128, Format.RGBA8888); // Create a
																// temp-pixmap
		// to use as a
		// background
		// texture
		pixel.setColor(Color.GRAY);
		pixel.fill();
		upTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.setColor(Color.CYAN);
		pixel.fill();
		downTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); // No longer needed

		upRegion = new TextureRegion(upTexture, 350, 128);
		downRegion = new TextureRegion(downTexture, 350, 128);

		buttonStyle = new TextButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(upRegion);
		buttonStyle.down = new TextureRegionDrawable(downRegion);

		buttonFont.setScale(3);
		buttonStyle.font = buttonFont;

		buttonSpiel1 = new TextButton("Spielstand 1", buttonStyle);
		buttonSpiel1.setRound(true);
		buttonSpiel2 = new TextButton("Spielstand 2", buttonStyle);
		buttonSpiel3 = new TextButton(/* "Spielstand 3" */"Swipe Test",
				buttonStyle);
		buttonCredits = new TextButton("Credits", buttonStyle);

		buttonSpiel1.toFront();
		buttonSpiel2.toFront();
		buttonSpiel3.toFront();
		buttonCredits.toFront();

		table.addActor(buttonSpiel1);
		table.addActor(buttonSpiel2);
		table.addActor(buttonSpiel3);
		table.addActor(buttonCredits);

	}

	private void loadCredits() {
		// TODO: Implement Creditscreen
		System.out.println("CreditScreen not yet implemented - Todo");
	}

	private void setupEventListeners() {
		buttonSpiel1.addListener(new SavegameButtonListener(1));
		buttonSpiel2.addListener(new SavegameButtonListener(2));
		// buttonSpiel3.addListener(new SavegameButtonListener(3));
		buttonSpiel3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance()
						.navigateToLevel("movementTest");
			}
		});
		buttonCredits.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("buttonCredits pressed");
				loadCredits();
			}

		});
	}

	private void initStage(Viewport viewport, Batch batch) {
		stage = new Stage(viewport, batch);
		Gdx.input.setInputProcessor(stage); // Now Stage is processing inputs
	}

	private void initTable() {
		table = new VerticalGroup();

		table.setPosition(0, -300);

		// table.debug(); // Show debug lines
		table.setFillParent(true);
	}

	@Override
	public void doRender(float delta) {
		/*
		 * if(playerMoveRight){ if(player.getX()> Gdx.graphics.getWidth()){
		 * player.setAnimation(Direction.LEFT, 0.3f); playerMoveRight = false; }
		 * else player.setPosition(player.getX()+5, player.getY()); }else{
		 * if(player.getX()<-100-player.getWidth()){
		 * player.setAnimation(Direction.RIGHT, 0.3f); playerMoveRight = true; }
		 * else player.setPosition(player.getX()-5, player.getY()); }
		 */

		Gdx.gl.glClearColor(1, 1, 1, 1); // Hintergrund malen - einfarbig,
											// langweilig
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage); // show debug lines

	}

	@Override
	public void doDispose() {
		stage.dispose();
		backgroundTexture.getTexture().dispose();
		;
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
