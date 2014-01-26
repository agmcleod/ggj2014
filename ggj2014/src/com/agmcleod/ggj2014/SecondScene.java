package com.agmcleod.ggj2014;

public class SecondScene extends Scene {
	
	private boolean canSwitchLayers;
	private GameScreen gameScreen;
	
	
	private class AllowMovementHandler implements DialogueCompleteEvent {
		@Override
		public void complete() {
			canSwitchLayers = true;
			Layer layerOne = getLayerByIndex(0);
			layerOne.setShowDialogue(false);
		}
	}
	
	private class LayerTwoItemHandler implements ItemClickEvent {

		@Override
		public void execute() {
			Layer layerTwo = getLayerByIndex(1);
			layerTwo.getItems().clear();
			layerTwo.setShowDialogue(true);
			
			Layer layerThree = getLayerByIndex(2);
			layerThree.setShowDialogue(true);
		}
	}
	
	private class NextLayerOneDialogueEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			Layer layer = getLayerByIndex(0);
			layer.nextDialogue();
		}
	}
	
	private class NextLayerThreeDialogueEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			Layer layerThree = getLayerByIndex(2);
			layerThree.nextDialogue();
		}
	}
	
	private class NextSceneEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			gameScreen.nextScene();
		}
	}
	
	public SecondScene(GameScreen gameScreen) {
		super(3);
		
		this.gameScreen = gameScreen;
		canSwitchLayers = false;
		
		try {
			setLayer(0, new Layer("layerone.png", ""));
			setLayer(1, new Layer("layertwo.png", ""));
			setLayer(2, new Layer("layerthree.png", ""));	
		}
		catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		NextLayerOneDialogueEvent nde = new NextLayerOneDialogueEvent();
		
		Layer layerOne = getLayerByIndex(0);
		layerOne.setShowDialogue(true);
		layerOne.addDialogue("It feels like it's been hours.", "blue", nde);
		layerOne.addDialogue("We're not there yet, but we're getting closer. Can't you feel it?", "yellow", nde);
		layerOne.addDialogue("Not really, you still haven't told us why we're wasting our time like this.", "gray", nde);
		layerOne.addDialogue("You'll understand when we get there.", "yellow", new AllowMovementHandler());
		
		Layer layerTwo = getLayerByIndex(1);
		layerTwo.addItem("demo.png", 800, 600, new LayerTwoItemHandler());
		layerTwo.addDialogue("This seems a little out of place...", "blue");
		
		NextLayerThreeDialogueEvent nltde = new NextLayerThreeDialogueEvent();
		
		Layer layerThree = getLayerByIndex(2);
		layerThree.addDialogue("Hey! Where did you get that?", "gray", nltde);
		layerThree.addDialogue("Is this yours?", "blue", nltde);
		layerThree.addDialogue("It must have fallen somewhere along the way.", "gray", nltde);
		layerThree.addDialogue("Maybe this is what you're looking for?", "blue", nltde);
		layerThree.addDialogue("Let me see.", "yellow", nltde);
		layerThree.addDialogue("...", "yellow", nltde);
		layerThree.addDialogue("No, that's not it.", "yellow", nltde);
		layerThree.addDialogue("You just read it didn't you?", "gray", nltde);
		layerThree.addDialogue("I'm sorry, I couldn't help it.", "yellow", nltde);
		layerThree.addDialogue("If I knew you felt that way, I wouldnt have handed it over.", "blue", nltde);
		layerThree.addDialogue("**snatches paper**", "black", nltde);
		layerThree.addDialogue("You didn't see anything.", "black", nltde);
		layerThree.addDialogue("If you say so. We still have to keep going. Come on.", "yellow", new NextSceneEvent());
	}
	
	public boolean changeLayer(int i) {
		if(canSwitchLayers) {
			return super.changeLayer(i);
		}
		else {
			return false;
		}
	}
	
	public boolean nextLayer() {
		if(canSwitchLayers) {
			return super.nextLayer();
		}
		else {
			return false;
		}
	}
}
