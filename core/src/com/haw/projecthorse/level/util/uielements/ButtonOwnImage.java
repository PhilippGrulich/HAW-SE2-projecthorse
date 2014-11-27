package com.haw.projecthorse.level.util.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonOwnImage extends ImageButton{
	
	/**
	 * @author Viktor
	 *	Diese Klasse ist kapselt den LibGDX ImageButton und erweitert ihn sinnvoll.
	 */

	public ButtonOwnImage(Drawable imageUp){
		super(imageUp);
		addVibrationFeedback();
	}
	
	public ButtonOwnImage(Skin skin){
		super(skin);
		addVibrationFeedback();
	}
	
	public ButtonOwnImage(ImageButtonStyle style){
		super(style);
		addVibrationFeedback();
	}
	
	private void addVibrationFeedback(){
		this.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			Gdx.input.vibrate(50);	
			}
		});
	}

}
