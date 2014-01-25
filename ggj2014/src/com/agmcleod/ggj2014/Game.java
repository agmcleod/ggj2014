package com.agmcleod.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class Game extends com.badlogic.gdx.Game implements InputProcessor {
	
	private int currentScreen;
	private GameScreen gameScreen;
	
	public Game() {
		gameScreen = new GameScreen(this);
		currentScreen = 0;
	}
	
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(getScreen() instanceof GameScreen) {
			gameScreen.handleKeyDown(keycode);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(getScreen() instanceof GameScreen) {
			gameScreen.handleMousePress(screenX, screenY);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
}
