package me.reckter.Pathfinding;

import me.reckter.Level.Tiles.BaseTile;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/4/13
 * Time: 1:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class Path {
	protected ArrayList<BaseTile> tiles;

	public Path(ArrayList<BaseTile> tiles){
		this.tiles = tiles;
	}

	public BaseTile getStep(int i){
		return tiles.get(i);
	}

	public BaseTile getNextStep(BaseTile currentTile){
		for(int i = 0; i < tiles.size() - 1; i++){
			if(currentTile == tiles.get(i)){
				return tiles.get(i + 1);
			}
		}
		return tiles.get(tiles.size() - 1);
	}
}
