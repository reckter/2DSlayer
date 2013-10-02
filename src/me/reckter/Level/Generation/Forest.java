package me.reckter.Level.Generation;

import me.reckter.Level.BaseLevel;
import me.reckter.Level.Tiles.BaseTile;
import me.reckter.Level.Tiles.ForestTile;
import me.reckter.Level.Tiles.GrassTile;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Forest extends BaseGeneration {

	@Override
	public BaseTile generateTile(BaseLevel level, int x, int y) {
		if((Math.sin(x) / Math.sin(y)) + (Math.sin(Math.random() * 1000) * Math.sin(x)) < 0 ){
			return new  GrassTile(level, x, y);
		} else {
			return new ForestTile(level, x, y);
		}
	}
}
