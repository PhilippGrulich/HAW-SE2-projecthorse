package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.level.util.overlay.popup.Popup;

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
	
	private void initGreetingPopup(){
		greetingPopup = new Popup();
		greetingPopup.setName("Popup");
		greetingLabel = this.createLabel("Drehe dein Smartphone \nauf die Seite! \nBist du bereit?");
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
