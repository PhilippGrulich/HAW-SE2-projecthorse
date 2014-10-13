package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.color.PlayerColor;

public class SaveGameImpl implements SaveGame {
	private int ID = -1, ep = 0;
	private Horse horse = new HorseImpl();

	public SaveGameImpl() {
	}
	
	public SaveGameImpl(int ID) {
		this.ID = ID;
	}
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public int getEP() {
		return ep;
	}

	@Override
	public String getHorseName() {
		return horse.getName();
	}
	
	@Override
	public void setHorseName(String name) {
		horse.setName(name);
	}

	@Override
	public void addEP(int toAdd) {
		ep += toAdd;
	}

	@Override
	public PlayerColor getHorseColor() {
		return horse.getColor();
	}

	@Override
	public void setHorseColor(PlayerColor color) {
		horse.setColor(color);
	}

}
