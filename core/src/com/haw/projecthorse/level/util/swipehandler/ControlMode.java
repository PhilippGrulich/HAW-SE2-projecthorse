package com.haw.projecthorse.level.util.swipehandler;

/**
 * Enum für die unterschiedlichen Kontroll-Modi der Swipe Gesten.
 * Siehe auch {@link com.haw.projecthorse.player.actions.Direction}
 * 
 * HORIZONTAL: Nur Rechts und Links wird unterschieden.
 * VERTICAL: Nur Hoch und Runter wird unterschieden.
 * TWO_AXIS: Rechts, Links, Hoch und Runter werden unterschieden.
 * FOUR_AXIS: Rechts, Links, Hoch, Runter, RechtsHoch, RechtsRunter, LinksHoch und LinksRunter werden unterschieden.
 * 
 * @author Oliver
 * @version 1.0
 */
public enum ControlMode {
	HORIZONTAL, VERTICAL, TWO_AXIS, FOUR_AXIS
}
