package com.haw.projecthorse.level.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.settings.SettingsImpl;
import com.haw.projecthorse.level.HorseScreen;

/**
 * @author Lars
 * MainMenu. Shown when game starts
 * Using a libgdx.stage and tables within to create an ui-layout
 * 
 */

public class MainMenu implements HorseScreen {

	private Table table;// = new Table();

	private Stage stage;

	private TextButtonStyle buttonStyle; // Defines style how buttons appear

	private Texture upTexture; // Loaded into upRegion
	private Texture downTexture;
	private TextureRegion upRegion; // Aussehen des buttons wenn nicht gedrückt;
	private TextureRegion downRegion; // Aussehen des buttons wenn nicht
										// gedrückt;

	private BitmapFont buttonFont = new BitmapFont(); // Standard 15pt Arial
														// Font. (inside
														// libgdx.jar file)

	private TextButton buttonCredits;
	private TextButton buttonSpiel1;
	private TextButton buttonSpiel2;
	private TextButton buttonSpiel3;

	private Viewport viewport;
	private OrthographicCamera cam;

	private Batch batch;

	private int height = SettingsImpl.getInstance().getScreenHeight();
	private int width = SettingsImpl.getInstance().getScreenWidth();
	
	public MainMenu() {
		

		
		
		initCameraAndViewport();
		initBatch();
		initStage(viewport, batch);
		initTable(); // Table = Gridlayout
		initButtons(); // To be called after initTable (adds itself to table)
		setupEventListeners();

		stage.addActor(table);

	}

	private void initCameraAndViewport() {
		cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false); // Set to Y-Up - Coord system
		viewport = new FitViewport(width, height, cam);

	}

	private void initBatch() {
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
	}

	private void initButtons() {
		// Setup Style:

		// 1. Load & Set gfx
		Pixmap pixel = new Pixmap(128, 64, Format.RGBA8888); // Create a pixmap
																// to use as a
																// background
																// texture
		pixel.setColor(Color.BLUE);
		pixel.fill();
		upTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.setColor(Color.CYAN);
		pixel.fill();
		downTexture = new Texture(pixel, Format.RGBA8888, true);
		pixel.dispose(); //No longer needed
		
		upRegion = new TextureRegion(upTexture, 128, 64);
		downRegion = new TextureRegion(downTexture, 128, 64);

		buttonStyle = new TextButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(upRegion);
		buttonStyle.down = new TextureRegionDrawable(downRegion);

		buttonStyle.font = buttonFont;

		buttonSpiel1 = new TextButton("Spielstand 1", buttonStyle);
		buttonSpiel2 = new TextButton("Spielstand 2", buttonStyle);
		buttonSpiel3 = new TextButton("Spielstand 3", buttonStyle);
		buttonCredits = new TextButton("Credits", buttonStyle);

//		actor.addListener(new ChangeListener() {
//		    public void changed (ChangeEvent event, Actor actor) {
//		        System.out.println("Changed!");
//		    }
//		});
		
		table.add(buttonSpiel1);
		table.add(buttonSpiel2);
		table.add(buttonSpiel3);
		table.add(buttonCredits);

	}
	
	private void loadSavegame(int id){
		//TODO add loading method
		System.out.println("Loading Game " + id +" - loadSavegame not yet implemented");
		
	}

	private void loadCredits(){
		//TODO: Implement Creditscreen
		System.out.println("CreditScreen not yet implemented - Todo");
	}
	
	private void setupEventListeners(){
		buttonSpiel1.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateToWorldMap();
				System.out.println("buttonSpiel1 pressed");
				
				loadSavegame(1);
			}
			
			
		});
		buttonSpiel2.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("buttonSpiel2 pressed");
				loadSavegame(2);
				GameManagerFactory.getInstance().navigateToLevel("4");
			}
			
			
		});
		buttonSpiel3.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("buttonSpiel3 pressed");
				loadSavegame(3);
				GameManagerFactory.getInstance().navigateToWorldMap();
			}
			
			
		});
		buttonCredits.addListener(new ChangeListener(){
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
		table = new Table();
		table.debug(); // Show debug lines
		table.setFillParent(true);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render(float delta) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage); // show debug lines
	}

	@Override
	public void dispose() {
		stage.dispose();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
}
