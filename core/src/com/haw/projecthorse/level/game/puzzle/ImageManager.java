package com.haw.projecthorse.level.game.puzzle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.Level;

public class ImageManager extends Level {

	private static Stage stage;

	private int index;

	private List<Image> imagelist;

	public ImageManager() {
		super();

		stage = new Stage(getViewport());
		InputManager.addInputProcessor(stage);

		imagelist = new ArrayList<Image>();

		index = 0;
		fillImagelist();
		createArrowButtons();
		//addToStage(String buttonname);

	}
	/**
	 * speichert alle Bilder aus asset/pictures/puzzle in die imagelist
	 */

	private void fillImagelist() {
		imagelist.add(new Image(AssetManager
				.getTextureRegion("puzzle", "horse")));
		imagelist.add(new Image(AssetManager.getTextureRegion("puzzle",
				"horse2")));
		imagelist.add(new Image(AssetManager.getTextureRegion("puzzle",
				"horse3")));
	}

	private void createArrowButtons() {
		ImageButton button_next = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("selfmade", "button_next")));
		ImageButton button_prev = new ImageButton(new TextureRegionDrawable(
				AssetManager.getTextureRegion("selfmade", "button_prev")));
		button_next.setHeight(100);
		button_next.setWidth(100);
		button_prev.setHeight(100);
		button_prev.setWidth(100);
		button_next.setPosition(width - button_next.getWidth(), height / 2);
		button_prev.setPosition(0, height / 2);
		button_next.setName("next");
		button_prev.setName("prev");
		addListener(button_prev);
		addListener(button_next);
		stage.addActor(button_next);
		stage.addActor(button_prev);
	}
	/**
	 * fügt alle Puzzlebilder an der Position(90,22) mit der Größe(540,960) in die Stage, keine Buttons
	 * @param buttonname
	 */

	private void addToStage(String buttonname) {
		int x = (width - Puzzle.getMyWidth()) / 2; // 90
		int y = (height - Puzzle.getMyHeight()) / 3
				+ (height - Puzzle.getMyHeight()) / 2; // 266

		for (Image im : imagelist) {

			im.setWidth(540);
			im.setHeight(960);
			im.setPosition(90, 266);
			
			stage.addActor(im);

		}

	}
/**
 * Listener für pre-und next-Button, damit man die einzelne Bilder aussuchen kann
 * @param button
 */
	private void addListener(final ImageButton button) {

		button.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				addToStage(button.getName());

			}
		});
	}

	@Override
	protected void doRender(float delta) {
		stage.act();
		stage.draw();
	}

	@Override
	protected void doDispose() {
		stage.dispose();

	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doShow() {
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
