package me.reckter.Level.Tiles;

import me.reckter.Engine.Engine;
import me.reckter.Entity.BaseEntity;
import me.reckter.Level.BaseLevel;
import me.reckter.Util;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseTile {

	public int x;
	public int y;

	protected int backgroundOffsetX;
	protected int backgroundOffsetY;

	protected int overlayOffsetX;
	protected int overlayOffsetY;

	protected BaseLevel level;

	protected Image terrain;
	protected Image overlay;

	public boolean isHighligthed;

	public BaseTile(BaseLevel level, int x, int y){
		this.level = level;
		this.x = x;
		this.y = y;
	}

	/**
	 * gets the Collision box of the tile
	 * @return Rectangle descibing the tile
	 */
	public Rectangle getCollisionBox(){
		return new Rectangle(x, y, Engine.SPRITE_SIZE, Engine.SPRITE_SIZE);
	}

	public int getMiddleX(){
		return x + Engine.SPRITE_SIZE / 2;
	}

	public int getMiddleY(){
		return y + Engine.SPRITE_SIZE / 2;
	}

	/**
	 * Check a collision with an entity's collision box
	 * @param with Entity to check
	 * @return True in case of collision
	 */
	public boolean checkColl(BaseEntity with) {
		return Util.checkColl(getCollisionBox(), with.getCollisionBox());
	}

	/**
	 * Check if the tile can collide with the entity
	 * @param with the entity
	 * @return true if it is possible for the tile to collide woth the entity
	 */
	public boolean wouldColide(BaseEntity with){

		double xt = with.x;
		double yt = with.y;

		with.x = getMiddleX();
		with.y = getMiddleY();

		boolean ret = checkColl(with);
		with.x = xt;
		with.y = yt;

		return ret;
	}

	/**
	 * Load an image from ./res/graphics/ and assign it to this tile
	 * @param sterrain Path relative to ./res/graphics/tiles/terrain/ with the trailing .png
	 */
	public void assignTerrain(String sterrain) {
		terrain = level.getEngine().loadImage("tiles/terrain/" + sterrain);
	}

	/**
	 * Load an image from ./res/graphics/ and assign this as an overlay to the tile
	 * @param overlay Path relative to ./res/graphics/tiles/decoration/ with the trailing .png
	 */
	public void assignOverlay(String overlay) {
		this.overlay = level.getEngine().loadImage("tiles/decoration/" + overlay);
	}


	/**
	 * Draw the tile with the assigned terrain
	 * @param g Graphics reference
	 */
	public void render(Graphics g) {
		if(terrain != null) {
			g.drawImage(terrain,  x * Engine.SPRITE_SIZE + backgroundOffsetX, y * Engine.SPRITE_SIZE  + backgroundOffsetY);
		}
		if(isHighligthed){
			g.setColor(new Color(255,0,0,0.5f));
			g.drawRect(x * Engine.SPRITE_SIZE + backgroundOffsetX, y * Engine.SPRITE_SIZE  + backgroundOffsetY, Engine.SPRITE_SIZE, Engine.SPRITE_SIZE);
		}
	}

	/**
	 * Draw a tile overlay. This function is called after every other tile has finished rendering their backgrounds
	 * @param g Graphics reference
	 */
	public void renderOverlay(Graphics g) {
		if(overlay != null) {
			g.drawImage(overlay, x * Engine.SPRITE_SIZE + overlayOffsetX, y * Engine.SPRITE_SIZE + overlayOffsetY);
		}
	}

}
