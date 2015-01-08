package com.haw.projecthorse.gamemanager.splashscreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Dies ist der SplashScreen der während des Ladens des MainMenus angezeigt
 * wird.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public class SplashScreen implements Screen {

	private Image image;
	private ImageTextButton textContent;
	private OrthographicCamera cam;
	private FitViewport viewport;
	private SpriteBatch spriteBatch;
	private Stage stage;
	private VerticalGroup table;
	private final int height = 1280;
	private final int width = 720;
	private ArrayList<String> loadingMessages = new ArrayList<String>(Arrays.asList(new String[] { "Male Pferde an",
			"Drehe den Wind auf", "Dressiere die Pferde", "Klaue etwas zum Füttern", "Male Landkarte",
			"Suche den Reisepass", "Züchte Kürbisse", "Stelle Boxen in den Weg"

	}));

	private float timeLeft;

	/**
	 * Konstruktor.
	 */
	public SplashScreen() {

		cam = createCamera();
		viewport = new FitViewport(width, height, cam);

		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);
		stage = new Stage(viewport, spriteBatch);
		image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(
				Gdx.files.internal("splashscreen/Background.png")))));
		createButton();
		initTable();

		table.addActor(textContent);

		stage.addActor(image);
		stage.addActor(table);
	}

	/**
	 * Setzt den LadeText.
	 * 
	 * @param text
	 *            LadeText
	 */
	public void setText(final String text) {
		textContent.setText(text);
	}

	/**
	 * Initialisiert die Tabelle.
	 */
	private void initTable() {

		table = new VerticalGroup();
		table.setHeight((float) (height * 0.1));
		table.setY((height / 2) - table.getHeight() / 2);
		table.setWidth(width);
	}

	/**
	 * Erzeugt den LadeButtonText.
	 */
	private void createButton() {
		Drawable drawable = new TextureRegionDrawable((new TextureRegion(new Texture(
				Gdx.files.internal("splashscreen/buttonBackground.png")))));
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/textfont/Grundschrift-Bold.ttf"));
		FreeTypeFontParameter frontPara = new FreeTypeFontParameter();
		frontPara.size = 40;

		BitmapFont b = gen.generateFont(frontPara);
		gen.dispose();
		ImageTextButtonStyle imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;
		imageButtonStyle.font = b;

		imageButtonStyle.fontColor = Color.valueOf("877E6A");

		textContent = new ImageTextButton("Lade... : ", imageButtonStyle);

	}

	/**
	 * Aktualisiert den LadeText.
	 * 
	 * @param delta
	 *            TimeDelta
	 */
	private void updateText(final float delta) {
		timeLeft = timeLeft - delta;
		if (timeLeft <= 0 && loadingMessages.size() > 0) {
			int index = new Random().nextInt(loadingMessages.size());
			String message = loadingMessages.get(index);
			loadingMessages.remove(index);
			textContent.setText("Lade... : \n" + message);
			timeLeft = (float) (Math.random() * 5);
		}

	}

	/**
	 * Erstellt eine OrthographicCamera diese wird f�r die jeweiliegen Viewports
	 * gebraucht.
	 * 
	 * @return {@link OrthographicCamera}
	 */
	private OrthographicCamera createCamera() {

		OrthographicCamera cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false); // Set to Y-Up - Coord system

		return cam;
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateText(delta);
		stage.draw();

	}

	@Override
	public void resize(final int width, final int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

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

	@Override
	public void dispose() {

	}

}
