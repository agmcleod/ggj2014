package com.agmcleod.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Layer {
	
	private int currentDialogue;
	private Array<Dialogue> dialogues;
	private BitmapFont font;
	private boolean showDialogue;
	private Texture texture;
	
	public Layer(String filename) {
		texture = new Texture(Gdx.files.internal("data/" + filename));
		font = new BitmapFont(Gdx.files.internal("data/layeronefont.fnt"), Gdx.files.internal("data/layeronefont.png"), false);
		dialogues = new Array<Dialogue>();
		showDialogue = false;
		currentDialogue = 0;
	}
	
	public void addDialogue(Dialogue dialogue) {
		dialogues.add(dialogue);
	}
	
	public void dispose() {
		texture.dispose();
		font.dispose();
	}
	
	public BitmapFont getFont() {
		return font;
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

	public void update(float delta) {
		
	}
}
