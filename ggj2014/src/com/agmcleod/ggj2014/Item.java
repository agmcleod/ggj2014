package com.agmcleod.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Item {
	private TextureRegion hoverRegion;
	private TextureRegion idleRegion;
	private ItemClickEvent itemClickEvent;
	private Layer layer;
	private Vector2 pos;
	private Rectangle rect;
	private Texture texture;
	
	private boolean useHoverImage;
	
	public Item(String textureName, Layer layer, int x, int y) {
		texture = new Texture(Gdx.files.internal("data/" + textureName));
		this.layer = layer;
		pos = new Vector2(x, y);
		rect = new Rectangle(pos.x, pos.y, texture.getWidth(), texture.getHeight());
		useHoverImage = false;
		idleRegion = new TextureRegion(texture, 0, 0, 128, 128);
	}
	
	public boolean containsPoint(int x, int y) {
		return rect.contains(x, y);
	}
	
	public ItemClickEvent getItemClickEvent() {
		return itemClickEvent;
	}
	
	public void onClick() {
		if(itemClickEvent != null) {
			itemClickEvent.execute();
		}
	}
	
	public void onHover() {
		useHoverImage = true;
	}

	public void onHoverOut() {
		useHoverImage = false;
	}
	
	public void setItemClickEvent(ItemClickEvent itemClickEvent) {
		this.itemClickEvent = itemClickEvent;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, pos.x, pos.y);
	}
}
