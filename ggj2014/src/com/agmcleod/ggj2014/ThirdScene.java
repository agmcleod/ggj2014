package com.agmcleod.ggj2014;

public class ThirdScene extends Scene {

	private boolean canSwitchLayers;
	private GameScreen gameScreen;
	
	private class AllowMovementHandler implements DialogueCompleteEvent {
		@Override
		public void complete() {
			canSwitchLayers = true;
			Layer layerThree = getLayerByIndex(2);
			layerThree.setShowDialogue(false);
		}
	}

	private class LayerOneItemHandler implements ItemClickEvent {

		@Override
		public void execute() {
			Layer layerOne = getLayerByIndex(0);
			layerOne.getItems().clear();
			layerOne.setShowDialogue(true);

			Layer layerTwo = getLayerByIndex(1);
			layerTwo.setShowDialogue(true);
		}
	}

	private class NextLayerTwoDialogueEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			Layer layer = getLayerByIndex(1);
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


	public ThirdScene(GameScreen gameScreen) {
		super(3);
		this.gameScreen = gameScreen;
		canSwitchLayers = false;
		setCurrentLayer(2);

		try {
			setLayer(0, new Layer("layerone.png"));
			setLayer(1, new Layer("layertwo.png"));
			setLayer(2, new Layer("layerthree.png"));
		}
		catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		NextLayerTwoDialogueEvent layerTwoEvent = new NextLayerTwoDialogueEvent();
		NextLayerThreeDialogueEvent layerThreeEvent = new NextLayerThreeDialogueEvent();

		Layer layerOne = getLayerByIndex(0);
		layerOne.addItem("demo.png", 800, 20, new LayerOneItemHandler());
		layerOne.addDialogue("Wow, I haven't seen this in a long time. I wonder...", "yellow");

		Layer layerTwo = getLayerByIndex(1);
		layerTwo.addDialogue("Hey!", "yellow", layerTwoEvent);
		layerTwo.addDialogue("Yeah?", "blue", layerTwoEvent);
		layerTwo.addDialogue("This is yours isn't it?!", "yellow", layerTwoEvent);
		layerTwo.addDialogue("It's... oh my. Yeah, it is. I haven't seen it in years.", "blue", layerTwoEvent);
		layerTwo.addDialogue("Looks a little run-down", "gray", layerTwoEvent);
		layerTwo.addDialogue("No, it's not!", "blue", layerTwoEvent);
		layerTwo.addDialogue("The eye is falling out and half of the stuffing is gone.", "gray", layerTwoEvent);
		layerTwo.addDialogue("It adds character!", "blue", layerTwoEvent);
		layerTwo.addDialogue("It's filthy. You shoudl throw it away.", "gray", layerTwoEvent);
		layerTwo.addDialogue("Just let me have this. We gave you back that silly little piece of paper.", "gray", layerTwoEvent);
		layerTwo.addDialogue("...Fine", "gray", layerTwoEvent);
		layerTwo.addDialogue("...we're here.", "yellow", new NextSceneEvent());

		Layer layerThree = getLayerByIndex(2);
		layerThree.setShowDialogue(true);
		layerThree.addDialogue("Don't you think it's been long enough? I'm so tired.", "gray", layerThreeEvent);
		layerThree.addDialogue("Not much longer.", "yellow", layerThreeEvent);
		layerThree.addDialogue("I think I can see it now.", "blue", layerThreeEvent);
		layerThree.addDialogue("I'm glad you're starting to see it, too.", "yellow", new AllowMovementHandler());
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
