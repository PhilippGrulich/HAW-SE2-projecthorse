package com.haw.projecthorse.player.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * Action-Klasse für die Animation des Players.
 * 
 * @author Oliver und Lars
 * @version 1.5
 */
public class AnimationAction extends Action {
	private static final float MIN_FRAMEDURATION = 0.05f;

	private int spriteIndex;
	private float deltaSum, duration, time;
	private Direction direction;
	private boolean infinity;
	private String imagePrefix; // z.B. "idle-" für idle-1.png,
										// idle-2.png. Wird automatisch gesetzt
										// anhand der Direction

	/**
	 * Privater Konstruktor.
	 * 
	 * @param direction
	 *            Die Richtung der Animation
	 * @param duration
	 *            Die Dauer der Animation
	 * @param infinity
	 *            True, wenn die Animation endlos laufen soll (die Dauer wird
	 *            dann ignoriert).
	 */
	private AnimationAction(final Direction direction, final float duration,
			final boolean infinity) {
		this.direction = direction;
		this.duration = duration;
		this.infinity = infinity;

		imagePrefix = direction.getImagePrefix();

		spriteIndex = 0;
		deltaSum = 1; // Init mit 1 damit sofort das erste Bild der Animation
						// gemalt wird. Sonst gibt es unschöne Verzögerungen
						// nach Animationswechsel (Überschüssiges delta
						// verschwindet danach durch Frameskip)
		restart();
	}

	/**
	 * Erstellt eine neue Action, um einen {@link Player} zu animieren.
	 * 
	 * @param direction
	 *            Die Richtung der Animation.
	 * @param duration
	 *            Die Dauer der Animation in Sekunden.
	 */
	public AnimationAction(final Direction direction, final float duration) {
		this(direction, duration, false);
	}

	/**
	 * Erstellt eine neue Action, um einen {@link Player} zu animieren. Die
	 * Animation läuft entlos, bis sie gestoppt oder von Player entfernt wird.
	 * 
	 * @param direction
	 *            Die Richtung der Animation.
	 */
	public AnimationAction(final Direction direction) {
		this(direction, 0f, true);
	}

	// private void finish() {
	// infinity = false;
	// time = duration;
	// }

	@Override
	public void restart() {
		time = 0;
	}

	@Override
	public boolean act(final float delta) {
		Actor a = getActor();
		if (!(a instanceof PlayerImpl)) {
			return false;
		}

		PlayerImpl p = (PlayerImpl) a;
		float speed = p.getAnimationSpeed();
		boolean flipX = direction.getFlipX();

		if (speed == 0) {
			return false;
		}

		time += delta;
		if (!infinity && time >= duration) {
			return true;
		}

		float frameDuration = (speed == 0) ? 0f : MIN_FRAMEDURATION / speed;

		deltaSum += delta;
		if (deltaSum >= frameDuration) {
			deltaSum %= frameDuration; // Frameskip: und nötig für ersten act()
										// weil mit delta = 1 initialisiert wird
			spriteIndex = ++spriteIndex % direction.getLastFrameId();

			p.changeSprite(imagePrefix + (spriteIndex + 1), direction, flipX);
		}

		return false;
	}
}
