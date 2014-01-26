package com.agmcleod.ggj2014;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
	
	public static void setTextOffsets() {
		LeftTextPadding = (Gdx.graphics.getWidth() - MAX_TEXT_WIDTH) / 2;
		BottomTextPadding = (MAX_HEIGHT - MAX_TEXT_HEIGHT) / 2;
	}
	private DialogueCompleteEvent completeEvent;
	private int currentPart;
	private BitmapFont font;
	private Layer layer;
	private Array<Array<String>> parts;
	private Vector2 pos;
	private boolean showBackdrop;
	private String text;
	
	private Texture texture;

	public Dialogue(Layer layer, BitmapFont font, String text) {
		this.font = font;
		this.layer = layer;
		parts = new Array<Array<String>>();
		setText(text);
		pos = new Vector2(0, 0);
		texture = new Texture(Gdx.files.internal("data/messagebox.png"));
		showBackdrop = true;
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public DialogueCompleteEvent getCompleteEvent() {
		return completeEvent;
	}
	
	public String getText() {
		return text;
	}
	
	public void nextPart() {
		if(currentPart + 1 >= parts.size) {
			if(getCompleteEvent() != null) {
				getCompleteEvent().complete();
			}
			else {
				layer.setShowDialogue(false);
			}
		}
		else {
			currentPart++;
		}
	}

	public void render(SpriteBatch batch) {
		Array<String> part = parts.get(currentPart);
		float y = BottomTextPadding;
		
		if(showBackdrop) {
			batch.draw(texture, 0, 0);
		}
		Iterator<String> iter = part.iterator();
		float i = 1;
		while(iter.hasNext()) {
			String line = iter.next();
			y = MAX_HEIGHT - BottomTextPadding - (i * (font.getBounds(line).height + LINE_OFFSET));
			font.draw(batch, line, LeftTextPadding, y);
			i++;
		}
	}

	public void setCompleteEvent(DialogueCompleteEvent completeEvent) {
		this.completeEvent = completeEvent;
	}
	
	public void setShowBackdrop(boolean showBackdrop) {
		this.showBackdrop = showBackdrop;
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
		if(!line.equals("")) {
			lines.add(line);
		}
		parts.add(lines);
	}
}
