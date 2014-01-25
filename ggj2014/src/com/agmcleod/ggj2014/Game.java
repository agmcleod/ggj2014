package com.agmcleod.ggj2014;

public class Game extends com.badlogic.gdx.Game {
	private GameScreen gameScreen;
	
	public Game() {
		gameScreen = new GameScreen(this);
	}
	
	@Override
	public void create() {		
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		
	}
}
