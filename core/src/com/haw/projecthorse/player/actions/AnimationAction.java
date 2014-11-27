package com.haw.projecthorse.player.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelegateAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.player.Player;
import com.haw.projecthorse.player.PlayerImpl;

/**
 * Action-Klasse für die Animation des Players.
 * 
 * @author Oliver
 * 
 */
public class AnimationAction extends Action {
	/*public static Action StopAnimationAction = new Action() {
		private void findAndFinishAnimationAction(Action action) {
			PlayerAnimationAction animationAction;
			if (action instanceof PlayerAnimationAction) {
				animationAction = (PlayerAnimationAction) action;
				if (!animationAction.isNew) {
					animationAction.finish();
				}
			} else if (action instanceof DelegateAction) {
				findAndFinishAnimationAction(((DelegateAction) action)
						.getAction());
			} else if (action instanceof ParallelAction) {
				for (Action a : ((ParallelAction) action).getActions()) {
					findAndFinishAnimationAction(a);
				}
			}
		}

		@Override
		public boolean act(float delta) {
			Actor a = getActor();
			if (!(a instanceof PlayerImpl)) {
				return false;
			}

			PlayerImpl p = (PlayerImpl) a;
			Array<Action> actions = p.getActions();

			for (Action action : actions) {
				findAndFinishAnimationAction(action);
			}
			return true;
		};
	};*/

	private static final float MIN_FRAMEDURATION = 0.05f;
	private static final int SPRITES_PER_ANIMATION = 4;

	private int spriteIndex, animationIndex;
	private float deltaSum, duration, time;
	private Direction direction;
	private boolean isNew, infinity;

	private AnimationAction(Direction direction, float duration,
			boolean infinity) {
		this.direction = direction;
		this.duration = duration;
		this.infinity = infinity;

		isNew = true;
		spriteIndex = 0;
		animationIndex = 0;
		deltaSum = 0;
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
	public AnimationAction(Direction direction, float duration) {
		this(direction, duration, false);
	}

	/**
	 * Erstellt eine neue Action, um einen {@link Player} zu animieren. Die
	 * Animation läuft entlos, bis sie gestoppt oder von Player entfernt wird.
	 * 
	 * @param direction
	 *            Die Richtung der Animation.
	 */
	public AnimationAction(Direction direction) {
		this(direction, 0f, true);
	}

	private void finish() {
		infinity = false;
		time = duration;
	}

	@Override
	public void restart() {
		time = 0;
	}

	@Override
	public boolean act(float delta) {
		Actor a = getActor();
		if (!(a instanceof PlayerImpl)) {
			return false;
		}

		PlayerImpl p = (PlayerImpl) a;
		float speed = p.getAnimationSpeed();
		boolean flipX = false;

		if (speed == 0) {
			return false;
		}

		time += delta;
		if (!infinity && time >= duration) {
			return true;
		}

		isNew = false;

		float frameDuration = (speed == 0) ? 0f : MIN_FRAMEDURATION / speed;

		deltaSum += delta;
		if (deltaSum >= frameDuration) {
			deltaSum %= frameDuration;
			spriteIndex = ++spriteIndex % SPRITES_PER_ANIMATION;

			switch (direction) {
			case LEFT:
				flipX = true;
			case RIGHT:
				animationIndex = 0;
				break;
			case UPLEFT:
				flipX = true;
			case UPRIGHT:
				animationIndex = 1;
				break;
			case DOWNLEFT:
				flipX = true;
			case DOWNRIGHT:
				animationIndex = 2;
				break;
			case UP:
				animationIndex = 3;
				break;
			case DOWN:
				animationIndex = 4;
				break;
			}

			// TODO: Die Position der TextureRegion muss geändert werden ->
			// nächstes Sprite laden
			p.changeSprite(direction, spriteIndex, animationIndex, flipX);
		}

		return false;
	}
}
