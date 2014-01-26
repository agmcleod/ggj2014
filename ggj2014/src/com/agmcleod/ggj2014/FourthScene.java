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
	
	private boolean allInPlace;
	private boolean bearItemPlaced;
	private Texture bearTexture;
	private Texture blankTexture;
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
	private float endStart = 0;
	private boolean fadeToEnd = false;
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
		
		Layer layer = new Layer();
		layer.setMusicFileName("Title.mp3");
		setLayer(0, layer);
		
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
		blankTexture = new Texture(Gdx.files.internal("data/production/stage4/blank.png"));
		
		blueImage = new Texture(Gdx.files.internal("data/production/stage4/blue.png"));
		greyImage = new Texture(Gdx.files.internal("data/production/stage4/grey.png"));
		yellowImage = new Texture(Gdx.files.internal("data/production/stage4/yellow.png"));
		
		pos = new Vector2(Gdx.graphics.getWidth() - inventory.getWidth(), Gdx.graphics.getHeight() - inventory.getHeight());
		collisionMap = new ObjectMap<Rectangle, String>();
		collisionMap.put(new Rectangle(331, 235, 32, 45), "ring");
		collisionMap.put(new Rectangle(442, 226, 166, 217), "bear");
		collisionMap.put(new Rectangle(699, 177, 105, 155), "paper");
		musicPlayString = "";
		allInPlace = false;
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
		blankTexture.dispose();
	}
	
	public float getEndStart() {
		return endStart;
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
				setMusic();
			}
		}
	}
	
	public boolean nextLayer() {
		return false;
	}
	
	public void render(SpriteBatch batch, float delta) {
		batch.draw(blankTexture, 0, 0);
		if(fadeToEnd) {
			if(endStart < Game.END_FADE) {
				endStart += delta;
			}
		}
		if(bearItemPlaced) {
			batch.draw(blueImage, 0, 0);
		}
		if(paperItemPlaced) {
			batch.draw(greyImage, 0, 0);
		}
		if(ringItemPlaced) {
			batch.draw(yellowImage, 0, 0);
		}
		
		if(!bearItemPlaced || !paperItemPlaced || !ringItemPlaced) {
			batch.draw(inventory, pos.x, pos.y);
		}
		if(!bearItemPlaced){
			batch.draw(bearTexture, pos.x + 10, pos.y + 10 + (2 * bearTexture.getHeight()));
		}
		
		if(!paperItemPlaced) {
			batch.draw(paperTexture, pos.x + 10, pos.y + 10 + (1 * paperTexture.getHeight()));
		}
		
		if(!ringItemPlaced) {
			batch.draw(ringTexture, pos.x + 10, pos.y + 10 + (0 * ringTexture.getHeight()));
		}
		
		if(bearItemPlaced && paperItemPlaced && ringItemPlaced) {
			if(!allInPlace) {
				endStart += delta;
				if(endStart >= 2f) {
					allInPlace = true;
					startNewDialogue();
				}
			}
		}
		getLayers()[0].render(batch);
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
	
	public void startNewDialogue() {
		Layer layer = getLayers()[0];
		layer.getDialogues().clear();
		NextLayerOneDialogueEvent nltde = new NextLayerOneDialogueEvent();
		
		DialogueCompleteEvent fadeOutEvent = new DialogueCompleteEvent() {
			@Override
			public void complete() {
				setFadeToEnd(true);
				getLayers()[0].setShowDialogue(false);
				currentMusic.stop();
				currentMusic.dispose();
				currentMusic = Gdx.audio.newMusic(Gdx.files.internal("data/scene4/finale.mp3"));
				currentMusic.play();
				endStart = 0;
			}
		};
		
		layer.addDialogue("This is...", "green", nltde);
		layer.addDialogue("From so long ago.", "green", nltde);
		layer.addDialogue("I remember it like it was yesterday. We were all there.", "green", nltde);
		layer.addDialogue("Is this what we were looking for?", "green", nltde);
		layer.addDialogue("...", "green", nltde);
		layer.addDialogue("Yeah. It was.", "green", nltde);
		layer.addDialogue("I needed to see it just one more time.", "green", nltde);
		layer.addDialogue("So I could find those feelings again.", "green", nltde);
		layer.addDialogue("I think I'll keep it this time.", "green", nltde);
		layer.addDialogue("We should move on though. We shouldn't dwell here any longer than we need to.", "green", nltde);
		layer.addDialogue("This time I won't forget", "green", fadeOutEvent);
		layer.setShowDialogue(true);
	}

	public boolean fadeToEnd() {
		return fadeToEnd;
	}

	public void setFadeToEnd(boolean fadeToEnd) {
		this.fadeToEnd = fadeToEnd;
	}
}
