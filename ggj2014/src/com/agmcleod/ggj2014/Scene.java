package com.agmcleod.ggj2014;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scene {
	
	private int currentLayer;
	private Layer[] layers;
	private boolean transitioning = false;
	
	public Scene(int numOflayers) {
		layers = new Layer[numOflayers];
		currentLayer = 0;
	}
	
	public boolean changeLayer(int i) {
		if(!isTransitioning()) {
			currentLayer = i - 1;
			transitioning = true;
			for(int j = 0; j < layers.length; j++) {
				Layer l = layers[j];
				l.stopMusic();
			}
			getCurrentLayer().startMusic();
			return true;
		}
		else {
			return false;
		}
	}
	
	public void dispose() {
		for(int i = 0; i < layers.length; i++) {
			layers[i].dispose();
		}
	}
	
	public Layer getCurrentLayer() {
		return layers[currentLayer];
	}
	
	public Layer[] getLayers() {
		return layers;
	}
	
	public Layer getLayerByIndex(int i) {
		return layers[i];
	}
	
	public void handleMousePress(int x, int y) {
		getCurrentLayer().handleMousePress(x, y);
	}
	
	public boolean isTransitioning() {
		return transitioning;
	}
	
	public boolean nextLayer() {
		if(!isTransitioning()) {
			currentLayer++;
			if(currentLayer >= layers.length) {
				currentLayer = 0;
			}
			transitioning = true;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void progressDialogue() {
		getCurrentLayer().progressDialogue();
	}
	
	public void render(SpriteBatch batch) {
		getCurrentLayer().render(batch);
	}
	
	public void setCurrentLayer(int value) {
		currentLayer = value;
	}
	
	public void setLayer(int i, Layer layer) throws Exception {
		if(layers[i] != null) {
			throw new Exception("Layer " + i + " is already set");
		}
		layers[i] = layer;
	}
	
	public void setTransitioning(boolean value) {
		transitioning = value;
	}
	
	public void update(float delta) {
		getCurrentLayer().update(delta);
	}
}
