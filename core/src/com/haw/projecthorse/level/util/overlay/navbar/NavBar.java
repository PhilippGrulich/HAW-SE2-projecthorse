package com.haw.projecthorse.level.util.overlay.navbar;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarButton;

/**
 * Eine Navbar ist eine kleine Leiste mit Menü Elementen(Pause Button) die
 * entweder am oberen oder am unteren Bildschirmrand angezeigt wird. Jeder
 * Navbar können mehrere Menü Elemente (NavBarButton) über die Methode addButton
 * hinzugefügt werden.
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
	 * Leifert das Parent Overlay Object zurück. Wenn die NavigationBar nicht
	 * auf einem Overlay liegt wird null zurück gegeben. Daher sollte die Navbar
	 * immer auf einem Overlay liegen.
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

	/**
	 * Fügt ein neuen NavbarButton zur Navbar hinzu.
	 * 
	 * @param btn
	 */
	public void addButton(NavbarButton btn) {
		this.horizontalGroup.addActor(btn);

	}
}
