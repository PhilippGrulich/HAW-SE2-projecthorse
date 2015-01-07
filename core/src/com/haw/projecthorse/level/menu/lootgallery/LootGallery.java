package com.haw.projecthorse.level.menu.lootgallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.util.background.EndlessBackground;
import com.haw.projecthorse.level.util.uielements.DefaultScrollPane;
import com.haw.projecthorse.lootmanager.Lootable;
import com.haw.projecthorse.savegame.SaveGameManager;

/**
 * Die LootGallery in der alle gesammelten Loots betrachtet werden können.
 * 
 * @author Oliver
 * @version 1.0
 */

public class LootGallery extends Menu {
	private Stage stage;
	private HashMap<String, List<Lootable>> loots;
	private String curCat;
	private Label category;
	private VerticalGroup lootTable;
	private float tableWidth, tableHeight;

	/**
	 * Erzeugt den Hintergrund.
	 */
	private void createBackground() {
		EndlessBackground background = new EndlessBackground(width,
				AssetManager.getTextureRegion("menu", "sky"), 30);
		stage.addActor(background);

		background = new EndlessBackground(width,
				AssetManager.getTextureRegion("menu", "second_grass"), 0);
		stage.addActor(background);

		background = new EndlessBackground(width,
				AssetManager.getTextureRegion("menu", "first_grass"), 0);
		stage.addActor(background);

		background = new EndlessBackground(width,
				AssetManager.getTextureRegion("menu", "ground"), 0);
		stage.addActor(background);
	}

	/**
	 * Erzeugt die Buttons zum Wechseln der Kategorie.
	 * 
	 * @return Die Gruppe in der sich alle Elemente zum Kategoriewechsel
	 *         befinden.
	 */
	private Group createSwitchButton() {
		Group switchBtn = new Group();
		float switchWidth = width * 0.8f;
		switchBtn.setPosition((width - switchWidth) / 2, height - 200);

		Image uiBackground = new Image(AssetManager.getTextureRegion("ui",
				"panel_beige"));
		uiBackground.setBounds(0, 0, switchWidth, 100);
		switchBtn.addActor(uiBackground);

		category = new Label(curCat, new LabelStyle(
				AssetManager.getTextFont(FontSize.SIXTY), Color.LIGHT_GRAY));
		category.setAlignment(Align.center);
		category.setBounds(100, 0, switchWidth - 200, 100);
		switchBtn.addActor(category);

		Image left = new Image(
				AssetManager.getTextureRegion("ui", "buttonLeft"));
		left.setBounds(25, 20, 60, 60);
		left.addListener(new InputListener() {
			@Override
			public boolean touchDown(final InputEvent event, final float x,
					final float y, final int pointer, final int button) {
				curCat = getPrevCategory();
				fillLootTable();
				return true;
			}
		});
		switchBtn.addActor(left);

		Image right = new Image(AssetManager.getTextureRegion("ui",
				"buttonRight"));
		right.setBounds(switchWidth - 85, 20, 60, 60);
		right.addListener(new InputListener() {
			@Override
			public boolean touchDown(final InputEvent event, final float x,
					final float y, final int pointer, final int button) {
				curCat = getNextCategory();
				fillLootTable();
				return true;
			}
		});
		switchBtn.addActor(right);

		return switchBtn;
	}

	/**
	 * Intialisiert die Liste der Loot-Objekte.
	 */
	private void initializeLootList() {
		loots = new HashMap<String, List<Lootable>>();
		List<Lootable> allLoot = SaveGameManager.getLoadedGame()
				.getSpecifiedLoot(Lootable.class);

		for (Lootable lootable : allLoot) {
			String category = lootable.getCategory();
			if (!loots.containsKey(category)) {
				loots.put(category, new ArrayList<Lootable>());
			}

			loots.get(category).add(lootable);
		}
	}

	/**
	 * Findet die erste Kategorie.
	 * 
	 * @return die erste Kategorie
	 */
	private String getFirstCategory() {
		for (String c : loots.keySet()) {
			return c;
		}
		return null;
	}

	/**
	 * Liefert die nächste Kategorie.
	 * 
	 * @return die nächste Kategorie
	 */
	private String getNextCategory() {
		String next = null;
		boolean found = false;

		for (String c : loots.keySet()) {
			if (found) {
				next = c;
				break;
			}
			if (c.equals(curCat)) {
				found = true;
			}
		}

		return next == null ? getFirstCategory() : next;
	}

	/**
	 * Liefert die vorherige Kategorie.
	 * 
	 * @return die vorherige Kategorie
	 */
	private String getPrevCategory() {
		String prev = null;

		for (String c : loots.keySet()) {
			if (c.equals(curCat)) {
				break;
			}
			prev = c;
		}

		if (prev == null) {
			for (String c : loots.keySet()) {
				prev = c;
			}
		}

		return prev;
	}

	/**
	 * Initialisiert die UI-Elemente.
	 */
	private void initializeUiElements() {
		DefaultScrollPane tableContainer;

		tableWidth = width - 150;
		tableHeight = height - 300;
		lootTable = new VerticalGroup();
		lootTable.space(10);
		lootTable.align(Align.left + Align.top);
		lootTable.setWidth(tableWidth);

		tableContainer = new DefaultScrollPane(lootTable, tableHeight,
				tableWidth);
		tableContainer.setPosition(75, 50);
		tableContainer.toFront();

		stage.addActor(tableContainer);

		// switch button erstellen
		stage.addActor(createSwitchButton());
	}

	/**
	 * Füllt die Liste der Lootobjekte (z.B. nach dem Wechsel der Kategorie).
	 */
	private void fillLootTable() {
		lootTable.clear();
		for (Lootable l : loots.get(curCat)) {
			LootItem item = new LootItem(l, tableWidth);
			lootTable.addActor(item);
		}
		lootTable.pack();

		category.setText(curCat);
	}

	@Override
	protected void doRender(final float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	protected void doDispose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResize(final int width, final int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doShow() {
		stage = new Stage(getViewport(), getSpriteBatch());
		InputManager.addInputProcessor(stage);
		createBackground();
		initializeLootList();
		curCat = getFirstCategory();

		if (curCat != null) {
			// Yeah :D
			initializeUiElements();
			fillLootTable();
			lootTable.toFront();
		}
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
