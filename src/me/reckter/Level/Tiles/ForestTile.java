package me.reckter.Level.Tiles;

import me.reckter.Level.BaseLevel;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForestTile extends BaseTile {
	public ForestTile(BaseLevel level, int x, int y) {
		super(level, x, y);
		assignTerrain("fantasy/grass");
		assignOverlay("fantasy/forest");
	}
}
