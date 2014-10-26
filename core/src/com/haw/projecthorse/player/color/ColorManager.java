package com.haw.projecthorse.player.color;

import java.util.ArrayList;
import java.util.List;

import com.haw.projecthorse.savegame.SaveGameManager;

public class ColorManager {
	private static ColorManager instance = null;

	public static ColorManager getColorManager() {
		if (instance == null) {
			instance = new ColorManager();
		}

		return instance;
	}

	private ColorManager() {
		// TODO: load enabled colors from savegame settings
	}
	
	public List<PlayerColor> getPossibleColors() {
//		return Arrays.asList(PlayerColor.DEFINED_COLORS);
		return SaveGameManager.getLoadedGame().getSpecifiedLoot(PlayerColor.class);
	}
	
	public List<PlayerColor> getEnabledColors() {
		List<PlayerColor> enabled = new ArrayList<PlayerColor>();
		
		for (PlayerColor c : PlayerColor.DEFINED_COLORS) {
			if (c.enabled) {
				enabled.add(c);
			}
		}
		
		return enabled;
	}

}
