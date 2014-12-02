package com.haw.projecthorse.level.game.puzzle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

public class PuzzlePlayer {
	private Player player;
	private Image speechBalloon;

	public PuzzlePlayer() {
		player = new PlayerImpl();
		player.setPosition(-30, ImageManager.getMyXPos() - player.getX()-30);
		player.scaleBy(0.5F);

		TextureRegion speechBalloonReg = AssetManager.getTextureRegion("puzzle", "speechballoon");
		speechBalloon=new Image(speechBalloonReg);
		speechBalloon.setWidth(300);
		speechBalloon.setHeight(200);
		speechBalloon.setPosition(player.getWidth()+50, 50);
		ImageManager.addToStage(ImageManager.getFirststage(), player);
		//ImageManager.addToStage(ImageManager.getFirststage(), speechBalloon);

	}

	public Player getPlayer() {
		return player;
	}
	
	
}
