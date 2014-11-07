package com.haw.projecthorse.level.util.swipehandler;

import com.haw.projecthorse.player.Direction;

/**
 * Enum für die unterschiedlichen Kontroll-Modi der Swipe Gesten.
 * Siehe auch {@link Direction}
 * 
 * HORIZONTAL: Nur Rechts und Links wird unterschieden.
 * VERTICAL: Nur Hoch und Runter wird unterschieden.
 * TWO_AXIS: Rechts, Links, Hoch und Runter werden unterschieden.
 * FOUR_AXIS: Rechts, Links, Hoch, Runter, RechtsHoch, RechtsRunter, LinksHoch und LinksRunter werden unterschieden.
 * @author Oliver
 *
 */
public enum ControlMode {
	HORIZONTAL, VERTICAL, TWO_AXIS, FOUR_AXIS
}
