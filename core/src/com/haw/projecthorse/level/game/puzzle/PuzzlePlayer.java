package com.haw.projecthorse.level.game.puzzle;

import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

public class PuzzlePlayer {
	private Player player;

	public PuzzlePlayer() {
		player = new PlayerImpl();
		player.setPosition(0, 0.14f * ImageManager.getMyXPos() - player.getX());
		player.scaleBy(0.5F);

		ImageManager.addToStage(ImageManager.getFirststage(), player);

	}
}
