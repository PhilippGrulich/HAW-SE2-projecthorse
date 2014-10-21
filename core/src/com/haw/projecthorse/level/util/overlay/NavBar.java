package com.haw.projecthorse.level.util.overlay;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.button.NavbarButton;

/**
 * Navbar die einem Level Hinzugefügt wird. Jeder Navbar Können mehrere Buttons
 * hinzugefügt werden(z.B. Pause Button)
 * 
 * @author Philipp
 *
 */
public class NavBar extends OverlayWidgetGroup {

	private HorizontalGroup horizontalGroup;
	private final int NAVBAR_HIGH = (int) (this.height * 0.1);
	private final int NAVBAR_WITH = (int) (this.width - this.width * 0.1);

	public NavBar() {

		horizontalGroup = new HorizontalGroup();
		horizontalGroup.reverse();
		horizontalGroup.setHeight(NAVBAR_HIGH);
		horizontalGroup.setWidth(NAVBAR_WITH);
		setToTop();
		this.addActor(horizontalGroup);

	}

	/**
	 * Returnt das Parent Overlay Object. Wenn die NavigationBar nicht auf einem
	 * Overlay liegt wird null zurück gegeben
	 * 
	 * @return
	 */
	public Overlay getOverlay() {
		if (this.getParent() == null)
			return null;
		if (!(this.getStage() instanceof Overlay))
			return null;
		return (Overlay) this.getStage();
	}

	public void setToTop() {
		this.horizontalGroup.setY(this.height - NAVBAR_HIGH);
	};

	public void setToButton() {
		this.horizontalGroup.setY(horizontalGroup.getHeight());
	}

	public void addButton(NavbarButton btn) {
		this.horizontalGroup.addActor(btn);

	}
}
