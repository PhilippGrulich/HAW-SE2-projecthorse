package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.level.util.overlay.button.Button;

/**
 * Navbar die einem Level Hinzugefügt wird.
 * Jeder Navbar Können mehrere Buttons hinzugefügt werden(z.B. Pause Button)
 * @author Philipp
 *
 */
public class NavigationBar extends Overlay {

	private HorizontalGroup horizontalGroup;
	private final int NAVBAR_HIGH = (int) (this.height * 0.1);
	private final int NAVBAR_WITH = this.width;

	public NavigationBar(Viewport viewport, Batch batch) {
		super(viewport, batch);

		horizontalGroup = new HorizontalGroup();
		horizontalGroup.setFillParent(true);
		horizontalGroup.setHeight(NAVBAR_HIGH);
		horizontalGroup.setWidth(NAVBAR_WITH);
		
		this.addActor(horizontalGroup);
		
		
	}

	public void setToTop() {
		this.horizontalGroup.setY(this.height - horizontalGroup.getHeight());
	};

	public void setToButton() {
		this.horizontalGroup.setY(horizontalGroup.getHeight());
	}

	public void addButton(Button btn) {
		this.horizontalGroup.addActor(btn);
	}
}
