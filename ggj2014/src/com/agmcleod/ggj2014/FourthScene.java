package com.agmcleod.ggj2014;

public class FourthScene extends Scene {
	
	private boolean canSwitchLayers;
	private GameScreen gameScreen;
	
	private class AllowMovementHandler implements DialogueCompleteEvent {
		@Override
		public void complete() {
			canSwitchLayers = true;
			Layer layerTwo = getLayerByIndex(1);
			layerTwo.setShowDialogue(false);
		}
	}
	
	private class NextLayerTwoDialogueEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			Layer layer = getLayerByIndex(1);
			layer.nextDialogue();
		}
	}
	
	public FourthScene(GameScreen gameScreen) {
		super(4);
		this.gameScreen = gameScreen;
		
		setLayer(0, new Layer("layerone.png"));
		setLayer(1, new Layer("layertwo.png"));
		setLayer(2, new Layer("layerthree.png"));
		
		NextLayerTwoDialogueEvent nltde = new NextLayerTwoDialogueEvent();
		
		Layer layerTwo = getLayerByIndex(1);
		layerTwo.setShowDialogue(true);
		layerTwo.addDialogue("This is it?", "blue", nltde);
		layerTwo.addDialogue("Yeah.", "yellow", nltde);
		layerTwo.addDialogue("I don't get it. It's like we came here for nothing.", "gray", nltde);
		layerTwo.addDialogue("Now or never I suppose.", "blue", new AllowMovementHandler());
		
		setCurrentLayer(1);
		canSwitchLayers = false;
	}
	
	public boolean changeLayer(int i) {
		if(canSwitchLayers) {
			return super.changeLayer(i);
		}
		else {
			return false;
		}
	}
	
	@Override
	public void handleMousePress(int x, int y) {
		// placement logic here. Check against coords & current scene.
	}
	
	public boolean nextLayer() {
		if(canSwitchLayers && !isTransitioning()) {
			currentLayer++;
			if(currentLayer >= getLayers().length - 1) {
				currentLayer = 0;
			}
			for(int j = 0; j < getLayers().length - 1; j++) {
				Layer l = getLayers()[j];
				l.stopMusic();
			}
			getCurrentLayer().startMusic();
			setTransitioning(true);
			return true;
		}
		else {
			return false;
		}
	}
}
