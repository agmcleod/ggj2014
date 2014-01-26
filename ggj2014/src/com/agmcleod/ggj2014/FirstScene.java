package com.agmcleod.ggj2014;

public class FirstScene extends Scene {
	
	private boolean hasItemFromLayerThree;
	
	class LayerThreeItemHandler implements ItemClickEvent {

		@Override
		public void execute() {
			hasItemFromLayerThree = true;
			Layer layerThree = getLayerByIndex(2);
			layerThree.getItems().clear();
		}
	}
	
	public FirstScene() {
		super(3);
		
		hasItemFromLayerThree = false;
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
	}
}
