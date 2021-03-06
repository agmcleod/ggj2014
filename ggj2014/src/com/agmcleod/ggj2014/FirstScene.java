package com.agmcleod.ggj2014;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstScene extends Scene {
	
	private GameScreen gameScreen;
	private boolean showInstructions;
	
	private static final Color COLOR_BLUE = new Color(47f / 255f, 72f / 255f, 173f / 255f, 1f);
	private static final Color COLOR_YELLOW = new Color(255f, 128f / 255f, 0f, 1f);
	
	private class LayerThreeItemHandler implements ItemClickEvent {

		@Override
		public void execute() {
			Layer layerThree = getLayerByIndex(2);
			layerThree.getItems().clear();
			layerThree.setShowDialogue(true);
			
			Layer layerOne = getLayerByIndex(0);
			layerOne.setShowDialogue(true);
		}
	}
	
	private class NextSceneEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			gameScreen.nextScene();
		}
	}
	
	public FirstScene(GameScreen gameScreen) {
		super(3);

		this.gameScreen = gameScreen;

		setLayer(0, new Layer("stage1/yellow1.png", "scene1/yellow.mp3"));
		setLayer(1, new Layer("stage1/blue1.png", "scene1/blue.mp3"));
		setLayer(2, new Layer("stage1/grey1.png", "scene1/gray.mp3"));
		
		DialogueCompleteEvent nde = new DialogueCompleteEvent() {
			@Override
			public void complete() {
				Layer layer = getLayerByIndex(0);
				layer.nextDialogue();
			}
		};
		
		Layer layerOne = getLayerByIndex(0);
		layerOne.addDialogue("Did you find something over there?", "yellow", nde);
		layerOne.addDialogue("Yeah. Catch.", "gray", nde);
		layerOne.addDialogue("**tosses ring**", "gray", nde);
		layerOne.addDialogue("This can't be... is it?", "yellow", nde);
		layerOne.addDialogue("What is it?", "blue", nde);
		layerOne.addDialogue("It's... a ring.", "yellow", nde);
		layerOne.addDialogue("Who would wear something like that?", "gray", nde);
		layerOne.addDialogue("It's mine. I lost it years ago.", "yellow", nde);
		layerOne.addDialogue("Where would you even get something like that?", "gray", nde);
		layerOne.addDialogue("It's been passed down through the family.", "yellow", nde);
		layerOne.addDialogue("So can we go now? You've found what you're looking for right?", "gray", nde);
		layerOne.addDialogue("This isn't what I'm looking for. I'm happy to have found it, but there's something else. We need to keep going.", "yellow", nde);
		layerOne.addDialogue("I don't like this.", "gray", new NextSceneEvent());
		
		Layer layerThree = getLayerByIndex(2);
		LayerThreeItemHandler handler = new LayerThreeItemHandler();
		layerThree.addItem("production/items/ring.png", 300, 250, 46, 42, handler);
		
		layerThree.addDialogue("There's something on the ground here... maybe this is... It's a ring. Looks like something out of Wonderland. ...A bit tacky if you ask me.", "grayitalic");
		
		showInstructions = true;
	}
	
	public boolean changeLayer(int i) {
		stopShowingInstructions();
		return super.changeLayer(i);
	}
	
	public void handleMousePress(int x, int y) {
		stopShowingInstructions();
		super.handleMousePress(x, y);
	}
	
	public boolean nextLayer() {
		stopShowingInstructions();
		return super.nextLayer();
	}
	
	public void progressDialogue() {
		if(showInstructions) {
			showInstructions = false;
		}
		else {
			super.progressDialogue();
		}
	}
	
	public void render(SpriteBatch batch, float delta) {
		super.render(batch, delta);
		BitmapFont font;
		switch(currentLayer) {
		case 1:
			font = Game.grayFont;
			break;
		case 2:
			font = Game.whiteFont;
			break;
		default:
			font = Game.font;
		}
		if(showInstructions) {
			font.draw(batch, "Try to find an item for one of the characters.", 180, 300);
			font.draw(batch, "Use the mouse to collect.", 300, 250);
			font.draw(batch, "Press 1 or 2 or 3 to switch character views.", 190, 200);
		}
	}
	
	public void stopShowingInstructions() {
		if(showInstructions) {
			showInstructions = false;
		}
	}
}
