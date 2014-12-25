package com.haw.projecthorse.level.menu.city;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.navigationmanager.json.CityObject;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.level.util.textWrapper.TextWrapper;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;
import com.haw.projecthorse.level.util.uielements.DefaultScrollPane;

public class CityPopup extends Popup {
	public CityPopup(CityObject cityObject) {
		Label label = createLabel(gennerateText(cityObject.getCityInfo()));
		ScrollPane scollContent = new DefaultScrollPane(label, height / 3, popupWidth * 0.8f);

		this.addActor(createLabel(cityObject.getCityName()));

		this.addActor(scollContent);
		Button btn = new ButtonLarge("Ok");
		btn.addListener(new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				getOverlay().disposePopup();
			}

		});
		this.addActor(btn);
	}

	private String gennerateText(String text) {
		TextWrapper wrapper = new TextWrapper(23);
		wrapper.appendLine(text);
		return wrapper.toString();
	}
}
