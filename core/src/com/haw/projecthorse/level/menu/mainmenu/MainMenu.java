package com.haw.projecthorse.level.menu.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.player.ChangeDirectionAction;
import com.haw.projecthorse.player.Direction;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

public class MainMenu extends Menu {

	private VerticalGroup table;

	private Stage stage;

	private ImageTextButton buttonCredits;
	private ImageTextButton buttonSpiel1;
	private ImageTextButton buttonSpiel2;
	private ImageTextButton buttonSpiel3;

	private Player player;

	//private TextureAtlas atlas;

	public MainMenu() {
		float moveToDuration = width / 5 / 30;

		initStage(this.getViewport(), this.getSpriteBatch());
		initTable(); // Table = Gridlayout

		addBackground();
		initButtons(); // To be called after initTable (adds itself to table)
		setupEventListeners();

		stage.addActor(table);

		player = new PlayerImpl();
		player.setPosition(0, 0.14f * height);
		player.scaleBy(0.5F);

		player.setAnimation(Direction.RIGHT, 0.4f);
		stage.addActor(player);

		player.addAction(Actions.forever(Actions.sequence(Actions.moveTo(width + 50, player.getY(), moveToDuration), new ChangeDirectionAction(Direction.LEFT),
				Actions.moveTo(-100 - player.getWidth(), player.getY(), moveToDuration), new ChangeDirectionAction(Direction.RIGHT))));
	}

	private void addBackground() {
		
		EndlessBackground background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "sky"), 30);
		stage.addActor(background);
		
		background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "second_grass"), 0);
		stage.addActor(background);

		background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "first_grass"), 0);
		stage.addActor(background);

		background = new EndlessBackground(width, AssetManager.getTextureRegion("menu", "ground"), 0);
		stage.addActor(background);
		

	}

	private ImageTextButtonStyle createButtonImageStyle() {
		Drawable drawable = new TextureRegionDrawable(AssetManager.getTextureRegion("menu", "buttonBackground"));

		ImageTextButtonStyle imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
		imageButtonStyle.down = drawable;
		imageButtonStyle.up = drawable;
		imageButtonStyle.font = AssetManager.getTextFont(FontSize.VIERZIG);		
		imageButtonStyle.fontColor = Color.GRAY;
		
		return imageButtonStyle;

	}

	private void initButtons() {
		
		ImageTextButtonStyle style = createButtonImageStyle();
		
		buttonSpiel1 = new ImageTextButton("Spielstand 1", style);
		buttonSpiel1.setRound(true);
		buttonSpiel2 = new ImageTextButton("Spielstand 2", style);
		buttonSpiel3 = new ImageTextButton(/* "Spielstand 3" */"Player Menu", style);
		buttonCredits = new ImageTextButton("Credits", style);

		table.addActor(buttonSpiel1);
		table.addActor(buttonSpiel2);
		table.addActor(buttonSpiel3);
		table.addActor(buttonCredits);
		
		buttonSpiel1.toFront();
		buttonSpiel2.toFront();
		buttonSpiel3.toFront();
		buttonCredits.toFront();

	}

	private void loadCredits() {
		// TODO: Implement Creditscreen

		Gdx.app.log("DEBUG", "CreditScreen not yet implemented - Todo");
	}

	private void setupEventListeners() {
		buttonSpiel1.addListener(new SavegameButtonListener(1));
		buttonSpiel2.addListener(new SavegameButtonListener(2));
		// buttonSpiel3.addListener(new SavegameButtonListener(3));
		
		buttonSpiel3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameManagerFactory.getInstance().navigateToLevel("playerMenu");
			}
		});
		buttonCredits.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("buttonCredits pressed");
				loadCredits();
			}

		});
	}

	private void initStage(Viewport viewport, Batch batch) {
		stage = new Stage(viewport, batch);
		InputManager.addInputProcessor(stage); // Now Stage is processing inputs
	}

	private void initTable() {
		
		table = new VerticalGroup();
		table.setHeight((float) (height*0.5));
		table.setY((height/2)-table.getHeight()/2);
		table.setWidth(width);
		System.out.println(table.getHeight());
		System.out.println(table.getY());
	}

	@Override
	public void doRender(float delta) {

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void doDispose() {
		stage.dispose();
	}

	@Override
	public void doShow() {
		// TODO Auto-generated method stub

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
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}
}
