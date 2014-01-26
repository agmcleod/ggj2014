package com.agmcleod.ggj2014;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Game extends com.badlogic.gdx.Game implements InputProcessor {
	
	
	public static BitmapFont blueFont;
	public static BitmapFont blueFontItalic;
	public static BitmapFont font;
	public static BitmapFont grayFont;
	public static BitmapFont grayFontItalic;
	public static Sound inventory;
	public static Sound transition;
	public static BitmapFont whiteFont;
	public static BitmapFont yellowFont;
	public static BitmapFont yellowFontItalic;
	
	private GameScreen gameScreen;
	private StartScreen startScreen;
	
	public Game() {
		
	}
	
	public static void disposeFonts() {
		font.dispose();
		yellowFont.dispose();
		blueFont.dispose();
		grayFont.dispose();
		yellowFontItalic.dispose();
		blueFontItalic.dispose();
		grayFontItalic.dispose();
		whiteFont.dispose();
	}
	
	public static void setupFonts() {
		font = new BitmapFont(Gdx.files.internal("data/layeronefont.fnt"), Gdx.files.internal("data/layeronefont.png"), false);
		yellowFont = new BitmapFont(Gdx.files.internal("data/yellowfont.fnt"), Gdx.files.internal("data/yellowfont.png"), false);
		blueFont = new BitmapFont(Gdx.files.internal("data/bluefont.fnt"), Gdx.files.internal("data/bluefont.png"), false);
		grayFont = new BitmapFont(Gdx.files.internal("data/grayfont.fnt"), Gdx.files.internal("data/grayfont.png"), false);
		yellowFontItalic = new BitmapFont(Gdx.files.internal("data/yellowitalicfont.fnt"), Gdx.files.internal("data/yellowitalicfont.png"), false);
		blueFontItalic = new BitmapFont(Gdx.files.internal("data/blueitalicfont.fnt"), Gdx.files.internal("data/blueitalicfont.png"), false);
		grayFontItalic = new BitmapFont(Gdx.files.internal("data/grayitalicfont.fnt"), Gdx.files.internal("data/grayitalicfont.png"), false);
		whiteFont = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), Gdx.files.internal("data/whitefont.png"), false);
		transition = Gdx.audio.newSound(Gdx.files.internal("data/scene1/transition.mp3"));
		inventory = Gdx.audio.newSound(Gdx.files.internal("data/scene1/inventory.mp3"));
	}
	
	@Override
	public void create() {
		setupFonts();
		Gdx.input.setInputProcessor(this);
		gameScreen = new GameScreen(this);
		startScreen = new StartScreen(this);
		setScreen(startScreen);
		Dialogue.setTextOffsets();
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		disposeFonts();
		transition.dispose();
		inventory.dispose();
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
		if(getScreen() instanceof GameScreen) {
			gameScreen.handleMouseHover(screenX, screenY);
		}
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
