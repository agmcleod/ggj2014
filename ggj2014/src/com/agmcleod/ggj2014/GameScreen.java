package com.agmcleod.ggj2014;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
	private final float TRANSITION_DURATION = 0.5f;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Scene currentScene;
	private Game game;
	private Array<Scene> scenes;
	private ShapeRenderer shapeRenderer;
	private int startLayer;
	
	private float transitionTime;
	
	public GameScreen(Game game) {
		this.game = game;
		startLayer = 0;
	}

	@Override
	public void dispose() {
		Iterator<Scene> it = scenes.iterator();
		while(it.hasNext()) {
			Scene s = it.next();
			s.dispose();
		}
		batch.dispose();
	}
	
	public void handleKeyDown(int keycode) {
		switch(keycode) {
		case Input.Keys.NUM_1:
			if(currentScene.changeLayer(1)) {
				transitionTime = 0f;
			}
			break;
		case Input.Keys.NUM_2:
			if(currentScene.changeLayer(2)) {
				transitionTime = 0f;
			}
			break;
		case Input.Keys.NUM_3:
			if(currentScene.changeLayer(3)) {
				transitionTime = 0f;
			}
			break;
		case Input.Keys.TAB:
			if(currentScene.nextLayer()) {
				transitionTime = 0f;
			}
			break;
		case Input.Keys.ENTER:
			currentScene.progressDialogue();
			break;
		}
	}
	
	public void handleMousePress(int x, int y) {
		currentScene.handleMousePress(x, y);
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
		currentScene.render(batch);
		batch.end();
		
		// drawing up here so it can be outside the batch start/end
		if(currentScene.isTransitioning()) {
			float percent = 1f - (transitionTime / TRANSITION_DURATION);
			Gdx.gl.glEnable(GL10.GL_BLEND);
			
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(new Color(1, 1, 1, percent));
			shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			shapeRenderer.end();
			
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	
	public void setStartLayer(int layer) {
		startLayer = layer - 1;
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		batch = new SpriteBatch();
		
		scenes = new Array<Scene>();
		Dialogue.setTextOffsets();
		scenes.add(new FirstScene());
		scenes.first().setCurrentLayer(startLayer);
		currentScene = scenes.first();
		shapeRenderer = new ShapeRenderer();
	}
	
	public void update(float delta) {
		camera.update();
		currentScene.update(delta);
		if(currentScene.isTransitioning()) {
			transitionTime += delta;
			if(transitionTime > TRANSITION_DURATION) {
				transitionTime = 0f;
				currentScene.setTransitioning(false);
			}
		}
	}

}
