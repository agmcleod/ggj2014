package com.agmcleod.ggj2014;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class FourthScene extends Scene {
	
	private boolean bearItemPlaced;
	private Texture bearTexture;
	private Texture blueImage;
	private boolean canSwitchLayers;
	private ObjectMap<Rectangle, String> collisionMap;
	private Music currentMusic;
	private GameScreen gameScreen;
	private Texture greyImage;
	private Texture inventory;
	private String musicPlayString;
	private boolean paperItemPlaced;
	private Texture paperTexture;
	private Vector2 pos;
	private boolean ringItemPlaced;
	private Texture ringTexture;
	private Texture yellowImage;
	
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
		
		setLayer(0, new Layer("stage4/blank.png", "Title.mp3"));
		
		NextLayerOneDialogueEvent nltde = new NextLayerOneDialogueEvent();
		
		Layer layerOne = getLayerByIndex(0);
		layerOne.setShowDialogue(true);
		layerOne.addDialogue("This is it?", "blue", nltde, false);
		layerOne.addDialogue("Yeah.", "yellow", nltde, false);
		layerOne.addDialogue("I don't get it. It's like we came here for nothing.", "gray", nltde, false);
		layerOne.addDialogue("Now or never I suppose.", "blue", new AllowMovementHandler(), false);
		canSwitchLayers = false;
		inventory = new Texture(Gdx.files.internal("data/inventory.png"));
		bearTexture = new Texture(Gdx.files.internal("data/production/items/inventory/bear.png"));
		paperTexture = new Texture(Gdx.files.internal("data/production/items/inventory/paper.png"));
		ringTexture = new Texture(Gdx.files.internal("data/production/items/inventory/ring.png"));
		
		blueImage = new Texture(Gdx.files.internal("data/production/stage4/blue.png"));
		greyImage = new Texture(Gdx.files.internal("data/production/stage4/grey.png"));
		yellowImage = new Texture(Gdx.files.internal("data/production/stage4/yellow.png"));
		
		pos = new Vector2(Gdx.graphics.getWidth() - inventory.getWidth(), Gdx.graphics.getHeight() - inventory.getHeight());
		collisionMap = new ObjectMap<Rectangle, String>();
		collisionMap.put(new Rectangle(331, 235, 32, 45), "ring");
		collisionMap.put(new Rectangle(442, 226, 166, 217), "bear");
		collisionMap.put(new Rectangle(699, 177, 105, 155), "paper");
		musicPlayString = "";
	}
	
	public boolean changeLayer(int i) {
		return false;
	}
	
	public void dispose() {
		getLayerByIndex(0).dispose();
		inventory.dispose();
		bearTexture.dispose();
		paperTexture.dispose();
		ringTexture.dispose();
		blueImage.dispose();
		greyImage.dispose();
		yellowImage.dispose();
	}
	
	@Override
	public void handleMousePress(int x, int y) {
		Iterator<Rectangle> iter = collisionMap.keys().iterator();
		while(iter.hasNext()) {
			Rectangle r = iter.next();
			if(r.contains(x, y)) {
				String value = collisionMap.get(r);
				if(value.equals("bear")) {
					bearItemPlaced = true;
					musicPlayString = "blue" + musicPlayString;
				}
				else if(value.equals("paper")) {
					paperItemPlaced = true;
					if(musicPlayString.equals("")) {
						musicPlayString = "grey";
					}
					else if(musicPlayString.equals("yellow")) {
						musicPlayString = "greyyellow";
					}
					else if(musicPlayString.equals("blueyellow")) {
						musicPlayString = "bluegreyyellow";
					}
				}
				else if(value.equals("ring")) {
					ringItemPlaced = true;
					musicPlayString += "yellow";
				}
				System.out.println(musicPlayString);
				setMusic();
			}
		}
	}
	
	public boolean nextLayer() {
		return false;
	}
	
	public void render(SpriteBatch batch) {
		getLayers()[0].render(batch);
		batch.draw(inventory, pos.x, pos.y);
		if(bearItemPlaced) {
			batch.draw(blueImage, 0, 0);
		}
		if(paperItemPlaced) {
			batch.draw(greyImage, 0, 0);
		}
		if(ringItemPlaced) {
			batch.draw(yellowImage, 0, 0);
		}
		
		if (!bearItemPlaced){
			batch.draw(bearTexture, pos.x + 10, pos.y + 10 + (2 * bearTexture.getHeight()));
		}
		
		if(!paperItemPlaced) {
			batch.draw(paperTexture, pos.x + 10, pos.y + 10 + (1 * paperTexture.getHeight()));
		}
		
		if(!ringItemPlaced) {
			batch.draw(ringTexture, pos.x + 10, pos.y + 10 + (0 * ringTexture.getHeight()));
		}
	}
	
	public void setMusic() {
		if(currentMusic != null) {
			currentMusic.stop();
			currentMusic.dispose();
		}
		else {
			Layer one = getLayers()[0];
			one.stopMusic();
		}
		currentMusic = Gdx.audio.newMusic(Gdx.files.internal("data/scene4/" + musicPlayString + ".mp3"));
		currentMusic.play();
	}
}
