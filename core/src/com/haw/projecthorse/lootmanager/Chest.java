package com.haw.projecthorse.lootmanager;

import java.util.Date;
import java.util.HashSet;

import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.swipehandler.ControlMode;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.lootmanager.popups.AchievmentPopup;
import com.haw.projecthorse.lootmanager.popups.LootPopup;
import com.haw.projecthorse.savegame.SaveGameManager;

/**
 * Truhe, die die gesammlten Loots für ein Spiel enthält und Funktionen zum
 * Anzeigen und Speichern des Loots anbietet.
 * 
 * @author Oliver
 * @version 1.0
 */

public class Chest {
	private HashSet<Lootable> content = null;
	private Overlay overlay;

	/**
	 * Konstruktor.
	 * 
	 * @param overlay
	 *            Das Overlay, auf dem die Popups angezeigt werden sollen.
	 */
	public Chest(final Overlay overlay) {
		this.overlay = overlay;
	}

	/**
	 * Fügt der Truhe ein Loot hinzu.
	 * 
	 * @param loot
	 *            Das hinzuzufügende Loot.
	 */
	public void addLoot(final Lootable loot) {
		if (content == null) {
			content = new HashSet<Lootable>();
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

	/**
	 * Fügt der Truhe ein Loot hinzu und zeigt ein kleines,
	 * selbstverschwindendes Popup dazu an.
	 * 
	 * @param loot
	 *            Das hinzuzufügende Loot.
	 */
	public void addLootAndShowAchievment(final Loot loot) {
		addLoot(loot);

		String name = loot.getName();
		if (name != null) {
			overlay.addActor(new AchievmentPopup(loot.getName(), loot
					.getLootImage().getDrawable(), overlay.getWidth(), overlay
					.getHeight()));
		}
	}
}
