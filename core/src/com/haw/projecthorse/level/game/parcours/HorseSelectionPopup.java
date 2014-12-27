package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.race.HorseRace;

public class HorseSelectionPopup extends Popup{

	Popup selectionPopup;
	Label selectionLabel;
	Stage stage;
	HorseRace race;
	
	public HorseSelectionPopup(HorseRace[] races, Stage s){
		super();
		
		this.stage = s;
		initHorseSelectionPopup(races, s);
	}
	
	private void initHorseSelectionPopup(HorseRace[] races, Stage s){
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
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
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
	public void draw(Batch batch, float parentAlpha){
		this.selectionPopup.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(float delta){
		this.selectionPopup.act(delta);
	}
	
	
}
