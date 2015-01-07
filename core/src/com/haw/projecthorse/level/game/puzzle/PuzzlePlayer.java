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

/**
 * 
 * @author Masha
 *@version 1.0
 */
public class PuzzlePlayer {

	private Player player;
	private static Image speechBalloon;
	private static Label label;

	/**
	 * 
	 */
	public PuzzlePlayer() {

		player = new PlayerImpl();
		player.setPosition(-30, PuzzleManager.getMyXPos() - player.getHeight()
				* 0.65f);
		player.scaleBy(0.5F);
		addSpeechBalloon();
		addTextLabel();
		setActorSpeech("Wähle ein \nBild aus");

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), player);

	}

	/**
	 * lade das Sprechballonbild.
	 * setze die Größe und die Position, füge dann
	 * das Bild in die zweite Stage
	 */
	private void addSpeechBalloon() {
		TextureRegion speechBalloonReg = AssetManager.getTextureRegion(
				"puzzle", "speechballoon");

		speechBalloon = new Image(speechBalloonReg);
		speechBalloon.setWidth(500);
		speechBalloon.setHeight(170);
		speechBalloon.setPosition(230, 0);

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), speechBalloon);

	}

	/**
	 * erstelle eine Label.
	 *  positioniere die in das Sprrechballonbild, füge die
	 * dann in die zweite Stage
	 */
	private void addTextLabel() {
		label = new Label(" ", new LabelStyle(
				AssetManager.getTextFont(FontSize.FORTY), Color.MAGENTA));

		label.setPosition(speechBalloon.getX()
				+ (speechBalloon.getWidth() * 0.3f), speechBalloon.getY()
				+ (speechBalloon.getHeight() * 0.3f));

		PuzzleManager.addToStage(PuzzleManager.getSecondstage(), label);

	}

	/**
	 * eine Hilfsmethode.
	 * die das Sprechballonbild und die Label ein- und nach
	 * kurzer Zeit wieder ausblendet
	 */
	private static void getActorSpeech() {
		float fade1 = 1;
		float fade2 = 2.5f;
		SequenceAction act1 = Actions.sequence(Actions.fadeIn(fade1),
				Actions.delay(2), Actions.fadeOut(fade2));
		SequenceAction act2 = Actions.sequence(Actions.fadeIn(fade1),
				Actions.delay(2), Actions.fadeOut(fade2));

		speechBalloon.addAction(act1);
		label.addAction(act2);

	}

	/**
	 * setzt den neuen Text in die Sprechblase.
	 * @param String
	 */
	public static void setActorSpeech(final String str) {
		getActorSpeech();
		label.setText(str);
	}

	public Player getPlayer() {
		return player;
	}

}
