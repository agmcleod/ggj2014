package com.agmcleod.ggj2014;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Layer {
	
	private int currentDialogue;
	private Array<Dialogue> dialogues;
	private BitmapFont font;
	private Array<Item> items;
	private Music music;
	private String musicFileName;
	private boolean showDialogue;
	private Texture texture;
	
	public Layer(String filename, String musicFileName) {
		texture = new Texture(Gdx.files.internal("data/" + filename));
		font = new BitmapFont(Gdx.files.internal("data/layeronefont.fnt"), Gdx.files.internal("data/layeronefont.png"), false);
		dialogues = new Array<Dialogue>();
		items = new Array<Item>();
		showDialogue = false;
		currentDialogue = 0;
		this.musicFileName = musicFileName;
	}
	
	public void addDialogue(Dialogue dialogue) {
		dialogues.add(dialogue);
	}
	
	public void dispose() {
		texture.dispose();
		font.dispose();
		Iterator<Dialogue> it = dialogues.iterator();
		while(it.hasNext()) {
			it.next().dispose();
		}
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
		if(!dialogues.get(currentDialogue).nextPart()) {
			showDialogue = false;
		}
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, 0, 0);
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
