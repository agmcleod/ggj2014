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
	private Color endColor;
	private BitmapFont font;
	private Array<Array<String>> parts;
	private Vector2 pos;
	private Color startColor;
	private String text;
	
	private Texture texture;

	public Dialogue(BitmapFont font, String text) {
		this.font = font;
		parts = new Array<Array<String>>();
		setText(text);
		pos = new Vector2(0, 0);
		texture = new Texture(Gdx.files.internal("data/messagebox.png"));
		endColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		startColor = new Color(1.0f, 1.0f, 1.0f, 0f);
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public void fadeIn() {
		startColor.a = 0f;
	}
	
	public DialogueCompleteEvent getCompleteEvent() {
		return completeEvent;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean nextPart() {
		currentPart++;
		fadeIn();
		return currentPart < parts.size;
	}

	public void render(SpriteBatch batch) {
		Array<String> part = parts.get(currentPart);
		float y = BottomTextPadding;
		
		if(startColor.a < 1f) {
			batch.setColor(startColor);
			font.setColor(startColor);
		}
		batch.draw(texture, 0, 0);
		
		Iterator<String> iter = part.iterator();
		float i = 1;
		while(iter.hasNext()) {
			String line = iter.next();
			y = MAX_HEIGHT - BottomTextPadding - (i * (font.getBounds(line).height + LINE_OFFSET));
			font.draw(batch, line, LeftTextPadding, y);
			i++;
		}
		
		if(startColor.a < 1f) {
			batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	public void setCompleteEvent(DialogueCompleteEvent completeEvent) {
		this.completeEvent = completeEvent;
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
	
	public void update(float delta) {
		if(startColor.a < 1f) {
			startColor.lerp(endColor, delta);
		}
	}
}
