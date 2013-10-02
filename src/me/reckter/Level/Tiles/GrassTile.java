package me.reckter.Level.Tiles;

import me.reckter.Level.BaseLevel;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class GrassTile extends PassableTile {

	public GrassTile(BaseLevel level, int x, int y) {
		super(level, x, y);
		this.assignTerrain("fantasy/grass");
		if(Math.random() <= 0.01) {
			assignOverlay("fantasy/grass");
		}
	}
}
