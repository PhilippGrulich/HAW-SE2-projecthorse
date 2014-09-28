package com.haw.projecthorse.level.worldmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.Level;

public class WorldMap extends Level {

	private Stage stage;
	private Skin skin;
	private Pixmap pixmap;
	private TextButtonStyle buttonStyle;
	private TextButton stadtButton;
<<<<<<< HEAD
	private ImageButton hhFlagge;
	
	private TextureAtlas germanyatlas;
	private SpriteBatch batch;
	private Drawable drawable;
	
=======
	//private ImageButton hhFlagge;
	private TextureAtlas germanyatlas;
	private AtlasRegion germanytexture;
	private Image background;
>>>>>>> f8447a69a310cf79ff5d739d17201bf8d1efc073
	public WorldMap() {
		super(); 
		stage = new Stage(getViewport());
		Gdx.input.setInputProcessor(stage);
		germanyatlas = AssetManager.load("worldmap", false, false, true);
<<<<<<< HEAD
		TextureAtlas germanyatlas1 = AssetManager.load("hamburg", false, false, true);
	
		AtlasRegion hamburgtexture = germanyatlas1.findRegion("hamburg");
		
		drawable = new TextureRegionDrawable(hamburgtexture);
		
		hhFlagge = new ImageButton(drawable);
		skin = new Skin();
		// skin.addRegions(texture);
		pixmap = new Pixmap(1, 1, Format.RGBA8888);
=======
		germanytexture = germanyatlas.findRegion("germanymap_scaled");
		//texture.getTexture();
       // hhFlagge = new ImageButton();
		skin = new Skin();
		//skin.addRegions(texture);
		pixmap = new Pixmap(80, 80, Format.RGBA8888);
>>>>>>> f8447a69a310cf79ff5d739d17201bf8d1efc073
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		pixmap.dispose();
<<<<<<< HEAD
		table = new Table(skin);

		
=======
	
	//	hhFlage = new ImageButton();
>>>>>>> f8447a69a310cf79ff5d739d17201bf8d1efc073
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.newDrawable("white", Color.PINK);
		skin.add("default", new BitmapFont());
		buttonStyle.font = skin.getFont("default");
		buttonStyle.font.scale(2f);
		stadtButton = new TextButton("HH", buttonStyle);// erzeugen
<<<<<<< HEAD
		
		table.add(stadtButton);
		table.row();
		table.add(hhFlagge);
=======
		stadtButton.setX(0.432f * width);
		stadtButton.setY(0.7f * height );
>>>>>>> f8447a69a310cf79ff5d739d17201bf8d1efc073
		stadtButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateToLevel("hamburg");
			}
		});
		background = new Image(germanytexture);
		background.toBack();
		background.setY((height - background.getHeight())/2);
		stage.addActor(background);
		stage.addActor(stadtButton);

	}

	@Override
	public void doRender(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
<<<<<<< HEAD
		// AssetManager.showTexture("pictures/cc-0/germanymap.png", 0, 0);
		batch.begin();
		batch.draw(this.germanyatlas.findRegion("germanymap"), 0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
=======
		stage.act(delta);
>>>>>>> f8447a69a310cf79ff5d739d17201bf8d1efc073
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
		skin.dispose();
	}

}
