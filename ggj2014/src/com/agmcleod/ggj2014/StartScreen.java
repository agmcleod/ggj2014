package com.agmcleod.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen implements Screen {
	
	private static final Color COLOR_BLUE = new Color(47f / 255f, 72f / 255f, 173f / 255f, 1f);
	private static final Color COLOR_YELLOW = new Color(255f, 128f / 255f, 0f, 1f);
	
	private SpriteBatch batch;
	private BitmapFont font;
	private Game game;
	private Layer layer;
	private Music music;
	private Texture texture;
	private boolean showNextInstructions;
	
	public class LoadGameScreenEvent implements DialogueCompleteEvent {
		public void complete() {
			game.startGameScreen();
			dispose();
		}
	}
	
	public class NextDialogueEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			layer.nextDialogue();
		}
	}
	
	public StartScreen(Game game) {
		this.game = game;
		showNextInstructions = true;
	}
	
	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
		layer.dispose();
		texture.dispose();
		music.dispose();
	}

	public void handleKeyDown(int keycode) {
		switch(keycode) {
		case Input.Keys.NUM_1:
			game.setSceneLayer(1);
			layer.setShowDialogue(true);
			break;
		case Input.Keys.NUM_2:
			game.setSceneLayer(2);
			layer.setShowDialogue(true);
			break;
		case Input.Keys.NUM_3:
			game.setSceneLayer(3);
			layer.setShowDialogue(true);
			break;
		case Input.Keys.SPACE:
		case Input.Keys.ENTER:
			if(layer.showDialogue()) {
				layer.progressDialogue();
			}
			showNextInstructions = false;
			break;
		case Input.Keys.ESCAPE:
			Gdx.app.exit();
			break;
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0, 0);
		if(layer.showDialogue()) {
			layer.render(batch);
			if(showNextInstructions) {
				font.setColor(Color.BLACK);
				font.draw(batch, "Press Space to continue", 600, 50);
			}
		}
		else {
			String whiteOne = "Press ";
			String yellow = "1";
			String whiteTwo = " or ";
			String blue = "2";
			String whiteThree = " or ";
			String black = "3";
			String whiteFour = " to start.";
			
			float whiteOneLength = font.getBounds(whiteOne).width;
			float yellowLength = font.getBounds(yellow).width;
			float whiteTwoLength = font.getBounds(whiteTwo).width;
			float blueLength = font.getBounds(blue).width;
			float whiteThreeLength = font.getBounds(whiteThree).width;
			float blackLength = font.getBounds(black).width;
			
			int x = 350;
			int y = 100;
			font.setColor(Color.BLACK);
			font.draw(batch, whiteOne, x, y);
			font.setColor(COLOR_YELLOW);
			font.draw(batch, yellow, x + whiteOneLength, y);
			font.setColor(Color.BLACK);
			font.draw(batch, whiteTwo, x + whiteOneLength +  yellowLength, y);
			font.setColor(COLOR_BLUE);
			font.draw(batch, blue, x + whiteOneLength +  yellowLength + whiteTwoLength, y);
			font.setColor(Color.BLACK);
			font.draw(batch, whiteThree, x + whiteOneLength +  yellowLength + whiteTwoLength + blueLength, y);
			font.setColor(Color.GRAY);
			font.draw(batch, black, x + whiteOneLength +  yellowLength + whiteTwoLength + blueLength + whiteThreeLength, y);
			font.setColor(Color.BLACK);
			font.draw(batch, whiteFour, x + whiteOneLength +  yellowLength + whiteTwoLength + blueLength + whiteThreeLength + blackLength, y);
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		NextDialogueEvent nde = new NextDialogueEvent();
		texture = new Texture(Gdx.files.internal("data/production/title.png"));
		font = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), Gdx.files.internal("data/whitefont.png"), false);
		batch = new SpriteBatch();
		layer = new Layer();
		layer.addDialogue("Why do we need to go back? There's nothing for us there anymore.", "gray", nde);
		layer.addDialogue("There just is. I know it. There's something we forgot along the way.", "yellow", nde);
		layer.addDialogue("Are you sure? We've come so far. Just to go right back...", "blue", new LoadGameScreenEvent());
		music = Gdx.audio.newMusic(Gdx.files.internal("data/Title.mp3"));
		music.play();
	}
	
	public void update(float delta) {
		
	}

}
