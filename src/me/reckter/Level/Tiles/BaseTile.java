package me.reckter.Level.Tiles;

import me.reckter.Engine.Engine;
import me.reckter.Entity.BaseEntity;
import me.reckter.Level.BaseLevel;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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

	public BaseTile(BaseLevel level, int x, int y){
		this.level = level;
		this.x = x;
		this.y = y;
	}

	public boolean wouldColide(BaseEntity with){
		return false;
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
