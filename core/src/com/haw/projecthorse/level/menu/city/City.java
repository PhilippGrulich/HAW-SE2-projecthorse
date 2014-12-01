package com.haw.projecthorse.level.menu.city;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.gamemanager.navigationmanager.exception.LevelNotFoundException;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.gamemanager.navigationmanager.json.GameObject;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;
import com.haw.projecthorse.level.util.uielements.ButtonLarge.ButtonColor;

public class City extends Menu {

	private Stage stage;

	private CityObject cityObject;

	private int lastButtonY = (int)(this.height - this.height * 0.35F);
	private VerticalGroup verticalGroup = new VerticalGroup();

	private Music music;
	
	public City(){
		AssetManager.loadMusic("mainMenu");
		AssetManager.loadSounds("city");
		
		music = audioManager.getMusic("mainMenu", "belotti.mp3");
		
		if (!music.isPlaying()) {
			music.setLooping(true);
			music.play();
		}
	}
	
	@Override
	protected void doShow() {		
		
		stage = new Stage(this.getViewport(), this.getSpriteBatch());		
		
		try {			
			cityObject = GameManagerFactory.getInstance().getCityObject(getLevelID());
			addBackground(cityObject.getParameter().get("backgroundPath"));
			addGameButtons();
			createCityLabel(cityObject.getCityName());
		} catch (LevelNotFoundException e1) {
			Gdx.app.log("CITY", "Für " +getLevelID() + " konnten keine City Informationen geladen werden");
		}		
		
		InputManager.addInputProcessor(stage);

	}

	private void createCityLabel(String cityName){
		BitmapFont textFont = AssetManager.getHeadlineFont(FontSize.SECHZIG);
		Label cityLabel = new Label(cityName, new LabelStyle(
				textFont, Color.MAGENTA));
		cityLabel.setPosition(this.width * 0.05f / 2, this.height - cityLabel.getHeight());
		
		stage.addActor(cityLabel);
	}
	
	private void addBackground(String backgroundImage) {
		Image background = new Image(AssetManager.getTextureRegion("city",backgroundImage));
		
		float scaleFactor = height / background.getHeight();
		background.setHeight(background.getHeight() * scaleFactor);
		background.setWidth(background.getWidth() * scaleFactor);
		
		float widthDiff = width - background.getWidth();
		background.setPosition(widthDiff / 2, 0);
		
		stage.addActor(background);
		background.toBack();
	}

	private void addGameButtons() throws LevelNotFoundException {

		
		GameObject[] games = cityObject.getGames();
		for (GameObject game : games) {
			addGameButton(game);
		}
	}

	private void addGameButton(final GameObject gameObject) {

		ImageTextButton imgTextButton = new ButtonLarge(gameObject.getGameTitle(), ButtonColor.LIGHT_BROWN);

		imgTextButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Spiel " + gameObject.getGameTitle() + " soll gestartet werden");
				GameManagerFactory.getInstance().navigateToLevel(gameObject.getLevelID());

			}
		});
		verticalGroup.addActor(imgTextButton);

		imgTextButton.setPosition(this.width /2 - imgTextButton.getWidth() /2, lastButtonY);
		lastButtonY = (int) (lastButtonY - imgTextButton.getHeight());
		stage.addActor(imgTextButton);
		imgTextButton.toFront();
	};

	@Override
	protected void doRender(float delta) {
		stage.draw();
	}

	@Override
	protected void doDispose() {
		music.pause();
		stage.dispose();
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
