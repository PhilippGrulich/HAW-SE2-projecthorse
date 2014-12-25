package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.actions.Direction;

public class GreetingPopup extends Popup {

	Popup greetingPopup;
	Label greetingLabel;
	ImageTextButton readyButton;
	
	public GreetingPopup(){
		super();
		this.toFront();
		readyButton =  this.createButton("Los geht's!");
		
		initGreetingPopup();
	}
	
	public GreetingPopup(String username){
		super();
		this.toFront();
		readyButton =  this.createButton("Los geht's!");
		initGreetingPopup(username);
	}
	
	private void initGreetingPopup(){
		greetingPopup = new Popup();
		greetingPopup.setName("Popup");
		greetingLabel = this.createLabel("Drehe dein Smartphone \nauf die Seite! \nBist du bereit?");
		greetingLabel.setName("Label");
		greetingPopup.addActor(greetingLabel);
		greetingPopup.addActor(readyButton);
	}
	
	private void initGreetingPopup(String username){
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
	public void draw(Batch batch, float parentAlpha){
		this.greetingPopup.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(float delta){
		this.greetingPopup.act(delta);
	}
	
}
