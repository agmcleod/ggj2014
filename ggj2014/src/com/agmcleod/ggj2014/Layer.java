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
	
	private BitmapFont blueFont;
	private int currentDialogue;
	private Array<Dialogue> dialogues;
	private BitmapFont font;
	private BitmapFont grayFont;
	private Array<Item> items;
	private Music music;
	private String musicFileName;
	private boolean showDialogue;
	private Texture texture;
	private BitmapFont yellowFont;
	
	public Layer() {
		dialogues = new Array<Dialogue>();
		currentDialogue = 0;
		showDialogue = false;
		font = new BitmapFont(Gdx.files.internal("data/layeronefont.fnt"), Gdx.files.internal("data/layeronefont.png"), false);
		yellowFont = new BitmapFont(Gdx.files.internal("data/yellowfont.fnt"), Gdx.files.internal("data/yellowfont.png"), false);
		blueFont = new BitmapFont(Gdx.files.internal("data/bluefont.fnt"), Gdx.files.internal("data/bluefont.png"), false);
		grayFont = new BitmapFont(Gdx.files.internal("data/grayfont.fnt"), Gdx.files.internal("data/grayfont.png"), false);
	}
	
	public Layer(String filename, String musicFileName) {
		texture = new Texture(Gdx.files.internal("data/" + filename));
		font = new BitmapFont(Gdx.files.internal("data/layeronefont.fnt"), Gdx.files.internal("data/layeronefont.png"), false);
		dialogues = new Array<Dialogue>();
		items = new Array<Item>();
		showDialogue = false;
		currentDialogue = 0;
		this.musicFileName = musicFileName;
	}
	
	public void addDialogue(String text) {
		dialogues.add(new Dialogue(this, font, text));
	}
	
	public void addDialogue(String text, String color) {
		dialogues.add(new Dialogue(this, chosenFont(color), text));
	}
	
	public void addDialogue(String text, String color, DialogueCompleteEvent completeEvent) {
		Dialogue dialogue = new Dialogue(this, chosenFont(color), text);
		dialogue.setCompleteEvent(completeEvent);
		dialogues.add(dialogue);
	}
	
	public BitmapFont chosenFont(String color) {
		BitmapFont chosenFont;
		if(color.equals("blue")) {
			chosenFont = blueFont;
		}
		else if(color.equals("gray")) {
			chosenFont = grayFont;
		}
		else if(color.equals("yellow")) {
			chosenFont = yellowFont;
		}
		else {
			chosenFont = font;
		}
		return chosenFont;
	}
	
	public void dispose() {
		texture.dispose();
		font.dispose();
		blueFont.dispose();
		yellowFont.dispose();
		grayFont.dispose();
		Iterator<Dialogue> it = dialogues.iterator();
		while(it.hasNext()) {
			it.next().dispose();
		}
	}
	
	public Dialogue getCurrentDialogue() {
		return dialogues.get(currentDialogue);
	}
	
	public Array<Dialogue> getDialogues() {
		return dialogues;
	}
	
	public BitmapFont getFont() {
		return font;
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
		getCurrentDialogue().nextPart();
	}
	
	public void render(SpriteBatch batch) {
		if(texture != null) {
			batch.draw(texture, 0, 0);
		}

		if(showDialogue()) {
			dialogues.get(currentDialogue).render(batch);
		}
	}

	public void setShowDialogue(boolean showDialogue) {
		this.showDialogue = showDialogue;
	}
	
	public boolean showDialogue() {
		return showDialogue;
	}
	
	public void startMusic() {
		/* music = Gdx.audio.newMusic(Gdx.files.internal("data/" + musicFileName));
		music.play(); */
	}
	
	public void stopMusic() {
		/* music.stop();
		music.dispose(); */
	}

	public void update(float delta) {
		if(showDialogue) {
			dialogues.get(currentDialogue).update(delta);
		}
	}
}
