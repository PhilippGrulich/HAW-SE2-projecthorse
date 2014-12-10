package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

public class GameOverPopup extends Popup {

	Popup wonPopup;
	Label wonLabel;
	ImageTextButton wonButtonYes;
	ImageTextButton wonButtonNo;
	ImageTextButton lostButtonYes;
	ImageTextButton lostButtonNo;
	Popup lostPopup;

	public GameOverPopup() {
		super();

		wonButtonYes = this.createButton("Ja");
		wonButtonNo = this.createButton("Nein");
		lostButtonYes = this.createButton("Ja");
		lostButtonNo = this.createButton("Nein");
		wonButtonYes.setName("Button");
		wonButtonNo.setName("Button");
		lostButtonYes.setName("Button");
		lostButtonNo.setName("Button");

		initWonPopup();
		initLostPopup();
	}

	private void initWonPopup() {
		wonPopup = new Popup();
		wonPopup.setName("Popup");

		Label wonLabel = this.createLabel("Gratulation!\nDu hast gewonnen!");
		Label labelQuestion = this.createLabel("Möchtest Du nochmal spielen?");
		wonLabel.setName("Label");
		labelQuestion.setName("Label");

		wonPopup.addActor(wonLabel);
		wonPopup.addActor(labelQuestion);
		wonPopup.addActor(wonButtonYes);
		wonPopup.addActor(wonButtonNo);
	}

	private void initLostPopup() {
		lostPopup = new Popup();
		lostPopup.setName("Popup");

		Label lostLabel = this.createLabel("Schade,\nDu hast leider verloren!");
		Label labelQuestion = this.createLabel("Möchtest Du nochmal spielen?");
		lostLabel.setName("Label");
		labelQuestion.setName("Label");

		lostPopup.addActor(lostLabel);
		lostPopup.addActor(labelQuestion);
		lostPopup.addActor(lostButtonYes);
		lostPopup.addActor(lostButtonNo);

	}

	public boolean isButtonYesPressed(GameState g) {
		if (g == GameState.WON)
			return wonButtonYes.isPressed();

		return lostButtonYes.isPressed();
	}

	public boolean isButtonNoPressed(GameState g) {
		if (g == GameState.WON)
			return wonButtonNo.isPressed();

		return lostButtonNo.isPressed();
	}

	public Popup getPopup(GameState g) {
		if (g == GameState.WON) {
			return wonPopup;
		}
		return lostPopup;
	}

	public enum GameState {
		WON, LOST, START, END;
	}

}
