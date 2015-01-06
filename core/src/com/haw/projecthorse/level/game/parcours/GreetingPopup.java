package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

/**
 * Die Klasse stellt das Begrüßungspopup vor Spielbeginn dar und erklärt
 * dem Spieler das Spiel.
 * @author Francis
 * @version 1.0
 */
public class GreetingPopup extends Popup {

	Popup greetingPopup;
	Label greetingLabel;
	ImageTextButton readyButton;
	
	/**
	 * Konstruktor.
	 */
	public GreetingPopup(){
		super();
		this.toFront();
		readyButton =  this.createButton("Los geht's!");
		
		initGreetingPopup();
	}
	
	/**
	 * Konstruktor.
	 * @param username Der Benutzername des Spielers.
	 */
	public GreetingPopup(final String username){
		super();
		this.toFront();
		readyButton =  this.createButton("Los geht's!");
		initGreetingPopup(username);
	}
	
	/**
	 * Initialisert das Begrüßungs-Popup mit Text und Button.
	 */
	private void initGreetingPopup(){
		greetingPopup = new Popup();
		greetingPopup.setName("Popup");
		greetingLabel = this.createLabel("Drehe dein Smartphone \nauf die Seite! \nBist du bereit?");
		greetingLabel.setName("Label");
		greetingPopup.addActor(greetingLabel);
		greetingPopup.addActor(readyButton);
	}
	
	/**
	 * Initialisert das Begrüßungs-Popup mit Text und Button.
	 * @param username Der Benutzername des Spielers.
	 */
	private void initGreetingPopup(final String username){
		greetingPopup = new Popup();
		greetingPopup.setName("Popup");
		greetingLabel = this.createLabel("Hallo " + username + "!\n"
				+ "Drehe Dein Smartphone \nauf die Seite! \n"
				+ "Versuche so viele Kürbisse \n"
				+ "einzusammeln wie Du kannst und\n"
				+ "berühre nicht die Kisten! \nBist du bereit?");
		greetingLabel.setName("Label");
		greetingPopup.addActor(greetingLabel);
		greetingPopup.addActor(readyButton);
	}
	
	public boolean isButtonPressed(){
		return readyButton.isPressed();
	}
	
	public Popup getPopup(){
		return greetingPopup;
	}
	
	@Override
	public void draw(final Batch batch, final float parentAlpha){
		this.greetingPopup.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(final float delta){
		this.greetingPopup.act(delta);
	}
	
}
