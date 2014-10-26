package com.haw.projecthorse.level.menu.city;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
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

public class City extends Level {

	private Stage stage;

	private SpriteBatch batcher = this.getSpriteBatch();
	private CityObject cityObject;
	Skin skin = new Skin();

	private int lastButtonY = (int)(this.height - this.height * 0.35F);
	private TextureRegion region;
	private ImageTextButtonStyle imageButtonStyle;
	private VerticalGroup verticalGroup = new VerticalGroup();

	@Override
	protected void doShow() {		
		
		stage = new Stage(this.getViewport(), batcher);		
		
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
		BitmapFont font = new BitmapFont(Gdx.files.internal("pictures/selfmade/font.txt"));
		font.setScale(1f, 1f);
		font.setColor(Color.MAGENTA);
		LabelStyle labelStyle = new LabelStyle(font,Color.MAGENTA);
		Label cityLabel = new Label(cityName, labelStyle);
		cityLabel.setPosition(this.width / 1.6f, this.height * 0.9f);
		
		stage.addActor(cityLabel);
	}
	private void addBackground(String backgroundImage) {
		region = AssetManager.getTextureRegion("city",backgroundImage);

		Image background = new Image(region);
		background.toBack();
		stage.addActor(background);
	}

	private void addGameButtons() throws LevelNotFoundException {
		
		Drawable drawable = new TextureRegionDrawable(AssetManager.getTextureRegion("city","Button0"));
		

		imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;

		imageButtonStyle.font = new BitmapFont(Gdx.files.internal("pictures/fontButton/font.fnt"));
		;
		imageButtonStyle.font.scale(-0.5f);
		imageButtonStyle.fontColor = Color.WHITE;

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
		imgTextButton.setWidth(this.width*0.5F);
		imgTextButton.setHeight(this.height * 0.1F);
		
		imgTextButton.setPosition(this.width * 0.25F, lastButtonY);
		lastButtonY = (int) (lastButtonY - this.height * 0.2F);
		imgTextButton.toFront();
		stage.addActor(imgTextButton);
	};

	@Override
	protected void doRender(float delta) {
		stage.draw();
		
	
		
	}

	@Override
	protected void doDispose() {
		stage.dispose();
		// atlant.dispose(); -> sollte nicht mehr gebraucht werden
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
