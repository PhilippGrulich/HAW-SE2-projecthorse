package com.haw.projecthorse.level.util.overlay.popup;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;

public class Popup extends OverlayWidgetGroup {

	int popupHeigh = height / 4;
	int popupWith = width;
	VerticalGroup contentGroup = new VerticalGroup();

	public Popup() {

		this.setHeight(popupHeigh);
		this.setWidth(popupWith);

		// 1. Load & Set gfx
		Pixmap pixel = new Pixmap(popupWith, popupHeigh, Format.RGBA8888); // Create
																			// a
		// temp-pixmap
		// to use as a
		// background
		// texture
		pixel.setColor(Color.GRAY);

		pixel.fill();
		Image image = new Image(new Texture(pixel, Format.RGBA8888, true));

		image.setY((height / 2) - popupHeigh / 2);
		this.setX(0);
		contentGroup.setY((height / 2) - popupHeigh / 2);
		super.addActor(image);

		image.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.cancel();
				return true;
			}
		});

		contentGroup.space(10);
		contentGroup.setHeight(popupHeigh);
		contentGroup.setWidth(popupWith);
		super.addActor(contentGroup);
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if(!event.isCancelled()){
					
					Overlay o = Popup.this.getOverlay();
					o.disposePopup();
					o.getLevel().resume();
				}
				

				return true;
			}
		});
	}

	@Override
	public void addActor(Actor actor) {
		contentGroup.addActor(actor);
	}

	protected Overlay getOverlay() {
		if (this.getParent() == null)
			return null;
		if (!(this.getStage() instanceof Overlay))
			return null;
		return (Overlay) this.getStage();
	}

}
