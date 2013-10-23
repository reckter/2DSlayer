package me.reckter.Level.Generation;

import me.reckter.Level.BaseLevel;
import me.reckter.Level.Tiles.BaseTile;
import me.reckter.Level.Tiles.GrassTile;
import me.reckter.Level.Tiles.Spike;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseGeneration {


	public BaseTile generateTile(BaseLevel level, int x, int y){
		if(Math.random() < 0.1){
			return new Spike(level, x, y);
		}
		return new GrassTile(level, x, y);
	}
}
