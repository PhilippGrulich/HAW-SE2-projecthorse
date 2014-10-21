package com.haw.projecthorse.intputmanager;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.haw.projecthorse.level.util.overlay.Overlay;

public class InputManager implements InputProcessor {

	private static PriorityArray<InputProcessor> processors = new PriorityArray<InputProcessor>();
	private static InputManager instance;

	

	private InputManager() {
		InputProcessor defaultInputProcessor = new DefaultInputProcessor();
		addInputProcessor(defaultInputProcessor);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
	}

	public static void createInstance() {
		instance = new InputManager();
	}

	

	public static void addInputProcessor(InputProcessor inputProcessor, int priority) {
		processors.add(inputProcessor,priority);
	}
	
	public static void addInputProcessor(InputProcessor inputProcessor) {
		processors.add(inputProcessor,1);
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
		
		for (InputProcessor processor : processors){
			if (processor.keyDown(keycode))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
	
		for (InputProcessor processor : processors){
			if (processor.keyUp(keycode))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		for (InputProcessor processor : processors){
			if (processor.keyTyped(character))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		for (InputProcessor processor : processors){
			if (processor.touchDown(screenX, screenY, pointer, button))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		for (InputProcessor processor : processors){
			if (processor.touchUp(screenX, screenY, pointer, button))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		for (InputProcessor processor : processors){
			if (processor.touchDragged(screenX, screenY, pointer))
				return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		for (InputProcessor processor : processors){
			if (processor.mouseMoved(screenX, screenY))
				return true;
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
	
		for (InputProcessor processor : processors){
			if (processor.scrolled(amount))
				return true;
		}
		return false;
	}

}
