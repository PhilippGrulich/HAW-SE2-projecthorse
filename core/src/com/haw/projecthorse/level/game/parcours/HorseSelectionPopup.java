package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.race.HorseRace;

/**
 * Das Popup zeigt die zur Auswahl stehenden Pferde an.
 * @author Francis
 * @version 1.0
 */
public class HorseSelectionPopup extends Popup{

	Popup selectionPopup;
	Label selectionLabel;
	Stage stage;
	HorseRace race;
	
	/**
	 * Konstruktor.
	 * @param races Die Pferderassen mit denen gespielt werden kann.
	 * @param s Die Stage auf der das Popup erscheinen soll.
	 */
	public HorseSelectionPopup(final HorseRace[] races, final Stage s){
		super();
		
		this.stage = s;
		initHorseSelectionPopup(races, s);
	}
	
	/**
	 * Initialisert das Pferdeauswahl-Popup mit Text und Listener.
	 * Der Listener registriert das Antippen bzw. Klicken auf ein Pferd.
	 * @param races Die zur Auwahl stehenden Pferderassen.
	 * @param s Die Stage auf der das Popup erscheinen soll.
	 */
	private void initHorseSelectionPopup(final HorseRace[] races, final Stage s){
		selectionPopup = new Popup();
		selectionPopup.setName("Popup");
		selectionLabel = this.createLabel("Tippe auf das Pferd \n"
				+ "mit dem du spielen möchtest!");
		selectionLabel.setName("Label");

		selectionPopup.addActor(selectionLabel);
		
		for(int i = 0; i < races.length; i++){
			PlayerImpl p = new PlayerImpl(races[i]);
			String name = races[i].name();
			Label l = this.createLabel(name.substring(0, 1) + name.substring(1).toLowerCase());
			Group g = new Group();
			g.addActor(l);
			l.setPosition(0, 0);
			g.addActor(p);
			p.setPosition(0, l.getY()+l.getHeight());
			g.setBounds(0, 0, p.getWidth() ,  p.getHeight());
			selectionPopup.addActor(g);
			p.setZIndex(i);
		}
		
		selectionPopup.addListener(new InputListener(){
			@Override
			public boolean touchDown(final InputEvent event, final float x, final float y,
					final int pointer, final int button) {
				// TODO Auto-generated method stub
				Actor result = stage.hit(x, y, false);
				if(result != null && result instanceof PlayerImpl){
					race = HorseRace.valueOf(((PlayerImpl)result).getRace().toUpperCase());
				}
				return true;
			}
		});
		
	
		
	}
	
	public boolean isHorseChosen(){
		return (race == null) ? false : true; 
	}
	
	public HorseRace getRace(){
		return race;
	}
	
	public Popup getPopup(){
		return selectionPopup;
	}
	
	@Override
	public void draw(final Batch batch, final float parentAlpha){
		this.selectionPopup.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(final float delta){
		this.selectionPopup.act(delta);
	}
	
	
}
