package com.haw.projecthorse.player.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
	private static final int SPRITES_PER_ANIMATION = 6;

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
			String spriteName = "";
			deltaSum %= frameDuration;
			spriteIndex = ++spriteIndex % SPRITES_PER_ANIMATION;

			switch (direction) {
			case RIGHT:
				flipX = true;
			case LEFT:
				spriteName = "left";
				break;
			case UPRIGHT:
				flipX = true;
			case UPLEFT:
				spriteName = "upleft";
				break;
			case DOWNRIGHT:
				flipX = true;
			case DOWNLEFT:
				spriteName = "downleft";
				break;
			case UP:
				spriteName = "up";
				break;
			case DOWN:
				spriteName = "down";
				break;
			}

			// TODO: Die Position der TextureRegion muss geändert werden ->
			// nächstes Sprite laden
			p.changeSprite(spriteName + "-" + (spriteIndex + 1), direction, flipX);
		}

		return false;
	}
}
