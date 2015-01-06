package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

/**
 * Enthält 2 Popups - eines, das beim Spielgewinn angezeigt wird
 * und eines, das bei einer Spielniederlage angezeigt wird.
 * @author Francis
 * @version 1.0
 */
public class GameOverPopup extends Popup {

	Popup wonPopup; //Popup das beim Sieg angezeigt wird.
	Label wonLabel; //Schriftzug der im wonPopup angezeigt wird.
	ImageTextButton wonButtonYes; //yes-Button im wonPopup
	ImageTextButton wonButtonNo; //no-Button im wonPopup
	ImageTextButton lostButtonYes; //yes-Button im lostPopup
	ImageTextButton lostButtonNo; //no-Button im lostPopup
	Popup lostPopup; //Popup das bei einer Niederlage angezeigt wird.

	/**
	 * Konstruktor.
	 */
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

	/**
	 * Initialisiert das wonPopup mit Schriftzug (Siegesnachricht) und yes- sowie no-Button.
	 */
	private void initWonPopup() {
		wonPopup = new Popup();
		wonPopup.setName("Popup");

		Label labelQuestion = this.createLabel("Möchtest Du nochmal spielen?");
		labelQuestion.setName("Label");

		wonPopup.addActor(labelQuestion);
		wonPopup.addActor(wonButtonYes);
		wonPopup.addActor(wonButtonNo);
	}

	/**
	 * Initialisiert das lostPopup mit Schriftzug (Nachricht der Niederlage) sowie yes- und no-Button.
	 */
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

	/**
	 * In Abhängigkeit des GameState wird hier geprüft, ob im wonPopup oder lostPopup der
	 * yes-Button gedrückt wurde.
	 * @param g GameState.WON oder GameState.LOST
	 * @return true, wenn GameState.WON u. wonPopup.yesButton.isPressed, sonst false. Analog GameState.LOST.
	 */
	public boolean isButtonYesPressed(final GameState g) {
		if (g == GameState.WON){
			return wonButtonYes.isPressed();
		}

		return lostButtonYes.isPressed();
	}

	/**
	 * In Abhängigkeit des GameState wird hier geprüft, ob im wonPopup oder lostPopup der
	 * no-Button gedrückt wurde.
	 * @param g GameState.WON oder GameState.LOST
	 * @return true, wenn GameState.WON u. wonPopup.noButton.isPressed, sonst false. Analog GameState.LOST.
	 */
	public boolean isButtonNoPressed(final GameState g) {
		if (g == GameState.WON){
			return wonButtonNo.isPressed();
		}

		return lostButtonNo.isPressed();
	}

	/**
	 * Liefert das Popup das bei Sieg bwz. Niederlage angezeigt wird.
	 * @param g GameState.WON oder GameState.LOST
	 * @return popup Wenn GameState.WON das gewinner Popup, sonst das Verlierer-Popup.
	 */
	public Popup getPopup(final GameState g) {
		if (g == GameState.WON) {
			return wonPopup;
		}
		return lostPopup;
	}

	/**
	 * Mögliche Spielzustände.
	 * @author Francis
	 *
	 */
	public enum GameState {
		WON, LOST, START, END, GREETING, HORSESELECTION;
	}

}
