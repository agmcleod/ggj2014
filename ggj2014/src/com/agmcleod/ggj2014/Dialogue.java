package com.agmcleod.ggj2014;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Dialogue {
	private static final int LINE_OFFSET = 10;
	private static final int MAX_TEXT_WIDTH = 940;
	private static final int MAX_HEIGHT = 300;
	private static final int MAX_TEXT_HEIGHT = 250;
	public static int LeftTextPadding;
	public static int BottomTextPadding;
	
	private DialogueCompleteEvent completeEvent;
	private int currentPart;
	private BitmapFont font;
	private Array<Array<String>> parts;
	private Vector2 pos;
	private String text;
	
	
	public static void setTextOffsets() {
		LeftTextPadding = (Gdx.graphics.getWidth() - MAX_TEXT_WIDTH) / 2;
		BottomTextPadding = (MAX_HEIGHT - MAX_TEXT_HEIGHT) / 2;
	}

	public Dialogue(BitmapFont font, String text) {
		this.font = font;
		parts = new Array<Array<String>>();
		setText(text);
		pos = new Vector2(0, 0);
	}
	
	public void dispose() {
		
	}
	
	public boolean nextPart() {
		currentPart++;
		return currentPart < parts.size;
	}
	
	public void render(SpriteBatch batch) {
		Array<String> part = parts.get(currentPart);
		float y = 0;
		float i = 1;
		Iterator<String> iter = part.iterator();
		while(iter.hasNext()) {
			String line = iter.next();
			y = MAX_TEXT_HEIGHT - (i * (font.getBounds(line).height + LINE_OFFSET));
			font.draw(batch, line, LeftTextPadding, y);
			i++;
		}
	}
	
	public void update(float delta) {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		String[] words = text.split(" ");
		parts.clear();
		currentPart = 0;
		
		float lineWidth = 0;
		String line = "";
		float totalHeight = 0;
		Array<String> lines = new Array<String>();
		for(int i = 0; i < words.length; i++) {
			String word = words[i];
			lineWidth += font.getBounds(word + " ").width;
			if(lineWidth >= MAX_TEXT_WIDTH) {
				float height = font.getBounds(line).height + LINE_OFFSET;
				totalHeight += height;
				if(totalHeight + height >= MAX_TEXT_HEIGHT) {
					parts.add(lines);
					lines = new Array<String>();
					totalHeight = 0;
				}
				lines.add(line);
				line = word + " ";
				lineWidth = font.getBounds(line).width;
			}
			else {
				line += word + " ";
			}
		}
		parts.add(lines);
	}

	public DialogueCompleteEvent getCompleteEvent() {
		return completeEvent;
	}

	public void setCompleteEvent(DialogueCompleteEvent completeEvent) {
		this.completeEvent = completeEvent;
	}
}
