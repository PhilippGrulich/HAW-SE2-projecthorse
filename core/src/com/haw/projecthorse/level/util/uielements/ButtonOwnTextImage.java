package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;

public class ButtonOwnTextImage extends ImageTextButton{
	
	/**
	 * @author Viktor
	 *	Diese Klasse ist kapselt den LibGDX ImageTextButton und erweitert ihn sinnvoll.
	 */

	
	public ButtonOwnTextImage(String text, Skin skin){
		super(text, skin);
		addFeedback();
	}
	
	public ButtonOwnTextImage(String text, ImageTextButtonStyle style){
		super(text, style);
		addFeedback();
	}
	
	private void addFeedback(){
		this.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			AudioManagerImpl.getInstance().getSound("ui", "click.ogg").play();
			Gdx.input.vibrate(50);	
			}
		});
	}

}
