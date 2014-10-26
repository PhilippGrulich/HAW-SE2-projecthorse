package com.haw.projecthorse.level.worldmap;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.intputmanager.InputManager;

public class WorldMapOLD extends Level {

	private Stage stage;
	final Image worldmapimage;
	final TextureAtlas worldmapatlas;
	private ImageButton imagebutton1;
	private ImageButton imagebutton2;
	private TextureAtlas wappenatlas;

	public WorldMapOLD() {
		super();

		worldmapatlas = AssetManager.load("worldmap", false, false, true);

		wappenatlas = AssetManager.load("flaggen", false, false, true);

		stage = new Stage(getViewport());
		InputManager.addInputProcessor(stage);

		worldmapimage = addBackground("erde-und-sterne");

		imagebutton1 = createImageButton("berlinflagge", 0.5f * width,
				0.13f * height);
		imagebutton2 = createImageButton("hamburgflagge", 0.1f * width,
				0.13f * height);

		// GameManagerFactory.getInstance().

		List<ImageButton> l = new ArrayList<ImageButton>();
		l.add(imagebutton1);
		l.add(imagebutton2);
		for (int i = 0; i < l.size(); i++) {
			addListener(l.get(i));
			stage.addActor(l.get(i));

		}
		// stage.addActor(imagebutton1);
		// stage.addActor(imagebutton2);

	}

	private Image addBackground(String str) {

		AtlasRegion atlasregion = worldmapatlas.findRegion(str);
		Image image = new Image(atlasregion);
		image.toBack();
		image.setY((height - image.getHeight()) / 2);
		stage.addActor(image);
		return image;

	}

	private ImageButton createImageButton(String imagename, float x, float y) {

		Drawable drawable = new TextureRegionDrawable(
				wappenatlas.findRegion(imagename));
		// new TextureRegion(
		// new Texture(Gdx.files.internal("pictures/wappen/" + imagename
		// + ".png"))));
		ImageButton buttonFlagge = new ImageButton(drawable);

		buttonFlagge.setHeight(180);
		buttonFlagge.setWidth(280);
		buttonFlagge.setX(x);
		buttonFlagge.setY(y);
		buttonFlagge.setName(imagename);
		return buttonFlagge;
	}

	private void addListener(final ImageButton imagebutton) {
		imagebutton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				imagebutton1.remove();
				imagebutton2.remove();

				String imagename = imagebutton.getName();
				if (imagename == "hamburgflagge") {
					navigateToGermany("hamburgflagge");
				} else if (imagename == "berlinflagge") {
					navigateToGermany("berlinflagge");
				} else {

				}
			}

		});

	}

	public void navigateToGermany(String imagename) {

		worldmapimage.setOrigin(0.643f * width, 0.65f * height);
		ScaleByAction scale1 = Actions.scaleBy(8f, 8f, 2f);
		final DelayAction delay = Actions.delay(1.5f);
		final Image germanyimage = addBackground("germanymap_scaled");
		if (imagename == "hamburgflagge") {
			germanyimage.setOrigin(0.463f * width, 0.65f * height);
		} else if (imagename == "berlinflagge") {
			germanyimage.setOrigin(0.859f * width, 0.541f * height);
		} else {

		}

		final ScaleByAction scale2 = Actions.scaleBy(7f, 7f, 2f);

		germanyimage.setColor(1, 1, 1, 0);

		SequenceAction sequence1 = Actions.sequence(scale1, delay,
				Actions.run(new Runnable() {
					public void run() {
						worldmapimage.addAction(Actions.alpha(0));
						germanyimage.addAction(Actions.sequence(
								Actions.fadeIn(3), delay));
						Action sequence2 = Actions.sequence(delay, scale2,
								delay, Actions.run(new Runnable() {
									public void run() {
										GameManagerFactory.getInstance()
												.navigateToLevel("Hamburg");// .navigateToMainMenu();
									}
								}));
						germanyimage.addAction(sequence2);
					}
				}));

		worldmapimage.addAction(sequence1);
	}

	@Override
	public void doRender(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
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
		/*
		 * -> sollte nicht mehr gebraucht werden
		 * worldmapatlas.dispose();
		 * wappenatlas.dispose(); 
		*/
	}

}