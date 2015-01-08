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
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarButton;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarCityInfoButton;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;
import com.haw.projecthorse.level.util.uielements.ButtonLarge.ButtonColor;

/**
 * Dies ist ein Generisches City Level. Es wird immer mit den jeweiliegen City
 * Informationen Befüllt die für die gerade aktive Stadt wichtig sind.
 * 
 * @author Philipp
 * @version 1.0
 */
public class City extends Menu {

	private Stage stage;

	private CityObject cityObject;

	private int lastButtonY = (int) (this.height - this.height * 0.35F);
	private VerticalGroup verticalGroup = new VerticalGroup();

	private Music music;

	/**
	 * Konstruktor.
	 */
	public City() {
		AssetManager.loadMusic("mainMenu");
		AssetManager.loadSounds("city");

		music = audioManager.getMusic("mainMenu", "belotti.mp3");

		if (!music.isPlaying()) {
			music.setLooping(true);
			music.play();
		}

		NavbarButton button = new NavbarCityInfoButton() {

			@Override
			public void clicked() {
				overlay.showPopup(new CityPopup(cityObject));

			}
		};
		overlay.getNavBar().addButtonAt(button, 3);
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
			Gdx.app.log("CITY", "Für " + getLevelID() + " konnten keine City Informationen geladen werden");
		}

		InputManager.addInputProcessor(stage);

	}

	/**
	 * Erstellt ein neues City Label.
	 * 
	 * @param cityName
	 *            City Name
	 */
	private void createCityLabel(final String cityName) {
		BitmapFont textFont = AssetManager.getHeadlineFont(FontSize.SIXTY);
		Label cityLabel = new Label(cityName, new LabelStyle(textFont, Color.MAGENTA));
		cityLabel.setPosition(this.width / 2 - cityLabel.getWidth() / 2, this.height - cityLabel.getHeight() - 180);

		stage.addActor(cityLabel);
	}

	/**
	 * Erstellt den Hintergrund.
	 * 
	 * @param backgroundImage
	 *            Name des Hintergrund Bilds.
	 */
	private void addBackground(final String backgroundImage) {
		Image background = new Image(AssetManager.getTextureRegion("city", backgroundImage));

		float scaleFactor = height / background.getHeight();
		background.setHeight(background.getHeight() * scaleFactor);
		background.setWidth(background.getWidth() * scaleFactor);

		float widthDiff = width - background.getWidth();
		background.setPosition(widthDiff / 2, 0);

		stage.addActor(background);
		background.toBack();
	}

	/**
	 * Fügt Buttons zu dem Spiel Hinzu.
	 * 
	 * @throws LevelNotFoundException
	 *             E
	 */
	private void addGameButtons() throws LevelNotFoundException {

		GameObject[] games = cityObject.getGames();
		for (GameObject game : games) {
			addGameButton(game);
		}
	}

	/**
	 * Fügt einen neuen Button für ein Spiel hinzu.
	 * 
	 * @param gameObject
	 *            Spiel Infos.
	 */
	private void addGameButton(final GameObject gameObject) {

		ImageTextButton imgTextButton = new ButtonLarge(gameObject.getGameTitle(), ButtonColor.LIGHT_BROWN);

		imgTextButton.addListener(new ChangeListener() {
			public void changed(final ChangeEvent event, final Actor actor) {
				Gdx.app.log("DEBUG", "Spiel " + gameObject.getGameTitle() + " soll gestartet werden");
				GameManagerFactory.getInstance().navigateToLevel(gameObject.getLevelID());

			}
		});
		verticalGroup.addActor(imgTextButton);

		imgTextButton.setPosition(this.width / 2 - imgTextButton.getWidth() / 2, lastButtonY);
		lastButtonY = (int) (lastButtonY - imgTextButton.getHeight());
		stage.addActor(imgTextButton);
		imgTextButton.toFront();
	};

	@Override
	protected void doRender(final float delta) {
		stage.draw();
	}

	@Override
	protected void doDispose() {
		music.pause();
		stage.dispose();
	}

	@Override
	protected void doResize(final int width, final int height) {
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
