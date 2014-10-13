package com.haw.projecthorse.level.worldmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.intputmanager.InputManager;

public class WorldMap extends Level {

	private Stage stage;
	final Image worldmapimage;
	boolean bool = false;
	final TextureAtlas worldmapatlas;

	public WorldMap() {
		super();

		worldmapatlas = AssetManager.load("worldmap", false, false, true);

		stage = new Stage(getViewport());
		InputManager.addInputProcessor(stage);

		worldmapimage = addBackground();

		final ImageButton imagebutton1 = createImageButton("berlinwappen",
				width / 2f, 0f);
		ImageButton imagebutton2 = createImageButton("hamburgwappen",
				width / 2f, height / 2f);

		addListener(imagebutton1);
		addListener(imagebutton2);

		stage.addActor(imagebutton1);
		stage.addActor(imagebutton2);

	}

	private Image addBackground() {

		AtlasRegion wolrldmapatlasregion = worldmapatlas
				.findRegion("erde-und-sterne");
		Image worldmapimage = new Image(wolrldmapatlasregion);
		worldmapimage.toBack();
		worldmapimage.setY((height - worldmapimage.getHeight()) / 2);
		stage.addActor(worldmapimage);
		return worldmapimage;

	}

	private ImageButton createImageButton(String imagename, float x, float y) {
		Drawable drawable = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("pictures/wappen/" + imagename
						+ ".png"))));
		ImageButton buttonFlagge = new ImageButton(drawable);
		buttonFlagge.setHeight(200);
		buttonFlagge.setWidth(300);
		buttonFlagge.setX(x);
		buttonFlagge.setY(y);
		return buttonFlagge;
	}

	private void addListener(final ImageButton imagebutton) {
		imagebutton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				imagebutton.setVisible(false);
				worldmapimage.setOriginX(0.643f * width);
				worldmapimage.setOriginY(0.65f * height);

				System.out.println("hier navigiere");
				GameManagerFactory.getInstance().navigateToLevel("Hamburg");//.navigateToMainMenu();

			}

		});

	}

	

	@Override
	public void doRender(float delta) {
		Gdx.gl.glClearColor(0, 1, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);// akktualisiere
		stage.draw();
	}

	@Override
	public void doResize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doShow() {
		// TODO Auto-generated method stub
		// AssetManager.load();
		// AssetManager.playSound("sounds/flap.wav");
		// AssetManager.playMusic("music/life.mp3");

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
	public void doDispose() {
		stage.dispose();
	}

}