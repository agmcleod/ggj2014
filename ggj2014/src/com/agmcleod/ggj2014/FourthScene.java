package com.agmcleod.ggj2014;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FourthScene extends Scene {
	
	private boolean bearItemPlaced;
	private boolean canSwitchLayers;
	private GameScreen gameScreen;
	private boolean paperItemPlaced;
	private boolean ringItemPlaced;
	
	private class AllowMovementHandler implements DialogueCompleteEvent {
		@Override
		public void complete() {
			canSwitchLayers = true;
			Layer layerOne = getLayerByIndex(0);
			layerOne.setShowDialogue(false);
		}
	}
	
	private class NextLayerOneDialogueEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			Layer layer = getLayerByIndex(0);
			layer.nextDialogue();
		}
	}
	
	public FourthScene(GameScreen gameScreen) {
		super(1);
		this.gameScreen = gameScreen;
		
		setLayer(0, new Layer("stage4/blank.png"));
		
		NextLayerOneDialogueEvent nltde = new NextLayerOneDialogueEvent();
		
		Layer layerOne = getLayerByIndex(0);
		layerOne.setShowDialogue(true);
		layerOne.addDialogue("This is it?", "blue", nltde);
		layerOne.addDialogue("Yeah.", "yellow", nltde);
		layerOne.addDialogue("I don't get it. It's like we came here for nothing.", "gray", nltde);
		layerOne.addDialogue("Now or never I suppose.", "blue", new AllowMovementHandler());
		canSwitchLayers = false;
	}
	
	public boolean changeLayer(int i) {
		return false;
	}
	
	@Override
	public void handleMousePress(int x, int y) {
		// placement logic here. Check against coords & current scene.
	}
	
	public boolean nextLayer() {
		return false;
	}
	
	public void render(SpriteBatch batch) {
		getLayers()[0].render(batch);
	}
}
