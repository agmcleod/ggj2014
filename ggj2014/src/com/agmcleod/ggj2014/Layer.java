package com.agmcleod.ggj2014;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Layer {
	
	private boolean acceptsItem;
	private int currentDialogue;
	private Array<Dialogue> dialogues;
	private Array<Item> items;
	private Music music;
	private String musicFileName;
	private boolean showDialogue;
	private Texture texture;
	
	public Layer() {
		genericInitialize();
	}
	
	public Layer(String filename) {
		genericInitialize();
		texture = new Texture(Gdx.files.internal("data/" + filename));
	}
	
	public Layer(String filename, String musicFileName) {
		genericInitialize();
		texture = new Texture(Gdx.files.internal("data/" + filename));
		this.musicFileName = musicFileName;
	}
	
	public boolean acceptsItem() {
		return acceptsItem;
	}
	
	public void addDialogue(String text) {
		dialogues.add(new Dialogue(this, Game.font, text));
	}
	
	public void addDialogue(String text, String color) {
		dialogues.add(new Dialogue(this, chosenFont(color), text));
	}
	
	public void addDialogue(String text, String color, DialogueCompleteEvent completeEvent) {
		Dialogue dialogue = new Dialogue(this, chosenFont(color), text);
		dialogue.setCompleteEvent(completeEvent);
		dialogues.add(dialogue);
	}
	
	public void addItem(String textureName, int x, int y, int width, int height, ItemClickEvent itemClickEvent) {
		Item item = new Item(textureName, this, x, y, width, height);
		item.setItemClickEvent(itemClickEvent);
		items.add(item);
	}
	
	public BitmapFont chosenFont(String color) {
		BitmapFont chosenFont;
		if(color.equals("blue")) {
			chosenFont = Game.blueFont;
		}
		else if(color.equals("gray")) {
			chosenFont = Game.grayFont;
		}
		else if(color.equals("yellow")) {
			chosenFont = Game.yellowFont;
		}
		else if(color.equals("blueitalic")) {
			chosenFont = Game.blueFontItalic;
		}
		else if(color.equals("grayitalic")) {
			chosenFont = Game.grayFontItalic;
		}
		else if(color.equals("yellowitalic")) {
			chosenFont = Game.yellowFontItalic;
		}
		else {
			chosenFont = Game.font;
		}
		return chosenFont;
	}
	
	public void dispose() {
		Iterator<Dialogue> it = dialogues.iterator();
		while(it.hasNext()) {
			it.next().dispose();
		}
		if(isMusicPlaying()) {
			stopMusic();
		}
	}
	
	public void genericInitialize() {
		dialogues = new Array<Dialogue>();
		items = new Array<Item>();
		currentDialogue = 0;
		showDialogue = false;
	}
	
	public Dialogue getCurrentDialogue() {
		return dialogues.get(currentDialogue);
	}
	
	public Array<Dialogue> getDialogues() {
		return dialogues;
	}
	
	public Array<Item> getItems() {
		return items;
	}
	
	public void handleMouseHover(int x, int y) {
		Iterator<Item> it = items.iterator();
		while(it.hasNext()) {
			Item item = it.next();
			if(item.containsPoint(x, y)) {
				item.onHover();
			}
			else {
				item.onHoverOut();
			}
		}
	}
	
	public void handleMousePress(int x, int y) {
		Iterator<Item> it = items.iterator();
		while(it.hasNext()) {
			Item item = it.next();
			if(item.containsPoint(x, y)) {
				item.onClick();
			}
		}
	}
	
	public boolean isMusicPlaying() {
		return music != null && music.isPlaying();
	}
	
	public void nextDialogue() {
		currentDialogue++;
	}
	
	public void progressDialogue() {
		if(currentDialogue < getDialogues().size) {
			getCurrentDialogue().nextPart();
		}
	}

	public void render(SpriteBatch batch) {
		if(texture != null) {
			batch.draw(texture, 0, 0);
		}
		
		Iterator<Item> it = items.iterator();
		while(it.hasNext()) {
			Item item = it.next();
			item.render(batch);
		}

		if(showDialogue()) {
			dialogues.get(currentDialogue).render(batch);
		}
	}
	
	public void setAcceptsItem(boolean acceptsItem) {
		this.acceptsItem = acceptsItem;
	}
	
	public void setShowDialogue(boolean showDialogue) {
		this.showDialogue = showDialogue;
	}
	
	public boolean showDialogue() {
		return showDialogue;
	}

	public void startMusic() {
		if(musicFileName != null && !musicFileName.equals("")) {
			music = Gdx.audio.newMusic(Gdx.files.internal("data/" + musicFileName));
			music.play();
		}
	}

	public void stopMusic() {
		if(isMusicPlaying()) {
			music.stop();
			music.dispose();
		}
	}

	public void update(float delta) {
		if(showDialogue) {
			dialogues.get(currentDialogue).update(delta);
		}
	}
}
