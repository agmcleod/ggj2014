package com.agmcleod.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Layer {
	
	private Texture texture;
	
	public Layer(String filename) {
		texture = new Texture(Gdx.files.internal("data/" + filename));
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, 0, 0);
	}
	
	public void update(float delta) {
		
	}
}
