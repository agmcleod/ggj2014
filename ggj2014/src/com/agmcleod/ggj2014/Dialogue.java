package com.agmcleod.ggj2014;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Dialogue {
	private final int MAX_TEXT_WIDTH = 974;
	private final int MAX_HEIGHT = 300;
	private final int MAX_TEXT_HEIGHT = 250;
	public static int LeftTextPadding;
	
	private DialogueCompleteEvent completeEvent;
	private BitmapFont font;
	private int parts;
	private Vector2 pos;
	private String text;
	private Array<String> lines;

	public Dialogue(BitmapFont font, String text) {
		this.font = font;
		lines = new Array<String>();
		setText(text);
		pos = new Vector2(0, 0);  
	}
	
	public void dispose() {
		
	}
	
	public void render(SpriteBatch batch) {
		Iterator<String> iter = lines.iterator();
		while(iter.hasNext()) {
			String line = iter.next();
			
			font.draw(batch, line, x, y);
		}
	}
	
	public void setTextOffsets() {
		Dialogue.LeftTextPadding = Gdx.graphics.getWidth();
	}
	
	public void update(float delta) {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		String[] words = text.split(" ");
		lines.clear();
		
		int lineWidth = 0;
		String line = "";
		for(int i = 0; i < words.length; i++) {
			String word = words[i];
			lineWidth += font.getBounds(word + " ").width;
			if(lineWidth > MAX_TEXT_WIDTH) {
				lines.add(line);
				line = word;
				lineWidth = 0;
			}
			else {
				line += word + " ";
			}
		}
	}

	public DialogueCompleteEvent getCompleteEvent() {
		return completeEvent;
	}

	public void setCompleteEvent(DialogueCompleteEvent completeEvent) {
		this.completeEvent = completeEvent;
	}
}
