package com.haw.projecthorse.inputmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * Der InputManager verwaltet der Input von mehreren InputProcessoren. So kann
 * Sichergestellt werden das die InputProcessoren nacheinander abgearbeitet
 * werden.
 * 
 * @author Philipp Grulich
 * @version 1.0
 *
 */
public final class InputManager implements InputProcessor {

	private static PriorityArray<InputProcessor> processors = new PriorityArray<InputProcessor>();
	private static InputManager instance;

	/**
	 * Singleton Konstruktor.
	 */
	private InputManager() {
		InputProcessor defaultInputProcessor = new DefaultInputProcessor();
		addInputProcessor(defaultInputProcessor);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
	}

	/**
	 * erzeugt eine neue Instanz.
	 */
	public static void createInstance() {
		instance = new InputManager();
	}

	/**
	 * Fügt einen neuen Input Processor hinzu.
	 * 
	 * @param inputProcessor
	 *            InputProcessor
	 * @param priority
	 *            Umsohöher der Wert ist umso eher wird er ausgelöst.
	 */
	public static void addInputProcessor(final InputProcessor inputProcessor, final int priority) {
		processors.add(inputProcessor, priority);
	}

	/**
	 * Fügt einen neuen Input Processor hinzu.
	 * 
	 * @param inputProcessor
	 *            InputProcessor
	 */
	public static void addInputProcessor(final InputProcessor inputProcessor) {
		processors.add(inputProcessor, 1);
	}

	/**
	 * Diese Methode löscht alle aktiven InputProcessoren.
	 */
	public static void clear() {
		processors.clear();
		InputProcessor defaultInputProcessor = new DefaultInputProcessor();
		addInputProcessor(defaultInputProcessor);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(instance);
	}

	@Override
	public boolean keyDown(final int keycode) {

		for (InputProcessor processor : processors) {
			if (processor.keyDown(keycode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(final int keycode) {

		for (InputProcessor processor : processors) {
			if (processor.keyUp(keycode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean keyTyped(final char character) {

		for (InputProcessor processor : processors) {
			if (processor.keyTyped(character)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {

		for (InputProcessor processor : processors) {
			if (processor.touchDown(screenX, screenY, pointer, button)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {

		for (InputProcessor processor : processors) {
			if (processor.touchUp(screenX, screenY, pointer, button)) {
				return true;
			}

		}
		return false;
	}

	@Override
	public boolean touchDragged(final int screenX, final int screenY, final int pointer) {

		for (InputProcessor processor : processors) {
			if (processor.touchDragged(screenX, screenY, pointer)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(final int screenX, final int screenY) {

		for (InputProcessor processor : processors) {
			if (processor.mouseMoved(screenX, screenY)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean scrolled(final int amount) {

		for (InputProcessor processor : processors) {
			if (processor.scrolled(amount)) {
				return true;
			}
		}
		return false;
	}

}
