package com.haw.projecthorse.level.game.puzzle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

public class PuzzlePlayer {
	private Player player;
	private static Image speechBalloon;
	private static Label label;

	public PuzzlePlayer() {
		player = new PlayerImpl();
		player.setPosition(-30, PuzzleManager.getMyXPos()-player.getHeight()*0.65f);
		player.scaleBy(0.5F);
		addSpeechBalloon();
		addTextLabel();
		setActorSpeech("Wähle ein \nBild aus");

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), player);

	}

	private void addSpeechBalloon() {
		TextureRegion speechBalloonReg = AssetManager.getTextureRegion(
				"puzzle", "speechballoon");

		speechBalloon = new Image(speechBalloonReg);
		speechBalloon.setWidth(500);
		speechBalloon.setHeight(170);
		speechBalloon.setPosition(230, 0);

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), speechBalloon);

	}

	private void addTextLabel() {
		label = new Label(" ", new LabelStyle(
				AssetManager.getTextFont(FontSize.FORTY), Color.MAGENTA));

		label.setPosition(speechBalloon.getX()
				+ (speechBalloon.getWidth() * 0.3f), speechBalloon.getY()
				+ (speechBalloon.getHeight() * 0.3f));

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), label);

	}

	private static void getActorSpeech() {
		float fade = 2.5f;

		SequenceAction act = Actions.sequence(Actions.fadeIn(fade),
				Actions.delay(2), Actions.fadeOut(fade));

		// speechBalloon.addAction(act);
		label.addAction(act);

	}

	public static void setActorSpeech(String str) {
		getActorSpeech();
		label.setText(str);
	}

	public Player getPlayer() {
		return player;
	}

}
