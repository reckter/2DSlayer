package me.reckter.Pathfinding;

import me.reckter.Level.Tiles.BaseTile;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/4/13
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class Tile implements Comparable<Tile>{
	protected BaseTile tile;
	protected int costs;

	public Tile(BaseTile tile, int costs){
		this.tile = tile;
		this.costs = costs;
	}

	public int getCosts(){
		return costs;
	}

	@Override
	public int compareTo(Tile o) {
		if(o.costs < costs){
			return -1;
		} else if(o.costs > costs){
			return 1;
		}
		return 0;
	}
}
