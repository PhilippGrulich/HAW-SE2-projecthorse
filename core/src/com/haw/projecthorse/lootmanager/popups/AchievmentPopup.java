package com.haw.projecthorse.lootmanager.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;

public class AchievmentPopup extends WidgetGroup {
	private float width, height;

	public AchievmentPopup(String description, Drawable img, float parentWidth,
			float parentHeight) {
		height = 100;
		width = parentWidth * 0.7f;

		createLabelAndImg(description, img);
		setBounds((parentWidth - width) / 2, parentHeight + 10, width, height);

		addAction(Actions.sequence(Actions.moveBy(0, -height, 0.8f),
				Actions.delay(0.5f), Actions.fadeOut(1.6f),
				Actions.removeActor()));
	}

	private void createLabelAndImg(String description, Drawable img) {
		Table table = new Table();
		table.setFillParent(true);
		table.left().top();
		table.setBackground(new TextureRegionDrawable(AssetManager
				.getTextureRegion("ui", "buttonBackground")));

		Image icon = new Image(img, Scaling.fit);
		table.add(icon).height(70).maxWidth(120).pad(15, 0, 15, 0);

		Label desc = new Label(description, new LabelStyle(
				AssetManager.getTextFont(FontSize.FORTY), Color.DARK_GRAY));
		desc.setEllipse(true);
		table.add(desc).expandX().left();

		addActor(table);
	}
}
