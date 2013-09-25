package me.reckter.Level;

import me.reckter.Engine.Engine;
import me.reckter.Entity.BaseEntity;
import me.reckter.Level.Generation.BasicGeneration;
import me.reckter.Level.Tiles.Tileshandler;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseLevel implements TileBasedMap{
	protected Engine engine;

	protected long seed;
	protected Random random;

	protected Tileshandler tiles;
	protected BasicGeneration generation;
	protected int loadingRadius;

	public BaseLevel(Engine engine){
		this.engine = engine;
	}

	public BaseLevel(Engine engine, BasicGeneration generation, int loadingRadius){

		this.engine = engine;
		this.generation = generation;
		this.loadingRadius = loadingRadius;

		tiles = new Tileshandler(generation, this);
		tiles.manageTiles(0, 0, loadingRadius);
	}


	public void logic(int delta) {
		tiles.manageTiles(0, 0, loadingRadius);
	}


	public void render(Graphics g)
	{
		tiles.render(g);
	}

	public Engine getEngine(){
		return engine;
	}


	@Override
	public int getWidthInTiles() {
		return loadingRadius;
	}

	@Override
	public int getHeightInTiles() {
		return loadingRadius;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
	}

	@Override
	public boolean blocked(PathFindingContext pathFindingContext, int x, int y) {
		Mover mover = pathFindingContext.getMover();
		if(mover instanceof BaseEntity){
			return ((BaseEntity) mover).wouldColide(tiles.getTile(x, y)) && tiles.getTile(x, y).wouldColide((BaseEntity) mover);
		}

		return false;
	}

	@Override
	public float getCost(PathFindingContext pathFindingContext, int x, int y) {
		return 1;
	}
}
