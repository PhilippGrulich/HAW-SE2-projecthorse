package com.haw.projecthorse.lootmanager;

import java.util.ArrayList;
import java.util.Date;

import com.haw.projecthorse.savegame.SaveGameManager;

public class Chest {
	private ArrayList<Loot> content = null;
	
	public Chest() {
		
	}
	
	public void addLoot(Loot loot) {
		if (content == null) {
			content = new ArrayList<Loot>();
		}
		loot.achievedDate = new Date();
		content.add(loot);
	}
	
	public void showAllLootDialog() {
		SaveGameManager.getLoadedGame().addCollectedLootList(content);
		SaveGameManager.saveLoadedGame();
		// TODO: Popup implementieren
	}
	
	public void addLootAndShowAchievment(Loot loot) {
		addLoot(loot);
		
		String message = loot.getDescription();
		if (message != null) {
			// TODO: Erfolg enzeigen
		}
	}
}
