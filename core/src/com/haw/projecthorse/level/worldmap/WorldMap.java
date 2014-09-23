package com.haw.projecthorse.level.worldmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.Level;

public class WorldMap extends Level {

	private Table table;
	private Stage stage;
	private Skin skin;
	private Pixmap pixmap;
	private TextButtonStyle buttonStyle;
	private TextButton stadtButton;
	private ImageButton hhFlagge;
	private TextureAtlas germanyatlas;
	private AtlasRegion germanytexture;
	private SpriteBatch batch;
	public WorldMap() {
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		germanyatlas = AssetManager.load("worldmap", false, false, true);
		 germanytexture = germanyatlas.findRegion("germanymap");
		//texture.getTexture();
       // hhFlagge = new ImageButton();
		skin = new Skin();
	//	skin.addRegions(texture);
		pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		pixmap.dispose();
		table = new Table(skin);

	//	hhFlage = new ImageButton();
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.newDrawable("white", Color.PINK);
		skin.add("default", new BitmapFont());
		buttonStyle.font = skin.getFont("default");
		stadtButton = new TextButton("HH", buttonStyle);// erzeugen
		table.add(stadtButton);
		stadtButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateToLevel("Hamburg");

			}
		});
		table.setBounds(0, 0, 590, 750);// position des buttons ändern()
		// table.debug();
		stage.addActor(table);

	}

	@Override
	public void doRender(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//AssetManager.showTexture("pictures/cc-0/germanymap.png", 0, 0);
		batch.begin();
		batch.draw(this.germanyatlas.findRegion("germanymap"), 0, 0, GameManagerFactory.getInstance().getSettings().getScreenWidth(), GameManagerFactory.getInstance().getSettings().getScreenHeight());
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage); // show debug lines

	}

	@Override
	public void doResize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doShow() {
		// TODO Auto-generated method stub
	//	AssetManager.load();
		//AssetManager.playSound("sounds/flap.wav");
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
