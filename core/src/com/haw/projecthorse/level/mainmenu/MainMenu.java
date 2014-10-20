package com.haw.projecthorse.level.mainmenu;

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
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.EndlessBackground;
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
	private TextureRegion upRegion; // Aussehen des buttons wenn nicht gedr�ckt;
	private TextureRegion downRegion; // Aussehen des buttons wenn nicht
										// gedr�ckt;

	private AtlasRegion backgroundTexture;
	private Image background;

	private BitmapFont buttonFont = new BitmapFont(); // Standard 15pt Arial
														// Font. (inside
														// libgdx.jar file)

	private TextButton buttonCredits;
	private TextButton buttonSpiel1;
	private TextButton buttonSpiel2;
	private TextButton buttonSpiel3;

	private Player player;

	private TextureAtlas atlas;

	public MainMenu() {
		float moveToDuration = width / 5 / 30;

		initStage(this.getViewport(), this.getSpriteBatch());
		initTable(); // Table = Gridlayout

		addBackground();
		initButtons(); // To be called after initTable (adds itself to table)
		setupEventListeners();

		stage.addActor(table);

		player = new PlayerImpl();
		player.setPosition(0, 0.14f * height);
		player.scaleBy(0.5F);

		player.setAnimation(Direction.RIGHT, 0.4f);
		stage.addActor(player);

		player.addAction(Actions.forever(Actions.sequence(Actions.moveTo(
				width + 50, player.getY(), moveToDuration),
				new ChangeDirectionAction(Direction.LEFT), Actions.moveTo(-100
						- player.getWidth(), player.getY(), moveToDuration),
				new ChangeDirectionAction(Direction.RIGHT))));
	}

	private void addBackground() {
//	
		 atlas = AssetManager.load("menu", false, false, true);

		EndlessBackground background = new EndlessBackground(width, atlas.findRegion("sky"), 30);
		background.toBack();
		stage.addActor(background);

		background = new EndlessBackground(width,
				atlas.findRegion("second_grass"), 0);
		background.toBack();
		stage.addActor(background);

		background = new EndlessBackground(width,
				atlas.findRegion("first_grass"), 0);
		background.toBack();
		stage.addActor(background);
		

		background = new EndlessBackground(width, atlas.findRegion("ground"), 0);
		background.toBack();
		
		stage.addActor(background);
		
//		background = new Image(backgroundTexture);
//		background.toBack();
	

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
		buttonSpiel3 = new TextButton(/* "Spielstand 3" */"Player Menu",
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
						.navigateToLevel("playerMenu");
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
		InputManager.addInputProcessor(stage); // Now Stage is processing inputs
	}

	private void initTable() {
		table = new VerticalGroup();

		table.setPosition(0, -300);

		// table.debug(); // Show debug lines
		table.setFillParent(true);
	}

	@Override
	public void doRender(float delta) {
	
		Gdx.gl.glClearColor(1, 1, 1, 1); // Hintergrund malen - einfarbig,
											// langweilig
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage); // show debug lines

	}

	@Override
	public void doDispose() {
		stage.dispose();
		
		atlas.dispose();
		
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
