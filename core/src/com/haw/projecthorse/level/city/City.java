package com.haw.projecthorse.level.city;

import sun.org.mozilla.javascript.internal.ast.WithStatement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.swipehandler.StageGestureDetector;

public class City extends Level {

	private Stage stage;
	private TextureAtlas atlant;
	private SpriteBatch batcher = this.getSpriteBatch();
	private CityObject cityObject;

	private int lastButtonY = GameManagerFactory.getInstance().getSettings().getScreenHeight();
	private BitmapFont font;
	private AtlasRegion region;
	private ImageTextButtonStyle imageButtonStyle;
	private VerticalGroup verticalGroup = new VerticalGroup();

	@Override
	protected void doShow() {
		// TODO Auto-generated method stub

		atlant = AssetManager.load("hamburg", false, false, true);
	
		stage = new Stage(this.getViewport(), batcher);
		addBackground();
		font = new BitmapFont(Gdx.files.internal("pictures/selfmade/font.txt"));
		font.setScale(.45f, .45f);
		font.setColor(Color.MAGENTA);
		try {
			cityObject = GameManagerFactory.getInstance().getCityObject(getLevelID());
			addGameButtons();
		} catch (LevelNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		InputManager.addInputProcessor(stage);

	}

	private void addBackground() {
		region = atlant.findRegion("Sankt-Michaelis-Kirche_Hamburg");

		Image background = new Image(region);
		background.toBack();
		stage.addActor(background);
	}

	private void addGameButtons() throws LevelNotFoundException {
		Drawable drawable = new TextureRegionDrawable(atlant.findRegion("raw_game_teaser_icon"));

		imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;

		imageButtonStyle.font = new BitmapFont(Gdx.files.internal("pictures/selfmade/font.txt"));
		;
		imageButtonStyle.font.scale(-0.5f);
		imageButtonStyle.fontColor = Color.BLACK;

		GameObject[] games = cityObject.getGames();
		for (GameObject game : games) {
			addGameButton(game);
		}
		imageButtonStyle = null;
	}

	private void addGameButton(final GameObject gameObject) {

		ImageTextButton imgTextButton = new ImageTextButton(gameObject.getGameTitle(), imageButtonStyle);

		imgTextButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Spiel " + gameObject.getGameTitle() + " soll gestartet werden");
				GameManagerFactory.getInstance().navigateToLevel(gameObject.getLevelID());

			}
		});
		verticalGroup.addActor(imgTextButton);
		imgTextButton.setWidth(this.width*0.8F);
		imgTextButton.setHeight(this.height * 0.1F);
		
		imgTextButton.setPosition(this.width * 0.1F, lastButtonY);
		lastButtonY = (int) (lastButtonY - this.height * 0.2F);
		imgTextButton.toFront();
		stage.addActor(imgTextButton);
	};

	@Override
	protected void doRender(float delta) {
		stage.draw();
		batcher.begin();
	
		//batcher.draw(region, 0, 0, GameManagerFactory.getInstance().getSettings().getScreenWidth(), GameManagerFactory.getInstance().getSettings().getScreenHeight());
		font.draw(batcher, cityObject.getCityName(), Gdx.graphics.getWidth() / 1.8f, Gdx.graphics.getHeight() / 1.03f);
		batcher.end();
		

	}

	@Override
	protected void doDispose() {
		atlant.dispose();
		font.dispose();

	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

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
