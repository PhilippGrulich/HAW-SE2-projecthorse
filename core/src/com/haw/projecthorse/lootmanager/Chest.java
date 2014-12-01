package com.haw.projecthorse.lootmanager;

import java.util.ArrayList;
import java.util.Date;

import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.lootmanager.popups.LootPopup;
import com.haw.projecthorse.savegame.SaveGameManager;

public class Chest {
	private ArrayList<Lootable> content = null;
	private Overlay overlay;

	public Chest(Overlay overlay) {
		this.overlay = overlay;
	}

	public void addLoot(Lootable loot) {
		if (content == null) {
			content = new ArrayList<Lootable>();
		}
		loot.setAchievedDate(new Date());
		content.add(loot);
	}

	/**
	 * Zeig das bisher gesammelte Loot in einem Popup an.
	 */
	public void showAllLoot() {
		if (content != null) {
			overlay.showPopup(new LootPopup(content));
			InputManager.addInputProcessor(new StageGestureDetector(overlay,
					false, ControlMode.HORIZONTAL), Integer.MAX_VALUE);
		}
	}

	/**
	 * Speichert das bisher gesammelte Loot und leert die Chest.
	 */
	public void saveAllLoot() {
		if (content != null) {
			SaveGameManager.getLoadedGame().addCollectedLootList(content);
			SaveGameManager.saveLoadedGame();

			content.clear();
		}
	}

	public void addLootAndShowAchievment(Loot loot) {
		addLoot(loot);

		String message = loot.getDescription();
		if (message != null) {
			// TODO: Erfolg enzeigen
		}
	}
}
