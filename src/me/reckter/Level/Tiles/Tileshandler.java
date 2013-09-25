package me.reckter.Level.Tiles;

import me.reckter.Level.BaseLevel;
import me.reckter.Level.Generation.BasicGeneration;
import org.newdawn.slick.Graphics;

import java.awt.font.GraphicAttribute;
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
public class Tileshandler {
	protected HashMap<String, BaseTile> tiles;
	protected BasicGeneration generation;
	protected BaseLevel level;

	public Tileshandler(BasicGeneration generation, BaseLevel level){
		tiles = new HashMap<String, BaseTile>();
		this.level = level;

		this.generation = generation;
	}

	public void addTile(int x, int y, BaseTile tile){
		tiles.put(getKey(x, y), tile);
	}

	public BaseTile getTile(int x, int y){
		return tiles.get(getKey(x, y));
	}

	private String getKey(int x, int y){
		return x + "|" + y;
	}

	public void manageTiles(int x, int y, int radius){
		HashMap<String, BaseTile> temp = new HashMap<String, BaseTile>();

		for(int x1 = x - radius; x1 <= x + radius; x1++){
			for(int y1 = y - radius; y1 <= y + radius; y1++){

				BaseTile tile = tiles.get(getKey(x1, y1));

				if(tile == null){
					temp.put(getKey(x1, y1), generation.generateTile(level, x1, y1));
				} else {
					temp.put(getKey(x1,y1), tile);
				}

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
