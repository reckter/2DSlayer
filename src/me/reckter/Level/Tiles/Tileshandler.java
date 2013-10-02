package me.reckter.Level.Tiles;

import me.reckter.Level.BaseLevel;
import me.reckter.Level.Generation.BaseGeneration;
import org.newdawn.slick.Graphics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class TilesHandler {
	protected HashMap<String, BaseTile> tiles;
	protected BaseGeneration generation;
	protected BaseLevel level;

	public TilesHandler(BaseGeneration generation, BaseLevel level){
		tiles = new HashMap<String, BaseTile>();
		this.level = level;

		this.generation = generation;
	}

	public void addTile(int x, int y, BaseTile tile){
		tiles.put(getKey(x, y), tile);
	}

	public BaseTile getTile(int x, int y){
		BaseTile tmp = tiles.get(getKey(x, y));
		if(tmp == null){
			return loadTile(x ,y);
	    }
		return tmp;
	}

	private String getKey(int x, int y){
		return x + "|" + y;
	}

	public BaseTile loadTile(int x, int y){
		BaseTile temp = generation.generateTile(level, x, y);
		tiles.put(getKey(x, y), temp);
		return temp;
	}

	public void manageTiles(int x, int y, int radiusX, int radiusY){
		HashMap<String, BaseTile> temp = new HashMap<String, BaseTile>();

		for(int x1 = x - radiusX; x1 <= x + radiusX; x1++){
			for(int y1 = y - radiusY; y1 <= y + radiusY; y1++){

				BaseTile tile = getTile(x1, y1);

				temp.put(getKey(x1, y1), tile);

			}
		}
		tiles = temp;
	}

	public void render(Graphics g){
		Iterator<Map.Entry<String,BaseTile>> iterator = tiles.entrySet().iterator();
		while(iterator.hasNext()){
			iterator.next().getValue().render(g);
		}
		iterator = tiles.entrySet().iterator();

		while(iterator.hasNext()){
			iterator.next().getValue().renderOverlay(g);
		}
	}
}
