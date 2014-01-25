package com.agmcleod.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Layer {
	
	private BitmapFont font;
	private Texture texture;
	private Array<Dialogue> dialogues;
	
	public Layer(String filename) {
		texture = new Texture(Gdx.files.internal("data/" + filename));
		Texture fontTexture = new Texture(Gdx.files.internal("data/layeronefont.png"));
		TextureRegion fontTextureRegion = new TextureRegion(fontTexture);
		font = new BitmapFont(Gdx.files.internal("data/layeronefont.fnt"), fontTextureRegion);
		fontTexture.dispose();
		dialogues = new Array<Dialogue>();
	}
	
	public void addDialogue(Dialogue dialogue) {
		dialogues.add(dialogue);
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, 0, 0);
		font.dispose();
	}
	
	public void update(float delta) {
		
	}
}
