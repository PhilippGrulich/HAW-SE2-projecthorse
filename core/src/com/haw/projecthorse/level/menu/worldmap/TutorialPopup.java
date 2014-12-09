package com.haw.projecthorse.level.menu.worldmap;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;

public class TutorialPopup extends Popup {

	ScrollPane scollContent;

	public TutorialPopup() {

		Table table = new Table();

		table.row();
		ScrollPaneStyle paneStyle = new ScrollPaneStyle();
		paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;

		Label label = createLabel("asgjjj\njjjjjjjjj\njjjjj\njjjjjjjjj\njjjjjjjjj\njjjjjjj\njjjjjjjjjjjjjj\njjjjjjjjjjjjj\njjjjjjj\njjjjjjjj\njjjjjjjjjjjj\n aasd");
		label.setAlignment(1);
		table.add(label);

		scollContent = new ScrollPane(table, paneStyle);
		scollContent.layout();
		scollContent.setSize(table.getPrefWidth(), 500);

		float t = scollContent.getPrefHeight();
		this.addActor(scollContent);

		for (int i = 0; i < 50 || i < 100; i++) {

			table.add(new ButtonLarge("test"));

			table.row();
		}

	}
}
