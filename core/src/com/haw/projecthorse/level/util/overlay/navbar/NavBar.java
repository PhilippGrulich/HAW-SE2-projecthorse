package com.haw.projecthorse.level.util.overlay.navbar;

import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.Overlay;
import com.haw.projecthorse.level.util.overlay.OverlayWidgetGroup;
import com.haw.projecthorse.level.util.overlay.navbar.button.NavbarButton;

/**
 * Eine Navbar ist eine kleine Leiste mit Men� Elementen(Pause Button) die
 * entweder am oberen oder am unteren Bildschirmrand angezeigt wird. Jeder
 * Navbar k�nnen mehrere Men� Elemente (NavBarButton) �ber die Methode addButton
 * hinzugef�gt werden.
 * 
 * @author Philipp
 *
 */
public class NavBar extends OverlayWidgetGroup {

	private HorizontalGroup horizontalGroup;
	private final int NAVBAR_HIGH;
	private final int NAVBAR_WITH;

	public NavBar() {
		if (GameManagerFactory.getInstance().getPlatform().getOrientation() == Orientation.Landscape) {
			NAVBAR_HIGH = (int) (this.height * 0.25);
			NAVBAR_WITH = (int) (this.width);
		} else {
			NAVBAR_HIGH = (int) (this.height * 0.14);
			NAVBAR_WITH = (int) (this.width);
		}
		
		horizontalGroup = new HorizontalGroup();

		horizontalGroup.reverse();
		horizontalGroup.space((float) (this.width * 0.005));
		horizontalGroup.setHeight(NAVBAR_HIGH);
		horizontalGroup.setWidth(NAVBAR_WITH);
		setToTop();
		this.addActor(horizontalGroup);
		this.pack();
	}

	/**
	 * Leifert das Parent Overlay Object zur�ck. Wenn die NavigationBar nicht
	 * auf einem Overlay liegt wird null zur�ck gegeben. Daher sollte die Navbar
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
	 * F�gt ein neuen NavbarButton zur Navbar hinzu.
	 * 
	 * @param btn
	 */
	public void addButton(NavbarButton btn) {

		this.horizontalGroup.addActor(btn);
		this.horizontalGroup.layout();

	}
}
