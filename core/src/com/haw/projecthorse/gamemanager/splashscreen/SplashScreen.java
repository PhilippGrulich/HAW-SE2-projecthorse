package com.haw.projecthorse.gamemanager.splashscreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class SplashScreen implements Screen {

	private Image image;
	private ImageTextButton textContent;
	private OrthographicCamera cam;
	private FitViewport viewport;
	private SpriteBatch spriteBatch;
	private Stage stage;
	private VerticalGroup table;
	private static final int height = 1280;
	private static final int width = 720;
	private ArrayList<String> loadingMessages = new ArrayList<String>(Arrays.asList(new String[] { "Male Pferde an", "Drehe den Wind auf", "Dressiere die Pferde", "Klaue etwas zum Füttern",
			"Male Landkarte", "Suche den Reisepass", "Züchte Kürbisse", "Stelle Boxen in den Weg"

	}));

	private float timeLeft;

	public SplashScreen() {
		
		cam = createCamera();
		viewport = new FitViewport(width, height, cam);

		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);
		stage = new Stage(viewport, spriteBatch);
		image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("splashscreen/Background.png")))));
		CreateButton();
		initTable();

		table.addActor(textContent);

		stage.addActor(image);
		stage.addActor(table);
	}

	public void setText(String text) {
		textContent.setText(text);
	}

	private void initTable() {

		table = new VerticalGroup();
		table.setHeight((float) (height * 0.1));
		table.setY((height / 2) - table.getHeight() / 2);
		table.setWidth(width);
		System.out.println(table.getHeight());
		System.out.println(table.getY());
	}

	private void CreateButton() {
		Drawable drawable = new TextureRegionDrawable((new TextureRegion(new Texture(Gdx.files.internal("splashscreen/buttonBackground.png")))));
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/textfont/Grundschrift-Bold.ttf"));
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

	private void updateText(float delta) {
		timeLeft = timeLeft - delta;
		if (timeLeft <= 0&& loadingMessages.size()>0) {
			int index = new Random().nextInt(loadingMessages.size());
			String message = loadingMessages.get(index);
			loadingMessages.remove(index);
			textContent.setText("Lade... : \n" + message);
			timeLeft = (float) (Math.random()*5);
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
	public void render(float delta) {
		updateText(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
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
