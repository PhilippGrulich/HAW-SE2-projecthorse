package com.haw.projecthorse.savegame.json;

import com.haw.projecthorse.player.color.PlayerColor;

public class HorseImpl implements Horse {
	private String name = "";
	private PlayerColor color = PlayerColor.BLACK;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public PlayerColor getColor() {
		return color;
	}

	@Override
	public void setColor(PlayerColor color) {
		this.color = color;
	}
}
