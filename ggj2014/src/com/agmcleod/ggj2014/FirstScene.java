package com.agmcleod.ggj2014;

public class FirstScene extends Scene {
	
	class LayerThreeItemHandler implements ItemClickEvent {

		@Override
		public void execute() {
			Layer layerThree = getLayerByIndex(2);
			layerThree.getItems().clear();
			
			Layer layerOne = getLayerByIndex(0);
			layerOne.setShowDialogue(true);
		}
	}
	
	public class NextDialogueEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			Layer layer = getLayerByIndex(0);
			layer.nextDialogue();
		}
	}
	
	public class NextSceneEvent implements DialogueCompleteEvent {
		@Override
		public void complete() {
			
		}
	}
	
	public FirstScene() {
		super(3);
		
		try {
			setLayer(0, new Layer("layerone.png", ""));
			setLayer(1, new Layer("layertwo.png", ""));
			setLayer(2, new Layer("layerthree.png", ""));	
		}
		catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		Layer layerThree = getLayerByIndex(2);
		LayerThreeItemHandler handler = new LayerThreeItemHandler();
		layerThree.addItem("demo.png", 300, 300, handler);
		
		NextDialogueEvent nde = new NextDialogueEvent();
		
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
		layerOne.addDialogue("It's been passed down through the family", "yellow", nde);
		layerOne.addDialogue("So can we go now? You've found what you're looking for right?", "gray", nde);
		layerOne.addDialogue("This isn't what I'm looking for. I'm happy to have found it, but there's something else. We need to keep going", "yellow", nde);
		layerOne.addDialogue("I don't like this.", "gray", new NextSceneEvent());
	}
}
