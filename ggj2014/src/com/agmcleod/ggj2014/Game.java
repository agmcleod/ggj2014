package com.agmcleod.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class Game extends com.badlogic.gdx.Game implements InputProcessor {
	
	private GameScreen gameScreen;
	private StartScreen startScreen;
	
	public Game() {
		gameScreen = new GameScreen(this);
		startScreen = new StartScreen(this);
	}
	
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		setScreen(startScreen);
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
		else if(getScreen() instanceof StartScreen) {
			startScreen.handleKeyDown(keycode);
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
	
	public void setSceneLayer(int startSceneLayer) {
		gameScreen.setStartLayer(startSceneLayer);
	}
	
	public void startGameScreen() {
		setScreen(gameScreen);
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
