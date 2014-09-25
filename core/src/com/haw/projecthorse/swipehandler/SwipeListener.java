/**
 * 
 */
package com.haw.projecthorse.swipehandler;

import com.haw.projecthorse.player.Player;

/**
 * Interface für Klassen, die auf Swipe-Bewegungen reagieren sollen.
 * Bespielimplementation: {@link Player}
 * 
 * @author Oliver
 *
 */
public interface SwipeListener {
	public void swipeUp();
	public void swipeDown();
	public void swipeLeft();
	public void swipeRight();
	public void swipeUpLeft();
	public void swipeUpRight();
	public void swipeDownLeft();
	public void swipeDownRight();
}
