package com.haw.projecthorse.intputmanager;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {

	private static ArrayList<InputProcessor> processors = new ArrayList<InputProcessor>();
	private static InputManager instance;
	private InputManager() {
		InputProcessor defaultInputProcessor = new DefaultInputProcessor();
		addInputProcessor(defaultInputProcessor);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
	}
	
	public static void createInstance(){
		instance = new InputManager();
		
	}


	public static void addInputProcessor(InputProcessor inputProcessor) {
		processors.add(inputProcessor);
	}

	public static void removeInputProcessor(InputProcessor inputProcessor) {
		processors.remove(inputProcessor);
	}

	public static void clear() {
		processors.clear();
		InputProcessor defaultInputProcessor = new DefaultInputProcessor();
		addInputProcessor(defaultInputProcessor);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(instance);
	}

	@Override
	public boolean keyDown(int keycode) {
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);
			if (processor.keyDown(keycode))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {		
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);
			if (processor.keyUp(keycode))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {		
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);

			if (processor.keyTyped(character))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {	
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);
			if (processor.touchDown(screenX, screenY, pointer, button))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {		
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);
			if (processor.touchUp(screenX, screenY, pointer, button))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {		
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);
			if (processor.touchDragged(screenX, screenY, pointer))
				return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);
			if (processor.mouseMoved(screenX, screenY))
				return true;
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		for (int i = processors.size()-1; i >= 0; i--) {
			InputProcessor processor = processors.get(i);
			if (processor.scrolled(amount))
				return true;
		}
		return false;
	}

}
